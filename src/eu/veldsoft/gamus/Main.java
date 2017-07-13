package eu.veldsoft.gamus;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.FixedElapsedTime;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.apache.commons.math3.genetics.UniformCrossover;

/**
 * Application single entry point class.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * Genetic algorithm population size.
	 */
	static final int POPULATION_SIZE = 37;

	/**
	 * Crossover probability.
	 */
	static final double CROSSOVER_RATE = 0.9;

	/**
	 * Mutation probability.
	 */
	static final double MUTATION_RATE = 0.03;

	/**
	 * Tournament arity.
	 */
	static final int TOURNAMENT_ARITY = 2;

	/**
	 * Rate of keeping the best found solutions.
	 */
	static final double ELITISM_RATE = 0.1;

	/**
	 * Optimization time in seconds.
	 */
	static final long OPTIMIZATION_TIMEOUT_SECONDS = 30;

	/**
	 * Single entry point method.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		/*
		 * Load data.
		 */
		DataParser parser = new DataParser(args[0]);
		WorkUnit work = new WorkUnit(parser.parse()[Integer.valueOf(args[1])]);
		work.load();

		/*
		 * Generate initial population.
		 */
		List<Chromosome> list = new LinkedList<Chromosome>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			list.add(new TaskListChromosome(work.generateRandomValidSolution(), work));
		}
		Population initial = new ElitisticListPopulation(list, 2 * list.size(), ELITISM_RATE);

		/*
		 * Initialize genetic algorithm.
		 */
		GeneticAlgorithm algorithm = new GeneticAlgorithm(new UniformCrossover<TaskListChromosome>(0.5), CROSSOVER_RATE,
				new RandomTaskMutation(), MUTATION_RATE, new TournamentSelection(TOURNAMENT_ARITY));

		/*
		 * Run optimization.
		 */
		Population optimized = algorithm.evolve(initial, new FixedElapsedTime(OPTIMIZATION_TIMEOUT_SECONDS));

		/*
		 * Obtain result.
		 */
		List<Task> solution = ((TaskListChromosome) optimized.getFittestChromosome()).getSolution();

		/*
		 * Check result.
		 */
		work.adjustScheduleTimes(solution);
		System.out.println(Arrays.toString(work.simulate(100000)));
		System.out.println(work.report());
	}
}
