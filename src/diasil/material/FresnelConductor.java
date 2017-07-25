package diasil.material;

import diasil.color.SpectralDistribution;

public class FresnelConductor implements FresnelEquation
{
	private float eta, k;
	public FresnelConductor(float eta, float k)
	{
		this.eta = eta;
		this.k = k;
	}
	public float evaluate(float cosi, float wavelength)
	{
		return BSDF.FresnelConductive(Math.abs(cosi), eta, k);
	}
}
