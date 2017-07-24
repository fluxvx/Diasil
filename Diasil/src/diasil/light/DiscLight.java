package diasil.light;

import diasil.color.SpectralDistribution;
import diasil.intersect.Intersectable;
import diasil.intersect.Intersection;
import diasil.intersect.SurfaceGeometry;
import diasil.material.Material;
import diasil.math.geometry2.Point2;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sample;

public class DiscLight extends CoordinateSpace3 implements AreaLight
{
    private float Y, R, R2;
	private SpectralDistribution power;
    public DiscLight(float y, float r, SpectralDistribution power)
    {
        Y = y;
        R = r;
        R2 = R*R;
		this.power = power;
    }
    public void closestIntersection(Ray3 rw, Intersection it)
    {
        Ray3 ro = toObjectSpace(rw);
        float t = (Y-ro.O.Y)/ro.D.Y;
		if (it.isValid(t))
		{
			Point3 po = ro.pointAt(t);
			if (po.distanceToSquared(new Point3(0.0f, Y, 0.0f)) < R2)
			{
				it.setValues(po, t, this);
			}
		}
    }
	
	public boolean isBlocked(Ray3 rw, Intersection it)
	{
        Ray3 ro = toObjectSpace(rw);
        float t = (Y-ro.O.Y)/ro.D.Y;
		if (it.isValid(t))
		{
			Point3 po = ro.pointAt(t);
			if (po.distanceToSquared(new Point3(0.0f, Y, 0.0f)) < R2)
			{
				return true;
			}
		}
		return false;
	}
    
    public SurfaceGeometry getSurfaceGeometry(Point3 p)
    {
        Normal3 n = new Normal3(0.0f, 1.0f, 0.0f);
		float u = (float)((Math.PI + Math.atan2(p.X, p.Z))/(2.0*Math.PI));
		float v = (float)(Math.sqrt(p.X*p.X + p.Z*p.Z))/R;
        Vector3 dpdu = new Vector3(1.0f, 0.0f, 0.0f);
        Vector3 dpdv = new Vector3(0.0f, 0.0f, 1.0f);
		Vector3 dndu = new Vector3(0.0f, 0.0f, 0.0f);
		Vector3 dndv = new Vector3(0.0f, 0.0f, 0.0f);
        return new SurfaceGeometry(n, u, v, dpdu, dpdv, this);
    }
	public Point3 sampleSurface(float u1, float u2)
	{
		Point2 p = Sample.ConcentricSampleDisk(u1, u2);
		Point3 r = new Point3(p.X*R, Y, p.Y*R);
		return toWorldSpace(r);
	}
    public Box3 getBoundingBox()
    {
        return super.toWorldSpace(new Box3(new Point3(-R,Y,-R),new Point3(R,Y,R)));
    }
	public Material getMaterial()
	{
		return null;
	}
	
	public LightSample sampleL(Point3 pw, float wavelength, float u, float v)
	{
		Point3 pl = sampleSurface(u, v);
		
		Vector3 wi = new Vector3(pw, pl);
		float d2 = wi.lengthSquared();
		
		// options: use dot for falloff, make one-sided
		//SurfaceGeometry sg = it.getSurfaceGeometry(pl);
		//wi.normalize();
		//float dot = Math.abs(sg.N.dot(wi));
		
		float Li = power.evaluate(wavelength)/d2;
		float d = (float)Math.sqrt(d2);
		wi.multiplyBy(1.0f/d);
		return new LightSample(wi, Li, d);
	}
	
	public float power(Point3 pw, float wavelength)
	{
		return power.evaluate(wavelength);
	}
}
