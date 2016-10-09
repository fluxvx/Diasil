package diasil.math.geometry3;

import diasil.math.Matrix4x4;

public class MTransform3 extends Transform3
{

    public Matrix4x4 M, I;

    public MTransform3()
    {
        M = new Matrix4x4();
        I = new Matrix4x4();
    }

    public MTransform3(Matrix4x4 m, Matrix4x4 i)
    {
        M = new Matrix4x4(m);
        I = new Matrix4x4(i);
    }
	
    public MTransform3(MTransform3 t)
    {
        M = new Matrix4x4(t.M);
        I = new Matrix4x4(t.I);
    }

    public MTransform3 multiply(MTransform3 t)
    {
        return new MTransform3(M.multiply(t.M), t.I.multiply(I));
    }

    public Point3 toWorldSpace(Point3 p)
    {
        float tx = M.X00 * p.X + M.X01 * p.Y + M.X02 * p.Z + M.X03;
        float ty = M.X10 * p.X + M.X11 * p.Y + M.X12 * p.Z + M.X13;
        float tz = M.X20 * p.X + M.X21 * p.Y + M.X22 * p.Z + M.X23;
        float w = M.X30 * p.X + M.X31 * p.Y + M.X32 * p.Z + M.X33;
        if (w != 1.F)
        {
            float inv_w = 1.F / w;
            return new Point3(tx * inv_w, ty * inv_w, tz * inv_w);
        }
        return new Point3(tx, ty, tz);
    }

    public Point3 toObjectSpace(Point3 p)
    {
        float tx = I.X00 * p.X + I.X01 * p.Y + I.X02 * p.Z + I.X03;
        float ty = I.X10 * p.X + I.X11 * p.Y + I.X12 * p.Z + I.X13;
        float tz = I.X20 * p.X + I.X21 * p.Y + I.X22 * p.Z + I.X23;
        float w = I.X30 * p.X + I.X31 * p.Y + I.X32 * p.Z + I.X33;
        if (w != 1.F)
        {
            float inv_w = 1.F / w;
            return new Point3(tx * inv_w, ty * inv_w, tz * inv_w);
        }
        return new Point3(tx, ty, tz);
    }

    public Vector3 toWorldSpace(Vector3 v)
    {
        return new Vector3(M.X00 * v.X + M.X01 * v.Y + M.X02 * v.Z,
                           M.X10 * v.X + M.X11 * v.Y + M.X12 * v.Z,
                           M.X20 * v.X + M.X21 * v.Y + M.X22 * v.Z);
    }

    public Vector3 toObjectSpace(Vector3 v)
    {
        return new Vector3(I.X00 * v.X + I.X01 * v.Y + I.X02 * v.Z,
                           I.X10 * v.X + I.X11 * v.Y + I.X12 * v.Z,
                           I.X20 * v.X + I.X21 * v.Y + I.X22 * v.Z);
    }

    public Normal3 toWorldSpace(Normal3 n)
    {
        return new Normal3(I.X00 * n.X + I.X10 * n.Y + I.X20 * n.Z,
                           I.X01 * n.X + I.X11 * n.Y + I.X21 * n.Z,
                           I.X02 * n.X + I.X12 * n.Y + I.X22 * n.Z);
    }

    public Normal3 toObjectSpace(Normal3 n)
    {
        return new Normal3(M.X00 * n.X + M.X10 * n.Y + M.X20 * n.Z,
                           M.X01 * n.X + M.X11 * n.Y + M.X21 * n.Z,
                           M.X02 * n.X + M.X12 * n.Y + M.X22 * n.Z);
    }
	
    public void setAsIdentity()
    {
        M.setAsIdentity();
        I.setAsIdentity();
    }

    public void setAsTranslator(float x, float y, float z)
    {
        setAsIdentity();

        M.X03 = x;
        M.X13 = y;
        M.X23 = z;

        I.X03 = -x;
        I.X13 = -y;
        I.X23 = -z;
    }
    
    public static MTransform3 createTranslator(float x, float y, float z)
    {
        MTransform3 r = new MTransform3();
        r.setAsTranslator(x, y, z);
        return r;
    }

