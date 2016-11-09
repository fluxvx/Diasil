package diasil.light;

import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Vector3;

public class LightSample
{
	public Point3 pw_surface, pw_light; // points on the object and light, in world space
	public float intensity; // intensity of the light received
	public Vector3 Wi; // vector pointing in the direction of the light
	public float distance; // distance to the light
	public LightSample(Point3 pw_surface, Point3 pw_light, float intensity, Vector3 wi, float distance)
	{
		this.pw_surface = pw_surface;
		this.pw_light = pw_light;
		this.intensity = intensity;
		this.Wi = wi;
		this.distance = distance;
	}
	
}
