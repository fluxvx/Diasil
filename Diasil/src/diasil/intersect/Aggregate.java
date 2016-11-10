package diasil.intersect;

import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import java.util.ArrayList;

// holds a collection of shapes that can be transformed together
// can employ techniques for accelerating intersections
// can contain other aggregates
public class Aggregate extends Shape
{
    public Box3 bounding_box;
    public ArrayList<Shape> entities;
    public boolean test_box;
    public Aggregate()
    {
		super(null);
        entities = new ArrayList<Shape>();
        bounding_box = null;
        test_box = false;
    }
    public void add(Shape e)
    {
        entities.add(e);
    }
    
    public Intersection getIntersection(Ray3 rw)
    {
        if (test_box && !bounding_box.testIntersection(rw))
        {
            return null;
        }
		
        Intersection r = null;
        for (int i=0; i<entities.size(); ++i)
        {
            Shape e = entities.get(i);
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
		for (int i=0; i<entities.size(); ++i)
		{
			Shape e = entities.get(i);
			Intersection t = e.getIntersection(rw);
			if (t != null && t.T < d)
			{
				return true;
			}
		}
		return false;
	}
    
    public SurfaceGeometry getSurfaceGeometry(Intersection it)
    {
        return null;
    }
    
    public Box3 getBoundingBox()
    {
        return bounding_box;
    }
	
	public void compileTransforms()
	{
		super.compileTransforms();
		for (int i=0; i<entities.size(); ++i)
		{
			Shape e = entities.get(i);
			e.compileTransforms();
			e.multiply(this);
		}
		bounding_box = new Box3();
		for (int i=0; i<entities.size(); ++i)
		{
			Box3 b = entities.get(i).getBoundingBox();
			bounding_box.unionWith(b);
		}
	}
	public Point3 sampleSurface(float u1, float u2)
	{
		return null;
	}
}
