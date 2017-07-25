package diasil.color;

import diasil.math.DMath;

public class RGBColor
{
    public float R, G, B;
    public RGBColor()
    {
        R = G = B = 0;
    }
    public RGBColor(float c)
    {
        R = G = B = c;
    }
    public RGBColor(float r, float g, float b)
    {
        R = r;
        G = g;
        B = b;
    }
    public RGBColor(RGBColor c)
    {
        R = c.R;
        G = c.G;
        B = c.B;
    }
    public void clamp()
    {
        R = DMath.clamp(R);
        G = DMath.clamp(G);
        B = DMath.clamp(B);
    }
    
    public void addBy(RGBColor c)
    {
        R += c.R;
        G += c.G;
        B += c.B;
    }
    
    public void multiplyBy(float f)
    {
        R *= f;
        G *= f;
        B *= f;
    }
    
    public void multiplyBy(RGBColor c)
    {
        R *= c.R;
        G *= c.G;
        B *= c.B;
    }
}