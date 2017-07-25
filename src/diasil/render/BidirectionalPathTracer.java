package diasil.render;

import diasil.sample.Sample;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;

public class BidirectionalPathTracer extends Integrator
{
	public void prepareForRendering(SampleCollector sc)
	{
	}
	public void takeSample(Sample sample, Sampler sampler)
	{
		// sample light surface point and direction, create path
		// create path from camera into scene
		// combine paths
	}
}
