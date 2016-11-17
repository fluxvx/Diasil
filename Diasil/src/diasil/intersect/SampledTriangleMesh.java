package diasil.intersect;

import diasil.material.Material;
import diasil.math.SampleRange;
import diasil.math.geometry3.Function3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Transform3;

public class SampledTriangleMesh extends TriangleMesh
{
	public SampledTriangleMesh(Function3 f, SampleRange rx, SampleRange ry, Transform3 transform, Material material)
	{
		super(3, 10, material);
		Point3[] pts = new Point3[rx.N*ry.N];
		float sx = (rx.wrap)? (rx.B-rx.A)/rx.N: (rx.B-rx.A)/(rx.N-1);
		float sy = (ry.wrap)? (ry.B-ry.A)/ry.N: (ry.B-ry.A)/(ry.N-1);
		for (int i=0; i<rx.N; ++i)
		{
			float x = rx.A + sx*i;
			for (int j=0; j<ry.N; ++j)
			{
				float y = ry.A + sy*j;
				float z = f.Z(x, y);
				int k = i*ry.N + j;
				pts[k] = new Point3(x, y, z);
				if (transform != null)
				{
					pts[k] = transform.toWorldSpace(pts[k]);
				}
			}
		}
		
		int n_triangles = 2*(rx.N-1)*(ry.N-1);
        int[] triangles = new int[3*n_triangles];
        for (int i=0, k=0; i<rx.N-1; ++i)
        {
            for (int j=0; j<ry.N-1; ++j, ++k)
            {
                int pt = i*ry.N + j;
                triangles[3*k] = pt;
                triangles[3*k+1] = pt+1;
                triangles[3*k+2] = pt + ry.N;
                ++k;
                
                triangles[3*k] = pt+1;
                triangles[3*k+1] = pt + ry.N;
                triangles[3*k+2] = pt + ry.N + 1;
            }
        }
        
        for (int i=0; i<n_triangles; ++i)
        {
			Point3 v0 = pts[triangles[3*i]];
			Point3 v1 = pts[triangles[3*i+1]];
			Point3 v2 = pts[triangles[3*i+2]];
            kdtree.add(new Triangle(v0, v1, v2, this));
        }
		
		kdtree.buildTree();
	}

}
