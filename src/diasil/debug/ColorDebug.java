package diasil.debug;

import diasil.ImageManager;
import diasil.color.RGBColor;
import diasil.color.RGBImage;
import diasil.color.SpectralDistribution;
import diasil.color.SimpleFilm;
import diasil.color.XYZColor;
import diasil.color.XYZtoRGB;

public class ColorDebug
{
	public static void showSPD(SpectralDistribution s)
	{
		int n_samples = 5000;
		XYZColor xyz = new XYZColor(0.0f);
		for (int i=0; i<n_samples; ++i)
		{
			float wl = 300+(((float)(i))/n_samples)*(800-300);
			XYZColor t = XYZColor.findForWavelength(wl);
			float v = s.evaluate(wl);
			xyz.X += v*t.X;
			xyz.Y += v*t.Y;
			xyz.Z += v*t.Z;
		}
		RGBColor rgb = XYZtoRGB.sRGB.toRGB(xyz);
		

		
		float max = Math.max(Math.max(rgb.R, rgb.G), rgb.B);
		rgb.R /= max;
		rgb.G /= max;
		rgb.B /= max;
		
		rgb.R = SimpleFilm.gammaEncode(rgb.R);
		rgb.G = SimpleFilm.gammaEncode(rgb.G);
		rgb.B = SimpleFilm.gammaEncode(rgb.B);
		
		RGBImage img = new RGBImage(500, 500);
		for (int i=0; i<img.width; ++i)
		{
			for (int j=0; j<img.height; ++j)
			{
				img.X[i][j] = rgb;
			}
		}
		
		ImageManager.show(img);
		
	}
}
