package diasil.material;

import diasil.math.geometry3.Vector3;

public class BSDFSample
{
	public Vector3 Wi;
	public float f;
	public float pdf;
	public BSDFSample(Vector3 wi, float reflectance, float pdf)
	{
		Wi = wi;
		this.f = reflectance;
		this.pdf = pdf;
	}
}
