package diasil.color;

public class ToneMapContrast extends ToneMapper
{
    private final float display_adaptation;
	public ToneMapContrast(float display_luminance)
	{
		super(display_luminance);
		this.display_adaptation = display_luminance / 2;
	}
    public ToneMapContrast(float display_luminance, float display_adaptation)
    {
		super(display_luminance);
        this.display_adaptation = display_adaptation;
    }
    public float[][] toneMap(XYZImage img)
    {
        float[][] r = new float[img.width()][img.height()];
        float ywa = 0.0f;
        for (int i=0; i<img.width(); ++i)
        {
            for (int j=0; j<img.height(); ++j)
            {
                float y = img.X[i][j].Y;
                if (y > 0.0f)
                {
                    ywa += (float)Math.log(y);
                }
            }
        }
        ywa = (float)Math.exp(ywa / (img.width()*img.height()));
        float s = (float)Math.pow((1.219 + Math.pow(display_adaptation, 0.4)) /
                        (1.219 + Math.pow(ywa, 0.4)), 2.5);
        for (int i=0; i<img.width(); ++i)
        {
            for (int j=0; j<img.height(); ++j)
            {
                r[i][j] = s;
            }
        }
        return r;
    }

}
