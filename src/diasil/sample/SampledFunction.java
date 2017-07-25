package diasil.sample;

import diasil.math.geometry2.Function2;

public class SampledFunction implements Function2
{
    public float min, max;
    public float[] V;
    public SampledFunction(Function2 f, float min, float max, int n)
    {
        this.min = min;
        this.max = max;
        V = new float[n];
        for (int i=0; i<n; ++i)
        {
            float x = interpolate(min, max, ((float)(i))/n);
            float y = f.Y(x);
            V[i] = y;
        }
    }
    
    public float Y(float x)
    {
        int i = (int)((x-min)/(max-min)*V.length);
        return V[i];
    }
    
    
    // assume V is increasing
    public float reverse(float y)
    {
        for (int i=0; i<V.length; ++i)
        {
            if (V[i] > y)
            {
                float x = interpolate(min, max, ((float)(i))/V.length);
                return x;
            }
        }
        return Float.NaN;
    }
    
    public void normalizeDomain()
    {
        min = 0.0f;
        max = 1.0f;
    }
    
    public void integrate()
    {
        for (int i=1; i<V.length; ++i)
        {
            V[i] += V[i-1];
        }
    }
    
    
    public void integrateAndNormalize()
    {
        integrate();
        scale(1.0f/V[V.length-1]);
    }
    
    public void scale(float f)
    {
        for (int i=0; i<V.length; ++i)
        {
            V[i] *= f;
            
        }
    }
    
    
    
    public static float interpolate(float a, float b, float t)
    {
        //return a + t*(b - a);
        return (1.0f - t)*a + t*b;
    }
    
            
}

