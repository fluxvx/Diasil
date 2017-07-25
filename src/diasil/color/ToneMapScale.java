package diasil.color;

public class ToneMapScale extends ToneMapper
{
	private float scale;
	public ToneMapScale(float scale)
	{
		super(1.0f);
		this.scale = scale;
	}
	public float[][] toneMap(XYZImage img)
	{
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
