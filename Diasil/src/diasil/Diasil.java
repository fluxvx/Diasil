
package diasil;

import diasil.camera.Camera;
import diasil.camera.PerspectiveCamera;
import diasil.color.RGBColor;
import diasil.color.RGBImage;
import diasil.color.SPD;
import diasil.color.SPDTable;
import diasil.color.SimpleFilm;
import diasil.color.ToneMapMaximumToWhite;
import diasil.color.ToneMapScale;
import diasil.color.ToneMapper;
import diasil.color.XYZColor;
import diasil.color.XYZImage;
import diasil.color.XYZtoRGB;
import diasil.debug.BooleanSamplerTracer;
import diasil.intersect.Cube;
import diasil.intersect.Sphere;
import diasil.light.Light;
import diasil.light.PointLight;
import diasil.material.DiffuseMaterial;
import diasil.material.Material;
import diasil.math.DMath;
import diasil.math.geometry3.Translate3;
import diasil.debug.BooleanTracer;
import diasil.debug.DepthTracer;
import diasil.render.RenderPool;
import diasil.render.Renderer;
import diasil.render.ReverseRayTracer;
import diasil.sample.FilterGaussian;
import diasil.sample.SampleCollector;
import diasil.sample.SamplerStratified;
import diasil.sample.Sampler;
import diasil.sample.SamplerRandom;
import java.util.SplittableRandom;

public class Diasil
{
	public static void main(String[] args)
	{
		long seed = System.currentTimeMillis();
		System.out.println("seed: "+seed);
		
		Scene scene = new Scene();
		
		SPD white = SPD.constant(1.0f);
		Material lambertian_white = new DiffuseMaterial(white, 0.0f);
		
		Sphere sphere = new Sphere(1.0f, lambertian_white);
		scene.add(sphere);
		
		Cube cube = new Cube(20.0f, lambertian_white);
		scene.add(cube);
		
		SPD light_color = SPD.getBlackbodyDistribution(6000.0f);
		Light point_light = new PointLight(light_color);
		point_light.transforms.add(new Translate3(3.0f, 3.0f, -3.0f));
		scene.add(point_light);
		
		
		Camera camera = new PerspectiveCamera(DMath.PI/8f, 8.0f, 0.0f);
        camera.transforms.add(new Translate3(0.0f, 0.0f, -8.0f));
		scene.add(camera);
		
		SplittableRandom random = new SplittableRandom(seed);
		
		int samples_per_pixel = 20*20;
		Sampler sampler = new SamplerStratified(samples_per_pixel, 1.0f, new FilterGaussian(0.5f, 1.0f), random);
		SimpleFilm film = new SimpleFilm(600, 600);
		Renderer renderer = new ReverseRayTracer(scene);
		
		scene.compileTransforms();
		renderer.prepareForRendering();
		
        RenderPool render_pool = new RenderPool(20, 4);
		render_pool.render(film, sampler, renderer);
		
		
		/*RGBImage render = new RGBImage(500, 500);
		sampler.allocateSamples(new SampleCollector());
		DepthTracer dt = new DepthTracer();
		BooleanSamplerTracer dt = new BooleanSamplerTracer(sampler);
		dt.render(scene, render);
		ImageManager.show(render);*/


		
	}
	
	
    public static void renderCompleted(SimpleFilm film)
    {
		ToneMapper tone_mapper = new ToneMapMaximumToWhite(1.0f);
		RGBImage rgb_image = film.toRGB(XYZtoRGB.sRGB, tone_mapper);
		ImageManager.save(rgb_image);
		ImageManager.show(rgb_image);
    }
	
}
