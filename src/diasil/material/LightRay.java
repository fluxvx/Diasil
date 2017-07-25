package diasil.material;

import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;

public class LightRay extends Ray3
{
    public float wavelength; // wavelength of this ray light
    public LightRay(LightRay r)
    {
        super(r);
        this.wavelength = r.wavelength;
    }
    public LightRay(Point3 p, Vector3 v, float wavelength)
    {
        super(p, v);
        this.wavelength = wavelength;
    }
    public LightRay(Ray3 r, float wavelength)
    {
        super(r);
        this.wavelength = wavelength;
    }
}
