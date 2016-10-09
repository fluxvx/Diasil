package diasil.camera;

import diasil.math.geometry2.Transform2;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Ray3;

public abstract class Camera extends CoordinateSpace3
{
    public Transform2 screen_space;
    public Camera()
    {
        screen_space = new Transform2();
    }
    public abstract Ray3 generateRay(float x, float y, float w, float h);
}
