package diasil.math;

public class Matrix4x4
{
    public float X00, X01, X02, X03,
                 X10, X11, X12, X13,
                 X20, X21, X22, X23,
                 X30, X31, X32, X33;
    
    public Matrix4x4()
    {
        setAsIdentity();
    }
    
    public Matrix4x4(float x00, float x01, float x02, float x03,
					float x10, float x11, float x12, float x13,
					float x20, float x21, float x22, float x23,
					float x30, float x31, float x32, float x33)
    {
        X00 = x00;
        X01 = x01;
        X02 = x02;
        X03 = x03;

        X10 = x10;
        X11 = x11;
        X12 = x12;
        X13 = x13;

        X20 = x20;
        X21 = x21;
        X22 = x22;
        X23 = x23;

        X30 = x30;
        X31 = x31;
        X32 = x32;
        X33 = x33;
    }
    
    public Matrix4x4(Matrix4x4 m)
    {
        X00 = m.X00;
        X01 = m.X01;
        X02 = m.X02;
        X03 = m.X03;

        X10 = m.X10;
        X11 = m.X11;
        X12 = m.X12;
        X13 = m.X13;

        X20 = m.X20;
        X21 = m.X21;
        X22 = m.X22;
        X23 = m.X23;

        X30 = m.X30;
        X31 = m.X31;
        X32 = m.X32;
        X33 = m.X33;
    }
    
    
    public void setAsIdentity()
    {
        X00 = 1.0f;
        X01 = 0.0f;
        X02 = 0.0f;
        X03 = 0.0f;

        X10 = 0.0f;
        X11 = 1.0f;
        X12 = 0.0f;
        X13 = 0.0f;

        X20 = 0.0f;
        X21 = 0.0f;
        X22 = 1.0f;
        X23 = 0.0f;

        X30 = 0.0f;
        X31 = 0.0f;
        X32 = 0.0f;
        X33 = 1.0f;
    }
    
    public Matrix4x4 multiply(Matrix4x4 m)
    {
        return new Matrix4x4(   X00*m.X00 + X01*m.X10 + X02*m.X20 + X03*m.X30,
                                X00*m.X01 + X01*m.X11 + X02*m.X21 + X03*m.X31,
                                X00*m.X02 + X01*m.X12 + X02*m.X22 + X03*m.X32,
                                X00*m.X03 + X01*m.X13 + X02*m.X23 + X03*m.X33,

                                X10*m.X00 + X11*m.X10 + X12*m.X20 + X13*m.X30,
                                X10*m.X01 + X11*m.X11 + X12*m.X21 + X13*m.X31,
                                X10*m.X02 + X11*m.X12 + X12*m.X22 + X13*m.X32,
                                X10*m.X03 + X11*m.X13 + X12*m.X23 + X13*m.X33,

                                X20*m.X00 + X21*m.X10 + X22*m.X20 + X23*m.X30,
                                X20*m.X01 + X21*m.X11 + X22*m.X21 + X23*m.X31,
                                X20*m.X02 + X21*m.X12 + X22*m.X22 + X23*m.X32,
                                X20*m.X03 + X21*m.X13 + X22*m.X23 + X23*m.X33,

                                X30*m.X00 + X31*m.X10 + X32*m.X20 + X33*m.X30,
                                X30*m.X01 + X31*m.X11 + X32*m.X21 + X33*m.X31,
                                X30*m.X02 + X31*m.X12 + X32*m.X22 + X33*m.X32,
                                X30*m.X03 + X31*m.X13 + X32*m.X23 + X33*m.X33);
    }
    
    
    public Matrix4x4 getInverse()
    {
        float s0 = X00*X11 - X10*X01;
        float s1 = X00*X12 - X10*X02;
        float s2 = X00*X13 - X10*X03;
        float s3 = X01*X12 - X11*X02;
        float s4 = X01*X13 - X11*X03;
        float s5 = X02*X13 - X12*X03;

        float c5 = X22*X33 - X32*X23;
        float c4 = X21*X33 - X31*X23;
        float c3 = X21*X32 - X31*X22;
        float c2 = X20*X33 - X30*X23;
        float c1 = X20*X32 - X30*X22;
        float c0 = X20*X31 - X30*X21;
        
        float inv_det = 1 / (s0*c5 - s1*c4 + s2*c3 + s3*c2 - s4*c1 + s5*c0);
        
        Matrix4x4 m = new Matrix4x4();

        m.X00 = (X11*c5 - X12*c4 + X13*c3)*inv_det;
        m.X01 = (-X01*c5 + X02*c4 - X03*c3)*inv_det;
        m.X02 = (X31*s5 - X32*s4 + X33*s3)*inv_det;
        m.X03 = (-X21*s5 + X22*s4 - X23*s3)*inv_det;

        m.X10 = (-X10*c5 + X12*c2 - X13*c1)*inv_det;
        m.X11 = (X00*c5 - X02*c2 + X03*c1)*inv_det;
        m.X12 = (-X30*s5 + X32*s2 - X33*s1)*inv_det;
        m.X13 = (X20*s5 - X22*s2 + X23*s1)*inv_det;

        m.X20 = (X10*c4 - X11*c2 + X13*c0)*inv_det;
        m.X21 = (-X00*c4 + X01*c2 - X03*c0)*inv_det;
        m.X22 = (X30*s4 - X31*s2 + X33*s0)*inv_det;
        m.X23 = (-X20*s4 + X21*s2 - X23*s0)*inv_det;

        m.X30 = (-X10*c3 + X11*c1 - X12*c0)*inv_det;
        m.X31 = (X00*c3 - X01*c1 + X02*c0)*inv_det;
        m.X32 = (-X30*s3 + X31*s1 - X32*s0)*inv_det;
        m.X33 = (X20*s3 - X21*s1 + X22*s0)*inv_det;
        
        return m;
    }
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(X00).append("\t").append(X01).append("\t").append(X02).append("\t").append(X03).append("\n");
		sb.append(X10).append("\t").append(X11).append("\t").append(X12).append("\t").append(X13).append("\n");
		sb.append(X20).append("\t").append(X21).append("\t").append(X22).append("\t").append(X23).append("\n");
		sb.append(X30).append("\t").append(X31).append("\t").append(X32).append("\t").append(X33).append("\n");
		return sb.toString();
	}
}
