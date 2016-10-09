package diasil.render;

import diasil.Scene;
import diasil.camera.Camera;
import diasil.color.DImage;
import diasil.color.RGBColor;
import diasil.entity.Intersection;
import diasil.math.geometry3.Ray3;

public class DepthTracer
{
    public void render(Scene scene, DImage<RGBColor> image)
    {
        Camera camera = scene.getCurrentCamera();
        float min_t=Float.POSITIVE_INFINITY, max_t=Float.NEGATIVE_INFINITY;
        for (int i=0; i<image.width(); ++i)
        {
            for (int j=0; j<image.height(); ++j)
            {
				float x = i + 0.5f;
				float y = image.height() - j - 1 + 0.5f;
                Ray3 r = camera.generateRay(x, y, image.width(), image.height());
                Intersection it = scene.aggregate.getIntersection(r);
                if (it != null)
                {
                    min_t = Math.min(min_t, it.T);
                    max_t = Math.max(max_t, it.T);
                    image.set(i, j, new RGBColor(it.T));
                }
                else
                {
                    image.set(i, j, new RGBColor(0.0f));
                }
            }
        }
        for (int i=0; i<image.width(); ++i)
        {
            for (int j=0; j<image.height(); ++j)
            {
                float c = image.get(i, j).R;
                c = (c - min_t)/(max_t - min_t);
                image.set(i, j, new RGBColor(c, c, c));
            }
        }
    }
}
