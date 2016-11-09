package diasil.material;

import diasil.color.SPD;

public class DiffuseMaterial extends Material
{
	public DiffuseMaterial(SPD r, float sigma)
	{
		super(1);
		if (sigma == 0.0f)
		{
			bxdfs[0] = new LambertianReflection(r);
		}
		else
		{
			bxdfs[0] = new OrenNayarReflection(r, sigma);
		}
	}
}