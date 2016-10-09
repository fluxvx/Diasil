package diasil.entity;

import diasil.material.LightRay;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class Intersection
{
    public Ray3 Ro;
    public LightRay Rw;
    public Point3 Pw, Po;
    public Vector3 Vw, Vo;
    public float T;
    public Entity E;
    
    public Intersection() {}
    public Intersection(Ray3 ro, float t, Entity i)
    {
        Ro = new Ray3(ro);
        Po = Ro.pointAt(t);
        Vo = new Vector3(ro.D);
        Vo.negate();
        T = t;
    }
    public Intersection(LightRay rw, Ray3 ro, float t, Entity i)
    {
        Rw = new LightRay(rw);
        Ro = new Ray3(ro);
        Pw = Rw.pointAt(t);
        Po = Ro.pointAt(t);
        Vw = new Vector3(Rw.D);
        Vo = new Vector3(Ro.D);
        Vw.negate();
        Vo.negate();
        T = t;
    }
    public void setRw(LightRay rw)
    {
        Rw = new LightRay(rw);
        Pw = Rw.pointAt(T);
        Vw = new Vector3(Rw.D);
        Vw.negate();
    }
    public SurfaceGeometry getSurfaceGeometry()
    {
        return E.getSurfaceGeometry(this);
    }
}
