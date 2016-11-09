package diasil.color;

import diasil.sample.Sample;

public class SimpleFilm implements Film
{
	private RawImage render;
    public SimpleFilm(int w, int h, int samples_per_pixel)
    {
		render = new RawImage(w, h, samples_per_pixel);
    }
    
    public int width()
    {
        return render.width;
    }
    public int height()
    {
        return render.height;
    }
	
    public void recordContribution(int i, int j, int k, Sample s)
    {
		render.X[i][j][k] = new RawSample(s.X, s.Y, s.wavelength, s.intensity);
    }
	
	public XYZImage toXYZ()
	{
		XYZImage r = new XYZImage(render.width, render.height);
		for (int i=0; i<render.width; ++i)
		{
			for (int j=0; j<render.height; ++j)
			{
				r.X[i][j] = new XYZColor(0.0f);
				for (int k=0; k<render.samples_per_pixel; ++k)
				{
					RawSample s = render.X[i][j][k];
					XYZColor c = XYZColor.findForWavelength(s.wavelength);
					r.X[i][j].X += s.intensity * c.X;
					r.X[i][j].Y += s.intensity * c.Y;
					r.X[i][j].Z += s.intensity * c.Z;
				}
			}
		}
		return r;
	}
    
    public RGBImage toRGB(XYZImage image, XYZtoRGB xyz_to_rgb, ToneMapper tone_mapper)
    {	
		float[][] tone_map_weights = tone_mapper.toneMap(image);
        RGBImage r = new RGBImage(image.width(), image.height());
        for (int i=0; i<r.width(); ++i)
        {
            for (int j=0; j<r.height(); ++j)
            {
				RGBColor c = xyz_to_rgb.toRGB(image.X[i][j]);
				
				c.R *= tone_map_weights[i][j];
				c.G *= tone_map_weights[i][j];
				c.B *= tone_map_weights[i][j];
				
				c.R = gammaEncode(c.R);
				c.G = gammaEncode(c.G);
				c.B = gammaEncode(c.B);
				
                r.X[i][j] = c;
            }
        }
        return r;
    }
	public static float gammaEncode(float c)
	{
		if (c <= 0.0031308f)
		{
			return 12.92f*c;
		}
		return 1.055f*(float)Math.pow(c, 1/2.4) - 0.055f;
	}
	public static float gammaDecode(float c)
	{
		if (c <= 0.04045f)
		{
			return c/12.92f;
		}
		return (float)Math.pow((c+0.055f)/1.055f, 2.4f);
	}
}
