package diasil.camera;

import diasil.math.geometry2.MTransform2;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Ray3;
import diasil.sample.Sample;

public abstract class Camera extends CoordinateSpace3
{
    public Camera() {}
    public abstract Ray3 generateRay(Sample s);
}
