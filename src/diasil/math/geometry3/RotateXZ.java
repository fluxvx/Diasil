package diasil.math.geometry3;

public class RotateXZ extends Transform3
{
    public float alpha;
    public float cos_alpha, sin_alpha;
    public RotateXZ(float a)
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
        return new Point3(p.X*cos_alpha + p.Z*sin_alpha,
                            p.Y,
                            -p.X*sin_alpha + p.Z*cos_alpha);
    }
    public Point3 toObjectSpace(Point3 p)
    {
        return new Point3(p.X*cos_alpha - p.Z*sin_alpha,
                            p.Y,
                            p.X*sin_alpha + p.Z*cos_alpha);
    }
    public Vector3 toWorldSpace(Vector3 v)
    {
        return new Vector3(v.X*cos_alpha + v.Z*sin_alpha,
                            v.Y,
                            -v.X*sin_alpha + v.Z*cos_alpha);
    }
    public Vector3 toObjectSpace(Vector3 v)
    {
        return new Vector3(v.X*cos_alpha - v.Z*sin_alpha,
                            v.Y,
                            v.X*sin_alpha + v.Z*cos_alpha);
    }
    public Normal3 toWorldSpace(Normal3 n)
    {
        return new Normal3(n.X*cos_alpha + n.Z*sin_alpha,
                    n.Y,
                    -n.X*sin_alpha + n.Z*cos_alpha);
    }
    public Normal3 toObjectSpace(Normal3 n)
    {
        return new Normal3(n.X*cos_alpha - n.Z*sin_alpha,
                    n.Y,
                    n.X*sin_alpha + n.Z*cos_alpha);
    }
	
	public MTransform3 toMTransform3()
	{
		MTransform3 r = new MTransform3();
		r.setAsRotatorXZ(alpha);
		return r;
	}
}
