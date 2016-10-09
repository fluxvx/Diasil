package diasil.math.geometry3;

public class RotateXY extends Transform3
{
    public float alpha;
    public float cos_alpha, sin_alpha;
    public RotateXY(float a)
    {
        setAlpha(a);
    }
    public void setAlpha(float a)
    {
        alpha = a;
        alphaChanged();
    }
    public void alphaChanged()
    {
        cos_alpha = (float)(Math.cos(alpha));
        sin_alpha = (float)(Math.sin(alpha));
    }
    public Point3 toWorldSpace(Point3 p)
    {
        return new Point3(p.X*cos_alpha + p.Y*sin_alpha,
                            -p.X*sin_alpha + p.Y*cos_alpha,
                            p.Z);
    }
    public Point3 toObjectSpace(Point3 p)
    {
        return new Point3(p.X*cos_alpha - p.Y*sin_alpha,
                            p.X*sin_alpha + p.Y*cos_alpha,
                            p.Z);
    }
    public Vector3 toWorldSpace(Vector3 v)
    {
        return new Vector3(v.X*cos_alpha + v.Y*sin_alpha,
                            -v.X*sin_alpha + v.Y*cos_alpha,
                            v.Z);
    }
    public Vector3 toObjectSpace(Vector3 v)
    {
        return new Vector3(v.X*cos_alpha - v.Y*sin_alpha,
                            v.X*sin_alpha + v.Y*cos_alpha,
                            v.Z);

    }
    
    public Normal3 toWorldSpace(Normal3 n)
    {
        return new Normal3(n.X*cos_alpha + n.Y*sin_alpha,
                            -n.X*sin_alpha + n.Y*cos_alpha,
                            n.Z);
    }
    public Normal3 toObjectSpace(Normal3 n)
    {
        return new Normal3(n.X*cos_alpha - n.Y*sin_alpha,
                            n.X*sin_alpha + n.Y*cos_alpha,
                            n.Z);

    }
	
	public MTransform3 toMTransform3()
	{
		MTransform3 r = new MTransform3();
		r.setAsRotatorXY(alpha);
		return r;
	}
}