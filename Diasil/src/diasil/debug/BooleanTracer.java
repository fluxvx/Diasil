package diasil.debug;

import diasil.Scene;
import diasil.camera.Camera;
import diasil.color.RGBColor;
import diasil.color.RGBImage;
import diasil.intersect.Intersection;
import diasil.math.geometry3.Ray3;
import diasil.sample.Sample;

public class BooleanTracer
{
    public void render(Scene scene, RGBImage image)
    {
        Camera camera = scene.getCurrentCamera();
        float min_t=Float.POSITIVE_INFINITY, max_t=Float.NEGATIVE_INFINITY;
        for (int i=0; i<image.width(); ++i)
        {
            for (int j=0; j<image.height(); ++j)
            {
				Sample s = new Sample(0,0);
				s.X = i + 0.5f;
				s.Y = image.height() - j - 1 + 0.5f;
				s.X = 2.0f*s.X/image.width - 1.0f;
				s.Y = 2.0f*s.Y/image.height - 1.0f;
				s.U = 0.0f;
				s.V = 0.0f;
                Ray3 r = camera.generateRay(s);
				Intersection it = new Intersection();
                boolean blocked = scene.aggregate.isBlocked(r, it);
                if (blocked)
                {
					image.X[i][j] = new RGBColor(1.0f);
                }
                else
                {
                    image.X[i][j] = new RGBColor(0.0f);
                }
            }
        }
    }
}
