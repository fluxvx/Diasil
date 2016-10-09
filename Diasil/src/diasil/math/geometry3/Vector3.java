package diasil.math.geometry3;

public class Vector3
{
	public float X, Y, Z;
	public Vector3()
	{
		X = Y = Z = 0.0f;
	}
	public Vector3(float x, float y, float z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	public Vector3(Vector3 v)
	{
		X = v.X;
		Y = v.Y;
		Z = v.Z;
	}
	public Vector3(Normal3 n)
	{
		X = n.X;
		Y = n.Y;
		Z = n.Z;
	}
	public Vector3(Point3 from, Point3 to)
	{
		X = to.X - from.X;
		Y = to.Y - from.Y;
		Z = to.Z - from.Z;
	}
	public float lengthSquared()
    {
        return X*X + Y*Y + Z*Z;
    }
    public float length()
    {
        return (float)Math.sqrt(lengthSquared());
    }
	public void normalize()
    {
        float il = 1.0f/length();
        X *= il;
        Y *= il;
        Z *= il;
    }
	public void negate()
	{
		X = -X;
		Y = -Y;
		Z = -Z;
	}
	public float dot(Vector3 v)
	{
		return X*v.X + Y*v.Y + Z*v.Z;
	}
	public float dot(Normal3 n)
	{
		return X*n.X + Y*n.Y + Z*n.Z;
	}
    public Vector3 cross(Vector3 v)
    {
        return new Vector3(Y*v.Z - Z*v.Y, 
                            Z*v.X - X*v.Z,
                            X*v.Y - Y*v.X);
    }
    public Vector3 reflectAcross(Normal3 n)
    {
        float cosI = dot(n);
        float rx = -X + 2*cosI*n.X;
        float ry = -Y + 2*cosI*n.Y;
        float rz = -Z + 2*cosI*n.Z;
        return new Vector3(rx, ry, rz);
    }
}
