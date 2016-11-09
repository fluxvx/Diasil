package diasil.light;

import diasil.material.LightRay;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Point3;

public abstract class Light extends CoordinateSpace3
{
	public Light() {}
	public abstract LightSample evaluate(Point3 pw, float wavelength);
}
