package diasil.light;

import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Point3;

public interface Light
{
	public abstract LightSample sampleL(Point3 pw, float wavelength, float u, float v);
}
