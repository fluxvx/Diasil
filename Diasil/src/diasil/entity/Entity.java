package diasil.entity;

import diasil.material.Material;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Ray3;

public abstract class Entity extends CoordinateSpace3
{
	private Material material;
	public Entity(Material m)
	{
		material = m;
	}
    public abstract Intersection getIntersection(Ray3 ray);
    public abstract SurfaceGeometry getSurfaceGeometry(Intersection it);
    public Material getMaterial()
	{
		return material;
	}
    public abstract Box3 getBoundingBox();
}
