package diasil.intersect;

import diasil.material.Material;
import diasil.math.DMath;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.SphericalFunction;
import diasil.math.geometry3.Transform3;

public class SphericalSubdivisionTriangleMesh extends TriangleMesh
{
	// http://www.opengl.org.ru/docs/pg/0208.html
	public SphericalSubdivisionTriangleMesh(SphericalFunction sf, int n_subdivisions, int n_spheres, Material material, Transform3 transform)
	{
		super(3, 10, material, transform);
		
		float X = 0.525731112119133606f;
		float Z = 0.850650808352039932f;

		float[][] pts = {    
		   {-X, 0.0f, Z}, {X, 0.0f, Z}, {-X, 0.0f, -Z}, {X, 0.0f, -Z},    
		   {0.0f, Z, X}, {0.0f, Z, -X}, {0.0f, -Z, X}, {0.0f, -Z, -X},    
		   {Z, X, 0.0f}, {-Z, X, 0.0f}, {Z, -X, 0.0f}, {-Z, -X, 0.0f} 
		};
		int[][] tids = { 
		   {0,4,1}, {0,9,4}, {9,5,4}, {4,5,8}, {4,8,1},    
		   {8,10,1}, {8,3,10}, {5,3,8}, {5,2,3}, {2,7,3},    
		   {7,10,3}, {7,6,10}, {7,11,6}, {11,0,6}, {0,1,6}, 
		   {6,1,10}, {9,0,11}, {9,11,2}, {9,2,5}, {7,2,11}};
		
		Triangle[] ts = new Triangle[tids.length];
		for (int i=0; i<ts.length; ++i)
		{
			float[] f0 = pts[tids[i][0]];
			float[] f1 = pts[tids[i][1]];
			float[] f2 = pts[tids[i][2]];
			Point3 p0 = new Point3(f0[0], f0[1], f0[2]);
			Point3 p1 = new Point3(f1[0], f1[1], f1[2]);
			Point3 p2 = new Point3(f2[0], f2[1], f2[2]);
			ts[i] = new Triangle(transform.toWorldSpace(p0),
									transform.toObjectSpace(p1),
									transform.toWorldSpace(p2), this);
		}
		
		// TODO: create the triangle array of its final length
		// and keep expanding those elements within it
		Triangle[] r = ts;
		for (int i=0; i<n_subdivisions; ++i)
		{
			r = new Triangle[4*ts.length];
			for (int j=0; j<ts.length; ++j)
			{
				subdivide(ts, r, j);
			}
			ts = r;
		}
		
		for (int ns=0; ns<n_spheres; ++ns)
		{
			for (int i=0; i<r.length; ++i)
			{
				Triangle t = r[i];
				t.v0 = adjust(t.v0, ns, sf);
				t.v1 = adjust(t.v1, ns, sf);
				t.v2 = adjust(t.v2, ns, sf);
				super.add(t);
			}
		}
		super.buildTree();
	}
	
	private Point3 adjust(Point3 p, int n_sphere, SphericalFunction sf)
	{
		Point3 ps = SphericalFunction.toSpherical(p);
		ps.Z = sf.r(ps.X + n_sphere*DMath.PI2,
					ps.Y + n_sphere*DMath.PI);
		return SphericalFunction.toRectangular(ps);
	}
	
	public void subdivide(Triangle[] ts, Triangle[] r, int ind)
	{
		Triangle t = ts[ind];
		Ray3 e1 = new Ray3(t.v0, t.v1);
		Ray3 e2 = new Ray3(t.v0, t.v2);
		Ray3 e3 = new Ray3(t.v1, t.v2);
		
		Point3 va = e1.pointAt(0.5f);
		Point3 vb = e2.pointAt(0.5f);
		Point3 vc = e3.pointAt(0.5f);
		
		r[(ind << 2)+0] = new Triangle(t.v0, va, vb, this);
		r[(ind << 2)+1] = new Triangle(t.v1, va, vc, this);
		r[(ind << 2)+2] = new Triangle(t.v2, vc, vb, this);
		r[(ind << 2)+3] = new Triangle(va, vb, vc, this);
	}
}
