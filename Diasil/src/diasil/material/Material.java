package diasil.material;

import diasil.intersect.Intersection;
import diasil.intersect.SurfaceGeometry;
import diasil.math.geometry3.Vector3;

public class Material
{
	public BSDF[] bsdfs;
	public Material(int n_bxdfs)
	{
		bsdfs = new BSDF[n_bxdfs];
	}
	public float f(Vector3 wow, Vector3 wiw, float wavelength, int flags, SurfaceGeometry sg)
	{
		Vector3 wo = sg.toLocalSpace(wow);
		Vector3 wi = sg.toLocalSpace(wiw);
		flags &= (wow.dot(sg.N) * wiw.dot(sg.N) > 0.0f)? ~BSDF.TRANSMISSION: ~BSDF.REFLECTION;
		float f = 0.0f;
		for (int i=0; i<bsdfs.length; ++i)
		{
			if (bsdfs[i].matchesFlags(flags))
			{
				f += bsdfs[i].f(wo, wi, wavelength);
			}
		}
		return f;
		
	}
	public BSDFSample samplef(Vector3 wow, float wavelength, float bsdf_sample, float u, float v, int flags, SurfaceGeometry sg)
	{
		int n_matching = 0;
		for (int i=0; i<bsdfs.length; ++i)
		{
			if (bsdfs[i].matchesFlags(flags))
			{
				n_matching++;
			}
		}
		int bsdf_id = (int)(bsdf_sample*n_matching);
		BSDF bsdf = null;
		for (int i=0, c=bsdf_id; i<bsdfs.length; ++i)
		{
			if (bsdfs[i].matchesFlags(flags) && c-- == 0)
			{
				bsdf = bsdfs[i];
				break;
			}
		}
		
		if (bsdf == null) return null;
		
		Vector3 wo = sg.toLocalSpace(wow);
		/*if (Math.abs(wo.length() - 1.0f) > 1E-3f
				|| Math.abs(wow.length() - 1.0f) > 1E-3f)
		{
			System.out.println(wow.length()+" "+wo.length()+" "+sg.N.length()+" "+sg.dPdU.length()+" "+sg.dPdV.length());
		}*/
		
		BSDFSample s = bsdf.samplef(wo, u, v, wavelength);
		if (s == null || s.pdf == 0.0f)
		{
			return null;
		}
		Vector3 wiw = sg.toWorldSpace(s.Wi);
		
		// compute PDF
		if ((bsdf.type & BSDF.SPECULAR) == 0 && n_matching > 1)
		{
			for (int i=0; i<bsdfs.length; ++i)
			{
				if (bsdfs[i] != bsdf && bsdfs[i].matchesFlags(flags))
				{
					s.pdf += bsdfs[i].pdf(wo, s.Wi);
				}
			}
		}
		if (n_matching > 1) s.pdf /= n_matching;
		
		// compute f
		if ((bsdf.type & BSDF.SPECULAR) == 0)
		{
			s.f = 0.0f;
			flags &= (wow.dot(sg.N) * wiw.dot(sg.N) > 0.0f)? ~BSDF.TRANSMISSION: ~BSDF.REFLECTION;
			for (int i=0; i<bsdfs.length; ++i)
			{
				if (bsdfs[i].matchesFlags(flags))
				{
					s.f += bsdfs[i].f(wo, s.Wi, wavelength);
				}
			}
		}
		s.Wi = wiw;
		return s;
	}
	public float pdf(Vector3 wo, Vector3 wi, int flags, SurfaceGeometry sg)
	{
		wo = sg.toLocalSpace(wo);
		wi = sg.toLocalSpace(wi);
		float pdf = 0.0f;
		int n_matching = 0;
		for (int i=0; i<bsdfs.length; ++i)
		{
			if (bsdfs[i].matchesFlags(flags))
			{
				pdf += bsdfs[i].pdf(wo, wi);
				++n_matching;
			}
		}
		return (n_matching == 0)? 0.0f: pdf/n_matching;
	}
}
