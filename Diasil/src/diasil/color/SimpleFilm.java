package diasil.color;

import diasil.sample.Sample;

public class SimpleFilm implements Film
{
	private XYZImage render;
    public SimpleFilm(int w, int h)
    {
		render = new XYZImage(w, h);
		for (int i=0; i<render.width(); ++i)
		{
			for (int j=0; j<render.height(); ++j)
			{
				render.X[i][j] = new XYZColor(0.0f, 0.0f, 0.0f);
			}
		}
    }
    
    public int width()
    {
        return render.width();
    }
    public int height()
    {
        return render.height();
    }
	
    public void recordContribution(int i, int j, int k, Sample s)
    {
		XYZColor c = render.X[i][j];
		XYZColor cmf = XYZColor.findForWavelength(s.wavelength);
		c.X += s.intensity*cmf.X;
		c.Y += s.intensity*cmf.Y;
		c.Z += s.intensity*cmf.Z;
		
    }
    
    public RGBImage toRGB(XYZtoRGB xyz_to_rgb, ToneMapper tone_mapper)
    {	
		float[][] tone_map_weights = tone_mapper.toneMap(render);
        RGBImage r = new RGBImage(render.width(), render.height());
        for (int i=0; i<r.width(); ++i)
        {
            for (int j=0; j<r.height(); ++j)
            {
				RGBColor c = xyz_to_rgb.toRGB(render.X[i][j]);
				
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
