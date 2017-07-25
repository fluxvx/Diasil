package diasil.light;

import diasil.intersect.Intersectable;
import diasil.math.geometry3.Point3;

public interface AreaLight extends Light, Intersectable
{
	public float power(Point3 pw, float wavelength);
}
