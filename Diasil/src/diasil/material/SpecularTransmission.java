package diasil.material;

import diasil.color.SpectralDistribution;
import diasil.math.geometry3.Vector3;

public class SpecularTransmission extends BSDF
{
	private SpectralDistribution color;
	private SpectralDistribution eta_i, eta_t;
	private FresnelDielectric fresnel;
	public SpecularTransmission(SpectralDistribution color, SpectralDistribution eta_i, SpectralDistribution eta_t)
	{
		super(TRANSMISSION | SPECULAR);
		this.color = color;
		this.eta_i = eta_i;
		this.eta_t = eta_t;
		fresnel = new FresnelDielectric(eta_i, eta_t);
	}
	public float f(Vector3 wo, Vector3 wi, float wavelength)
	{
		return 0.0f;
	}
	public BSDFSample samplef(Vector3 wo, float u, float v, float wavelength)
	{
		float ei = eta_i.evaluate(wavelength);
		float et = eta_t.evaluate(wavelength);
		boolean entering = CosTheta(wo) > 0.0f;
		if (!entering)
		{
			float t = ei;
			ei = et;
			et = t;
		}
		float sini2 = SinTheta2(wo);
		float eta = ei/et;
		float sint2 = eta*eta*sini2;
		if (sint2 > 1.0f) return null;
		float cost = (float)Math.sqrt(Math.max(0.0f, 1.0f-sint2));
		if (entering) cost = -cost;
		float sint_over_sini = eta;
		Vector3 wi = new Vector3(sint_over_sini*-wo.X, sint_over_sini*-wo.Y, cost);
		float pdf = 1.0f;
		float f = fresnel.evaluate(CosTheta(wo), wavelength);
		float c = color.evaluate(wavelength);
		float reflectance = (1.0f-f)*c/AbsCosTheta(wi);
		return new BSDFSample(wi, reflectance, pdf);		
	}
}
