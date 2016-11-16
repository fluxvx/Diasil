package diasil.light;

import diasil.math.geometry3.Vector3;

public class LightSample
{
	public Vector3 Wi; // vector pointing in the direction of the light
	public float Li; // intensity of the light received
	public float distance; // distance to the light
	public LightSample(Vector3 Wi, float Li, float distance)
	{
		this.Wi = Wi;
		this.Li = Li;
		this.distance = distance;
	}
	
}
