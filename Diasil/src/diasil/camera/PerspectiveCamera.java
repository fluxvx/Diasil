package diasil.camera;

import diasil.math.geometry2.Point2;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sample;
import diasil.sample.Sample;

public class PerspectiveCamera extends Camera
{
	private float lens_radius; // radius of the lens, set to 0 for perfect sharpness
	private float focal_plane; // distance between the lens and the plane of focus
	private float fov_scale;
    public PerspectiveCamera(float field_of_view, float focal_plane, float lens_radius)
    {
        this.focal_plane = focal_plane;
		this.lens_radius = lens_radius;
		fov_scale = focal_plane*((float)(Math.tan(field_of_view/2.0)));
    }
    public Ray3 generateRay(Sample s)
    {
		Ray3 r = new Ray3(new Point3(0.0f, 0.0f, 0.0f),
							new Vector3(s.X*fov_scale, s.Y*fov_scale, focal_plane));
		if (lens_radius > 0.0f)
		{
			Point2 p_lens = Sample.ConcentricSampleDisk(s.U, s.V);
			p_lens.X *= lens_radius;
			p_lens.Y *= lens_radius;
			
			// find the new point on the focal plane
			float ft = focal_plane / r.D.Z;
			Point3 p_focus = r.pointAt(ft);
			
			// update ray
			r.O = new Point3(s.U, s.V, 0.0f);
			r.D = new Vector3(r.O, p_focus);
		}
		Ray3 rw = toWorldSpace(r);
		rw.D.normalize();
        return rw;
    }
}
