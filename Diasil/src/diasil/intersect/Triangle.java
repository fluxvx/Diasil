package diasil.intersect;

import diasil.material.LightRay;
import diasil.material.Material;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class Triangle implements Intersectable
{
	// TODO: UV mapping, store UV value with each vertex and interpolate
	public Point3 v0, v1, v2;
	private TriangleMesh mesh;
	public Triangle(Point3 v0, Point3 v1, Point3 v2, TriangleMesh mesh)
	{
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.mesh = mesh;
	}
	
	// http://www.graphics.cornell.edu/pubs/1997/MT97.html
	public void closestIntersection(Ray3 ro, Intersection it)
	{
        Vector3 e1 = new Vector3(v0, v1);
        Vector3 e2 = new Vector3(v0, v2);
        Vector3 pv = ro.D.cross(e2);
        float det = e1.dot(pv);
        if (Math.abs(det) < 1.0E-3f)
        {
            return;
        }
        
        float inv_det = 1.0f/det;
        Vector3 tv = new Vector3(v0, ro.O);
        float u = tv.dot(pv)*inv_det;
        if (u < 0.0f || u > 1.0f)
        {
            return;
        }
        
        Vector3 qv = tv.cross(e1);
        float v = ro.D.dot(qv)*inv_det;
        if (v < 0.0f || u + v > 1.0f)
        {
            return;
        }
		
        float t = e2.dot(qv)*inv_det;
        if (it.isValid(t))
		{
			it.setValues(ro.pointAt(t), t, this);
		}
		
	}
	public boolean isBlocked(Ray3 rw, Intersection it)
	{
		closestIntersection(rw, it);
		return it.E == this;
	}
	public SurfaceGeometry getSurfaceGeometry(Point3 p)
	{
        Vector3 e1 = new Vector3(v0, v1);
        Vector3 e2 = new Vector3(v0, v2);
		Vector3 n = e1.cross(e2);
		Vector3 e3 = n.cross(e1);
		return new SurfaceGeometry(new Normal3(n), 0, 0, e1, e3, mesh);
	}
	public Point3 sampleSurface(float u1, float u2)
	{
		Vector3 e1 = new Vector3(v0, v1);
        Vector3 e2 = new Vector3(v0, v2);
		return new Point3(v0.X + u1*e1.X + u2*e2.X,
							v0.Y + u1*e1.Y + u2*e2.Y,
							v0.Z + u1*e1.Z + u2*e2.Z);
	}
	public Material getMaterial()
	{
		return mesh.getMaterial();
	}
	public Box3 getBoundingBox()
	{
		Box3 r = new Box3(v0);
		r.unionWith(v1);
		r.unionWith(v2);
		return r;
	}
}
