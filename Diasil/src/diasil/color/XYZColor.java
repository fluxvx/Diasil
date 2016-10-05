package diasil.color;

public class XYZColor
{
    public float X, Y, Z;
    public XYZColor()
    {
        this(0.0f);
    }
    public XYZColor(float c)
    {
        this(c, c, c);
    }
    public XYZColor(float x, float y, float z)
    {
        X = x;
        Y = y;
        Z = z;
    }
    public XYZColor(XYZColor c)
    {
        X = c.X;
        Y = c.Y;
        Z = c.Z;
    }
    
    
    public XYZColor add(XYZColor c)
    {
        return new XYZColor(X + c.X, Y + c.Y, Z + c.Z);
    }
    public XYZColor multiply(float f)
    {
        return new XYZColor(X*f, Y*f, Z*f);
    }
    
    public void normalize()
    {
        float max = Math.max(X,Math.max(Y,Z));
        X /= max;
        Y /= max;
        Z /= max;
    }
    
    public static XYZColor findForWavelength(float wl, float s)
    {
		float x1 = (wl-442.0f)*((wl < 442.0f)?0.0624f:0.0374f);
		float x2 = (wl-599.8f)*((wl < 599.8f)?0.0264f:0.0323f);
		float x3 = (wl-501.1f)*((wl < 501.1f)?0.0490f:0.0382f);
		float x = 0.362f*(float)Math.exp(-0.5f*x1*x1) + 1.056f*(float)Math.exp(-0.5f*x2*x2) - 0.065f*(float)Math.exp(-0.5f*x3*x3);

		float y1 = (wl-568.8f)*((wl < 568.8f)?0.0213f:0.0247f);
		float y2 = (wl-530.9f)*((wl < 530.9f)?0.0613f:0.0322f);
		float y = 0.821f*(float)Math.exp(-0.5f*y1*y1) + 0.286f*(float)Math.exp(-0.5f*y2*y2);

		float z1 = (wl-437.0f)*((wl < 437.0f)?0.0845f:0.0278f);
		float z2 = (wl-459.0f)*((wl < 459.0f)?0.0385f:0.0725f);
		float z = 1.217f*(float)Math.exp(-0.5f*z1*z1) + 0.681f*(float)Math.exp(-0.5f*z2*z2);
        
        return new XYZColor(s*x, s*y, s*z);
    }
}
