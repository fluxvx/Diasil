package diasil.math.geometryN;

public interface TransformN
{
    public abstract int N();
    
    public abstract void transform(PointN p);
    public abstract void invert(PointN p);
    
    public abstract void transform(VectorN v);
    public abstract void invert(VectorN v);
}

