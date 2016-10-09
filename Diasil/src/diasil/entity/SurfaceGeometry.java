package diasil.entity;

import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.MTransform3;
import diasil.math.geometry3.Vector3;

public class SurfaceGeometry extends MTransform3
{
    // in object space
    public Point3 P;
    public Normal3 N;
    public float U, V;
    public Vector3 R;
    public Vector3 dPdU, dPdV;
    public Vector3 dNdU, dNdV;
    
    public SurfaceGeometry(Point3 p, Normal3 n, Vector3 r)
    {
        P = new Point3(p);
        N = new Normal3(n);
        R = new Vector3(r);
        
        if (N.dot(R) < 0.0f)
        {
            N.negate();
        }
        
        Vector3[] v = N.formBasis();
        dNdU = new Vector3(v[0]);
        dNdV = new Vector3(v[1]);
        
        
        super.setAsBasis(P, dNdU, dNdV, N);
    }
    
    public SurfaceGeometry(Point3 p, Normal3 n, Vector3 dndu, Vector3 dndv, Vector3 r)
    {
        P = new Point3(p);
        N = new Normal3(n);
        R = new Vector3(r);
        dNdU = new Vector3(dndu);
        dNdV = new Vector3(dndv);
        
        if (N.dot(R) < 0.0f)
        {
            N.negate();
        }
        
        super.setAsBasis(P, dNdU, dNdV, N);
    }
    
    public SurfaceGeometry(Point3 p, Vector3 dpdu, Vector3 dpdv, Normal3 n, Vector3 dndu, Vector3 dndv, Vector3 r)
    {
        P = new Point3(p);
        dPdU = new Vector3(dpdu);
        dNdU = new Vector3(dndu);
        N = new Normal3(n);
        R = new Vector3(r);
        dNdU = new Vector3(dndu);
        dNdV = new Vector3(dndv);
        
        if (N.dot(R) < 0.0f)
        {
            N.negate();
        }
        
        super.setAsBasis(P, dNdU, dNdV, N);
    }
    
}

