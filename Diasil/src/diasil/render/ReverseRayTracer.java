package diasil.render;

import diasil.Scene;
import diasil.camera.Camera;
import diasil.color.SimpleFilm;
import diasil.intersect.Intersection;
import diasil.intersect.SurfaceGeometry;
import diasil.light.Light;
import diasil.light.LightSample;
import diasil.material.LightRay;
import diasil.material.Material;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sample;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;

public class ReverseRayTracer implements Renderer
{
	private Scene scene;
	private Camera camera;
	private SimpleFilm film;
	public ReverseRayTracer(Scene scene)
	{
		this.scene = scene;
	}
	
	public void prepareForRendering()
	{
		camera = scene.getCurrentCamera();
	}
	
    public void takeSample(Sample sample)
    {
        Ray3 ray = camera.generateRay(sample);
        LightRay light_ray = new LightRay(ray, sample.wavelength, 1.0f, scene.refractive_index);
        sample.intensity = traceRay(light_ray, 0);
    }
	
	public float traceRay(LightRay rw, int depth)
	{
		Intersection it = scene.aggregate.getIntersection(rw);
		if (it == null)
		{
			return 0.0f;
		}
		
		SurfaceGeometry sg = it.getSurfaceGeometry();
		if (sg.N.dot(rw.D) > 0)
		{
			sg.N.negate();
		}
        Material material = it.E.getMaterial();
		
		// if the material is a light, use the brightness
		// if material is a delta distribution, trace another ray
		// otherwise find the contribution from a light, scale it by the material's brdf
		
		float intensity = 0.0f;
		for (int i=0; i<scene.lights.size(); ++i)
		{
			Light light = scene.lights.get(i);
			LightSample light_sample = light.evaluate(it.P, rw.wavelength);
			Ray3 light_test = new Ray3(light_sample.pw_light, light_sample.pw_surface);
			light_test.D.normalize();
			if (!scene.aggregate.isBlocked(light_test, light_sample.distance))
			{
				intensity += light_sample.intensity
								*sg.N.dot(light_sample.Wi)
								*material.evaluate(it.Wo, light_sample.Wi, rw.wavelength, sg);
			}
		}
		return intensity;
	}
	
	public void collectSampleCounts(SampleCollector sc)
	{
	}


}
