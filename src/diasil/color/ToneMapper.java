package diasil.color;

public abstract class ToneMapper
{
	public final float display_luminance;
	public ToneMapper(float display_luminance)
	{
		this.display_luminance = display_luminance;
	}
    public abstract float[][] toneMap(XYZImage img);
}
