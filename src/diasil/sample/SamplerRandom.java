package diasil.sample;

import diasil.math.DMath;
import java.util.SplittableRandom;

public class SamplerRandom extends Sampler
{
	private Sample[] samples;
	private int[] nSamples1D, nSamples2D;
	private Filter filter;
	
	public SamplerRandom(int n_samples, Filter filter, SplittableRandom random)
	{
		super(random);
		samples = new Sample[n_samples];
		this.filter = filter;
	}
	public void allocateSamples(SampleCollector sc)
	{
		int n1D=0, n2D=0;
		nSamples1D = sc.getSamples1D();
		for (int i=0; i<nSamples1D.length; ++i)
		{
			n1D += nSamples1D[i];
		}
		
		nSamples2D = sc.getSamples2D();
		for (int i=0; i<nSamples2D.length; ++i)
		{
			n2D += nSamples2D[i];
		}
		
		for (int i=0; i<samples.length; ++i)
		{
			samples[i] = new Sample(n1D, n2D);
		}
	}
	public Sample[] regenerateSamples(int img_i, int img_j, int img_width, int img_height)
	{
		for (int i=0; i<samples.length; ++i)
		{
			Sample s = samples[i];
			
			s.X = img_i + nextFloat();
			s.Y = img_j + nextFloat();
			super.rasterToScreen(s, img_width, img_height);
			
			s.U = 2.0f*nextFloat()-1.0f;
			s.V = 2.0f*nextFloat()-1.0f;
			s.wavelength = DMath.interpolate(WAVELENGTH_MIN, WAVELENGTH_MAX, nextFloat());
			for (int j=0; j<s.samples1D.length; ++j)
			{
				s.samples1D[j] = nextFloat();
			}
			for (int j=0; j<s.samples2D.length; ++j)
			{
				s.samples2D[j] = nextFloat();
			}
			filter.weight(s);
		}
		return samples;
	}
	public int roundSize(int n)
	{
		return n;
	}
	public Sampler clone()
	{
		return new SamplerRandom(samples.length, filter.clone(), random.split());
	}
}

