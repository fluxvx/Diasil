package diasil.intersect;

import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Ray3;
import java.util.ArrayList;



// a simple KDTree implementation
// judge whether an object goes to the left or right based on its center
// left.bounds and right.bounds may overlap
// the same element won't be found in different nodes

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
	
	
	public void closestIntersection(Ray3 rw, Intersection it)
    {
		float[] nt = root.bounds.getIntersection(rw);
		if (nt != null && (it.isValid(nt[0]) || it.isValid(nt[1])))
		{
			closestIntersection(rw, it, root);
		}
	}
	
	// this was painful to write, I wanted to make sure each case was handled properly
	// if I traversed it by splitting plane rather than checking boxes
	// a few of these cases would be superfluous
	public void closestIntersection(Ray3 rw, Intersection it, KDTreeNode node)
	{
		if (node.its == null)
		{
			float[] lt = node.left.bounds.getIntersection(rw);
			float[] rt = node.right.bounds.getIntersection(rw);
			
			if (lt == null)
			{
				if (rt != null && (it.isValid(rt[0]) || it.isValid(rt[1])))
				{
					closestIntersection(rw, it, node.right);
				}
			}
			else
			{
				if (rt == null)
				{
					if (it.isValid(lt[0]) || it.isValid(lt[1]))
					{
						closestIntersection(rw, it, node.left);
					}
				}
				else
				{
					if (it.isValid(lt[0]))
					{
						if (it.isValid(rt[0]))
						{
							if (lt[0] < rt[0])
							{
								closestIntersection(rw, it, node.left);
								if (it.isValid(rt[0])) // check again, as it.T may have been updated
								{
									closestIntersection(rw, it, node.right);
								}
							}
							else
							{
								closestIntersection(rw, it, node.right);
								if (it.isValid(lt[0]))
								{
									closestIntersection(rw, it, node.left);
								}
							}
						}
						else if (it.isValid(rt[1]))
						{
							if (lt[0] < rt[1])
							{
								closestIntersection(rw, it, node.left);
								if (it.isValid(rt[1]))
								{
									closestIntersection(rw, it, node.right);
								}
							}
							else
							{
								closestIntersection(rw, it, node.right);
								if (it.isValid(lt[0]))
								{
									closestIntersection(rw, it, node.left);
								}
							}
						}
						else
						{
							closestIntersection(rw, it, node.left);
						}
					}
					else if (it.isValid(lt[1]))
					{
						if (it.isValid(rt[0]))
						{
							if (lt[1] < rt[0])
							{
								closestIntersection(rw, it, node.left);
								if (it.isValid(rt[0])) // check again, as it.T may have been updated
								{
									closestIntersection(rw, it, node.right);
								}
							}
							else
							{
								closestIntersection(rw, it, node.right);
								if (it.isValid(lt[1]))
								{
									closestIntersection(rw, it, node.left);
								}
							}
						}
						else if (it.isValid(rt[1]))
						{
							if (lt[1] < rt[1])
							{
								closestIntersection(rw, it, node.left);
								if (it.isValid(rt[1]))
								{
									closestIntersection(rw, it, node.right);
								}
							}
							else
							{
								closestIntersection(rw, it, node.right);
								if (it.isValid(lt[1]))
								{
									closestIntersection(rw, it, node.left);
								}
							}
						}
						else
						{
							closestIntersection(rw, it, node.left);
						}
					}
					else if (it.isValid(rt[0]) || it.isValid(rt[1]))
					{
						closestIntersection(rw, it, node.right);
					}
				}
			}
		}
		else
		{
			for (int i=0; i<node.its.size(); ++i)
			{
				Intersectable e = node.its.get(i);
				e.closestIntersection(rw, it);
			}
		}
	}
	
	public boolean isBlocked(Ray3 rw, Intersection it)
	{
		float[] nt = root.bounds.getIntersection(rw);
		if (nt != null && (it.isValid(nt[0]) || it.isValid(nt[1])))
		{
			return isBlocked(rw, it, root);
		}
		return false;
	}
	private boolean isBlocked(Ray3 rw, Intersection it, KDTreeNode node)
	{
		if (node.its == null)
		{
			float[] lt = node.left.bounds.getIntersection(rw);
			float[] rt = node.right.bounds.getIntersection(rw);
			
			if (lt == null)
			{
				if (rt == null)
				{
					return false;
				}
				if (it.isValid(rt[0]) || it.isValid(rt[1]))
				{
					return isBlocked(rw, it, node.right);
				}
				return false;
			}
			
			if (rt == null)
			{
				if (it.isValid(lt[0]) || it.isValid(lt[1]))
				{
					return isBlocked(rw, it, node.left);
				}
				return false;
			}
			
			if (it.isValid(lt[0]))
			{
				if (it.isValid(rt[0]))
				{
					if (lt[0] < rt[0])
					{
						return isBlocked(rw, it, node.left) || isBlocked(rw, it, node.right);
					}
					return isBlocked(rw, it, node.right) || isBlocked(rw, it, node.left);
				}
				if (it.isValid(rt[1]))
				{
					if (lt[0] < rt[1])
					{
						return isBlocked(rw, it, node.left) || isBlocked(rw, it, node.right);
					}
					return isBlocked(rw, it, node.right) || isBlocked(rw, it, node.left);
				}
				return false;
			}
			
			if (it.isValid(lt[1]))
			{
				if (it.isValid(rt[0]))
				{
					if (lt[0] < rt[0])
					{
						return isBlocked(rw, it, node.left) || isBlocked(rw, it, node.right);
					}
					return isBlocked(rw, it, node.right) || isBlocked(rw, it, node.left);
				}
				if (it.isValid(rt[1]))
				{
					if (lt[0] < rt[1])
					{
						return isBlocked(rw, it, node.left) || isBlocked(rw, it, node.right);
					}
					return isBlocked(rw, it, node.right) || isBlocked(rw, it, node.left);
				}
				return false;
			}
			
			if (it.isValid(rt[0]) || it.isValid(rt[1]))
			{
				return isBlocked(rw, it, node.right);
			}
			
			return false;
		}
		
		for (int i=0; i<node.its.size(); ++i)
		{
			if (node.its.get(i).isBlocked(rw, it))
			{
				return true;
			}
		}
		return false;
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
