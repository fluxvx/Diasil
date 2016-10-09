package diasil.material;


import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;


public class LightRay extends Ray3
{
    public float wavelength;
    public float refractive_index;
	public float wavelength_reflectivity;
    public LightRay(LightRay r)
    {
        super(r);
        wavelength = r.wavelength;
        refractive_index = r.refractive_index;
		wavelength_reflectivity = r.wavelength_reflectivity;
    }
    public LightRay(Point3 p, Vector3 v, float wavelength, float refractive_index, float wavelength_reflectivity)
    {
        super(p, v);
        this.wavelength = wavelength;
        this.refractive_index = refractive_index;
		this.wavelength_reflectivity = wavelength_reflectivity;
    }
    public LightRay(Ray3 r, float wavelength, float refractive_index, float wavelength_reflectivity)
    {
        super(r);
        this.wavelength = wavelength;
        this.refractive_index = refractive_index;
		this.wavelength_reflectivity = wavelength_reflectivity;
    }
    
    
    public static boolean isValid(float t)
    {
        return t > 1.0E-3f;
    }
    
    public String toString()
    {
        return "{"+O.toString()+","+D.toString()+","+wavelength+","+refractive_index+"}";
    }
}
