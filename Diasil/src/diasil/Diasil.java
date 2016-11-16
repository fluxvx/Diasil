
package diasil;

import diasil.camera.Camera;
import diasil.camera.PerspectiveCamera;
import diasil.color.RGBImage;
import diasil.color.SpectralDistribution;
import diasil.color.SimpleFilm;
import diasil.color.ToneMapMaximumToWhite;
import diasil.color.ToneMapper;
import diasil.color.XYZtoRGB;
import diasil.debug.BooleanTracer;
import diasil.debug.DepthTracer;
import diasil.debug.SampleDebug;
import diasil.intersect.Aggregate;
import diasil.intersect.Cube;
import diasil.intersect.Cylinder;
import diasil.intersect.Hypercube;
import diasil.intersect.KDTree;
import diasil.intersect.Sphere;
import diasil.intersect.TriangleMesh;
import diasil.intersect.XYPlane;
import diasil.intersect.XZDisc;
import diasil.light.Light;
import diasil.light.PointLight;
import diasil.light.AreaLight;
import diasil.material.DiffuseMaterial;
import diasil.material.GlassMaterial;
import diasil.material.Material;
import diasil.material.SellmeierEquation;
import diasil.math.DMath;
import diasil.math.Matrix4x4;
import diasil.math.SampleRange;
import diasil.math.geometry3.CoordinateSpace3;
import diasil.math.geometry3.Function3;
import diasil.math.geometry3.Point3;
import diasil.math.geometry3.RotateXZ;
import diasil.math.geometry3.RotateYZ;
import diasil.math.geometry3.Scale3;
import diasil.math.geometry3.SphericalToRectangular;
import diasil.math.geometry3.Translate3;
import diasil.render.RenderPool;
import diasil.render.Renderer;
import diasil.render.ReverseRayTracer;
import diasil.sample.FilterGaussian;
import diasil.sample.SampleCollector;
import diasil.sample.SamplerStratified;
import diasil.sample.Sampler;
import diasil.sample.SamplerRandom;
import java.util.Random;
import java.util.SplittableRandom;
import javafx.scene.transform.Scale;

