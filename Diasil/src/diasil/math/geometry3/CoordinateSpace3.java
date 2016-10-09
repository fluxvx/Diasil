package diasil.math.geometry3;

import java.util.ArrayList;

public class CoordinateSpace3 extends MTransform3
{
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
    }
	
	public void compileTransforms()
	{
		if (transforms.isEmpty())
		{
			super.setAsIdentity();
		}
		else
		{
			MTransform3 compiled = transforms.get(0).toMTransform3();
			for (int i=1; i<transforms.size(); ++i)
			{
				compiled = compiled.multiply(transforms.get(i).toMTransform3());
			}
			super.M = compiled.M;
			super.I = compiled.I;
		}
	}
}