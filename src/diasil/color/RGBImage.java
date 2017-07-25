package diasil.color;

public class RGBImage
{
	public final RGBColor[][] X;
	public final int width, height;
	public RGBImage(int width, int height)
	{
		this.width = width;
		this.height = height;
		X = new RGBColor[width][height];
	}
	public int width()
	{
		return X.length;
	}
	public int height()
	{
		return X[0].length;
	}
}
