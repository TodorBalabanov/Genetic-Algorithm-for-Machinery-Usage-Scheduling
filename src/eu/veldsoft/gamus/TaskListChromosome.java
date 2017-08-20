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
	 * Constructor.
	 * 
	 * @param representation
	 *            Solution vector.
	 * @param copy
	 *            Deep copy flag.
	 * @param work
	 *            Work unit reference.
	 */
	public TaskListChromosome(List<Task> representation, boolean copy, WorkUnit work) {
		super(representation, copy);
		this.work = work;
	}

	/**
	 * Constructor.
	 * 
	 * @param representation
	 *            Solution vector.
	 * @param work
	 *            Work unit reference.
	 * 
	 * @throws InvalidRepresentationException
	 *             Invalid solution exception.
	 */
	public TaskListChromosome(Task[] representation, WorkUnit work) throws InvalidRepresentationException {
		super(representation);
		this.work = work;
	}

	/**
	 * Constructor.
	 * 
	 * @param representation
	 *            Solution vector.
	 * @param work
	 *            Work unit reference.
	 * 
	 * @throws InvalidRepresentationException
	 *             Invalid solution exception.
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
		work.reset();
		work.adjustScheduleTimes(getSolution());
		double counters[][] = work.simulate();

		double value = 0;
		for (int i = 0; i < counters[0].length && i < counters[1].length; i++) {
			value += (1 + counters[0][i]) * counters[1][i];
		}

		return value;
	}

	/**
	 * Solution validity check.
	 * 
	 * @param solution
	 *            List of tasks.
	 * 
	 * @throws InvalidRepresentationException
	 *             Invalid solution exception.
	 */
	@Override
	protected void checkValidity(List<Task> solution) throws InvalidRepresentationException {
		/*
		 * List of tasks should not be empty.
		 */
		if (solution.size() == 0) {
			throw new InvalidRepresentationException(LocalizedFormats.DIMENSION, solution.size());
		}
	}

	/**
	 * Chromosome generation by usage of solution vector.
	 * 
	 * @param solution
	 *            List of tasks.
	 * 
	 * @return Newly generated chromosome.
	 */
	@Override
	public AbstractListChromosome<Task> newFixedLengthChromosome(List<Task> solution) {
		return new TaskListChromosome(work.generateRandomValidSolution(), false, work);
	}

}
