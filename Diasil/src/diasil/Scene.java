package diasil;

import diasil.camera.Camera;
import diasil.intersect.Aggregate;
import diasil.intersect.Shape;
import diasil.light.Light;
import java.util.ArrayList;

public class Scene
{
    public Aggregate aggregate;
    public ArrayList<Camera> cameras;
	public ArrayList<Light> lights;
    public int current_camera_id;
    public float refractive_index;
    public Scene()
    {   
        aggregate = new Aggregate();
        cameras = new ArrayList<Camera>();
        current_camera_id = -1;
		lights = new ArrayList<Light>();
		refractive_index = 1.0f;
    }
    
    public void add(Shape it)
    {
        aggregate.add(it);
    }
    
    public void add(Camera camera)
    {
        cameras.add(camera);
        current_camera_id = cameras.size() - 1;
    }
	
	public void add(Light light)
	{
		lights.add(light);
	}
	
	public void compileTransforms()
	{
		aggregate.compileTransforms();
		for (int i=0; i<cameras.size(); ++i)
		{
			cameras.get(i).compileTransforms();
		}
		for (int i=0; i<lights.size(); ++i)
		{
			lights.get(i).compileTransforms();
		}
	}
    
    
    public Camera getCurrentCamera()
    {
        return (current_camera_id < 0)? null: cameras.get(current_camera_id);
    }
    
}
