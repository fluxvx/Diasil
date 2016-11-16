package diasil.math.geometryN;

import diasil.math.DMath;

public class PointN extends TupleN
{
    public PointN(int n)
    {
        super(n);
    }
    public PointN(TupleN t)
    {
        super(t);
    }
    
    public float distance2(PointN p)
    {
        float r = 0.0f;
        int min_n = Math.min(X.length, p.X.length);
        int i=0;
        
        while (i < min_n)
        {
            float d = X[i] - p.X[i];
            r += d*d;
            ++i;
        }
        
        while (i < X.length)
        {
            r += X[i]*X[i];
            ++i;
        }
        
        while (i < p.X.length)
        {
            r += p.X[i]*p.X[i];
            ++i;
        }
        
        return r;
    }
    
    public float distance(PointN p)
    {
        return DMath.sqrt(distance2(p));
    }
}
