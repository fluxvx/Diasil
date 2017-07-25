package diasil.intersect;

import diasil.Diasil;
import diasil.material.LightRay;
import diasil.material.Material;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class XYPlane extends CoordinateSpace3 implements Intersectable
{
	private float width, height;
	private Material material;
	public XYPlane(float width, float height, Material material)
	{
		this.width = width;
		this.height = height;
		this.material = material;
	}
	public void closestIntersection(Ray3 rw, Intersection it)
	{
		Ray3 ro = toObjectSpace(rw);
		float t = -ro.O.Z/ro.D.Z;
        if (it.isValid(t))
        {
			Point3 po = ro.pointAt(t);
			if (po.X > -width && po.X < width
					&& po.Y > -height && po.Y < height)
			{
				it.setValues(po, t, this);
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
		Normal3 n = new Normal3(0.0f, 0.0f, 1.0f);
		float u = (width+p.X)/(2*width);
		float v = (height+p.Y)/(2*height);
		Vector3 dpdu = new Vector3(1.0f, 0.0f, 0.0f);
		Vector3 dpdv = new Vector3(0.0f, 1.0f, 0.0f);
		Vector3 dndu = new Vector3(0.0f, 0.0f, 0.0f);
		Vector3 dndv = new Vector3(0.0f, 0.0f, 0.0f);
		return new SurfaceGeometry(n, u, v, dpdu, dpdv, this);
	}
	public Point3 sampleSurface(float u1, float u2)
	{
		Point3 r = new Point3((2.0f*u1 - 1.0f)*(width/2),
								(2.0f*u2 - 1.0f)*(height/2),
								0.0f);
		return toWorldSpace(r);
	}
	public Box3 getBoundingBox()
	{
		Box3 r = new Box3(new Point3(-width, -height, 0.0f), new Point3(width, height, 0.0f));
		return toWorldSpace(r);
	}
	public Material getMaterial()
	{
		return material;
	}
}
