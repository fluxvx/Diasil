	package diasil.render;

import diasil.Scene;
import diasil.camera.Camera;
import diasil.intersect.Intersection;
import diasil.intersect.SurfaceGeometry;
import diasil.light.AreaLight;
import diasil.light.Light;
import diasil.light.LightSample;
import diasil.material.BSDF;
import diasil.material.BSDFSample;
import diasil.material.LightRay;
import diasil.material.Material;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sample;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;

public class PathTracer extends Integrator
{
	private Scene scene;
	private Camera camera;
	private int max_depth = 6;
	public PathTracer(Scene scene)
	{
		this.scene = scene;
	}
	public void prepareForRendering(SampleCollector sc)
	{
		camera = scene.getCurrentCamera();
		/*for (int i=0; i<max_depth; ++i)
		{
			sc.addSamples1D(1); // bsdf component
			sc.addSamples2D(1); // bsdf uv
			//sc.addSamples1D(1); // light
			//sc.addSamples2D(1); // light uv
		}*/
	}
    public void takeSample(Sample sample, Sampler sampler)
    {
        Ray3 ray = camera.generateRay(sample);
        LightRay light_ray = new LightRay(ray, sample.wavelength);
        sample.L = traceRay(light_ray, sample, sampler, 0);
    }
	
	public float traceRay(LightRay rw, Sample sample, Sampler sampler, int depth)
	{
		Intersection it = new Intersection();
		scene.aggregate.closestIntersection(rw, it);
		if (!it.success())
		{
			return 0.0f;
		}
		
		Point3 p = rw.pointAt(it.T);
        
		if (it.E instanceof AreaLight)
		{
			AreaLight light = (AreaLight)(it.E);
			//if (depth == 0) return 0.0f;
			return light.power(p, rw.wavelength);
		}
		
		if (depth >= max_depth)
		{
			return 0.0f;
		}
		
		Vector3 wo = new Vector3(-rw.D.X, -rw.D.Y, -rw.D.Z);
		SurfaceGeometry sg = it.getSurfaceGeometry();
		if (sg.N.dot(rw.D) > 0)
		{
			sg.N.negate();
		}
		Material material = it.E.getMaterial();
		
		
		float L = 0.0f;
		/*for (int i=0; i<scene.lights.size(); ++i)
		{
			Light light = scene.lights.get(i);
			float ud1 = sampler.nextFloat();
			float ud2 = sampler.nextFloat();
			LightSample light_sample = light.sampleL(p, rw.wavelength, ud1, ud2);
			Ray3 light_test = new Ray3(p, light_sample.Wi);
			Intersection it_lt = new Intersection();
			it_lt.T = light_sample.distance - Intersection.MIN_T;
			if (!scene.aggregate.isBlocked(light_test, it_lt))
			{
				float Li = light_sample.Li;
				float dot = Math.abs(sg.N.dot(light_sample.Wi));
				float f = material.f(wo, light_sample.Wi, rw.wavelength, BSDF.DIFFUSE | BSDF.REFLECTION, sg);
				L += Li*dot*f;
			}
		}*/
		
		//float bsdf_c = sample.samples1D[depth];
		//float bsdf_u = sample.samples2D[(depth<<1)];
		//float bsdf_v = sample.samples2D[(depth<<1)+1];
		BSDFSample bsdf_sample = material.samplef(wo, rw.wavelength, sampler.nextFloat(), sampler.nextFloat(), sampler.nextFloat(), BSDF.ALL, sg);
		if (bsdf_sample == null)
		{
			return 0.0f;
		}
		
		Vector3 wi = bsdf_sample.Wi;
		LightRay next_ray = new LightRay(p, bsdf_sample.Wi, rw.wavelength);
		next_ray.D.normalize();
		float f = bsdf_sample.f;
		float pdf = bsdf_sample.pdf;
		float dot = Math.abs(bsdf_sample.Wi.dot(sg.N));
		if (pdf > 0.0f && f != 0.0f && dot != 0.0f)
		{
			float Li = traceRay(next_ray, sample, sampler, depth+1);
			L += f*Li*dot/pdf;
		}
		return L;
	}
}
