package diasil.gui;

import diasil.color.RGBColor;
import diasil.color.RGBImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
    private BufferedImage image;
    public ImagePanel()
    {
        image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void setImage(RGBImage img)
    {
        image = new BufferedImage(img.width(), img.height(), BufferedImage.TYPE_INT_RGB);
        for (int i=0; i<img.width(); ++i)
        {
            for (int j=0; j<img.height(); ++j)
            {
                RGBColor cc = new RGBColor(img.X[i][j]);
                cc.clamp();
                
                int r = (int)(cc.R*255);
                int g = (int)(cc.G*255);
                int b = (int)(cc.B*255);
                
                Color color = new Color(r, g, b);
                image.setRGB(i, j, color.getRGB());
            }
        }
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        int image_x = getWidth()/2 - image.getWidth()/2;
        int image_y = getHeight()/2 - image.getHeight()/2;
        g.setColor(Color.WHITE);
        g.drawRect(image_x-1, image_y-1, image.getWidth()+1, image.getHeight()+1);
        g.drawImage(image, image_x, image_y, this);
        
    }
}
