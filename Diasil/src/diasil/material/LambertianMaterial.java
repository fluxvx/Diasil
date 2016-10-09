package diasil.material;

import diasil.color.SPD;
import diasil.entity.Intersection;
import diasil.entity.SurfaceGeometry;
import diasil.math.DMath;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class LambertianMaterial implements Material
{
    private SPD color;
    public LambertianMaterial()
    {
        color = SPD.constant(1.0f/(float)Math.PI);
    }
    public LambertianMaterial(SPD spd)
    {
        color = spd;
    }
    public LightRay sampleBRDF(Intersection it, SurfaceGeometry sg)
    {
        // Vector3 rv = new Vector3(0.0f, 0.0f, 1.0f);
        Vector3 rv = generateRay(DMath.random(), DMath.random());
        Point3 p = new Point3(0.0f, 0.0f, 0.0f);
		Ray3 r = it.E.toWorldSpace(new Ray3(p, rv));
        r.D.normalize();
        return new LightRay(r, it.Rw.wavelength,
								it.Rw.refractive_index,
								color.evaluate(it.Rw.wavelength));
    }
    
    public boolean isLight()
    {
        return false;
    }
    
    public float getIntensity(Intersection it, SurfaceGeometry sg)
    {
        return 0.0f;
    }
    
    
    public Vector3 generateRay(float u1, float u2)
    {
        float r = (float)Math.sqrt(u1);
        float theta = 2*(float)Math.PI*u2;

        float x = r * (float)Math.cos(theta);
        float y = r * (float)Math.sin(theta);

        return new Vector3(x, y, (float)Math.sqrt(Math.max(0.0f, 1 - u1)));
    }
}