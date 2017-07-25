package diasil.light;

import diasil.color.SpectralDistribution;
import diasil.math.DMath;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Vector3;

public class HemisphereLight implements Light
{
	private SpectralDistribution power;
	public HemisphereLight(SpectralDistribution power)
	{
		this.power = power;
	}
	public LightSample sampleL(Point3 pw, float wavelength, float u, float v)
	{
		float theta = u*DMath.PI2;
		float phi = v*DMath.PI/2.0f;
		
		float x = DMath.sin(phi)*DMath.sin(theta);
		float y = DMath.cos(phi);
		float z = DMath.sin(phi)*DMath.cos(theta);
		Vector3 wi = new Vector3(x, y, z);
		float Li = power.evaluate(wavelength);
		float d = Float.MAX_VALUE;
		
		return new LightSample(wi, power.evaluate(wavelength), Float.MAX_VALUE);
	}

}
