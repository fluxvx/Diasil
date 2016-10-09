package diasil;

import diasil.camera.Camera;
import diasil.entity.Aggregate;
import diasil.entity.Entity;
import java.util.ArrayList;

public class Scene
{
    public Aggregate aggregate;
    public ArrayList<Camera> cameras;
    public int current_camera_id;
    public float min_t;
    public float refractive_index;
    public Scene()
    {   
        aggregate = new Aggregate();
        
        cameras = new ArrayList<Camera>();
        current_camera_id = -1;
    }
    
    public void add(Entity it)
    {
        aggregate.add(it);
    }
    
    public void add(Camera camera)
    {
        cameras.add(camera);
        current_camera_id = cameras.size() - 1;
    }
	
	public void compileTransforms()
	{
		aggregate.compileTransforms();
	}
    
    
    public Camera getCurrentCamera()
    {
        return (current_camera_id < 0)? null: cameras.get(current_camera_id);
    }
    
}
