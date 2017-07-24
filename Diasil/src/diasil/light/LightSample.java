package diasil.light;

import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sampler;

public class LightSample
{
	//public Point3 P;
	//public float wavelength;
	//public float uL, u1, u2;
	
	public Vector3 Wi; // vector pointing in the direction of the light
	public float Li; // intensity of the light received
	public float distance; // distance to the light
	
	public LightSample(Vector3 Wi, float Li, float distance)
	{
		this.Wi = Wi;
		this.Li = Li;
		this.distance = distance;
	}
	
	/*public LightSample(Point3 p, float wavelength, float uL, float u1, float u2)
	{
		this.P = p;
		this.wavelength = wavelength;
		this.uL = uL;
		this.u1 = u1;
		this.u2 = u2;
		
		Wi = null;
		Li = Float.NaN;
		distance = Float.NaN;
	}
	
	public LightSample(Point3 p, float wavelength, Sampler sampler)
	{
		this.P = p;
		this.wavelength = wavelength;
		uL = sampler.nextFloat();
		u1 = sampler.nextFloat();
		u2 = sampler.nextFloat();
		
		Wi = null;
		Li = Float.NaN;
		distance = Float.NaN;
	}
	
	public void returnValues(Vector3 Wi, float Li, float distance)
	{
		this.Wi = Wi;
		this.Li = Li;
		this.distance = distance;
	}*/
}
