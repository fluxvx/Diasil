package diasil.sample;

import diasil.math.geometry2.Point2;

public class FilterGaussian extends Filter
{
	private float alpha, expr;
	public FilterGaussian(float radius, float alpha)
	{
		super(radius);
		this.alpha = alpha;
		this.expr = (float)Math.exp(-alpha*radius*radius);
	}
	private float gaussian(float d)
	{
		return Math.max(0.0f, (float)Math.exp(-alpha*d*d)-expr);
	}
	public void weight(Sample s)
	{
		s.filter_weight = gaussian(s.X)*gaussian(s.Y);
	}
	public Filter clone()
	{
		return new FilterGaussian(radius, alpha);
	}
}
