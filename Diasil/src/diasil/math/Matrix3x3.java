package diasil.math;

public final class Matrix3x3
{
    public float X00, X01, X02;
    public float X10, X11, X12;
    public float X20, X21, X22;
    
    public Matrix3x3()
    {
        setAsIdentity();
    }
    
    public Matrix3x3(float x00, float x01, float x02,
                     float x10, float x11, float x12,
                     float x20, float x21, float x22)
    {
        X00 = x00;
        X01 = x01;
        X02 = x02;
        X10 = x10;
        X11 = x11;
        X12 = x12;
        X20 = x20;
        X21 = x21;
        X22 = x22;
    }
    
    public Matrix3x3(Matrix3x3 m)
    {
        X00 = m.X00;
        X01 = m.X01;
        X02 = m.X02;
        X10 = m.X10;
        X11 = m.X11;
        X12 = m.X12;
        X20 = m.X20;
        X21 = m.X21;
        X22 = m.X22;
    }
    
    
    public void setAsIdentity()
    {
        X00 = 1.0f;
        X01 = 0.0f;
        X02 = 0.0f;
        X10 = 0.0f;
        X11 = 1.0f;
        X12 = 0.0f;
        X20 = 0.0f;
        X21 = 0.0f;
        X22 = 1.0f;
    }
    
    
    public Matrix3x3 multiply(Matrix3x3 m)
    {
        return new Matrix3x3(X00*m.X00+X01*m.X10+X02*m.X20,
                                X00*m.X01+X01*m.X11+X02*m.X21,
                                X00*m.X02+X01*m.X12+X02*m.X22,

                                X10*m.X00+X11*m.X10+X12*m.X20,
                                X10*m.X01+X11*m.X11+X12*m.X21,
                                X10*m.X02+X11*m.X12+X12*m.X22,

                                X20*m.X00+X21*m.X10+X22*m.X20,
                                X20*m.X01+X21*m.X11+X22*m.X21,
                                X20*m.X02+X21*m.X12+X22*m.X22);
    }
    
    
    
    
    public float getDeterminant()
    {
        return X00*(X22*X11 - X21*X12)
            - X10*(X22*X01 - X21*X02)
            + X20*(X12*X01 - X11*X02);
    }
    public Matrix3x3 getInverse()
    {
        float id = 1.0f / getDeterminant();
        return new Matrix3x3(id*(X11*X00-X10*X01),
                             id*(X21*X02-X22*X01),
                             id*(X12*X01-X11*X02),
                             id*(X20*X12-X22*X10),
                             id*(X22*X00-X20*X02),
                             id*(X10*X02-X12*X00),
                             id*(X21*X10-X20*X11),
                             id*(X20*X01-X21*X00),
                             id*(X11*X00-X10*X01));
    }
    
}