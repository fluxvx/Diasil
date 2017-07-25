package diasil.material;

import diasil.math.geometry3.Vector3;

public class BSDFSample
{
	//public Point3 P;
	//public float wavelength;
	//public float uC, u1, u2;
	
	public Vector3 Wi;
	public float f;
	public float pdf;
	
	public BSDFSample(Vector3 Wi, float f, float pdf)
	{
		this.Wi = Wi;
		this.f = f;
		this.pdf = pdf;
	}
	
	/*public void returnValues(Vector3 wi, float reflectance, float pdf)
	{
		Wi = wi;
		this.f = reflectance;
		this.pdf = pdf;
	}*/
}
