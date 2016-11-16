package diasil.sample;

import java.util.SplittableRandom;

public abstract class Sampler
{
    protected static final float WAVELENGTH_MIN = 390.0f, WAVELENGTH_MAX = 700.0f;
	public SplittableRandom random;
	
	public Sampler(SplittableRandom random)
	{
		this.random = random;
	}
	
	public float nextFloat()
	{
		return (float)(random.nextDouble(0.9999999));
	}
	
	public abstract void allocateSamples(SampleCollector sg);
	public abstract Sample[] regenerateSamples(int img_i, int img_j, int img_width, int img_height);
	public abstract int roundSize(int n);
	public abstract Sampler clone();	
	
	// [0,w]x[0,h] -> [-1,1]x[-1,1]
	public void rasterToScreen(Sample s, int w, int h)
	{
		s.Y = h - s.Y - 1.0f;
		s.X = 2.0f*s.X/w - 1.0f;
		s.Y = 2.0f*s.Y/h - 1.0f;
	}
	
	
	public void Shuffle(float[] f, int min, int ns, int nd)
	{
		for (int i=0; i<ns; ++i)
		{
			int ia = min + i*nd;
			int ib = min + random.nextInt(ns)*nd;
			for (int j=0; j<nd; ++j)
			{
				float t = f[ia+j];
				f[ia+j] = f[ib+j];
				f[ib+j] = t;
			}
		}
	}
	
	
	public void Shuffle1D(float[] f)
    {
        for (int i=0; i<f.length; ++i)
        {
            int ip = random.nextInt(f.length);
            float t = f[i];
            f[i] = f[ip];
            f[ip] = t;
        }
    }
	public void Shuffle2D(float[] f, int ns)
	{
		for (int i=0; i<ns; ++i)
		{
			int ia = i << 1;
			int ib = random.nextInt(ns) << 1;
			
			float tx = f[ia];
			f[ia] = f[ib];
			f[ib] = tx;
			
			float ty = f[ia+1];
			f[ia+1] = f[ib+1];
			f[ib+1] = ty;
		}
	}
	public static void Swap(float[] f, int ia, int ib)
	{
		float t = f[ia];
		f[ia] = f[ib];
		f[ib] = t;
	}
}
