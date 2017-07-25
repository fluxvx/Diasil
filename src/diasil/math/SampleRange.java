package diasil.math;

public class SampleRange
{
	public float A, B;
	public int N;
	public boolean wrap;
	public SampleRange(float a, float b, int n, boolean wrap)
	{
		A = a;
		B = b;
		N = n;
		this.wrap = wrap;
	}
}
