package diasil.material;

import diasil.math.DMath;
import diasil.math.geometry3.Vector3;
import diasil.sample.Sample;
import diasil.sample.Sampler;

public abstract class ReflectionModel
{
	public abstract boolean isDelta();
	public abstract float evaluate(Vector3 wo, Vector3 wi, float wavelength);
	public BSDFSample sample(Vector3 wo, float u, float v, float wavelength)
	{
		Vector3 wi = Sample.CosineSampleHemisphere(u, v);
		if (wo.Z < 0.0f) wi.Z = -wi.Z;
		float pdf = pdf(wo, wi);
		return new BSDFSample(wi, wavelength, evaluate(wo, wi, wavelength), pdf);
	}
	public float pdf(Vector3 wo, Vector3 wi)
	{
		return SameHemisphere(wo, wi)? AbsCosTheta(wi)*DMath.IPI: 0.0f;
	}
	
	
	
	
	
	// fresnel reflectance for dielectric materials and circularly polarized light
	public static float FresnelDielectric(float cosi, float cost, float etai, float etat)
	{
		float r_para = ((etat*cosi) - (etai*cost)) /
							((etat*cosi) + (etai*cost));
		float r_perp = ((etai*cosi) - (etat*cost)) /
								((etai*cosi) + (etat*cost));
		return (r_para*r_para + r_perp*r_perp) / 2.0f;
	}
	
	// fresnel reflectance for conductive materials and circularly polarized light
	public static float FresnelConductive(float cosi, float eta, float k)
	{
		float t = eta*eta + k*k;
		float t2 = t*cosi*cosi;
		float r_para2 = (t2 - (2.0f * eta * cosi) + 1.0f) /
							(t2 + (2.0f * eta * cosi) + 1.0f);
		float r_perp2 = (t - (2.0f*eta*cosi) + cosi*cosi) /
						(t + (2.0f*eta*cosi) + cosi*cosi);
		return (r_para2 + r_perp2) / 2.0f;
	}
	
	
	protected static float CosTheta(Vector3 w)
	{
		return w.Z;
	}
	protected static float AbsCosTheta(Vector3 w)
	{
		return Math.abs(w.Z);
	}
	protected static float SinTheta2(Vector3 w)
	{
		return Math.max(0.0f, 1.0f - CosTheta(w)*CosTheta(w));
	}
	protected static float SinTheta(Vector3 w)
	{
		return (float)Math.sqrt(SinTheta2(w));
	}
	protected static float CosPhi(Vector3 w)
	{
		float sintheta = SinTheta(w);
		if (sintheta == 0.0f) return 1.0f;
		return DMath.clamp(w.X / sintheta, -1.0f, 1.0f);
	}
	protected static float SinPhi(Vector3 w)
	{
		float st = SinTheta(w);
		if (st == 0.f) return 0.f;
		return DMath.clamp(w.Y / st, -1.0f, 1.0f);
	}
	protected static Vector3 FlipHemisphere(Vector3 v)
	{
		return new Vector3(v.X, v.Y, -v.Z);
	}
	protected static boolean SameHemisphere(Vector3 a, Vector3 b)
	{
		return a.Z*b.Z > 0.0f;
	}
}
