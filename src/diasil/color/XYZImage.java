package diasil.color;

public class XYZImage
{
	public XYZColor[][] X;
	public XYZImage(int width, int height)
	{
		X = new XYZColor[width][height];
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
