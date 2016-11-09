package diasil.sample;

import diasil.math.geometry2.Point2;

public abstract class Filter
{
	public final float radius;
	public Filter(float r)
	{
		radius = r;
	}
	
	// s.X and s.Y may be altered for importance sampling
	public abstract void weight(Sample s);
	public abstract Filter clone();
}
