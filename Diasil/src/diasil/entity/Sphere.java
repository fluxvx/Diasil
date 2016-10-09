package diasil.entity;

import diasil.material.LightRay;
import diasil.material.Material;
import diasil.math.DMath;
import diasil.math.geometry3.Box3;
import diasil.math.geometry3.Normal3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class Sphere extends Entity
{
    public float radius;
    public Sphere(float r, Material m)
    {
        super(m);
        radius = r;
    }
    
    public Intersection getIntersection(Ray3 ro)
    {
        float a = ro.D.dot(ro.D);
        float b = 2.0f*(ro.O.X*ro.D.X + ro.O.Y*ro.D.Y + ro.O.Z*ro.O.Z);
        float c = ro.O.X*ro.O.X + ro.O.Y*ro.O.Y + ro.O.Z*ro.O.Z - radius*radius; 
        float[] t = DMath.quadratic(a, b, c);
        
        if (t.length != 0 && LightRay.isValid(t[0]))
        {
            return new Intersection(ro, t[0], this);
        }

        if (t.length == 2 && LightRay.isValid(t[1]))
        {
            return new Intersection(ro, t[1], this);
        }
        
        return null;
    }
    
    public SurfaceGeometry getSurfaceGeometry(Intersection it)
    {
        Point3 p = it.Po;
        Normal3 N = new Normal3(p.X, p.Y, p.Z);
        N.normalize();
        Vector3[] t = N.formBasis();
        Vector3 dpdu = new Vector3(t[0]);
        Vector3 dpdv = new Vector3(t[1]);
        Vector3 dndu = dpdu;
        Vector3 dndv = dpdv;
        return new SurfaceGeometry(it.Po, dpdu, dpdv, N, dndu, dndv, it.Vo);
    }
    
    public Box3 getBoundingBox()
    {
        Point3 pmin = new Point3(-radius, -radius, -radius);
        Point3 pmax = new Point3 (radius, radius, radius);
        return new Box3(pmin, pmax);
    }
}