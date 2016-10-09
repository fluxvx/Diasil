package diasil.math.geometry2;

public class Vector2
{
	public float X, Y;
	public Vector2()
	{
		X = Y = 0.0f;
	}
	public Vector2(float x, float y)
	{
		X = x;
		Y = y;
	}
	public Vector2(Vector2 v)
	{
		X = v.X;
		Y = v.Y;
	}
}
