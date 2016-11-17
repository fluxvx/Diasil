package diasil.intersect;

import diasil.material.Material;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;

// TODO: write a custom KDTree, have each node maintain a float[] and int[] to represent the triangles within it
// to avoid the overhead of using triangles and points

public class TriangleMesh extends CoordinateSpace3 implements Intersectable
{
	protected Material material;
	protected KDTree kdtree;
	public TriangleMesh(int min_its, int max_depth, Material m)
	{
		material = m;
		kdtree = new KDTree(min_its, max_depth);
	}
	public void closestIntersection(Ray3 rw, Intersection it)
	{
		Ray3 ro = toObjectSpace(rw);
		kdtree.closestIntersection(ro, it);
	}
	public boolean isBlocked(Ray3 rw, Intersection it)
	{
		Ray3 ro = toObjectSpace(rw);
		return kdtree.isBlocked(ro, it);
	}
	public SurfaceGeometry getSurfaceGeometry(Point3 p)
	{
		return null;
	}
	public Point3 sampleSurface(float u1, float u2)
	{
		return new Point3(0.0f, 0.0f, 0.0f); // TODO
	}
	public Material getMaterial()
	{
		return material;
	}
	public Box3 getBoundingBox()
	{
		return kdtree.getBoundingBox();
	}
}
