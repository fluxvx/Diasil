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
import diasil.sample.Sample;

public class Sphere extends CoordinateSpace3 implements Intersectable
{
    private float radius;
	private Material material;
    public Sphere(float radius, Material material)
    {
        this.radius = radius;
		this.material = material;
    }
    
    public Intersection getIntersection(Ray3 rw)
    {
		Ray3 ro = super.toObjectSpace(rw);
        float a = ro.D.dot(ro.D);
        float b = 2.0f*(ro.O.X*ro.D.X + ro.O.Y*ro.D.Y + ro.O.Z*ro.D.Z);
        float c = ro.O.X*ro.O.X + ro.O.Y*ro.O.Y + ro.O.Z*ro.O.Z - radius*radius; 
        float[] t = DMath.quadratic(a, b, c);
        if (t.length != 0 && LightRay.isValid(t[0]))
        {
            return new Intersection(ro, t[0], this);
        }
        if (t.length == 2 && LightRay.isValid(t[1]))
        {
            return new Intersection(ro, t[1], this);
        }
        return null;
    }
    
    public SurfaceGeometry getSurfaceGeometry(Point3 p)
    {
        float ir = 1.0f / radius;
        float phi = (float)Math.atan2(p.Y, p.X);
        if (phi < 0.0f)
        {
            phi += DMath.PI2;
        }
        float costheta = p.Z*ir;
        float sintheta = (float)Math.sqrt(1.0f - costheta*costheta);
        float theta = (float)Math.acos(costheta);
        float izr = 1.0f / (float)Math.sqrt(p.X*p.X + p.Y*p.Y);
        float cosphi = p.X*izr;
        float sinphi = p.Y*izr;
		
		Normal3 n = new Normal3(p.X, p.Y, p.Z);
        float u = phi * DMath.IPI2;
        float v = theta * DMath.IPI;
		
		Vector3[] t = n.formBasis();
		Vector3 dpdu = t[0];
		Vector3 dpdv = t[1];
		
		return new SurfaceGeometry(n, u, v, dpdu, dpdv, this);
    }
	public Point3 sampleSurface(float u, float v)
	{
		Point3 p = Sample.UniformSampleSphere(u, v);
		p.X *= radius;
		p.Y *= radius;
		p.Z *= radius;
		return toWorldSpace(p);
	}
    
    public Box3 getBoundingBox()
    {
        Point3 pmin = new Point3(-radius, -radius, -radius);
        Point3 pmax = new Point3 (radius, radius, radius);
        return toWorldSpace(new Box3(pmin, pmax));
    }

	public Material getMaterial()
	{
		return material;
	}
}