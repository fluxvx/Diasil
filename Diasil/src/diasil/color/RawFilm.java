package diasil.color;

import diasil.sample.Sample;

public class RawFilm implements Film
{
	public RawSample[][][] X;
	public final int width, height, samples_per_pixel;
	public RawFilm(int width, int height, int samples_per_pixel)
	{
		this.width = width;
		this.height = height;
		this.samples_per_pixel = samples_per_pixel;
		X = new RawSample[width][height][samples_per_pixel];
	}
	public int width()
	{
		return width;
	}
	public int height()
	{
		return height;
	}
	public void recordContribution(int i, int j, int k, Sample s)
	{
		X[i][j][k] = new RawSample(s.X, s.Y, s.wavelength, s.intensity);
	}
	
	public XYZImage toXYZ()
	{
		XYZImage r = new XYZImage(width, height);
		for (int i=0; i<width; ++i)
		{
			for (int j=0; j<height; ++j)
			{
				r.X[i][j] = new XYZColor(0.0f);
				for (int k=0; k<samples_per_pixel; ++k)
				{
					RawSample s = X[i][j][k];
					XYZColor c = XYZColor.findForWavelength(s.wavelength);
					r.X[i][j].X += s.intensity * c.X;
					r.X[i][j].Y += s.intensity * c.Y;
					r.X[i][j].Z += s.intensity * c.Z;
				}
			}
		}
		return r;
	}
}
