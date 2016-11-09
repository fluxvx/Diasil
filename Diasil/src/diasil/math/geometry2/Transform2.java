package diasil.math.geometry2;

public interface Transform2
{
	public Point2 toScreenSpace(Point2 p);
	public Point2 toRasterSpace(Point2 p);
	public Vector2 toScreenSpace(Vector2 v);
	public Vector2 toRasterSpace(Vector2 v);
}
