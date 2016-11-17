package diasil.math.geometry3;

import diasil.math.DMath;

public abstract class SphericalFunction
{
	public abstract float r(float theta, float phi);
	
	public static Point3 toRectangular(Point3 p)
	{
		float theta = p.X;
		float phi = p.Y;
		float r = p.Z;
		float x = r*DMath.sin(phi)*DMath.cos(theta);
		float y = r*DMath.sin(phi)*DMath.sin(theta);
		float z = r*DMath.cos(phi);
		return new Point3(x, y, z);
		
	}
	
	/*private static float min_theta = Float.POSITIVE_INFINITY, max_theta = Float.NEGATIVE_INFINITY;
	private static float min_phi = Float.POSITIVE_INFINITY, max_phi = Float.NEGATIVE_INFINITY;
	public static void report()
	{
		System.out.println("theta: ["+min_theta+","+max_theta+"]");
		System.out.println("phi: ["+min_phi+","+max_phi+"]");
		System.exit(0);
	}*/
	
	public static Point3 toSpherical(Point3 p)
	{
		float r = DMath.sqrt(p.X*p.X + p.Y*p.Y + p.Z*p.Z);
		float theta = DMath.PI + DMath.atan2(p.Y, p.X);
		float phi = (r == 0)? 0: DMath.acos(p.Z/r);
		
		/*min_theta = Math.min(theta, min_theta);
		max_theta = Math.max(theta, max_theta);
		min_phi = Math.min(phi, min_phi);
		max_phi = Math.max(phi, max_phi);*/
		
		return new Point3(theta, phi, r);
	}
}
