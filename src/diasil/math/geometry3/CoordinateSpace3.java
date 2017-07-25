package diasil.math.geometry3;

import java.util.ArrayList;

public class CoordinateSpace3 extends MTransform3
{
	private static ArrayList<CoordinateSpace3> coordinate_spaces = new ArrayList<CoordinateSpace3>();
	public static void compileAll()
	{
		for (int i=0; i<coordinate_spaces.size(); ++i)
		{
			coordinate_spaces.get(i).compileTransforms();
		}
	}
	
	/* Here we maintain both a list of transforms and a compiled transform
	 * When an scene is created, an object will be given transforms
	 * When a scene is to be rendered, those transforms are compiled
	 * This enables efficient movement between world- and object-space
	 * and enables transforms to be edited and recompiled for animations
	*/
    public ArrayList<Transform3> transforms;
    public CoordinateSpace3()
    {
        transforms = new ArrayList<Transform3>();
		coordinate_spaces.add(this);
    }	
	public void translate(float tx, float ty, float tz)
	{
		transforms.add(new Translate3(tx, ty, tz));
	}
	public void scale(float sx, float sy, float sz)
	{
		transforms.add(new Scale3(sx, sy, sz));
	}
	public void rotateXY(float a)
	{
		transforms.add(new RotateXY(a));
	}
	public void rotateXZ(float a)
	{
		transforms.add(new RotateXZ(a));
	}
	public void rotateYZ(float a)
	{
		transforms.add(new RotateYZ(a));
	}
	public void compileTransforms()
	{
		super.setAsIdentity();
		for (int i=transforms.size()-1; i>=0; --i)
		{
			MTransform3 t = transforms.get(i).toMTransform3();
			M = M.multiply(t.M);
			I = t.I.multiply(I);
		}
	}
}