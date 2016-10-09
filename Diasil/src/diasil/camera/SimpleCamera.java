package diasil.camera;

import diasil.math.geometry2.Point2;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class SimpleCamera extends Camera
{
    private float project_plane;
    public SimpleCamera(float p)
    {
        project_plane = p;
    }
    public Ray3 generateRay(float x, float y, float w, float h)
    {
        float s = Math.max(w, h);
        float xp = 2.0f*x/s - 1.0f;
        float yp = 2.0f*y/s - 1.0f;
        
        Point2 ps = screen_space.toWorldSpace(new Point2(xp, yp));
        
        Ray3 r;
		if (project_plane < 0)
		{
			r = new Ray3(new Point3(ps.X, ps.Y, 0.0f),
							new Vector3(0.0f, 0.0f, 1.0f));
		}
		else
		{
			r = new Ray3(new Point3(0.0f, 0.0f, 0.0f),
							new Vector3(ps.X, ps.Y, project_plane));
		}
        r = super.toWorldSpace(r);
        r.D.normalize();

        return r;
    }
}
