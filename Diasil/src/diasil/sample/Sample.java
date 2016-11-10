package diasil.sample;

import diasil.math.DMath;
import diasil.math.geometry2.Point2;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.Vector3;

public class Sample
{
	public float X, Y; // relative to the center of a pixel
	public float U, V; // lens sample
	public float wavelength;
	public float filter_weight;
	public float intensity;
	public float[] samples1D, samples2D;
	public Sample(int n1D, int n2D)
	{
		samples1D = new float[n1D];
		samples2D = new float[n2D];
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Point3 UniformSampleSphere(float u1, float u2)
	{
		float z = 1.0f - 2.0f*u1;
		float r = DMath.sqrt(Math.max(0.0f, 1.0f - z*z));
		float phi = 2.0f*DMath.PI*u2;
		float x = r*DMath.cos(phi);
		float y = r*DMath.sin(phi);
		return new Point3(x, y, z);
	}
	
	public static Point2 ConcentricSampleDisk(float x, float y)
	{
		// Map uniform random numbers to $[-1,1]^2$
		float sx = 2*x - 1;
		float sy = 2*y - 1;
		
		// Handle degeneracy at the origin
		if (sx == 0.0f && sy == 0.0f)
		{
			return new Point2(0.0f, 0.0f);
		}
		
		float r, theta;
		if (sx >= -sy)
		{
			if (sx > sy)
			{
				r = sx;
				theta = (sy > 0.0f)? sy/r: 8.0f + sy/r;
			}
			else
			{
				r = sy;
				theta = 2.0f - sx/r;
			}
		}
		else
		{
			if (sx <= sy)
			{
				r = -sx;
				theta = 4.0f - sy/r;
			}
			else
			{
				r = -sy;
				theta = 6.0f + sx/r;
			}
		}
		theta *= (float)(Math.PI/4.0);
		float rx = (float)(r*Math.cos(theta));
		float ry = (float)(r*Math.sin(theta));
		return new Point2(rx, ry);
	}
	
	public static Vector3 CosineSampleHemisphere(float u, float v)
	{
		Point2 p = ConcentricSampleDisk(u, v);
		float z = (float)Math.sqrt(Math.max(0.f, 1.f - p.X*p.X - p.Y*p.Y));
		return new Vector3(p.X, p.Y, z);
	}
	
	// uses the Marsaglia polar method
	public static Point2 sampleGaussian(float mu, float sigma)
	{
        double u, v, s;
        do {
            u = Math.random()*2.0 - 1.0;
            v = Math.random()*2.0 - 1.0;
            s = u * u + v * v;
        } while (s >= 1.0 || s == 0.0);
        double mul = Math.sqrt(-2.0 * Math.log(s) / s);
        return new Point2((float)(mu + sigma*u*mul), (float)(mu + sigma*v*mul));
	}
}
