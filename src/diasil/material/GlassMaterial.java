package diasil.material;

import diasil.color.SpectralDistribution;

public class GlassMaterial extends Material
{
	public GlassMaterial(SpectralDistribution R, SpectralDistribution T, SpectralDistribution eta)
	{
		super(2);
		bsdfs[0] = new SpecularReflection(R, new FresnelDielectric(SpectralDistribution.constant(1.0f), eta));
		bsdfs[1] = new SpecularTransmission(T, SpectralDistribution.constant(1.0f), eta);
	}
}
