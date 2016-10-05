package diasil.color;

public class ToneMapMaximumToWhite implements ToneMapper
{
    public float scale;
    public ToneMapMaximumToWhite(float s)
    {
        scale = s;
    }
    public DImage<XYZColor> toneMap(DImage<XYZColor> img)
    {
        DImage<XYZColor> r = new DImage<XYZColor>(img.width(), img.height());
        float max = Float.NEGATIVE_INFINITY;
        for (int i=0; i<r.width(); ++i)
        {
            for (int j=0; j<r.height(); ++j)
            {
                max = Math.max(max, img.get(i, j).Y);
            }
        }
        max *= scale;
        float inv_max = 1.0f/max;
        for (int i=0; i<r.width(); ++i)
        {
            for (int j=0; j<r.height(); ++j)
            {
                r.set(i, j, img.get(i, j).multiply(inv_max));
            }
        }
        
        return r;
    }

}
