package diasil.material;

import diasil.color.SpectralDistribution;
import diasil.math.geometry3.Vector3;

public class LambertianReflection extends BSDF
{
	private SpectralDistribution R;
	public LambertianReflection(SpectralDistribution R)
	{
		super(REFLECTION | DIFFUSE);
		this.R = R;
	}
	public float f(Vector3 wo, Vector3 wi, float wavelength)
	{
		return R.evaluate(wavelength)/(float)Math.PI;
	}
}
