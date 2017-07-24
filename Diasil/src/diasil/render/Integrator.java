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
	
	
	
	
	public float EstimateDirect(Light light)
	{
		// sample light source
		
		// sample BSDF
		
		//LightSample ls = light.sampleL(pw, 0, 0, 0)
		return 0.0f;
	}
}
