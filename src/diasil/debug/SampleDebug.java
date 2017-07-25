package diasil.debug;

import diasil.ImageManager;
import diasil.math.geometry2.Point2;
import diasil.sample.FilterBox;
import diasil.sample.Sample;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;
import diasil.sample.SamplerStratified;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.SplittableRandom;

public class SampleDebug
{
	public static void runTest1()
	{
		int n_samples = 1000;
		
		int w = 1000, h = 1000;
		int r = 5;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.BLACK);
		
		g.drawLine(0, h/2, w, h/2);
		g.drawLine(w/2, 0, w/2, h);
		
		for (int i=0; i<n_samples; ++i)
		{
			float u = (float)Math.random();
			float v = (float)Math.random();
			Point2 p = Sample.ConcentricSampleDisk(u, v);
			p.X = (p.X + 1)/2;
			p.Y = (p.Y + 1)/2;
			
			int x = (int)(p.X*w) - r;
			int y = (int)(p.Y*h) - r;
			
			g.fillOval(x, y, r, r);
			
		}
		
		ImageManager.show(img);
	}
	
	public static void runTest2()
	{
		int n_samples = 1000;
		
		int w = 1000, h = 1000;
		int r = 5;
		
		Sampler sampler = new SamplerStratified(5*5, 1.0f, new FilterBox(1.0f), new SplittableRandom());
		SampleCollector sc = new SampleCollector();
		for (int i=0; i<n_samples; ++i)
		{
			sc.addSamples2D(1);
		}
		sampler.allocateSamples(sc);
		
		
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.BLACK);
		
		g.drawLine(0, h/2, w, h/2);
		g.drawLine(w/2, 0, w/2, h);
		
		Sample[] samples = sampler.regenerateSamples(0, 0, 1, 1);
		for (int i=0; i<n_samples; ++i)
		{
			float u = samples[0].samples1D[i << 1];
			float v = samples[0].samples1D[(i << 1) + 1];
			
			Point2 p = Sample.ConcentricSampleDisk(u, v);
			
			int x = (int)(p.X*w) - r;
			int y = (int)(p.Y*h) - r;
			
			g.fillOval(x, y, r, r);
			
		}
		
		ImageManager.show(img);
	}
}
