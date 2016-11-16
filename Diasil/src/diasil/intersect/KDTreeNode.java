package diasil.intersect;

import diasil.math.geometry3.Box3;
import java.util.ArrayList;

public class KDTreeNode
{
	public Box3 bounds;
	public KDTreeNode left, right;
	public ArrayList<Intersectable> its;
	public KDTreeNode(Box3 bounds, ArrayList<Intersectable> its)
	{
		this.bounds = bounds;
		this.its = its;
		left = right = null;
	}
	public KDTreeNode(Box3 bounds, KDTreeNode left, KDTreeNode right)
	{
		this.bounds = bounds;
		this.left = left;
		this.right = right;
		its = null;
	}
}
