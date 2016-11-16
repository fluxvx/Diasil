package diasil.intersect;

import diasil.material.Material;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;

public interface Intersectable
{
    public Intersection getIntersection(Ray3 ray);
    public SurfaceGeometry getSurfaceGeometry(Point3 p);
	public Point3 sampleSurface(float u1, float u2);
    public Material getMaterial();
    public Box3 getBoundingBox();
}
