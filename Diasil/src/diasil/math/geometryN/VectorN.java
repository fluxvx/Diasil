package diasil.math.geometryN;

public class VectorN extends TupleN
{
    public VectorN(int n)
    {
        super(n);
    }
    public VectorN(TupleN t)
    {
        super(t);
    }
    
    public float length2()
    {
        float r = 0.0f;
        for (int i=0; i<X.length; ++i)
        {
            r += X[i]*X[i];
        }
        return r;
    }
    public float length()
    {
        return (float)(Math.sqrt(length2()));
    }
}