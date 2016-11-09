package diasil.material;

import diasil.math.geometry3.Vector3;

public class BSDFSample extends Vector3
{
	public float wavelength;
	public float reflectance;
	public float pdf;
	public BSDFSample(Vector3 v, float wavelength, float reflectance, float pdf)
	{
		super(v);
		this.wavelength = wavelength;
		this.reflectance = reflectance;
		this.pdf = pdf;
	}
}
