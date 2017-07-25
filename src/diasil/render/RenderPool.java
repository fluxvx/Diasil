package diasil.render;

import diasil.Diasil;
import diasil.DUtility;
import diasil.color.SimpleFilm;
import diasil.sample.SampleCollector;
import diasil.sample.Sampler;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RenderPool
{
	private int block_size;
	private int n_threads;
	
	private long render_start_milli;
	private int n_pixels_total;
	private int n_pixels_completed;
	
	public RenderPool()
	{
		this(20);
	}
	public RenderPool(int block_size)
	{
		this(block_size, -1);
	}
	
	public RenderPool(int block_size, int n_threads)
	{
		this.block_size = block_size;
		this.n_threads = n_threads;
	}
		
    public void render(SimpleFilm film, Sampler sampler, Integrator renderer)
    {
		render_start_milli = System.currentTimeMillis();
		
        n_pixels_completed = 0;
		n_pixels_total = film.width()*film.height();
		if (n_threads <= 0)
		{
			n_threads = Runtime.getRuntime().availableProcessors();
		}
		System.out.println("rendering "+render_start_milli);
		System.out.println("\tblock size: "+block_size);
		System.out.println("\tn_threads: "+n_threads);
        ExecutorService render_pool = Executors.newFixedThreadPool(n_threads);
		
		SampleCollector sc = new SampleCollector();
		renderer.prepareForRendering(sc);
		Sampler[] samplers = new Sampler[n_threads];
        for (int i=0; i<n_threads; ++i)
        {
			samplers[i] = sampler.clone();
			samplers[i].allocateSamples(sc);
        }
		
		for (int i=0; i<n_threads; ++i)
		{
			render_pool.execute(new RenderThread(i, n_threads, block_size, samplers[i], film, renderer, this));
		}
		
		render_pool.shutdown();
		try
		{
			if (render_pool.awaitTermination(7, TimeUnit.DAYS))
			{
				System.out.println("render completed!");
				Diasil.renderCompleted(film);
			}
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
    }
	
	public void pixelCompleted()
	{
		n_pixels_completed++;
        if (n_pixels_completed%1000 == 0)
        {
            int n_pixels_remaining = n_pixels_total - n_pixels_completed;
            float pc = n_pixels_completed*100.0f/n_pixels_total;
			long elapsed_time = System.currentTimeMillis() - render_start_milli;
            float average_time = ((float)(elapsed_time))/(n_pixels_completed+1);
			long remaining_time = (long)(average_time*n_pixels_remaining);

            DecimalFormat df = new DecimalFormat("#.##");
            String pc_s = df.format(pc)+"%";

            String txt = pc_s + ", elapsed: " + DUtility.getReadableTime(elapsed_time)
								+ ", remaining: "+DUtility.getReadableTime(remaining_time)
								+ ", total: " + DUtility.getReadableTime(elapsed_time + remaining_time);

            System.out.println(txt);
        }
	}
}
