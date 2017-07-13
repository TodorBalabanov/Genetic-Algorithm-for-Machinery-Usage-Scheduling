package eu.veldsoft.gamus;

import java.util.Arrays;

/**
 * Application single entry point class.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * Single entry point method.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		DataParser parser = new DataParser(args[0]);
		WorkUnit work = new WorkUnit(parser.parse()[5]);

		work.load();
		work.adjustScheduleTimes(work.generateRandomValidSolution());
		System.out.println(Arrays.toString(work.simulate(100000)));
		System.out.println(work.report());
	}
}
