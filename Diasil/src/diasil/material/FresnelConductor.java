package diasil.material;

public class FresnelConductor implements FresnelReflection
{
	private float eta, k;
	public FresnelConductor(float eta, float k)
	{
		this.eta = eta;
		this.k = k;
	}
	public float evaluate(float cosi)
	{
		return ReflectionModel.FresnelConductive(Math.abs(cosi), eta, k);
	}
}
