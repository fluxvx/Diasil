package diasil.sample;

import diasil.math.geometry2.Point2;

public class FilterLanczosSinc extends Filter
{
	private float tau;
	public FilterLanczosSinc(float radius, float tau)
	{
		super(radius);
		this.tau = tau;
	}
	private float Sinc1D(float x)
	{
		x = Math.abs(x);
		if (x < 1.0E-5) return 1.0f;
		if (x > 1.0f) return 0.0f;
		x *= (float)(Math.PI);
		float sinc = (float)Math.sin(x * tau) / (x * tau);
		float lanczos = (float)Math.sin(x) / x;
		return sinc * lanczos;
	}
	public void weight(Sample s)
	{
		s.filter_weight = Sinc1D(s.X/radius)*Sinc1D(s.Y/radius);
	}
	public Filter clone()
	{
		return new FilterLanczosSinc(radius, tau);
	}

}
