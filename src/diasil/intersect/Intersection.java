package diasil.intersect;

import diasil.math.geometry3.Point3;

public class Intersection
{
	public static float MIN_T = 1E-3f;
	
    public Point3 Pobj;
    public float T;
    public Intersectable E;
	public Intersection()
	{
		Pobj = null;
		T = Float.POSITIVE_INFINITY;
		E = null;
	}
	public void setValues(Point3 pobj, float t, Intersectable e)
	{
		Pobj = pobj;
		T = t;
		E = e;
	}
	public boolean isValid(float t)
	{
		return t > MIN_T && t < T;
	}
	public boolean success()
	{
		return Float.isFinite(T);
	}
	public SurfaceGeometry getSurfaceGeometry()
	{
		return E.getSurfaceGeometry(Pobj);
	}
}
