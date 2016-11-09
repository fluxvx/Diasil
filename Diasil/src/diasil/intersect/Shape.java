package diasil.intersect;

import diasil.material.Material;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;

public abstract class Shape extends CoordinateSpace3
{
	private Material material;
	public Shape(Material m)
	{
		material = m;
	}
    public abstract Intersection getIntersection(Ray3 ray);
    public abstract SurfaceGeometry getSurfaceGeometry(Intersection it);
	public abstract Point3 sampleSurface(float u1, float u2);
    public Material getMaterial()
	{
		return material;
	}
    public abstract Box3 getBoundingBox();
}
