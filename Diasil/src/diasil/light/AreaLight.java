package diasil.light;

import diasil.color.SpectralDistribution;
import diasil.intersect.Intersectable;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Vector3;

public class AreaLight extends CoordinateSpace3 implements Light
{
	private Intersectable it;
	private SpectralDistribution power;
	public AreaLight(Intersectable it, SpectralDistribution power)
	{
		this.it = it;
		this.power = power;
	}
	public LightSample sampleL(Point3 pw, float wavelength, float u, float v)
	{
		Point3 pl = it.sampleSurface(u, v);
		
		Vector3 wi = new Vector3(pw, pl);
		float d2 = wi.lengthSquared();
		
		// options: use dot for falloff, make one-sided
		//SurfaceGeometry sg = it.getSurfaceGeometry(pl);
		//wi.normalize();
		//float dot = Math.abs(sg.N.dot(wi));
		
		float Li = power.evaluate(wavelength)/d2;
		float d = (float)Math.sqrt(d2);
		wi.X /= d;
		wi.Y /= d;
		wi.Z /= d;
		return new LightSample(wi, Li, d);
	}
	
}
