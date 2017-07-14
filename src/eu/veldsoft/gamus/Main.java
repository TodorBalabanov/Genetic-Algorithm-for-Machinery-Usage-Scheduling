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
	 * Single entry point method.
	 * 
	 * Run with the following command:
	 * 
	 * java Main ./dat/data05072017.xls 5 10000 60
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		/*
		 * Load data.
		 */
		DataParser parser = new DataParser(args[0]);
		WorkUnit work = new WorkUnit(parser.parse()[Integer.valueOf(args[1])], Integer.valueOf(args[2]));
		work.load();

		/*
		 * Generate initial population.
		 */
		List<Chromosome> list = new LinkedList<Chromosome>();
		for (int i = 0; i < Util.DEFAULT_POPULATION_SIZE; i++) {
			list.add(new TaskListChromosome(work.generateRandomValidSolution(), work));
		}
		Population initial = new ElitisticListPopulation(list, 2 * list.size(), Util.DEFAULT_ELITISM_RATE);

		/*
		 * Initialize genetic algorithm.
		 */
		GeneticAlgorithm algorithm = new GeneticAlgorithm(new UniformCrossover<TaskListChromosome>(0.5),
				Util.DEFAULT_CROSSOVER_RATE, new RandomTaskMutation(-10, +10), Util.DEFAULT_MUTATION_RATE,
				new TournamentSelection(Util.DEFAULT_TOURNAMENT_ARITY));

		/*
		 * Run optimization.
		 */
		Population optimized = algorithm.evolve(initial, new FixedElapsedTime(Integer.valueOf(args[3])));

		/*
		 * Obtain result.
		 */
		List<Task> solution = ((TaskListChromosome) optimized.getFittestChromosome()).getSolution();

		/*
		 * Check result.
		 */
		work.adjustScheduleTimes(solution);
		System.out.println(Arrays.toString(work.simulate(Integer.valueOf(args[2]))));
		System.out.println(work.report());
	}

}
