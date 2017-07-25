package diasil.color;

import diasil.sample.Sample;

public interface Film
{
	public int width();
	public int height();
	public void recordContribution(int i, int j, int k, Sample sample);
}
