package diasil.math.geometry3;

import diasil.math.DMath;

public class SphericalToRectangular implements PointTransform
{
	public Point3 transform(Point3 p)
	{
		float theta = p.X;
		float phi = p.Y;
		float r = p.Z;
		float x = r*DMath.sin(theta)*DMath.cos(phi);
		float y = r*DMath.sin(theta)*DMath.sin(phi);
		float z = r*DMath.cos(theta);
		return new Point3(x, y, z);
		
	}
	public Point3 invert(Point3 p)
	{
		float r = DMath.sqrt(p.X*p.X + p.Y*p.Y + p.Z*p.Z);
		float theta = DMath.acos(p.Z/r);
		float phi = DMath.atan2(p.Y, p.X);
		return new Point3(theta, phi, r);
	}
}
