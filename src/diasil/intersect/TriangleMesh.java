package diasil.intersect;

import diasil.material.Material;
import diasil.math.geometry3.Transform3;

// TODO: write a custom KDTree, have each node maintain a float[] and int[] to represent the triangles within it
// to avoid the overhead of using triangles and points

public class TriangleMesh extends KDTree
{
	protected Material material;
	protected Transform3 transform;
	public TriangleMesh(int min_its, int max_depth, Material material, Transform3 transform)
	{
		super(min_its, max_depth);
		this.material = material;
		this.transform = transform;
	}
}
