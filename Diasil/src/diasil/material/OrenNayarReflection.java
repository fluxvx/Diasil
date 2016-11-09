package diasil.material;

import diasil.color.SPD;
import diasil.math.DMath;
import diasil.math.geometry3.Vector3;

public class OrenNayarReflection extends ReflectionModel
{
	private SPD R;
	private float A, B;
	public OrenNayarReflection(SPD r, float sigma)
	{
		R = r;
        float sigma2 = sigma*sigma;
        A = 1.0f-(sigma2/(2.0f*(sigma2 + 0.33f)));
        B = 0.45f*sigma2/(sigma2+0.09f);
	}
	public boolean isDelta()
	{
		return false;
	}
	
	public float evaluate(Vector3 wo, Vector3 wi, float wavelength)
	{
		float sinthetai = SinTheta(wi);
		float sinthetao = SinTheta(wo);
		
		// compute cosine term
		float maxcos = 0.0f;
		if (sinthetai > 1.0E-4f && sinthetao > 1.0E-4f)
		{
			float sinphii = SinPhi(wi), cosphii = CosPhi(wi);
			float sinphio = SinPhi(wo), cosphio = CosPhi(wo);
			float dcos = cosphii * cosphio + sinphii * sinphio;
			maxcos = Math.max(0.0f, dcos);
		}

		// computer sin and tan terms
		float sinalpha, tanbeta;
		if (AbsCosTheta(wi) > AbsCosTheta(wo))
		{
			sinalpha = sinthetao;
			tanbeta = sinthetai / AbsCosTheta(wi);
		}
		else
		{
			sinalpha = sinthetai;
			tanbeta = sinthetao / AbsCosTheta(wo);
		}
		float r = R.evaluate(wavelength);
		return r*DMath.IPI*(A + B*maxcos*sinalpha*tanbeta);
	}

}
