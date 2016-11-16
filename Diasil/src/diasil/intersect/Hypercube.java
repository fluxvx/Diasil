package diasil.intersect;

import diasil.material.Material;
import diasil.math.DMath;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Translate3;
import diasil.math.geometryN.PointN;
import diasil.math.geometryN.RotateN;
import java.util.Random;

public class Hypercube extends CoordinateSpace3 implements Intersectable
{
	private KDTree kdtree;
	private PointN[] vertices;
	private int[][] edges;
	public Hypercube(int n, float r0, float r1, Material m)
	{
		kdtree = new KDTree(3, 10);
		vertices = new PointN[1 << n];
		for (int i=0; i<vertices.length; ++i)
		{
			vertices[i] = new PointN(n);
			int t = i;
			for (int j=0; j<n; ++j)
			{
				vertices[i].X[j] = (t%2 == 0)? -r0: r0;
				t /= 2;
			}
		}
		
		edges = new int[n*vertices.length/2][2];
		for (int i=0, e=0; i<vertices.length; ++i)
		{
			for (int j=i+1; j<vertices.length; ++j)
			{
				boolean one_different = false;
				for (int k=0; k<n; ++k)
				{
					if (vertices[i].X[k] != vertices[j].X[k])
					{
						if (one_different)
						{
							one_different = false;
							break;
						}
						else
						{
							one_different = true;
						}
					}
				}
				if (one_different)
				{
					edges[e][0] = i;
					edges[e][1] = j;
					e++;
				}
			}
		}
		
		Random random = new Random(1000);
		
		RotateN rotate = new RotateN(n);
		//System.out.print("hypercube angle ids: ");
		for (int i=0; i<rotate.rotators.length; ++i)
		{
			int angle_id = (int)(random.nextFloat()*8);
			//System.out.print(angle_id+" ");
			float angle = angle_id/8.0f*DMath.PI2;
			rotate.rotators[i].setAngle(angle);
		}
		//System.out.println();
		
		
		Point3[] v = new Point3[vertices.length];
		for (int i=0; i<v.length; ++i)
		{
			PointN pt = new PointN(vertices[i]);
			rotate.transform(pt);
			v[i] = new Point3(pt.X[0], pt.X[1], pt.X[2]);
		}
		
		for (int i=0; i<v.length; ++i)
		{
			Sphere s = new Sphere(r1, m);
			s.translate(v[i].X, v[i].Y, v[i].Z);
			s.compileTransforms();
			kdtree.add(s);
		}
		for (int i=0; i<edges.length; ++i)
		{
			Cylinder c = new Cylinder(r1, v[edges[i][0]], v[edges[i][1]], m);
			c.compileTransforms();
			kdtree.add(c);
		}
		kdtree.buildTree();
	}
	
	public Intersection getIntersection(Ray3 ray)
	{
		return kdtree.getIntersection(ray);
	}
	
	public SurfaceGeometry getSurfaceGeometry(Point3 p)
	{
		return null;
	}
	
	public Point3 sampleSurface(float u1, float u2)
	{
		return null;
	}
	
	public Material getMaterial()
	{
		return null;
	}
	
	public Box3 getBoundingBox()
	{
		return kdtree.getBoundingBox();
	}
}
