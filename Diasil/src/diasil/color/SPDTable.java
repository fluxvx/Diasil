package diasil.color;

public class SPDTable extends SPD
{
    public float min, max;
    public float[] values;
    public SPDTable(float min, float max, float[] values)
    {
        this.min = min;
        this.max = max;
        this.values = values;
    }
	public SPDTable(float min, float max, int n_samples, SPD spd)
	{
		this.min = min;
		this.max = max;
		values = new float[n_samples];
		for (int i=0; i<values.length; ++i)
		{
			float wl = min + (i+0.5f)/(max-min);
			values[i] = spd.evaluate(wl);
		}
	}
    public float evaluate(float w)
    {
		if (w < min || w > max) return 0.0f;
        int i = (int)((w - min)/(max - min)*values.length);
        return values[i];
    }
	public void normalize()
	{
		float sum = 0;
		for (int i=0; i<values.length; ++i)
		{
			sum += values[i];
		}
		float inv_sum = 1.0f/sum;
		for (int i=0; i<values.length; ++i)
		{
			values[i] *= inv_sum;
		}
	}
	public void multiplyBy(float v)
	{
		for (int i=0; i<values.length; ++i)
		{
			values[i] *= v;
		}
	}
}
