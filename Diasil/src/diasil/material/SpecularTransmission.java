package diasil.material;

import diasil.color.SPD;
import diasil.math.geometry3.Vector3;

public class SpecularTransmission extends ReflectionModel
{
	private SPD color;
	private float eta_i, eta_t;
	private FresnelDialectric fresnel;
	public SpecularTransmission(SPD color, float eta_i, float eta_t)
	{
		this.color = color;
		this.eta_i = eta_i;
		this.eta_t = eta_t;
		fresnel = new FresnelDialectric(eta_i, eta_t);
	}
	public boolean isDelta()
	{
		return true;
	}
	public float evaluate(Vector3 wo, Vector3 wi, float wavelength)
	{
		return 0.0f;
	}
	public BSDFSample sample(BSDFSample wo, float u, float v)
	{
		float ei, et;
		boolean entering = CosTheta(wo) > 0.0f;
		if (entering)
		{
			ei = eta_i;
			et = eta_t;
		}
		else
		{
			ei = eta_t;
			et = eta_i;
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
		float f = fresnel.evaluate(CosTheta(wo));
		float c = color.evaluate(wo.wavelength);
		float reflectance = (et*et)/(ei*ei)*(1.0f-f)*c/AbsCosTheta(wi);
		return new BSDFSample(wi, wo.wavelength, reflectance, pdf);		
	}
}
