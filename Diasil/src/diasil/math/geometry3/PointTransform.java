package diasil.math.geometry3;

public interface PointTransform
{
	public Point3 transform(Point3 p);
	public Point3 invert(Point3 p);
}
