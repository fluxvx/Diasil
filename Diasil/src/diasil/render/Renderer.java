package diasil.render;

import diasil.sample.Sample;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;
import java.util.SplittableRandom;

public abstract class Renderer
{
	public abstract void prepareForRendering(SampleCollector sc);
	public abstract void takeSample(Sample sample, Sampler sampler);
}
