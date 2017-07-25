package diasil.math.geometryN;

import java.util.Arrays;

public class ScaleN implements TransformN
{
    public float[] S;
    public ScaleN(float[] s)
    {
        S = Arrays.copyOf(s, s.length);
    }
    public int N()
    {
        return S.length;
    }
    public void transform(PointN p)
    {
        int min_n = (S.length < p.X.length)? S.length: p.X.length;
        for (int i=0; i<min_n; ++i)
        {
            p.X[i] *= S[i];
        }
    }
    public void invert(PointN p)
    {
        int min_n = (S.length < p.X.length)? S.length: p.X.length;
        for (int i=0; i<min_n; ++i)
        {
            p.X[i] /= S[i];
        }
    }
    public void transform(VectorN v)
    {
        int min_n = (S.length < v.X.length)? S.length: v.X.length;
        for (int i=0; i<min_n; ++i)
        {
            v.X[i] *= S[i];
        }
    }
    public void invert(VectorN v)
    {
        int min_n = (S.length < v.X.length)? S.length: v.X.length;
        for (int i=0; i<min_n; ++i)
        {
            v.X[i] /= S[i];
        }
    }
}