package diasil.math.geometry3;

public class Normal3
{
	public float X, Y, Z;
	public Normal3()
	{
		X = Y = Z = 0.0f;
	}
	public Normal3(float x, float y, float z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	public Normal3(Normal3 n)
	{
		X = n.X;
		Y = n.Y;
		Z = n.Z;
	}
	public Normal3(Vector3 v)
	{
		X = v.X;
		Y = v.Y;
		Z = v.Z;
	}
	
	public float dot(Vector3 v)
	{
		return X*v.X + Y*v.Y + Z*v.Z;
	}
	public float dot(Normal3 n)
	{
		return X*n.X + Y*n.Y + Z*n.Z;
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
	
	public Vector3[] formBasis()
	{
        Vector3 vx, vy, vz;
        vz = new Vector3(this);
        if (Math.abs(vz.X) > Math.abs(vz.Y))
        {
            float inv_len = 1.0f/(float)Math.sqrt(vz.X*vz.X + vz.Z*vz.Z);
            vy = new Vector3(-vz.Z*inv_len, 0.0f, vz.X*inv_len);
        }
        else
        {
            float inv_len = 1.0f/(float)Math.sqrt(vz.Y*vz.Y + vz.Z*vz.Z);
            vy = new Vector3(0.0f, vz.Z*inv_len, -vz.Y*inv_len);
        }
        vx = vz.cross(vy);
        return new Vector3[]{vx, vy};
	}
}
