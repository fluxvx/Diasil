package diasil.material;


import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Ray3;
import diasil.math.geometry3.Vector3;


public class LightRay extends Ray3
{
    public float wavelength; // wavelength of this ray light
    public float refractive_index; // keep track of the index of refraction of the medium we're in
	public float wavelength_reflectivity; // reflectivity of a surface for the given wavelength
    public LightRay(LightRay r)
    {
        super(r);
        this.wavelength = r.wavelength;
        this.refractive_index = r.refractive_index;
		this.wavelength_reflectivity = r.wavelength_reflectivity;
    }
    public LightRay(Point3 p, Vector3 v, float wavelength, float wavelength_reflectivity, float refractive_index)
    {
        super(p, v);
        this.wavelength = wavelength;
        this.refractive_index = refractive_index;
		this.wavelength_reflectivity = wavelength_reflectivity;
    }
    public LightRay(Ray3 r, float wavelength, float wavelength_reflectivity, float refractive_index)
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
