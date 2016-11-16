package diasil.intersect;

import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class Intersection
{
    public Point3 Pobj;
    public float T;
    public Intersectable E;
	public Intersection(Point3 pobj, float t, Intersectable e)
	{
		Pobj = pobj;
		T = t;
		E = e;
	}
    public Intersection(Ray3 ro, float t, Intersectable e)
    {
        Pobj = ro.pointAt(t);
        T = t;
		E = e;
    }
	
	public SurfaceGeometry getSurfaceGeometry()
	{
		return E.getSurfaceGeometry(Pobj);
	}
}
