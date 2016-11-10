package diasil.math;

import java.util.Random;

public class DMath
{
    public static final float PI = (float)(Math.PI);
    public static final float PI2 = (float)(Math.PI*2.0);
    
    public static final float IPI = (float)(1.0/Math.PI);
    public static final float IPI2 = (float)(0.5/Math.PI);
    
    private static Random random;
    
    private DMath() {}
	
	
	
	
	public static float sqrt(float v)
	{
		return (float)Math.sqrt(v);
	}
	
    
    
    public static float acos(float cos_theta)
    {
        return (float)(Math.acos(cos_theta));
    }
    
    
    public static float log10(float v)
    {
        return (float)(Math.log10(v));
    }
    
    public static float log(float v)
    {
        return (float)(Math.log(v));
    }
    
    public static float pow(float a, float b)
    {
        return (float)(Math.pow(a, b));
    }
    
    public static float exp(float a)
    {
        return (float)(Math.exp(a));
    }
    
    public static float cos(float angle)
    {
        return (float)(Math.cos(angle));
    }
    
    public static float sin(float angle)
    {
        return (float)(Math.sin(angle));
    }
    
    public static float tan(float a)
    {
        return (float)(Math.tan(a));
    }
    
    public static float atan2(float y, float x)
    {
        return (float)(Math.atan2(y, x));
    }
    
    
    
    
    public static float clamp(float x)
    {
        return (x < 0.0f)? 0.0f: (x > 1.0f)? 1.0f: x;
    }
	public static float clamp(float x, float min, float max)
	{
		return (x < min)? min: (x > max)? max: x;
	}
    
    public static int clamp(int x, int a, int b)
    {
        return (x < a)? a: (x > b)? b: x;
    }
    
    public static int wrap(int x, int a, int b)
    {
        int d = b-a;
        while (x < a) x += d;
        while (x >= b) x -= d;
        return x;
    }
    
    public static float wrap(float x, float a, float b)
    {
        float d = b-a;
        while (x < a) x += d;
        while (x > b) x -= d;
        return x;
    }
    
    public static float wrap(float x)
    {
        while (x < 0) x += PI2;
        while (x > PI2) x -= PI2;
        return x;
    }
    
    
    
    public static float[] quadratic(float a, float b, float c)
    {
        float t = b*b - 4F*a*c;
        if (t < 0F)
        {
            return new float[0];
        }
        if (t < 1E-3F)
        {
            return new float[]{-b/(2F*a)};
        }
        t = (float)Math.sqrt(t);
        if (b < 0F)
        {
            t = -.5F*(b-t);
        }
        else
        {
            t = -.5F*(b+t);
        }
        float r0 = t/a, r1 = c/t;
        if (r0 < r1)
        {
            return new float[]{r0,r1};
        }
        return new float[]{r1,r0};
    }
    
    
    public static float interpolate(float a, float b, float t)
    {
        //return a + t*(b - a);
        return (1.0f - t)*a + t*b;
    }
    
    public static void scaleSum(float[] f)
    {
        float sum = 0.0f;
        for (int i=0; i<f.length; ++i)
        {
            sum += f[i];
        }
        float inv = 1.0f/sum;
        for (int i=0; i<f.length; ++i)
        {
            f[i] *= inv;
        }
    }
}