    public void setAsScaler(float x, float y, float z)
    {
        setAsIdentity();

        M.X00 = x;
        M.X11 = y;
        M.X22 = z;

        I.X00 = 1.0f / x;
        I.X11 = 1.0f / y;
        I.X22 = 1.0f / z;
    }
    
    public static MTransform3 createScaler(float x, float y, float z)
    {
        MTransform3 r = new MTransform3();
        r.setAsScaler(x, y, z);
        return r;
    }

    public void setAsRotatorXY(float t)
    {
        setAsIdentity();

        float cost = (float)Math.cos(t);
        float sint = (float)Math.sin(t);

        M.X00 = cost;
        M.X01 = -sint;
        M.X10 = sint;
        M.X11 = cost;

        I.X00 = cost;
        I.X01 = sint;
        I.X10 = -sint;
        I.X11 = cost;
    }

    public void setAsRotatorXZ(float t)
    {
        setAsIdentity();

        float cost = (float)Math.cos(t);
        float sint = (float)Math.sin(t);

        M.X00 = cost;
        M.X02 = sint;
        M.X20 = -sint;
        M.X22 = cost;

        I.X00 = cost;
        I.X02 = -sint;
        I.X20 = sint;
        I.X22 = cost;
    }

    public void setAsRotatorYZ(float t)
    {
        setAsIdentity();

        float cost = (float)Math.cos(t);
        float sint = (float)Math.sin(t);

        M.X11 = cost;
        M.X12 = -sint;
        M.X21 = sint;
        M.X22 = cost;

        I.X11 = cost;
        I.X12 = sint;
        I.X21 = -sint;
        I.X22 = cost;
    }

    public void setAsRotator(float xy, float xz, float yz)
    {
        float cx = (float)Math.cos(yz);
        float sx = (float)Math.sin(yz);
        float cy = (float)Math.cos(xz);
        float sy = (float)Math.sin(xz);
        float cz = (float)Math.cos(xy);
        float sz = (float)Math.sin(xy);

        M.X00 = I.X00 = cy * cz;
        M.X01 = I.X10 = -cy * sz;
        M.X02 = I.X20 = sy;
        M.X03 = I.X30 = 0;

        M.X10 = I.X01 = sx * sy * cz + cx * sz;
        M.X11 = I.X11 = cx * cz - sx * sy * sz;
        M.X12 = I.X21 = -sx * cy;
        M.X13 = I.X31 = 0;

        M.X20 = I.X02 = sx * sz - cx * sy * cz;
        M.X21 = I.X12 = cx * sy * sz + sx * cz;
        M.X22 = I.X22 = cx * cy;
        M.X23 = I.X32 = 0;

        M.X30 = I.X03 = 0;
        M.X31 = I.X13 = 0;
        M.X32 = I.X23 = 0;
        M.X33 = I.X33 = 1;
    }
    
    public static MTransform3 createRotator(float xy, float xz, float yz)
    {
        MTransform3 r = new MTransform3();
        r.setAsRotator(xy, xz, yz);
        return r;
    }


    public void setAsPerspective(float fov, float n, float f)
    {
        setAsIdentity();

        float p = 1.F / (1.F - n / f);
        float inv_tan = 1.F / (float)Math.tan(fov * 0.5F);
        M.X00 = inv_tan;
        M.X11 = inv_tan;
        M.X22 = p;
        M.X22 = -n * p;
        M.X32 = 1.F;
        M.X33 = 0.F;
    }
    
    
    public void setAsBasis(Point3 c, Vector3 x, Vector3 y, Normal3 z)
    {
        M.X00 = x.X;
        M.X10 = x.Y;
        M.X20 = x.Z;
        M.X30 = 0.0f;
        
        M.X01 = y.X;
        M.X11 = y.Y;
        M.X21 = y.Z;
        M.X31 = 0.0f;
        
        M.X02 = z.X;
        M.X12 = z.Y;
        M.X22 = z.Z;
        M.X32 = 0.0f;
        
        M.X03 = c.X;
        M.X13 = c.Y;
        M.X23 = c.Z;
        M.X33 = 1.0f;
        
        I = M.getInverse();
    }
	
	public MTransform3 toMTransform3()
	{
		return new MTransform3(this);
	}
}
