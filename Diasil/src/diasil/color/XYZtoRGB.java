package diasil.color;

import diasil.math.Matrix3x3;

public class XYZtoRGB
{
    public static XYZtoRGB AdobeRGB = new XYZtoRGB(
                                        new Matrix3x3(
                                            0.5767309f, 0.2973769f, 0.0270343f,
                                            0.1855540f, 0.6273491f, 0.0706872f,
                                            0.1881852f, 0.0752741f, 0.9911085f
                                        ), new Matrix3x3(
                                            2.0413690f, -0.9692660f, 0.0134474f,
                                            -0.5649464f, 1.8760108f, -0.1183897f,
                                            -0.3446944f, 0.0415560f, 1.0154096f
                                        ));
    
    public static XYZtoRGB sRGB = new XYZtoRGB(
                                        new Matrix3x3(
                                            3.4210f, -1.5374f, -0.4986f,
                                            -0.9692f, 1.8760f, 0.0416f,
                                            0.0556f, -0.2040f, 1.0570f
                                        ), new Matrix3x3(
                                            3.2404542f, -0.9692660f, 0.0556434f,
                                            -1.5371385f, 1.8760108f, -0.2040259f,
                                            -0.4985314f, 0.0415560f, 1.0572252f
                                        ));
   
    
    public static XYZtoRGB CIERGB = new XYZtoRGB(
                                        new Matrix3x3(
                                            0.4887180f, 0.1762044f, 0.0000000f,
                                            0.3106803f, 0.8129847f, 0.0102048f,
                                            0.2006017f, 0.0108109f, 0.9897952f
                                        ), new Matrix3x3(
                                            2.3706743f, -0.5138850f, 0.0052982f,
                                            -0.9000405f, 1.4253036f, -0.0146949f,
                                            -0.4706338f, 0.0885814f, 1.0093968f
                                        ));
    public static XYZtoRGB AppleRGB = new XYZtoRGB(
                                        new Matrix3x3(
                                            0.4497288f, 0.2446525f, 0.0251848f,
                                            0.3162486f, 0.6720283f, 0.1411824f,
                                            0.1844926f, 0.0833192f, 0.9224628f
                                        ), new Matrix3x3(
                                            2.9515373f, -1.0851093f, 0.0854934f,
                                            -1.2894116f, 1.9908566f, -0.2694964f,
                                            -0.4738445f, 0.0372026f, 1.0912975f
                                        ));
    
    public Matrix3x3 trn, inv;
    public XYZtoRGB(Matrix3x3 trn, Matrix3x3 inv)
    {
        this.trn = trn;
        this.inv = inv;
    }
    
    public RGBColor toRGB(XYZColor c)
    {
        float r = c.X*trn.X00 + c.Y*trn.X01 + c.Z*trn.X02;
        float g = c.X*trn.X10 + c.Y*trn.X11 + c.Z*trn.X12;
        float b = c.X*trn.X20 + c.Y*trn.X21 + c.Z*trn.X22;
        return new RGBColor(r, g, b);
    }
    
    public XYZColor toXYZ(RGBColor c)
    {
        float x = c.R*inv.X00 + c.G*inv.X01 + c.B*inv.X02;
        float y = c.R*inv.X10 + c.G*inv.X11 + c.B*inv.X12;
        float z = c.R*inv.X20 + c.G*inv.X21 + c.B*inv.X22;
        return new XYZColor(x, y, z);
    }
	
}

