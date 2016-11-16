package diasil.intersect;

import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Ray3;
import java.util.ArrayList;

public class KDTree extends Aggregate
{
	private KDTreeNode root;
	private int min_its, max_depth;
	public KDTree(int min_its, int max_depth)
	{
		root = null;
		this.min_its = min_its;
		this.max_depth = max_depth;
	}
	public void buildTree()
	{
		ArrayList<Box3> bounds = new ArrayList<Box3>(intersectables.size());
		Box3 total_bounds = new Box3();
		for (int i=0; i<intersectables.size(); ++i)
		{
			Box3 b = intersectables.get(i).getBoundingBox();
			bounds.add(b);
			total_bounds.unionWith(b);
		}
		root = buildTree(intersectables, bounds, total_bounds, 0);
	}
	
	private KDTreeNode buildTree(ArrayList<Intersectable> its, ArrayList<Box3> bounds, Box3 total_bounds, int depth)
	{
		if (its.size() <= min_its || depth >= max_depth)
		{
			return new KDTreeNode(total_bounds, its);
		}
		
		int axis = total_bounds.longestDimension();
		
		float v = 0.0f;
		int ad = Integer.MAX_VALUE;
		
		// find the splitting plane which evenly divides the two groups
		for (int i=0; i<bounds.size(); ++i)
		{
			float v_min = bounds.get(i).Pmin.get(axis);
			float v_max = bounds.get(i).Pmax.get(axis);
			
			int ad_min = checkPlane(axis, v_min, bounds);
			int ad_max = checkPlane(axis, v_max, bounds);
			
			if (ad_min < ad)
			{
				v = v_min;
				ad = ad_min;
			}
			
			if (ad_max < ad)
			{
				v = v_max;
				ad = ad_max;
			}
			
		}
		
		ArrayList<Intersectable> left_its = new ArrayList<Intersectable>();
		ArrayList<Box3> left_bounds = new ArrayList<Box3>();
		Box3 left_total = new Box3();
		
		ArrayList<Intersectable> right_its = new ArrayList<Intersectable>();
		ArrayList<Box3> right_bounds = new ArrayList<Box3>();
		Box3 right_total = new Box3();
		
		for (int i=0; i<bounds.size(); ++i)
		{
			Intersectable st = its.get(i);
			Box3 bt = bounds.get(i);
			float c = (bt.Pmin.get(axis) + bt.Pmax.get(axis))/2.0f;

			if (c <= v)
			{
				left_its.add(st);
				left_bounds.add(bt);
				left_total.unionWith(bt);
			}
			else
			{
				right_its.add(st);
				right_bounds.add(bt);
				right_total.unionWith(bt);
			}
		}
		
		KDTreeNode left = buildTree(left_its, left_bounds, left_total, depth+1);
		KDTreeNode right = buildTree(right_its, right_bounds, right_total, depth+1);
		return new KDTreeNode(total_bounds, left, right);
	}
	
	private int checkPlane(int axis, float v, ArrayList<Box3> bounds)
	{
		int n_left = 0, n_right = 0;
		for (int i=0; i<bounds.size(); ++i)
		{
			float c = (bounds.get(i).Pmin.get(axis) + bounds.get(i).Pmax.get(axis))/2.0f;
			if (c <= v)
			{
				n_left++;
			}
			else
			{
				n_right++;
			}
		}
		return Math.abs(n_left-n_right);
	}
	
	
	public Intersection getIntersection(Ray3 rw)
    {
		return getIntersection(rw, root);
    }
	
	// possible optimization: pass an intersection as a parameter
	// and check if it.T is less than the box's T, enabling you to skip checking an entire node
	public Intersection getIntersection(Ray3 rw, KDTreeNode node)
	{
		if (node.bounds.getIntersection(rw) == null)
		{
			return null;
		}
		
		if (node.its == null)
		{
			Intersection left = getIntersection(rw, node.left);
			Intersection right = getIntersection(rw, node.right);
			if (left == null)
			{
				if (right == null)
				{
					return null;
				}
				else
				{
					return right;
				}
			}
			else
			{
				if (right == null)
				{
					return left;
				}
				else
				{
					if (left.T < right.T)
					{
						return left;
					}
					else
					{
						return right;
					}
				}
			}
		}
		else
		{
			Intersection r = null;
			for (int i=0; i<node.its.size(); ++i)
			{
				Intersectable e = node.its.get(i);
				Intersection t = e.getIntersection(rw);
				if (r == null || (t != null && t.T < r.T))
				{
					r = t;
				}
			}
			return r;
		}
	}
	
	
	public boolean isBlocked(Ray3 rw, float d)
	{
		d -= 1.0E-3f;
		return isBlocked(rw, d, root);
	}
	private boolean isBlocked(Ray3 rw, float d, KDTreeNode node)
	{
		float[] nt = node.bounds.getIntersection(rw);
		if (nt == null || nt[0] > d)
		{
			return false;
		}
		
		if (node.its == null)
		{
			return isBlocked(rw, d, node.left) || isBlocked(rw, d, node.right);
		}
		else
		{
			for (int i=0; i<node.its.size(); ++i)
			{
				Intersectable e = node.its.get(i);
				Intersection t = e.getIntersection(rw);
				if (t != null && t.T < d)
				{
					return true;
				}
			}
			return false;
		}
	}
	
	public String toString()
	{
		return toString(root, "");
	}
	private String toString(KDTreeNode node, String prefix)
	{
		String r;
		if (node.its == null)
		{
			r = prefix+"branch\n";
			r += prefix+toString(node.left, prefix+" ")+"\n";
			r += prefix+toString(node.right, prefix+" ")+"\n";
		}
		else
		{
			r = prefix+"leaf: "+node.its.size()+" intersectables";
		}
		return r;
	}
}
