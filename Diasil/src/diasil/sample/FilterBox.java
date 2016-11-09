package diasil.sample;

public class FilterBox extends Filter
{
	public FilterBox(float radius)
	{
		super(radius);
	}
	public void weight(Sample s)
	{
		s.filter_weight = 1.0f;
	}
	public Filter clone()
	{
		return new FilterBox(radius);
	}

}
