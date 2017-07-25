package diasil.material;

import diasil.color.SpectralDistribution;
import diasil.math.DMath;

public class SellmeierEquation implements SpectralDistribution
{
	private float b1, b2, b3, c1, c2, c3;
	public SellmeierEquation(float b1, float b2, float b3, float c1, float c2, float c3)
	{
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}
	public float evaluate(float wavelength)
	{
		float w2 = wavelength*wavelength;
		float n2 = 1 + (b1*w2)/(w2-c1)
						+ (b2*w2)/(w2-c2)
						+ (b3*w2)/(w2-c3);
		return DMath.sqrt(n2);
	}
	
	public static final SellmeierEquation BK7 = new SellmeierEquation(1.03961212f, 0.231792344f, 1.01046945f, 6.00069867E-3f, 2.00179144E-2f, 1.03560653E2f);
	public static final SellmeierEquation FusedSilica = new SellmeierEquation(0.696166300f, 0.407942600f, 0.897479400f, 4.67914826E-3f, 1.35120631E-2f, 97.9340025f);
}
