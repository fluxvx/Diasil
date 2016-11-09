package diasil.sample;

import diasil.DUtility;
import diasil.math.DMath;
import diasil.math.geometry2.Point2;

public class SamplerStratified extends Sampler
{
	private Filter filter;
	private Sample[] samples;
	private int sqrt_ns;
    private float jitter;
	
	private int[] nSamples1D;
	private int[] nSamples2D;
	private int[] sqrtnSamples2D;
	
	// sample buffers
	private float[] pixel_sb;
	private float[] lens_sb;
	private float[] wavelength_sb;
	
	public SamplerStratified(int n_samples, float jitter, Filter filter)
	{
		sqrt_ns = (int)Math.round(Math.sqrt(n_samples));
		n_samples = sqrt_ns*sqrt_ns;
		samples = new Sample[n_samples];
		pixel_sb = new float[n_samples << 1];
		lens_sb = new float[n_samples << 1];
		wavelength_sb = new float[n_samples << 1];
		this.jitter = jitter;
		this.filter = filter;
	}
	
	public void allocateSamples(SampleCollector sg)
	{
		int n1D=0, n2D=0;
		nSamples1D = sg.getSamples1D();
		for (int i=0; i<nSamples1D.length; ++i)
		{
			n1D += nSamples1D[i];
		}
		
		nSamples2D = sg.getSamples2D();
		sqrtnSamples2D = new int[nSamples2D.length];
		for (int i=0; i<nSamples2D.length; ++i)
		{
			sqrtnSamples2D[i] = (int)(Math.round(Math.sqrt(nSamples2D[i])));
			n2D += nSamples2D[i]<<1;
		}
		
		for (int i=0; i<samples.length; ++i)
		{
			samples[i] = new Sample(n1D, n2D);
		}
	}
	
	
	public Sample[] regenerateSamples(int img_i, int img_j, int img_width, int img_height)
	{
		Stratified2D(pixel_sb, 0, sqrt_ns, jitter);
		Stratified2D(lens_sb, 0, sqrt_ns, jitter);
		Stratified1D(wavelength_sb, 0, samples.length, jitter);
		Shuffle(lens_sb, 0, samples.length, 2);
		Shuffle(wavelength_sb, 0, samples.length, 1);
		for (int i=0; i<samples.length; ++i)
		{
			Sample s = samples[i];
			int i2 = i << 1;
			
			s.X = img_i + pixel_sb[i2];
			s.Y = img_j + pixel_sb[i2+1];
			super.rasterToScreen(s, img_width, img_height);
			
			s.U = lens_sb[i2];
			s.V = lens_sb[i2+1];
			s.wavelength = wavelength_sb[i];
			filter.weight(s);
			
			for (int j=0, sid=0; j<nSamples1D.length; ++j)
			{
				Stratified1D(s.samples1D, sid, nSamples1D[j], jitter);
				sid += nSamples1D[j];
			}
			for (int j=0, sid=0; j<nSamples2D.length; ++j)
			{
				Stratified2D(s.samples2D, sid, sqrtnSamples2D[j], jitter);
				sid += nSamples2D[j]<<1;
			}
		}
		return samples;
	}
    public int roundSize(int n)
    {
        n = (int)(Math.round(Math.sqrt(n)));
        return n*n;
    }
	
	public Sampler clone()
	{
		return new SamplerStratified(samples.length, jitter, filter.clone());
	}
    
	
	
	
	private static void Stratified1D(float[] r, int min, int ns, float jitter)
	{
		float scale = 1.0f/ns;
		float offset = 0.5f*scale;
		for (int i=0; i<ns; ++i)
		{
			r[min+i] = offset + i*scale + (DMath.random()-0.5f)*scale*jitter;
		}
	}
	private static void Stratified2D(float[] r, int min, int sqrt_ns, float jitter)
	{
		float scale = 1.0f/sqrt_ns;
		float offset = 0.5f*scale;
		for (int i=0, k=0; i<sqrt_ns; ++i)
		{
			float x = offset + i*scale + (DMath.random()-0.5f)*scale*jitter;
			for (int j=0; j<sqrt_ns; ++j, ++k)
			{
				float y = offset + j*scale + (DMath.random()-0.5f)*scale*jitter;
				r[min + (k<<1)] = x;
				r[min + (k<<1)+1] = y;
			}
		}
	}
	public static void LatinHypercube(float[] f, int min, int ns, int nd)
	{
		float scale = 1.0f/ns;
		for (int i=0; i<ns; ++i)
		{
			for (int j=0; j<nd; ++j)
			{
				f[nd*i + j] = (i + DMath.random())*scale;
			}
		}

		for (int i=0; i<nd; ++i)
		{
			for (int j=0; j<ns; ++j)
			{
				int oj = j + DMath.randomInt(ns - j);
				Swap(f, nd*j + i, nd*oj + i);
			}
		}
	}
}
