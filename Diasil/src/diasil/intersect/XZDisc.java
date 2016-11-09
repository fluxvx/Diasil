package diasil.intersect;

import diasil.material.LightRay;
import diasil.material.Material;
import diasil.math.DMath;
import diasil.math.geometry2.Point2;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sample;

public class XZDisc extends Shape
{
    private float Y, R, R2;
    public XZDisc(float y, float r, Material material)
    {
        super(material);
        Y = y;
        R = r;
        R2 = R*R;
    }
    public Intersection getIntersection(Ray3 rw)
    {
        Ray3 ro = toObjectSpace(rw);
        float t = (Y-ro.O.Y)/ro.D.Y;
		if (LightRay.isValid(t))
		{
			Point3 po = ro.pointAt(t);
			if (po.distanceToSquared(new Point3(po.X, Y, po.Z)) < R2)
			{
				return new Intersection(rw, ro, t, this);
			}
		}
        return null;
    }
    
    public SurfaceGeometry getSurfaceGeometry(Intersection it)
    {
		Point3 p = it.Pobject;
        Normal3 n = new Normal3(0.0f, 1.0f, 0.0f);
		float u = (float)((Math.PI + Math.atan2(p.X, p.Z))/(2.0*Math.PI));
		float v = (float)(Math.sqrt(p.X*p.X + p.Z*p.Z))/R;
        Vector3 dpdu = new Vector3(1.0f, 0.0f, 0.0f);
        Vector3 dpdv = new Vector3(0.0f, 0.0f, 1.0f);
		Vector3 dndu = new Vector3(0.0f, 0.0f, 0.0f);
		Vector3 dndv = new Vector3(0.0f, 0.0f, 0.0f);
        return new SurfaceGeometry(it, n, u, v, dpdu, dpdv, this);
    }
	
	public Point3 sampleSurface(float u1, float u2)
	{
		Point2 p = Sample.ConcentricSampleDisk(u1, u2);
		Point3 r = new Point3(p.X*R, Y, p.Y*R);
		return toWorldSpace(r);
	}
    
    public Box3 getBoundingBox()
    {
        return super.toWorldSpace(new Box3(new Point3(-R,0,-R),new Point3(R,0,R)));
    }

}