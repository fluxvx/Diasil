package diasil.material;

import diasil.color.SpectralDistribution;
import diasil.math.geometry3.Vector3;

public class SpecularReflection extends BSDF
{
	private SpectralDistribution R;
	private FresnelEquation fresnel;
	public SpecularReflection(SpectralDistribution color, FresnelEquation fresnel)
	{
		super(REFLECTION | SPECULAR);
		this.R = color;
		this.fresnel = fresnel;
	}
	public float f(Vector3 wo, Vector3 wi, float wavelength)
	{
		return 0.0f;
	}
	public float pdf(Vector3 wo, Vector3 wi)
	{
		return 0.0f;
	}
	public BSDFSample samplef(Vector3 wo, float u, float v, float wavelength)
	{
		Vector3 wi = new Vector3(-wo.X, -wo.Y, wo.Z);	
		float f = fresnel.evaluate(CosTheta(wo), wavelength)*R.evaluate(wavelength)/AbsCosTheta(wi);
		if (Float.isNaN(f) || Float.isInfinite(f))
		{
			f = 0.0f;
			//System.out.println("SR1 "+wo.X+" "+wo.Y+" "+wo.Z);
			//System.out.println("SR2 "+wo.X+" "+wo.Y+" "+wo.Z);
			//System.out.println("SR3 "+fresnel.evaluate(CosTheta(wo), wavelength)+" "+R.evaluate(wavelength)+" "+AbsCosTheta(wi));
		}
		float pdf = 1.0f;
		return new BSDFSample(wi, f, pdf);
	}
}
