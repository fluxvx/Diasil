package diasil.color;

import diasil.sample.Sample;

public class RawImage
{
	public RawSample[][][] X;
	public final int width, height, samples_per_pixel;
	public RawImage(int width, int height, int samples_per_pixel)
	{
		this.width = width;
		this.height = height;
		this.samples_per_pixel = samples_per_pixel;
		X = new RawSample[width][height][samples_per_pixel];
	}
}
