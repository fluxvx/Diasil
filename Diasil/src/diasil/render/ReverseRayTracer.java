package diasil.render;

import diasil.Scene;
import diasil.camera.Camera;
import diasil.intersect.Intersection;
import diasil.intersect.SurfaceGeometry;
import diasil.light.Light;
import diasil.light.LightSample;
import diasil.material.BSDFSample;
import diasil.material.LightRay;
import diasil.material.Material;
import diasil.material.BSDF;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sample;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;
import java.util.SplittableRandom;

public class ReverseRayTracer extends Integrator
{
	private Scene scene;
	private Camera camera;
	private int max_depth = 6;
	public ReverseRayTracer(Scene scene)
	{
		this.scene = scene;
	}
	
	public void prepareForRendering(SampleCollector sc)
	{
		camera = scene.getCurrentCamera();
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
		Vector3 wo = new Vector3(-rw.D.X, -rw.D.Y, -rw.D.Z);
		
		SurfaceGeometry sg = it.getSurfaceGeometry();
		if (sg.N.dot(rw.D) > 0)
		{
			sg.N.negate();
		}
        Material material = it.E.getMaterial();
		
		float L = 0.0f;
		for (int i=0; i<scene.lights.size(); ++i)
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
		}
		
		if (depth+1 < max_depth)
		{
			L += scatter(p, wo, sample, it, sg, rw.wavelength, sampler, BSDF.REFLECTION | BSDF.SPECULAR, depth);
			L += scatter(p, wo, sample, it, sg, rw.wavelength, sampler, BSDF.TRANSMISSION | BSDF.SPECULAR, depth);
		}
		return L;
	}
	
	public float scatter(Point3 p, Vector3 wo, Sample sample, Intersection it, SurfaceGeometry sg, float wavelength, Sampler sampler, int flags, int depth)
	{
		Material m = it.E.getMaterial();
		float uc = sampler.nextFloat();
		float ud1 = sampler.nextFloat();
		float ud2 = sampler.nextFloat();
		BSDFSample bsdf_sample = m.samplef(wo, wavelength, uc, ud1, ud2, flags, sg);
		if (bsdf_sample != null)
		{
			LightRay next_ray = new LightRay(p, bsdf_sample.Wi, wavelength);
			next_ray.D.normalize();
			float f = bsdf_sample.f;
			float pdf = bsdf_sample.pdf;
			float dot = Math.abs(bsdf_sample.Wi.dot(sg.N));
			if (pdf > 0.0f && f != 0.0f && dot != 0.0f)
			{
				float Li = traceRay(next_ray, sample, sampler, depth+1);
				return f*Li*dot/pdf;
			}
		}
		return 0.0f;
	}
}
