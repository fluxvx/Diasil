package diasil.sample;

import diasil.math.geometry2.Point2;

public class FilterMitchell extends Filter
{
	private float B, C;
	public FilterMitchell(float radius, float b, float c)
	{
		super(radius);
		B = b;
		C = c;
	}
	
	public void weight(Sample s)
	{
		s.filter_weight = Mitchell1D(s.X/radius)*Mitchell1D(s.Y/radius);
	}
	
	private float Mitchell1D(float x)
	{
		x = Math.abs(2.0f * x);
		if (x > 1.0f)
		{
			return ((-B - 6*C) * x*x*x + (6*B + 30*C) * x*x +
					(-12*B - 48*C) * x + (8*B + 24*C)) * (1.0f/6.0f);
		}
		return ((12 - 9*B - 6*C) * x*x*x +
				(-18 + 12*B + 6*C) * x*x +
				(6 - 2*B)) * (1.0f/6.0f);
	}
	public Filter clone()
	{
		return new FilterMitchell(radius, B, C);
	}
}
