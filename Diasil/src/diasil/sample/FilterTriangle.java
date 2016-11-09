package diasil.sample;

import diasil.math.geometry2.Point2;

public class FilterTriangle extends Filter
{
	public FilterTriangle(float radius)
	{
		super(radius);
	}
	public void weight(Sample s)
	{
		s.filter_weight = Math.max(0.0f, 1.0f-Math.abs(s.X))
				* Math.max(0.0f, 1.0f - Math.abs(s.Y));
	}
	public Filter clone()
	{
		return new FilterTriangle(radius);
	}
}
