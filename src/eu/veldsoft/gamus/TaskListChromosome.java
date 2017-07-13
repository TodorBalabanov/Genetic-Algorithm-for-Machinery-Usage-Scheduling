package eu.veldsoft.gamus;

import java.util.List;

import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

/**
 * Solution vector as list of tasks.
 * 
 * @author Todor Balabanov
 */
class TaskListChromosome extends AbstractListChromosome<Task> {
	/**
	 * Reference to external object of work unit.
	 */
	private WorkUnit work = null;

	/**
	 * 
	 * @param representation
	 * @param copy
	 * @param work
	 */
	public TaskListChromosome(List<Task> representation, boolean copy, WorkUnit work) {
		super(representation, copy);
		this.work = work;
	}

	/**
	 * 
	 * @param representation
	 * @param work
	 * @throws InvalidRepresentationException
	 */
	public TaskListChromosome(Task[] representation, WorkUnit work) throws InvalidRepresentationException {
		super(representation);
		this.work = work;
	}

	/**
	 * 
	 * @param representation
	 * @param work
	 * @throws InvalidRepresentationException
	 */
	public TaskListChromosome(List<Task> representation, WorkUnit work) throws InvalidRepresentationException {
		super(representation);
		this.work = work;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param original
	 *            Original solution.
	 */
	public TaskListChromosome(TaskListChromosome original) {
		super(original.getRepresentation(), true);
		this.work = original.work;
	}

	/**
	 * Work unit reference getter.
	 * 
	 * @return Work unit reference.
	 */
	public WorkUnit getWork() {
		return work;
	}

	/**
	 * Solution getter.
	 * 
	 * @return List of tasks.
	 */
	public List<Task> getSolution() {
		return getRepresentation();
	}

	/**
	 * Calculate solution fitness value.
	 * 
	 * @return Solution fitness value.
	 */
	@Override
	public double fitness() {
		// TODO Supply time limit by better way.
		int counters[] = work.simulate(10000);

		return 0.001 / (1 + counters[0]) + 0.1 / (1 + counters[1]) + 0.01 / (1 + counters[2])
				+ 0.01 / (1 + counters[3]);
	}

	@Override
	protected void checkValidity(List<Task> solution) throws InvalidRepresentationException {
		if (solution.size() == 0) {
			throw new InvalidRepresentationException(LocalizedFormats.DIMENSION, solution.size());
		}
	}

	@Override
	public AbstractListChromosome<Task> newFixedLengthChromosome(List<Task> solution) {
		return new TaskListChromosome(work.generateRandomValidSolution(), false, work);
	}
}
