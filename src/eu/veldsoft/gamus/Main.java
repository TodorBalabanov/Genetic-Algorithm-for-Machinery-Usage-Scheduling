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
	 * Print simulation execution command.
	 *
	 * @param args
	 *            Command line arguments list.
	 */
	private static void printExecuteCommand(String[] args) {
		System.out.println("Execute command:");
		System.out.print("java Main ");
		for (int i = 0; i < args.length; i++) {
			System.out.print(args[i] + " ");
		}
		System.out.println();
	}

	/**
	 * Print help information.
	 */
	private static void printHelp() {
		System.out.println("*******************************************************************************");
		System.out.println("* Genetic Algorithm for Machinery Usage Scheduling version 0.1.0              *");
		System.out.println("* Copyrights (C) 2017 Velbazhd Software LLC                                   *");
		System.out.println("*                                                                             *");
		System.out.println("* developed by Todor Balabanov ( todor.balabanov@gmail.com )                  *");
		System.out.println("* Sofia, Bulgaria                                                             *");
		System.out.println("*                                                                             *");
		System.out.println("* This program is free software: you can redistribute it and/or modify        *");
		System.out.println("* it under the terms of the GNU General Public License as published by        *");
		System.out.println("* the Free Software Foundation, either version 3 of the License, or           *");
		System.out.println("* (at your option) any later version.                                         *");
		System.out.println("*                                                                             *");
		System.out.println("* This program is distributed in the hope that it will be useful,             *");
		System.out.println("* but WITHOUT ANY WARRANTY; without even the implied warranty of              *");
		System.out.println("* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               *");
		System.out.println("* GNU General Public License for more details.                                *");
		System.out.println("*                                                                             *");
		System.out.println("* You should have received a copy of the GNU General Public License           *");
		System.out.println("* along with this program. If not, see <http://www.gnu.org/licenses/>.        *");
		System.out.println("*                                                                             *");
		System.out.println("*******************************************************************************");
		System.out.println("*                                                                             *");
		System.out.println("* -help           Help screen.                                                *");
		System.out.println("*                                                                             *");
		System.out.println("* -fn<string>     Input file path and name.                                   *");
		System.out.println("* -ds<integer>    Data sheet index (start from 0).                            *");
		System.out.println("*                                                                             *");
		System.out.println("* -ps<integer>    Population size (should be more than 3).                    *");
		System.out.println("* -cr<deciaml>    Crossover rate (between 0.0 and 1.0).                       *");
		System.out.println("* -mr<deciaml>    Mutation rate (between 0.0 and 1.0).                        *");
		System.out.println("* -tr<integer>    Tournament arity (should be more than 2).                   *");
		System.out.println("* -er<deciaml>    Elitism rate (between 0.0 and 1.0).                         *");
		System.out.println("* -lm<integer>    Low boudary for random start time mutation.                 *");
		System.out.println("* -hm<integer>    High boudary for random start time mutation.                *");
		System.out.println("*                                                                             *");
		System.out.println("* -ot<integer>    Optimization time in seconds.                               *");
		System.out.println("* -st<integer>    Simulation time in discrete time steps.                     *");
		System.out.println("*                                                                             *");
		System.out.println("*******************************************************************************");
		System.out.println("*                                                                             *");
		System.out.println("* Example:        java Main -fn./dat/data05072017.xls -ds5                    *");
		System.out.println("*                                                                             *");
		System.out.println("*******************************************************************************");
	}

	/**
	 * Single entry point method.
	 * 
	 * Run with the following commands:
	 * 
	 * java Main -fn./dat/data05072017.xls -ds5
	 * 
	 * java -cp .:../lib/commons-math3-3.6.1.jar:../lib/poi-3.16.jar
	 * eu.veldsoft.gamus.Main -fn../dat/data05072017.xls -ds5
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		printExecuteCommand(args);
		System.out.println();

		int populationSize = Util.DEFAULT_POPULATION_SIZE;
		double crossoverRate = Util.DEFAULT_CROSSOVER_RATE;
		double mutationRate = Util.DEFAULT_MUTATION_RATE;
		int tournamentArity = Util.DEFAULT_TOURNAMENT_ARITY;
		double elitismRate = Util.DEFAULT_ELITISM_RATE;
		int optimizationTimeout = Util.DEFAULT_OPTIMIZATION_TIMEOUT_SECONDS;
		int simulationTimeout = Util.DEFAULT_SIMULATION_TIMEOUT;
		String fileName = "";
		int dataSheet = -1;
		int lowMutationBoundary = -1;
		int highMutationBoundary = +1;

		/*
		 * Parse command line arguments.
		 */
		for (int a = 0; a < args.length; a++) {
			if (args.length > 0 && args[a].contains("-ps")) {
				String parameter = args[a].substring(3);

				try {
					populationSize = Integer.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-cr")) {
				String parameter = args[a].substring(3);

				try {
					crossoverRate = Double.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-mr")) {
				String parameter = args[a].substring(3);

				try {
					mutationRate = Double.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-ta")) {
				String parameter = args[a].substring(3);

				try {
					tournamentArity = Integer.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-er")) {
				String parameter = args[a].substring(3);

				try {
					elitismRate = Double.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-ot")) {
				String parameter = args[a].substring(3);

				try {
					optimizationTimeout = Integer.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-st")) {
				String parameter = args[a].substring(3);

				try {
					simulationTimeout = Integer.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-fn")) {
				String parameter = args[a].substring(3);

				try {
					fileName = parameter;
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-ds")) {
				String parameter = args[a].substring(3);

				try {
					dataSheet = Integer.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-lm")) {
				String parameter = args[a].substring(3);

				try {
					lowMutationBoundary = Integer.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-hm")) {
				String parameter = args[a].substring(3);

				try {
					highMutationBoundary = Integer.valueOf(parameter);
				} catch (Exception exception) {
				}
			}

			if (args.length > 0 && args[a].contains("-help")) {
				printHelp();
				System.out.println();
				System.exit(0);
			}
		}

		/*
		 * Input data are mandatory.
		 */
		if (fileName.equals("") == true) {
			System.out.println("Input data file is missing!");
			System.exit(0);
		}

		/*
		 * Sheet index is mandatory.
		 */
		if (dataSheet == -1) {
			System.out.println("Sheet index is not selected!");
			System.exit(0);
		}

		/*
		 * Load data.
		 */
		DataParser parser = new DataParser(fileName);
		WorkUnit work = new WorkUnit(parser.parse()[dataSheet], simulationTimeout);
		work.load();

		/*
		 * Generate initial population.
		 */
		List<Chromosome> list = new LinkedList<Chromosome>();
		for (int i = 0; i < populationSize; i++) {
			list.add(new TaskListChromosome(work.generateRandomValidSolution(), work));
		}
		Population initial = new ElitisticListPopulation(list, 2 * list.size(), elitismRate);

		/*
		 * Initialize genetic algorithm.
		 */
		GeneticAlgorithm algorithm = new GeneticAlgorithm(new UniformCrossover<TaskListChromosome>(0.5), crossoverRate,
				new RandomTaskMutation(lowMutationBoundary, highMutationBoundary), mutationRate,
				new TournamentSelection(tournamentArity));

		/*
		 * Run optimization.
		 */
		Population optimized = algorithm.evolve(initial, new FixedElapsedTime(optimizationTimeout));

		/*
		 * Obtain result.
		 */
		List<Task> solution = ((TaskListChromosome) optimized.getFittestChromosome()).getSolution();

		/*
		 * Check result.
		 */
		work.adjustScheduleTimes(solution);
		System.out.println(Arrays.deepToString(work.simulate(simulationTimeout)));
		System.out.println(work.report());
	}

}
