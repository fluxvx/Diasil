package diasil.color;

import diasil.math.DMath;

public class ToneMapMean extends ToneMapper
{
	public ToneMapMean(float display_luminance)
	{
		super(display_luminance);
	}
	public float[][] toneMap(XYZImage img)
	{
		int w = img.width(), h = img.height();
		int n = w*h;
		
		float mean = 0.0f;
		for (int i=0; i<w; ++i)
		{
			for (int j=0; j<h; ++j)
			{
				mean += img.X[i][j].Y;
			}
		}
		mean /= n;
		
		float sd = 0.0f;
		for (int i=0; i<w; ++i)
		{
			for (int j=0; j<h; ++j)
			{
				float t = (img.X[i][j].Y - mean);
				sd += t*t;
			}
		}
		sd = DMath.sqrt(sd/n);
		
		float scale = display_luminance/(mean);
		float[][] r = new float[img.width()][img.height()];
		for (int i=0; i<r.length; ++i)
		{
			for (int j=0; j<r.length; ++j)
			{
				r[i][j] = scale;
			}
		}
		return r;
	}
	
}
