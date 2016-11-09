package diasil.intersect;

import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Transform3;
import diasil.math.geometry3.Vector3;

public class SurfaceGeometry
{
    public Point3 P;
	public Vector3 R;
    public Normal3 N;
    public float U, V;
    public Vector3 dPdU, dPdV;
	
	public SurfaceGeometry(Intersection it, Normal3 n, float u, float v, Vector3 dpdu, Vector3 dpdv, Transform3 trn)
	{
		P = it.P;
		R = new Vector3(it.Wo);
		
		N = trn.toWorldSpace(n);
		U = u;
		V = v;
		dPdU = trn.toWorldSpace(dpdu);
		dPdV = trn.toWorldSpace(dpdv);
		
		N.normalize();
	}
	public Vector3 toLocalSpace(Vector3 v)
	{
		return new Vector3(dPdU.X*v.X + dPdU.Y*v.Y + dPdU.Z*v.Z,
							dPdV.X*v.X + dPdV.Y*v.Y + dPdV.Z*v.Z,
							N.X*v.X + N.Y*v.Y + N.Z*v.Z);
	}
	public Vector3 toWorldSpace(Vector3 v)
	{
		return new Vector3(dPdU.X*v.X + dPdV.X*v.Y + N.X*v.Z,
							dPdU.Y*v.X + dPdV.Y*v.Y + N.Y*v.Z,
							dPdU.Z*v.X + dPdV.Z*v.Y + N.Z*v.Z);
	}
	public static SurfaceGeometry toWorldSpace(Intersection it, Normal3 n, float u, float v, Vector3 dpdu, Vector3 dpdv, Transform3 trn)
	{
		return new SurfaceGeometry(it, n, u, v, dpdu, dpdv, trn);
	}

}