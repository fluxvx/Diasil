package diasil.intersect;

import diasil.material.LightRay;
import diasil.material.Material;
import diasil.math.DMath;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Cube extends CoordinateSpace3 implements Intersectable
{
    private float radius;
	private Material material;
    public Cube(float radius, Material material)
    {
		this.radius = radius;
		this.material = material;
    }
    public SurfaceGeometry getSurfaceGeometry(Point3 p)
    {
		// really lazy UV mapping - have the texture repeated on each face
		float inv_len = 1.0f/(2*radius);
		Point3 ps = new Point3((radius + p.X)*inv_len,
								(radius + p.Y)*inv_len,
								(radius + p.Z)*inv_len);
		
		int max_ind = (Math.abs(p.X) > Math.abs(p.Y))?
						(Math.abs(p.X) > Math.abs(p.Z))? 0: 2:
						(Math.abs(p.Y) > Math.abs(p.Z))? 1: 2;
		
		Normal3 n;
		float u, v;
		Vector3 dpdu, dpdv, dndu, dndv;
		if (max_ind == 0)
		{
			n = new Normal3(1.0f, 0.0f, 0.0f);
			u = ps.Y;
			v = ps.Z;
			dpdu = new Vector3(0.0f, 1.0f, 0.0f);
			dpdv = new Vector3(0.0f, 0.0f, 1.0f);
		}
		else if (max_ind == 1)
		{
			n = new Normal3(0.0f, 1.0f, 0.0f);
			u = ps.X;
			v = ps.Z;
            dpdu = new Vector3(1.0f, 0.0f, 0.0f);
            dpdv = new Vector3(0.0f, 0.0f, 1.0f);
		}
		else
		{
            n = new Normal3(0.0f, 0.0f, 1.0f);
			u = ps.X;
			v = ps.Y;
            dpdu = new Vector3(1.0f, 0.0f, 0.0f);
            dpdv = new Vector3(0.0f, 1.0f, 0.0f);
		}
		
        return new SurfaceGeometry(n, u, v, dpdu, dpdv, this);
    }
    public Intersection getIntersection(Ray3 rw)
    {
        Ray3 ro = super.toObjectSpace(rw);
        
        float tx1 = (-radius - ro.O.X)/ro.D.X;
        float tx2 = (radius - ro.O.X)/ro.D.X;

        float tmin = Math.min(tx1, tx2);
        float tmax = Math.max(tx1, tx2);

        float ty1 = (-radius - ro.O.Y)/ro.D.Y;
        float ty2 = (radius - ro.O.Y)/ro.D.Y;

        tmin = Math.max(tmin, Math.min(ty1, ty2));
        tmax = Math.min(tmax, Math.max(ty1, ty2));

        float tz1 = (-radius - ro.O.Z)/ro.D.Z;
        float tz2 = (radius - ro.O.Z)/ro.D.Z;

        tmin = Math.max(tmin, Math.min(tz1, tz2));
        tmax = Math.min(tmax, Math.max(tz1, tz2));

        if (tmax >= Math.max(0.0f, tmin))
        {
            if (LightRay.isValid(tmin))
            {
                return new Intersection(ro, tmin, this);
            }
            else if (LightRay.isValid(tmax))
            {
                return new Intersection(ro, tmax, this);
            }
        }
        return null;
    }
    public Box3 getBoundingBox()
    {
        Point3 pmin = new Point3(-radius, -radius, -radius);
        Point3 pmax = new Point3 (radius, radius, radius);
        Box3 r = new Box3(pmin, pmax);
        return super.toWorldSpace(r);
    }
	public Point3 sampleSurface(float u1, float u2)
	{
		Random random = ThreadLocalRandom.current();
		int d = random.nextInt(3);
		float v = (random.nextInt()%2 == 0)? -radius: radius;
		u1 = (2.0f*u1 - 1.0f)*radius;
		u2 = (2.0f*u2 - 1.0f)*radius;
		
		Point3 r;
		if (d == 0)
		{
			r =  new Point3(v, u1, u2);
		}
		else if (d == 1)
		{
			r = new Point3(u1, v, u2);
		}
		else
		{
			r = new Point3(u1, u2, v);
		}
		return toWorldSpace(r);
	}
	
	public Material getMaterial()
	{
		return material;
	}
}
