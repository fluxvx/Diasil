package diasil.material;

import diasil.color.SPD;
import diasil.math.geometry3.Vector3;

public class SpecularReflection extends ReflectionModel
{
	private SPD color;
	private FresnelReflection fresnel;
	public SpecularReflection(SPD color, FresnelReflection fresnel)
	{
		this.color = color;
		this.fresnel = fresnel;
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
		Vector3 wi = new Vector3(-wo.X, -wo.Y, wo.Z);
		float wavelength = wo.wavelength;
		float reflectance = fresnel.evaluate(CosTheta(wo))*color.evaluate(wo.wavelength)/AbsCosTheta(wi);
		float pdf = 1.0f;
		return new BSDFSample(wi, wavelength, reflectance, pdf);
	}

}
