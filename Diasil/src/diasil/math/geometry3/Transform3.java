package diasil.math.geometry3;

public abstract class Transform3
{
	public abstract Point3 toWorldSpace(Point3 p);
	public abstract Point3 toObjectSpace(Point3 p);
	public abstract Vector3 toWorldSpace(Vector3 v);
	public abstract Vector3 toObjectSpace(Vector3 v);
	public abstract Normal3 toWorldSpace(Normal3 n);
	public abstract Normal3 toObjectSpace(Normal3 n);
    public Ray3 toWorldSpace(Ray3 r)
    {
        return new Ray3(toWorldSpace(r.O), toWorldSpace(r.D));
    }
    public Ray3 toObjectSpace(Ray3 r)
    {
        return new Ray3(toObjectSpace(r.O), toObjectSpace(r.D));   
    }

	
    public Box3 toWorldSpace(Box3 b)
    {
        Box3 r = new Box3(toWorldSpace(b.Pmin));
        r.unionWith(toWorldSpace(new Point3(b.Pmin.X, b.Pmin.Y, b.Pmax.Z)));
        r.unionWith(toWorldSpace(new Point3(b.Pmin.X, b.Pmax.Y, b.Pmin.Z)));
        r.unionWith(toWorldSpace(new Point3(b.Pmin.X, b.Pmax.Y, b.Pmax.Z)));
        r.unionWith(toWorldSpace(new Point3(b.Pmax.X, b.Pmin.Y, b.Pmin.Z)));
        r.unionWith(toWorldSpace(new Point3(b.Pmax.X, b.Pmin.Y, b.Pmax.Z)));
        r.unionWith(toWorldSpace(new Point3(b.Pmax.X, b.Pmax.Y, b.Pmin.Z)));
        r.unionWith(toWorldSpace(b.Pmax));
        return r;
    }

    public Box3 toObjectSpace(Box3 b)
    {
        Box3 r = new Box3(toObjectSpace(b.Pmin));
        r.unionWith(toObjectSpace(new Point3(b.Pmin.X, b.Pmin.Y, b.Pmax.Z)));
        r.unionWith(toObjectSpace(new Point3(b.Pmin.X, b.Pmax.Y, b.Pmin.Z)));
        r.unionWith(toObjectSpace(new Point3(b.Pmin.X, b.Pmax.Y, b.Pmax.Z)));
        r.unionWith(toObjectSpace(new Point3(b.Pmax.X, b.Pmin.Y, b.Pmin.Z)));
        r.unionWith(toObjectSpace(new Point3(b.Pmax.X, b.Pmin.Y, b.Pmax.Z)));
        r.unionWith(toObjectSpace(new Point3(b.Pmax.X, b.Pmax.Y, b.Pmin.Z)));
        r.unionWith(toObjectSpace(b.Pmax));
        return r;
    }
	public abstract MTransform3 toMTransform3();
}
