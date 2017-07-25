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
    public void closestIntersection(Ray3 rw, Intersection it)
    {	
        for (int i=0; i<intersectables.size(); ++i)
        {
			intersectables.get(i).closestIntersection(rw, it);
        }
    }
	public boolean isBlocked(Ray3 rw, Intersection it)
	{
		for (int i=0; i<intersectables.size(); ++i)
        {
			if (intersectables.get(i).isBlocked(rw, it))
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
