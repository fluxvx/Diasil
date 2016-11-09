package diasil.material;

import diasil.color.SPD;
import diasil.math.geometry3.Vector3;

public class LambertianReflection extends ReflectionModel
{
	private SPD R;
	public LambertianReflection(SPD R)
	{
		this.R = R;
	}
	public boolean isDelta()
	{
		return false;
	}
	public float evaluate(Vector3 wo, Vector3 wi, float wavelength)
	{
		return R.evaluate(wavelength)/(float)Math.PI;
	}
}
