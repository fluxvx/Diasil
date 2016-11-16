package diasil.math.geometryN;

import java.util.ArrayList;

public class CoordinateSpaceN implements TransformN
{
    public ArrayList<TransformN> transforms;
    private int n;
    
    public CoordinateSpaceN(int n)
    {
        transforms = new ArrayList<TransformN>();
        this.n = n;
    }
    
    public int N()
    {
        return n;
    }
    
    public void transform(PointN p)
    {
        for (int i=0; i<transforms.size(); ++i)
        {
            transforms.get(i).transform(p);
        }
    }
    
    public void invert(PointN p)
    {
        for (int i=transforms.size()-1; i>=0; --i)
        {
            transforms.get(i).invert(p);
        }
    }
    
    public void transform(VectorN v)
    {
        for (int i=0; i<transforms.size(); ++i)
        {
            transforms.get(i).transform(v);
        }
    }
    
    public void invert(VectorN v)
    {
        for (int i=transforms.size()-1; i>=0; --i)
        {
            transforms.get(i).invert(v);
        }
    }
}

