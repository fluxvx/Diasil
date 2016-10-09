
package diasil;

import diasil.camera.Camera;
import diasil.camera.SimpleCamera;
import diasil.color.DImage;
import diasil.color.RGBColor;
import diasil.color.SPD;
import diasil.entity.Sphere;
import diasil.material.LambertianMaterial;
import diasil.material.Material;
import diasil.math.DMath;
import diasil.math.geometry3.Translate3;
import diasil.render.DepthTracer;

public class Diasil
{
	public static void main(String[] args)
	{
		System.out.println(DMath.seedRNG());
		
		Scene scene = new Scene();
		
		Material lambertian_white = new LambertianMaterial(SPD.getGaussianDistribution(0.0f, 1.0f, 1E-7f, (400+700/2)));
        Sphere sphere = new Sphere(0.5f, lambertian_white);
		scene.add(sphere);
		
		Camera camera = new SimpleCamera(3.0f);
		float s = 100.0f;
		camera.screen_space.scale(s, s);
        camera.transforms.add(new Translate3(0.0f, 0.0f, -8.0f));
		scene.add(camera);
		
		DImage<RGBColor> depth_test = new DImage<RGBColor>(1000, 1000);
		DepthTracer dt = new DepthTracer();
		
		for (int i=0; i<100; ++i)
		{
			System.out.println(i + " " + s);
			dt.render(scene, depth_test);
			ImageManager.save("glitch"+i, depth_test);
			s *= 0.9f;
			camera.screen_space.setAsIdentity();
			camera.screen_space.scale(s, s);
			
		}
        
        
	}
	
}
