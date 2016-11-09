package diasil.intersect;

import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class Intersection
{
	public Vector3 Wo;
    public Point3 P, Pobject;
    public float T;
    public Shape E;
    
    public Intersection(Ray3 rw, Ray3 ro, float t, Shape e)
    {
		Wo = new Vector3(rw.D);
		Wo.negate();
        P = rw.pointAt(t);
        Pobject = ro.pointAt(t);
        T = t;
		E = e;
    }
	
	public SurfaceGeometry getSurfaceGeometry()
	{
		return E.getSurfaceGeometry(this);
	}
}
