package diasil.math.geometryN;

import diasil.math.DMath;

public final class RotatePlaneN implements TransformN
{
    public int I, J, N;
    private float angle, cosa, sina;
    public RotatePlaneN(float a, int i, int j, int n)
    {
        angle = a;
        I = i;
        J = j;
        N = n;
    }
    public int N()
    {
        return N;
    }
    public void setAngle(float a)
    {
        angle = a;
        cosa = DMath.cos(angle);
        sina = DMath.sin(angle);
    }
    public void transform(PointN p)
    {
        float xi = p.X[I]*cosa + p.X[J]*sina;
        float xj = -p.X[I]*sina + p.X[J]*cosa;
        p.X[I] = xi;
        p.X[J] = xj;
    }
    public void invert(PointN p)
    {
        float xi = p.X[I]*cosa - p.X[J]*sina;
        float xj = p.X[I]*sina + p.X[J]*cosa;
        p.X[I] = xi;
        p.X[J] = xj;
    }
    public void transform(VectorN v)
    {
        float xi = v.X[I]*cosa + v.X[J]*sina;
        float xj = -v.X[I]*sina + v.X[J]*cosa;
        v.X[I] = xi;
        v.X[J] = xj;
    }
    public void invert(VectorN v)
    {
        float xi = v.X[I]*cosa - v.X[J]*sina;
        float xj = v.X[I]*sina + v.X[J]*cosa;
        v.X[I] = xi;
        v.X[J] = xj;
    }
}