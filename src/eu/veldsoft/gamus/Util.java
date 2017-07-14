package eu.veldsoft.gamus;

import java.util.Random;

/**
 * Utilities class.
 * 
 * @author Todor Balabanov
 */
class Util {

	/**
	 * Pseudo random number generator.
	 */
	static final Random PRNG = new Random();

	/**
	 * Genetic algorithm population size.
	 */
	static final int DEFAULT_POPULATION_SIZE = 37;

	/**
	 * Crossover probability.
	 */
	static final double DEFAULT_CROSSOVER_RATE = 0.9;

	/**
	 * Mutation probability.
	 */
	static final double DEFAULT_MUTATION_RATE = 0.3;

	/**
	 * Tournament arity.
	 */
	static final int DEFAULT_TOURNAMENT_ARITY = 5;

	/**
	 * Rate of keeping the best found solutions.
	 */
	static final double DEFAULT_ELITISM_RATE = 0.1;

	/**
	 * Optimization time in seconds.
	 */
	static final long DEFAULT_OPTIMIZATION_TIMEOUT_SECONDS = 60;

}
