package diasil.intersect;

import diasil.material.Material;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Transform3;

public class Sphereflake extends KDTree
{
	private int max_iterations;
	private float scale;
	private Material material;
	private Transform3 transformation;
	public Sphereflake(int max_iterations, float radius, float scale, Material material, Transform3 transformation)
	{
		super(3, 10);
		this.max_iterations = max_iterations;
		this.scale = scale;
		this.material = material;
		this.transformation = transformation;
		
		Point3 p = new Point3(0,0,0);
		addSphere(radius, p);
		createBranch(1, p, radius, 0);
		createBranch(-1, p, radius, 0);
		createBranch(2, p, radius, 0);
		createBranch(-2, p, radius, 0);
		createBranch(3, p, radius, 0);
		createBranch(-3, p, radius, 0);
		super.buildTree();
	}
	
	private void addSphere(float radius, Point3 p)
	{
		Sphere sphere = new Sphere(radius, material);
		sphere.translate(p.X, p.Y, p.Z);
		sphere.transforms.add(transformation);
		sphere.compileTransforms();
		super.add(sphere);
	}
	
	public void createBranch(int direction, Point3 last_point, float last_radius, int depth)
	{
		if (depth > max_iterations)
		{
			return;
		}
		
		float r = last_radius*scale;
		Point3 p = new Point3(last_point);
		float pm = last_radius + r;
		
		if (direction == 1)
		{
			p.X += pm;
		}
		else if (direction == -1)
		{
			p.X -= pm;
		}
		else if (direction == 2)
		{
			p.Y += pm;
		}
		else if (direction == -2)
		{
			p.Y -= pm;
		}
		else if (direction == 3)
		{
			p.Z += pm;
		}
		else if (direction == -3)
		{
			p.Z -= pm;
		}
		
		addSphere(r, p);
		
		int dp1 = depth+1;
		if (direction != 1) createBranch(-1, p, r, dp1);
		if (direction != -1) createBranch(1, p, r, dp1);
		if (direction != 2) createBranch(-2, p, r, dp1);
		if (direction != -2) createBranch(2, p, r, dp1);
		if (direction != 3) createBranch(-3, p, r, dp1);
		if (direction != -3) createBranch(3, p, r, dp1);
	}
}
