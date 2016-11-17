package diasil.render;

import diasil.light.Light;
import diasil.light.LightSample;
import diasil.math.geometry3.Point3;
import diasil.sample.Sample;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;
import java.util.SplittableRandom;

public abstract class Integrator
{
	public abstract void prepareForRendering(SampleCollector sc);
	public abstract void takeSample(Sample sample, Sampler sampler);
	
	
	/*public static float UniformSampleOneLight()
	{
		// pick light, surface uv
		// pick bsdf component sample, bsdf uv
		// invoke estimatedirect
		
	}
	public static float EstimateDirect(Point3 p, Light light)
	{
		LightSample ls = light.sam
	}*/
}
