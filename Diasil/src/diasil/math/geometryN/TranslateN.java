package diasil.math.geometryN;

public class TranslateN implements TransformN
{
    public float[] T;
    public TranslateN(float[] t)
    {
        T = t;
    }
    public int N()
    {
        return T.length;
    }
    public void transform(PointN p)
    {
        int min_n = (T.length < p.X.length)? T.length: p.X.length;
        for (int i=0; i<min_n; ++i)
        {
            p.X[i] += T[i];
        }
    }
    public void invert(PointN p)
    {
        int min_n = (T.length < p.X.length)? T.length: p.X.length;
        for (int i=0; i<min_n; ++i)
        {
            p.X[i] -= T[i];
        }
    }
    public void transform(VectorN v) {}
    public void invert(VectorN v) {}
}