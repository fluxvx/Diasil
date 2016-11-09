package diasil.render;

import diasil.sample.Sample;
import diasil.sample.SampleCollector;

public interface Renderer
{
	public void prepareForRendering();
	public void collectSampleCounts(SampleCollector sc);
	public void takeSample(Sample sample);
}
