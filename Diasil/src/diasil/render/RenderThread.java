package diasil.render;

import diasil.color.SimpleFilm;
import diasil.sample.Sample;
import diasil.sample.Sampler;

public class RenderThread implements Runnable
{
	private RenderPool render_thread_pool;
	private Integrator renderer;
    private Sampler sampler;
    private SimpleFilm film;
    private int index, period, block_size;
    public RenderThread(int index, int period, int block_size, Sampler sampler, SimpleFilm film, Integrator renderer, RenderPool render_thread_pool)
    {
        this.index = index;
        this.period = period;
		this.block_size = block_size;
        this.sampler = sampler;
        this.film = film;
		this.renderer = renderer;
        this.render_thread_pool = render_thread_pool;
    }
    public void run()
    {
		for (int i=index; i<film.width()/block_size; i+=period)
		{
			int x_start = i*block_size;
			int x_end = Math.min(x_start + block_size, film.width());
			for (int j=0; j<film.height()/block_size; ++j)
			{
				int y_start = j*block_size;
				int y_end = Math.min(y_start+block_size, film.height());
				renderBlock(x_start, x_end, y_start, y_end);
			}
		}
		//System.out.println("thread stopped");
    }
	
	private void renderBlock(int x_start, int x_end, int y_start, int y_end)
	{
		//System.out.println(Thread.currentThread().getId()+" - rendering ["+x_start+","+y_start+"] to ["+x_end+","+y_end+"]");
		for (int i=x_start; i<x_end; ++i)
		{
			for (int j=y_start; j<y_end; ++j)
			{
				Sample[] samples = sampler.regenerateSamples(i, j, film.width(), film.height());
				for (int k=0; k<samples.length; ++k)
				{
					renderer.takeSample(samples[k], sampler);
					film.recordContribution(i, j, k, samples[k]);
				}
				render_thread_pool.pixelCompleted();
			}
		}
	}
	

}
