package diasil.math.geometry3;

public class Ray3
{
    public Point3 O;
    public Vector3 D;
    public Ray3()
    {
        O = new Point3();
        D = new Vector3();
    }
    public Ray3(Point3 o, Vector3 d)
    {
        O = new Point3(o);
        D = new Vector3(d);
    }
    public Ray3(Point3 o, Point3 d)
    {
        O = new Point3(o);
        D = new Vector3(d.X - o.X, d.Y - o.Y, d.Z - o.Z);
    }
    public Ray3(Ray3 r)
    {
        O = new Point3(r.O);
        D = new Vector3(r.D);
    }
    
    public Point3 pointAt(float t)
    {
        return new Point3(O.X + t*D.X,
                          O.Y + t*D.Y,
                          O.Z + t*D.Z);
    }
    
    
    public String toString()
    {
        return "{"+O.toString()+","+D.toString()+"}";
    }

}