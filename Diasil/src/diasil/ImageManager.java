package diasil;

import diasil.color.RGBColor;
import diasil.color.RGBImage;
import diasil.color.RawImage;
import diasil.color.RawSample;
import diasil.gui.ImagePanel;
import diasil.sample.Sample;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class ImageManager
{
	
	public void saveRawRender(RawImage img)
	{
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		saveRawRender("render" + format.format(date), img);
	}
	public void saveRawRender(String name, RawImage img)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("renders/"+name+".csv"), "utf-8"));
			writer.write(img.width+","+img.height+","+img.X[0][0].length);
			writer.newLine();
			for (int i=0; i<img.width; ++i)
			{
				for (int j=0; j<img.height; ++j)
				{
					for (int k=0; k<img.X[i][j].length; ++k)
					{
						RawSample s = img.X[i][j][k];
						writer.write(s.X + ","
									+ s.Y + ","
									+ s.wavelength + ","
									+ s.intensity);
						writer.newLine();
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public RawImage readRawRender(String path)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String[] info = reader.readLine().split(",");
			int width = Integer.parseInt(info[0]);
			int height = Integer.parseInt(info[1]);
			int spp = Integer.parseInt(info[2]);
			RawImage img = new RawImage(width, height, spp);
			for (int i=0; i<width; ++i)
			{
				for (int j=0; j<height; ++j)
				{
					for (int k=0; k<spp; ++k)
					{
						String[] d = reader.readLine().split(",");
						float x = Float.parseFloat(d[0]);
						float y = Float.parseFloat(d[1]);
						float wavelength = Float.parseFloat(d[4]);
						float intensity = Float.parseFloat(d[5]);
						img.X[i][j][k] = new RawSample(x, y, wavelength, intensity);
					}
				}
			}
			return img;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static int encodeRGB(RGBColor c)
	{
		c.clamp();
        int r = (int)(c.R*255.0f);
        int g = (int)(c.G*255.0f);
        int b = (int)(c.B*255.0f);
		
        int rgb = r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		
		return rgb;
	}
	public static RGBColor decodeRGB(int rgb)
	{
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = rgb & 0xFF;
		return new RGBColor(r/255.0f, g/255.0f, b/255.0f);
	}
	
	public static void save(RGBImage img)
	{
		Date date = new Date() ;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		save("render" + format.format(date), img);
	}
    
    public static void save(String name, RGBImage img)
    {
        BufferedImage image = new BufferedImage(img.width(), img.height(), BufferedImage.TYPE_INT_RGB);
        for (int i=0; i<img.width(); ++i)
        {
            for (int j=0; j<img.height(); ++j)
            {
				int rgb = encodeRGB(img.X[i][j]);
                image.setRGB(i, j, rgb);
            }
        }
        save(name, image);
    }
    
    private static void save(String name, BufferedImage image)
    {
        // String default_path = System.getProperty("user.dir");
        File file = new File("./renders/"+name+".png");
        file.mkdirs();
        try
        {
            ImageIO.write(image, "png", file);
        }
        catch (IOException ex)
        {
			ex.printStackTrace();
        }
    }
	
	
	
	public static void show(RGBImage render)
	{
		JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImagePanel image_panel = new ImagePanel();
        image_panel.setImage(render);
        frame.setLayout(new BorderLayout());
        frame.add(image_panel, BorderLayout.CENTER);
        
        frame.setSize(render.width()+100, render.height()+100);
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
}