public class Diasil
{
	public static void main(String[] args)
	{
		
		long seed = System.currentTimeMillis();
		System.out.println("seed: "+seed);
		
		Scene scene = new Scene();
		
		
		SpectralDistribution white = SpectralDistribution.constant(1.0f);
		SpectralDistribution orange = SpectralDistribution.getGaussianDistribution(0.1f, 1.0f, 1E-3f, 590.0f);
		SpectralDistribution blue = SpectralDistribution.getGaussianDistribution(0.1f, 1.0f, 1E-3f, 475.0f);
		
		Material lambertian_white = new DiffuseMaterial(white, 0.0f);
		
		float br = 20.0f;
		float brs = br+0.01f;
		XYPlane left_plane = new XYPlane(brs, brs, lambertian_white);
		left_plane.rotateXZ(DMath.PI/2);
		left_plane.translate(-br, 0.0f, 0.0f);
		scene.add(left_plane);
		
		XYPlane right_plane = new XYPlane(brs, brs, lambertian_white);
		right_plane.rotateXZ(DMath.PI/2);
		right_plane.translate(br, 0.0f, 0.0f);
		scene.add(right_plane);
		
		XYPlane bottom_plane = new XYPlane(brs, brs, lambertian_white);
		bottom_plane.rotateYZ(DMath.PI/2);
		bottom_plane.translate(0.0f, -br, 0.0f);
		scene.add(bottom_plane);
		
		XYPlane top_plane = new XYPlane(brs, brs, lambertian_white);
		top_plane.rotateYZ(DMath.PI/2);
		top_plane.translate(0.0f, br, 0.0f);
		scene.add(top_plane);
		
		XYPlane back_plane = new XYPlane(brs, brs, lambertian_white);
		back_plane.translate(0.0f, 0.0f, br);
		scene.add(back_plane);
		
		
		/*Sphere white_sphere = new Sphere(4.0f, lambertian_white);
		white_sphere.translate(-10.0f, 2.0f, 10.0f);
		scene.add(white_sphere);
		
		Material glass = new GlassMaterial(white, white, SpectralDistribution.constant(1.3f));
		Sphere glass_sphere = new Sphere(6.0f, glass);
		glass_sphere.translate(6.0f, -16.0f, 6.0f);
		//glass_sphere.translate(-8f, 0f, 2.0f);
		scene.add(glass_sphere);
		
		Cube box = new Cube(6.0f, new DiffuseMaterial(white, 0.0f));
		box.translate(-10.0f, -14.0f, 10.0f);
		box.scale(1.0f, 2.0f, 1.0f);
		box.rotateXZ(DMath.PI/4.0f);
		scene.add(box);*/
		
		
		/*Function3 f = new Function3()
		{
			public float Z(float x, float y)
			{
				float theta = x;
				float phi = y;
				//float r = 1+2*DMath.cos(2*x);
				float r = DMath.cos(2*x)*DMath.cos(2*y);
				return r*12;
			}
		};
		SampleRange rx = new SampleRange(0, DMath.PI, 200, false);
		SampleRange ry = new SampleRange(0, DMath.PI2, 200, false);
		TriangleMesh mesh = new TriangleMesh(f, rx, ry, new SphericalToRectangular(), new GlassMaterial(white, white, SpectralDistribution.constant(1.3f)));
		mesh.rotateXZ(DMath.PI/4);
		mesh.rotateYZ(DMath.PI/4);
		//mesh.translate(0, -5, 0);
		scene.add(mesh);*/
		
		Hypercube hypercube = new Hypercube(6, 6.0f, 0.3f, new GlassMaterial(white, white, SpectralDistribution.constant(1.3f)));
		scene.add(hypercube);
		
		/*Random rt = new Random(1000);
		Aggregate aggr = new KDTree(30, 10);
		for (int i=0; i<50; ++i)
		{
			Sphere s = new Sphere(1.0f, lambertian_white);
			float x = (2*rt.nextFloat()-1)*10;
			float y = (2*rt.nextFloat()-1)*10;
			float z = (2*rt.nextFloat()-1)*10;
			s.translate(x,y,z);
			aggr.add(s);
		}
		scene.add(aggr);*/
		
		
		SpectralDistribution light_color = SpectralDistribution.getBlackbodyDistribution(6500.0f);
		/*PointLight point_light = new PointLight(light_color);
		point_light.translate(3.0f, 3.0f, -3.0f);
		scene.add(point_light);*/
		
		Light disc_light = new AreaLight(new XZDisc(br-2f, br/3.0f, null), light_color);
		scene.add(disc_light);
		
		//Cylinder light_block = new Cylinder(br/3.0f+1, 4.5f, lambertian_white);
		//light_block.rotateYZ(DMath.PI/2);
		//light_block.translate(0.0f, br, 0.0f);
		//scene.add(light_block);
		
		Camera camera = new PerspectiveCamera(0.0f, 2*br, DMath.PI/2.5f, 0.0f);
        camera.translate(0.0f, 0.0f, -2*br);
		scene.add(camera);
		
		SplittableRandom random = new SplittableRandom(seed);
		
		int samples_per_pixel = 10*10;
		Sampler sampler = new SamplerStratified(samples_per_pixel, 1.0f, new FilterGaussian(0.5f, 1.0f), random);
		SimpleFilm film = new SimpleFilm(600, 600);
		Renderer renderer = new ReverseRayTracer(scene);
		
		CoordinateSpace3.compileAll();
		
        RenderPool render_pool = new RenderPool();
		render_pool.render(film, sampler, renderer);
		
		
		/*RGBImage render = new RGBImage(500, 500);
		sampler.allocateSamples(new SampleCollector());
		DepthTracer dt = new DepthTracer();
		//BooleanTracer dt = new BooleanTracer();
		//BooleanSamplerTracer dt = new BooleanSamplerTracer(sampler);
		dt.render(scene, render);
		ImageManager.show(render);*/
		
	}
	
	
    public static void renderCompleted(SimpleFilm film)
    {
		ToneMapper tone_mapper = new ToneMapMaximumToWhite(10.0f);
		RGBImage rgb_image = film.toRGB(XYZtoRGB.sRGB, tone_mapper);
		ImageManager.save(rgb_image);
		ImageManager.show(rgb_image);
    }
	
}
