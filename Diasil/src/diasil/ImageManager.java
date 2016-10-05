package diasil;

import diasil.color.DImage;
import diasil.color.RGBColor;
import diasil.color.ToneMapper;
import diasil.color.XYZColor;
import diasil.color.XYZtoRGB;
import diasil.gui.ImagePanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class ImageManager
{
	public DImage<RGBColor> toRGB(DImage<XYZColor> image, XYZtoRGB xyz_to_rgb, ToneMapper tone_mapper)
    {
        if (tone_mapper != null)
            image = tone_mapper.toneMap(image);
        DImage<RGBColor> r = new DImage<RGBColor>(image.width(), image.height());
        for (int i=0; i<r.width(); ++i)
        {
            for (int j=0; j<r.height(); ++j)
            {
                r.set(i, j, xyz_to_rgb.toRGB(image.get(i, j)));
            }
        }
        return r;
    }
	
	
    private static void saveCSV(String name, DImage<XYZColor> image)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("renders/"+name+".csv"), "utf-8"));
            for (int i=0; i<image.width(); ++i)
            {
                for (int j=0; j<image.height(); ++j)
                {
                    XYZColor color = image.get(i, j);
                    writer.write(color.X+"-"+color.Y+"-"+color.Z);
                    if (j < image.height() -  1)
                    {
                        writer.write(",");
                    }
                }
                if (i < image.width() - 1)
                {
                    writer.newLine();
                }
            }
            writer.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private static DImage<XYZColor> readCSV(String name)
    {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/renders/"+name+".csv")));
        List<String> lines = Utility.readAllLines(name);
        String[] split = lines.get(0).split(",");

        DImage<XYZColor> r = new DImage<XYZColor>(lines.size(), split.length);
        for (int i=0; i<lines.size(); ++i)
        {
            split = lines.get(i).split(",");
            for (int j=0; j<split.length; ++j)
            {
                String[] cs = split[j].split("-");
                float x = Float.parseFloat(cs[0]);
                float y = Float.parseFloat(cs[1]);
                float z = Float.parseFloat(cs[2]);
                r.set(i, j, new XYZColor(x, y, z));
            }
        }
        return r;
    }
	
	public static void save(DImage<RGBColor> img)
	{
		Date date = new Date() ;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		save("render" + format.format(date), img);
	}
    
    public static void save(String name, DImage<RGBColor> img)
    {
        BufferedImage image = new BufferedImage(img.width(), img.height(), BufferedImage.TYPE_INT_RGB);
        for (int i=0; i<img.width(); ++i)
        {
            for (int j=0; j<img.height(); ++j)
            {
                Color color = RGBColorToColor(img.get(i, j));
                image.setRGB(i, j, color.getRGB());
            }
        }
        save(name, image);
    }
    
    
    private static Color RGBColorToColor(RGBColor c)
    {
        c.clamp();
        int r = (int)(c.R*255);
        int g = (int)(c.G*255);
        int b = (int)(c.B*255);
        return new Color(r, g, b);
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
	
	
	
	public static void show(DImage<RGBColor> render)
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
