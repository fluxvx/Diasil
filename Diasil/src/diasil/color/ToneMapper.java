package diasil.color;

public interface ToneMapper
{
    public DImage<XYZColor> toneMap(DImage<XYZColor> img);
}
