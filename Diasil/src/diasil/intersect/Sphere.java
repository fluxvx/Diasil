package diasil.intersect;

import diasil.material.LightRay;
import diasil.material.Material;
import diasil.math.DMath;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sample;

public class Sphere extends Shape
{
    public float radius;
    public Sphere(float r, Material m)
    {
        super(m);
        radius = r;
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
            return new Intersection(rw, ro, t[0], this);
        }
        if (t.length == 2 && LightRay.isValid(t[1]))
        {
            return new Intersection(rw, ro, t[1], this);
        }
        return null;
    }
    
    public SurfaceGeometry getSurfaceGeometry(Intersection it)
    {
        Point3 p = it.Pobject;
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
        Vector3 dpdu = new Vector3(-DMath.PI2*p.Y, DMath.PI2*p.X, 0.0f);
        Vector3 dpdv = new Vector3(p.Z*cosphi*DMath.PI, p.Z*sinphi, -radius*sintheta);
		
		return new SurfaceGeometry(it, n, u, v, dpdu, dpdv, this);
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
}