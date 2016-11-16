package diasil.intersect;

import diasil.material.Material;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import java.util.ArrayList;

public class Aggregate implements Intersectable
{
    public ArrayList<Intersectable> intersectables;
    public Aggregate()
    {
        intersectables = new ArrayList<Intersectable>();
    }
    public void add(Intersectable e)
    {
        intersectables.add(e);
    }
    public Intersection getIntersection(Ray3 rw)
    {	
        Intersection r = null;
        for (int i=0; i<intersectables.size(); ++i)
        {
            Intersectable e = intersectables.get(i);
            Intersection t = e.getIntersection(rw);
            if (r == null || (t != null && t.T < r.T))
            {
                r = t;
            }
        }
        return r;
    }
	public boolean isBlocked(Ray3 rw, float d)
	{
		d -= 1.0E-3f;
		for (int i=0; i<intersectables.size(); ++i)
		{
			Intersectable e = intersectables.get(i);
			Intersection t = e.getIntersection(rw);
			if (t != null && t.T < d)
			{
				return true;
			}
		}
		return false;
	}
    public SurfaceGeometry getSurfaceGeometry(Point3 p)
    {
        return null;
    }
    public Box3 getBoundingBox()
    {
		Box3 bounding_box = new Box3();
		for (int i=0; i<intersectables.size(); ++i)
		{
			Box3 b = intersectables.get(i).getBoundingBox();
			bounding_box.unionWith(b);
		}
        return bounding_box;
    }
	public Point3 sampleSurface(float u1, float u2)
	{
		return null;
	}
	public Material getMaterial()
	{
		return null;
	}
}
