package diasil.sample;

import java.util.ArrayList;

public class SampleCollector
{
	private ArrayList<Integer> nSamples1D, nSamples2D;
	public SampleCollector()
	{
		nSamples1D = new ArrayList<Integer>();
		nSamples2D = new ArrayList<Integer>();
	}
	public int addSamples1D(int n)
	{
		nSamples1D.add(n);
		return nSamples1D.size()-1;
	}
	public int addSamples2D(int n)
	{
		nSamples2D.add(n);
		return nSamples2D.size()-1;
	}
	private int[] convert(ArrayList<Integer> a)
	{
		int[] r = new int[a.size()];
		for (int i=0; i<r.length; ++i)
		{
			r[i] = a.get(i);
		}
		return r;
	}
	public int[] getSamples1D()
	{
		return convert(nSamples1D);
	}
	public int[] getSamples2D()
	{
		return convert(nSamples2D);
	}
}
