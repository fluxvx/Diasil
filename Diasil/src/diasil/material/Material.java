package diasil.material;

import diasil.entity.Intersection;
import diasil.entity.SurfaceGeometry;

public interface Material
{
    public LightRay sampleBRDF(Intersection it, SurfaceGeometry sg);
    public boolean isLight();
    public float getIntensity(Intersection it, SurfaceGeometry sg);
}

