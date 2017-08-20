package eu.veldsoft.gamus;

import net.sourceforge.jswarm_pso.Particle;

/**
 * Task particle for particle swarm optimization.
 * 
 * @author Todor Balabanov
 */
public class TaskParticle extends Particle {
	/**
	 * It is needed for constructor without parameters problem.
	 */
	private static int dimension = 0;

	/**
	 * Constructor without parameters.
	 */
	public TaskParticle() {
		super(dimension);
	}

	/**
	 * Constructor with particle dimension.
	 * 
	 * @param dimension
	 *            Particle dimension.
	 */
	public TaskParticle(int dimension) {
		super(dimension);
		TaskParticle.dimension = dimension;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param particle
	 *            Original object.
	 */
	public TaskParticle(TaskParticle particle) {
		super(particle);
	}
}
