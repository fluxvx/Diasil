package diasil.math.geometry3;

public class Scale3 extends Transform3
{
    public float Sx, Sy, Sz;
    public Scale3(float tx, float ty, float tz)
    {
        Sx = tx;
        Sy = ty;
        Sz = tz;
    }
    
    public Point3 toWorldSpace(Point3 p)
    {
        return new Point3(p.X*Sx, p.Y*Sy, p.Z*Sz);
    }
    
    
    public Point3 toObjectSpace(Point3 p)
    {
        return new Point3(p.X/Sx, p.Y/Sy, p.Z/Sz);
    }
    
    public Vector3 toWorldSpace(Vector3 v)
    {
        return new Vector3(v.X*Sx, v.Y*Sy, v.Z*Sz);
    }
    
    public Vector3 toObjectSpace(Vector3 v)
    {
        return new Vector3(v.X/Sx, v.Y/Sy, v.Z/Sz);
    }
    
    public Normal3 toWorldSpace(Normal3 n)
    {
        return new Normal3(n.X*Sx, n.Y*Sy, n.Z*Sz);
    }
    
    public Normal3 toObjectSpace(Normal3 n)
    {
       return new Normal3(n.X/Sx, n.Y/Sy, n.Z/Sz);
    }
	
	public MTransform3 toMTransform3()
	{
		MTransform3 t = new MTransform3();
		t.setAsScaler(Sx, Sy, Sz);
		return t;
	}
	
}