package diasil.material;

import diasil.color.SpectralDistribution;

public class DiffuseMaterial extends Material
{
	public DiffuseMaterial(SpectralDistribution r, float sigma)
	{
		super(1);
		if (sigma == 0.0f)
		{
			bsdfs[0] = new LambertianReflection(r);
		}
		else
		{
			bsdfs[0] = new OrenNayarReflection(r, sigma);
		}
	}
}