package diasil.light;

import diasil.math.geometry3.Point3;

public interface Light
{
	public LightSample sampleL(Point3 pw, float wavelength, float u, float v);
}
