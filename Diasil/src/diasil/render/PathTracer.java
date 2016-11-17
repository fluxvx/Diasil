	package diasil.render;

import diasil.sample.Sample;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;

public class PathTracer extends Integrator
{
	private int max_depth = 6;
	public PathTracer()
	{
		
	}
	public void prepareForRendering(SampleCollector sc)
	{
		for (int i=0; i<max_depth; ++i)
		{
			sc.addSamples1D(1); // bsdf component
			sc.addSamples2D(1); // bsdf uv
			sc.addSamples1D(1); // light
			sc.addSamples2D(1); // light uv
		}
	}
	public void takeSample(Sample sample, Sampler sampler)
	{
		
	}
}
