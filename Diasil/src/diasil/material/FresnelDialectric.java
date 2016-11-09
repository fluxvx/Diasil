package diasil.material;

import diasil.math.DMath;

public class FresnelDialectric implements FresnelReflection
{
	private float eta_i, eta_t;
	public FresnelDialectric(float eta_i, float eta_t)
	{
		this.eta_i = eta_i;
		this.eta_t = eta_t;
	}
	public float evaluate(float cosi)
	{
		cosi = DMath.clamp(cosi, -1.0f, 1.0f);
		float ei, et;
		if (cosi > 0.0f) // entering
		{
			ei = eta_i;
			et = eta_t;
		}
		else
		{
			ei = eta_t;
			et = eta_i;
		}
		float sint = ei/et * (float)Math.sqrt(Math.max(0.0f, 1.0f-cosi*cosi));
		if (sint >= 1.0f)
		{
			return 1.0f; // total internal reflection
		}
		else
		{
			float cost = (float)Math.sqrt(Math.max(0.0f, 1.0f-sint*sint));
			return ReflectionModel.FresnelDielectric(Math.abs(cosi), cost, ei, et);
		}
	}

}
