package diasil.light;

import diasil.color.SpectralDistribution;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Vector3;

public class PointLight extends CoordinateSpace3 implements Light
{
	public Point3 ow;
	public SpectralDistribution power;
	public PointLight(SpectralDistribution intensity)
	{
		this.power = intensity;
		ow = null;
	}
	public void compileTransforms()
	{
		super.compileTransforms();
		ow = super.toWorldSpace(new Point3(0.0f, 0.0f, 0.0f));
	}
	public LightSample sampleL(Point3 pw, float wavelength, float u, float v)
	{
		Vector3 wi = new Vector3(pw, ow);
		float d2 = wi.lengthSquared();
		float d = (float)Math.sqrt(d2);
		wi.multiplyBy(1.0f/d);
		float Li = power.evaluate(wavelength)/d2;
		return new LightSample(wi, Li, d);
	}
}
