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

public class Cylinder extends CoordinateSpace3 implements Intersectable
{
	private float radius, height;
	private Material material;
	public Cylinder(float radius, float height, Material material)
	{
		this.radius = radius;
		this.height = height;
		this.material = material;
	}
	public Cylinder(float r, Point3 p0, Point3 p1, Material m)
	{
		this(r, p0.distanceTo(p1), m);
		
		// rotate z+ (0,0,1) to (p0-p1)
		Vector3 v = new Vector3(p0, p1);
		v.normalize();
		float phi = DMath.acos(v.Z);
		float theta = DMath.atan2(v.Y, v.X);
		
		rotateXZ(phi);
		rotateXY(theta);
		translate((p0.X+p1.X)/2,
					(p0.Y+p1.Y)/2,
					(p0.Z+p1.Z)/2);
	}
	
	public void closestIntersection(Ray3 rw, Intersection it)
	{
		Ray3 r = toObjectSpace(rw);
		float a = r.D.X*r.D.X + r.D.Y*r.D.Y;
		float b = 2*(r.D.X*r.O.X + r.D.Y*r.O.Y);
		float c = r.O.X*r.O.X + r.O.Y*r.O.Y - radius*radius;
		float[] t = DMath.quadratic(a, b, c);
		if (t.length > 0)
        {
            if (it.isValid(t[0]))
            {
				Point3 po = r.pointAt(t[0]);
				if (po.Z > -height/2 && po.Z < height/2)
				{
					it.setValues(po, t[0], this);
				}
            }
			else if (t.length == 2 && it.isValid(t[1]))
            {
				Point3 po = r.pointAt(t[1]);
				if (po.Z > -height/2 && po.Z < height/2)
				{
					it.setValues(po, t[1], this);
				}
            }
        }
	}
	public boolean isBlocked(Ray3 rw, Intersection it)
	{
		closestIntersection(rw, it);
		return it.E == this;
	}
	
	public SurfaceGeometry getSurfaceGeometry(Point3 p)
	{
		Normal3 n = new Normal3(p.X, p.Y, 0.0f);
		float u = (DMath.PI + DMath.atan2(p.Y, p.X))/DMath.PI2;
		float v = (p.Z+height/2)/height;
		return new SurfaceGeometry(n, u, v, this);
	}
	
	public Point3 sampleSurface(float u1, float u2)
	{
		float theta = u1*DMath.PI2;
		float x = radius*DMath.cos(theta);
		float y = radius*DMath.sin(theta);
		float z = -height/2 + u2*height;
		return toWorldSpace(new Point3(x, y, z));
	}
	
	public Box3 getBoundingBox()
	{
		Point3 pmin = new Point3(-radius, -radius, -height/2);
		Point3 pmax = new Point3(radius, radius, height/2);
		return toWorldSpace(new Box3(pmin, pmax));
	}
	
	public Material getMaterial()
	{
		return material;
	}
}
