package diasil.math.geometry3;

public class Box3
{
    public Point3 Pmin, Pmax;
    public Box3()
    {
        Pmin = new Point3(Float.POSITIVE_INFINITY,
                            Float.POSITIVE_INFINITY,
                            Float.POSITIVE_INFINITY);
        Pmax = new Point3(Float.NEGATIVE_INFINITY,
                            Float.NEGATIVE_INFINITY,
                            Float.NEGATIVE_INFINITY);
    }
    
    public Box3(Point3 a, Point3 b)
    {
        Pmin = new Point3(a);
        Pmax = new Point3(b);
    }
    public Box3(Point3 p)
    {
        this(p, p);
    }
    public Box3(Box3 b)
    {
        this(b.Pmin, b.Pmax);
    }
    
    public float[] getIntersection(Ray3 r)
    {
        float tx1 = (Pmin.X - r.O.X)/r.D.X;
        float tx2 = (Pmax.X - r.O.X)/r.D.X;

        float tmin = Math.min(tx1, tx2);
        float tmax = Math.max(tx1, tx2);

        float ty1 = (Pmin.Y - r.O.Y)/r.D.Y;
        float ty2 = (Pmax.Y - r.O.Y)/r.D.Y;

        tmin = Math.max(tmin, Math.min(ty1, ty2));
        tmax = Math.min(tmax, Math.max(ty1, ty2));

        float tz1 = (Pmin.Z - r.O.Z)/r.D.Z;
        float tz2 = (Pmax.Z - r.O.Z)/r.D.Z;

        tmin = Math.max(tmin, Math.min(tz1, tz2));
        tmax = Math.min(tmax, Math.max(tz1, tz2));

        if (tmax >= Math.max(0.0, tmin))
        {
            return new float[]{ tmin, tmax };
        }
        return null;
    }
    
    public boolean contains(Point3 p)
    {
        return p.X > Pmin.X && p.X < Pmax.X
                && p.Y > Pmin.Y && p.Y < Pmax.Y
                && p.Z > Pmin.Z && p.Z < Pmax.Z;
    }
    
    public void unionWith(Point3 p)
    {
        if (p.X < Pmin.X)
        {
            Pmin.X = p.X;
        }
        
        if (p.X > Pmax.X)
        {
            Pmax.X = p.X;
        }
        
        if (p.Y < Pmin.Y)
        {
            Pmin.Y = p.Y;
        }
        
        if (p.Y > Pmax.Y)
        {
            Pmax.Y = p.Y;
        }
        
        if (p.Z < Pmin.Z)
        {
            Pmin.Z = p.Z;
        }
        
        if (p.Z > Pmax.Z)
        {
            Pmax.Z = p.Z;
        }
        
    }
    
    public void unionWith(Box3 b)
    {
        unionWith(b.Pmin);
        unionWith(b.Pmax);
    }

    public int longestDimension()
    {
        Vector3 d = new Vector3(Pmin, Pmax);
        if (d.X > d.Y)
        {
            if (d.X > d.Z)
            {
                return 0;
            }
            return 2;
        }
        else if (d.Y > d.Z)
        {
            return 1;
        }
        return 2;
    }
    
    public float area()
    {
        Vector3 d = new Vector3(Pmin, Pmax);
        return 2.0f*(d.X*d.Y + d.X*d.Z + d.Y*d.Z);
    }
	
	public Box3 clone()
	{
		return new Box3(Pmin, Pmax);
	}
    
}

