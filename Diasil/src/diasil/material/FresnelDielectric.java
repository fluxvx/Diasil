package diasil.material;

import diasil.color.SpectralDistribution;
import diasil.math.DMath;

public class FresnelDielectric implements FresnelEquation
{
	private SpectralDistribution eta_i, eta_t;
	public FresnelDielectric(SpectralDistribution eta_i, SpectralDistribution eta_t)
	{
		this.eta_i = eta_i;
		this.eta_t = eta_t;
	}
	public float evaluate(float cosi, float wavelength)
	{
		cosi = DMath.clamp(cosi, -1.0f, 1.0f);
		float ei = eta_i.evaluate(wavelength);
		float et = eta_t.evaluate(wavelength);
		if (cosi < 0.0f) // leaving
		{
			float t = ei;
			ei = et;
			et = t;
		}
		float sint = ei/et * (float)Math.sqrt(Math.max(0.0f, 1.0f-cosi*cosi));
		if (sint >= 1.0f)
		{
			return 1.0f; // total internal reflection
		}
		else
		{
			float cost = (float)Math.sqrt(Math.max(0.0f, 1.0f-sint*sint));
			return BSDF.FresnelDielectric(Math.abs(cosi), cost, ei, et);
		}
	}
}
