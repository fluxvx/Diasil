package diasil.light;

import diasil.color.SPD;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Vector3;

public class PointLight extends Light
{
	public Point3 ow;
	public SPD intensity;
	public PointLight(SPD intensity)
	{
		this.intensity = intensity;
		ow = null;
	}
	public void compileTransforms()
	{
		super.compileTransforms();
		ow = super.toWorldSpace(new Point3(0.0f, 0.0f, 0.0f));
	}
	public LightSample evaluate(Point3 pw, float wavelength)
	{
		Vector3 v = new Vector3(pw, ow);
		float d2 = v.lengthSquared();
		float d = (float)Math.sqrt(d2);
		float r_int = intensity.evaluate(wavelength)/d2;
		return new LightSample(pw, ow, r_int, v, d);
	}
}
