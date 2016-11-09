package diasil.color;

public class RawSample
{
	public float X, Y;
	public float wavelength;
	public float intensity;
	public RawSample(float x, float y, float wavelength, float intensity)
	{
		X = x;
		Y = y;
		this.wavelength = wavelength;
		this.intensity = intensity;
	}
}
