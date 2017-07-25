package diasil.sample;

import diasil.math.DMath;
import diasil.math.geometry2.Function2;

public class ImportanceSampleFunction
{
    public float min, max;
    public float[] V;
    public ImportanceSampleFunction(Function2 f, float min, float max, int n)
    {
        this.min = min;
        this.max = max;
        SampledFunction sf = new SampledFunction(f, min, max, n);
        sf.normalizeDomain();
        sf.integrateAndNormalize();
        V = new float[n];
        for (int i=0; i<n; ++i)
        {
            float y = ((float)(i))/n;
            V[i] = sf.reverse(y);
        }
    }
    
    // 0 <= t < 1
    public float sampleV(float t)
    {
        //int i = (int)(Math.round(t*V.length));
        //if (i >= V.length) i = V.length-1;
        //return V[i];
        
        int i = (int)(Math.round(t*V.length));
        if (i >= V.length-1) i = V.length-2;
        float lerp = t - ((float)(i))/V.length;
        return DMath.interpolate(V[i], V[i+1], lerp);
    }
    public float sample(float t)
    {
        return DMath.interpolate(min, max, sampleV(t));
    }
}