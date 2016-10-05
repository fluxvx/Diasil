package diasil.color;

public class DImage<T>
{
    public T[][] C;
    private int width, height;
    public DImage(int w, int h)
    {
        width = w;
        height = h;
        C = (T[][])(new Object[w][h]);
    }
    public int width()
    {
        return width;
    }
    public int height()
    {
        return height;
    }
    public T get(int i, int j)
    {
        return C[i][j];
    }
    public void set(int i, int j, T t)
    {
        C[i][j] = t;
    }
}

