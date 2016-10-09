package diasil.math.geometry2;

public class Point2
{
	public float X, Y;
	public Point2()
	{
		X = Y = 0.0f;
	}
	public Point2(float x, float y)
	{
		X = x;
		Y = y;
	}
	public Point2(Point2 p)
	{
		X = p.X;
		Y = p.Y;
	}
}
