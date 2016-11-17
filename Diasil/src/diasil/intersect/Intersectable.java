package diasil.intersect;

import diasil.material.Material;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;

public interface Intersectable
{
    public void closestIntersection(Ray3 ray, Intersection it);
	public boolean isBlocked(Ray3 ray, Intersection it);
    public SurfaceGeometry getSurfaceGeometry(Point3 p);
	public Point3 sampleSurface(float u1, float u2);
    public Material getMaterial();
    public Box3 getBoundingBox();
}
