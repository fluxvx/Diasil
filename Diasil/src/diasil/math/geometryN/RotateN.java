package diasil.math.geometryN;

public class RotateN implements TransformN
{
    private int n;
    public RotatePlaneN[] rotators;
    public RotateN(int n)
    {
        this.n = n;
        rotators = new RotatePlaneN[n*(n-1)/2];
        for (int i=0, k=0; i<n; ++i)
        {
            for (int j=i+1; j<n; ++j, ++k)
            {
                rotators[k] = new RotatePlaneN(0.0f, i, j, n);
            }
        }
    }
    public int N()
    {
        return n;
    }
    public void transform(PointN p)
    {
        for (int i=0; i<rotators.length; ++i)
        {
            rotators[i].transform(p);
        }
    }
    public void invert(PointN p)
    {
        for (int i=rotators.length-1; i>=0; --i)
        {
            rotators[i].invert(p);
        }
    }
    public void transform(VectorN v)
    {
        for (int i=0; i<rotators.length; ++i)
        {
            rotators[i].transform(v);
        }
    }
    public void invert(VectorN v)
    {
        for (int i=rotators.length-1; i>=0; --i)
        {
            rotators[i].invert(v);
        }
    }
}