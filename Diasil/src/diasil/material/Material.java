package diasil.material;

import diasil.intersect.Intersection;
import diasil.intersect.SurfaceGeometry;
import diasil.math.geometry3.Vector3;

public class Material
{
	protected ReflectionModel[] bxdfs;
	public Material(int n_bxdfs)
	{
		bxdfs = new ReflectionModel[n_bxdfs];
	}
	public float evaluate(Vector3 wo, Vector3 wi, float wavelength, SurfaceGeometry sg)
	{
		wo = sg.toLocalSpace(wo);
		wi = sg.toLocalSpace(wi);
		float pdf = 0.0f;
		for (int i=0; i<bxdfs.length; ++i)
		{
			pdf += bxdfs[i].pdf(wo, wi);
		}
		return pdf/bxdfs.length;
	}
	public BSDFSample sample(Vector3 wo, float wavelength, float bxdf_sample, float u, float v, SurfaceGeometry sg)
	{
		wo = sg.toLocalSpace(wo);
		ReflectionModel m = bxdfs[(int)(bxdf_sample*bxdfs.length)];
		return m.sample(wo, wavelength, u, v);
	}
	public float pdf(Vector3 wo, Vector3 wi, SurfaceGeometry sg)
	{
		wo = sg.toLocalSpace(wo);
		wi = sg.toLocalSpace(wi);
		float pdf = 0.0f;
		for (int i=0; i<bxdfs.length; ++i)
		{
			pdf += bxdfs[i].pdf(wo, wi);
		}
		return pdf/bxdfs.length;
	}
}
