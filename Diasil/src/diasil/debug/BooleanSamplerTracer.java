package diasil.debug;

import diasil.Scene;
import diasil.camera.Camera;
import diasil.color.RGBColor;
import diasil.color.RGBImage;
import diasil.intersect.Intersection;
import diasil.math.geometry3.Ray3;
import diasil.sample.Sample;
import diasil.sample.Sampler;

public class BooleanSamplerTracer
{
	private Sampler sampler;
	public BooleanSamplerTracer(Sampler sampler)
	{
		this.sampler = sampler;
	}
    public void render(Scene scene, RGBImage image)
    {
        Camera camera = scene.getCurrentCamera();
        float min_t=Float.POSITIVE_INFINITY, max_t=Float.NEGATIVE_INFINITY;
        for (int i=0; i<image.width(); ++i)
        {
            for (int j=0; j<image.height(); ++j)
            {
				Sample[] samples = sampler.regenerateSamples(i, j, image.width, image.height);
				float v = 0.0f;
				for (int k=0; k<samples.length; ++k)
				{
					Sample s = samples[k];
					Ray3 r = camera.generateRay(s);
					Intersection it = scene.aggregate.getIntersection(r);
					if (it != null)
					{
						v += 1.0f;
					}
				}
				v /= samples.length;
				image.X[i][j] = new RGBColor(v);
            }
        }
    }
}
