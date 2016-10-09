package diasil.entity;

import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Ray3;
import java.util.ArrayList;

// an aggregate holds a collection of entities that can be transformed together
// an aggregate can contain other aggregates
public class Aggregate extends Entity
{
    public Box3 bounding_box;
    public ArrayList<Entity> entities;
    public boolean test_box;
    public Aggregate()
    {
		super(null);
        entities = new ArrayList<Entity>();
        bounding_box = new Box3();
        test_box = false;
    }
    public void add(Entity e)
    {
        entities.add(e);
		Box3 b = e.getBoundingBox();
		b = e.toWorldSpace(b);
        bounding_box.unionWith(b);
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
            Entity e = entities.get(i);
			Ray3 ro = e.toObjectSpace(rw);
            Intersection t = e.getIntersection(ro);
            if (r == null || (t != null && t.T < r.T))
            {
                r = t;
            }
        }
        
        return r;
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
			Entity e = entities.get(i);
			e.compileTransforms();
			e.multiply(this);
		}
	}
}
