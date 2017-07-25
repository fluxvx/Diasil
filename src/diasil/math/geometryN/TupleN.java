package diasil.math.geometryN;

import java.util.Arrays;

public class TupleN
{
    public float[] X;
    public TupleN(int n)
    {
        X = new float[n];
    }
    
    public TupleN(float[] x)
    {
        X = Arrays.copyOf(x, x.length);
    }
    public TupleN(float[] x, int n)
    {
        X = Arrays.copyOf(x, n);
    }
    public TupleN(TupleN t)
    {
        X = new float[t.X.length];
        for (int i=0; i<X.length; ++i)
        {
            X[i] = t.X[i];
        }
    }
    public int N()
    {
        return X.length;
    }
}