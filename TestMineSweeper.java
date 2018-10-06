/**
 * This file contains testing methods for the MineSweeper project.
 * These methods are intended to serve several objectives:
 * 1) provide an example of a way to incrementally test your code
 * 2) provide example method calls for the MineSweeper methods
 * 3) provide examples of creating, accessing and modifying arrays
 *    
 * Toward these objectives, the expectation is that part of the 
 * grade for the MineSweeper project is to write some tests and
 * write header comments summarizing the tests that have been written. 
 * Specific places are noted with FIXME but add any other comments 
 * you feel would be useful.
 * 
 * Some of the provided comments within this file explain
 * Java code as they are intended to help you learn Java.  However,
 * your comments and comments in professional code, should
 * summarize the purpose of the code, not explain the meaning
 * of the specific Java constructs.
 *    
 */

import java.util.Random;
import java.util.Scanner;

/**
 * This class contains a few methods for testing methods in the MineSweeper
 * class as they are developed. These methods are all private as they are only
 * intended for use within this class.
 * 
 * @author Jim Williams
 * @author Aniya Allen add your name here when you add tests and comment the
 *         tests
 *
 */
public class TestMineSweeper {

	/**
	 * This is the main method that runs the various tests. Uncomment the tests when
	 * you are ready for them to run.
	 * 
	 * @param args
	 *            (unused)
	 */
	public static void main(String[] args) {

		// Milestone 1
		// testing the main loop, promptUser and simplePrintMap, since they have
		// a variety of output, is probably easiest using a tool such as diffchecker.com
		// and comparing to the examples provided.
		testEraseMap();

		// Milestone 2
		testPlaceMines();
		testNumNearbyMines();
		testShowMines();
		testAllSafeLocationsSwept();

		// Milestone 3
		testSweepLocation();
		testSweepAllNeighbors();
		// testing printMap, due to printed output is probably easiest using a
		// tool such as diffchecker.com and comparing to the examples provided.
	}

	/**
	 * This is intended to run some tests on the eraseMap method. 1. Tests when the
	 * height and width the user inputs is 5. 2. Tests when the height is 30 and
	 * width is 5.
	 */
	private static void testEraseMap() {
		// Review the eraseMap method header carefully and write
		// tests to check whether the method is working correctly.
		// Test 1
		char[][] map = new char[5][5];
		MineSweeper.eraseMap(map);
		for (int height = 0; height < map.length; height++) {
			for (int width = 0; width < map[height].length; width++) {
				if (map[height][width] != Config.UNSWEPT) {
					System.out.println("testEraseMap: failed");
					return;
				}
			}
		}

		// Test 2
		map = new char[30][5];
		MineSweeper.eraseMap(map);
		for (int height = 0; height < map.length; height++) {
			for (int width = 0; width < map[height].length; width++) {
				if (map[height][width] != Config.UNSWEPT) {
					System.out.println("testEraseMap: failed");
					return;
				}
			}
		}
		System.out.println("testEraseMap: passed");
	}

	/*
	 * Calculate the number of elements in array1 with different values from those
	 * in array2
	 */
	private static int getDiffCells(boolean[][] array1, boolean[][] array2) {
		int counter = 0;
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array1[i].length; j++) {
				if (array1[i][j] != array2[i][j])
					counter++;
			}
		}
		return counter;
	}

	/**
	 * This runs some tests on the placeMines method. 1. FIXME describe each test in
	 * your own words. 2.
	 */
	private static void testPlaceMines() {
		boolean error = false;

		// These expected values were generated with a Random instance set with
		// seed of 123 and MINE_PROBABILITY is 0.2.
		boolean[][] expectedMap = new boolean[][] { { false, false, false, false, false },
				{ false, false, false, false, false }, { false, false, false, true, true },
				{ false, false, false, false, false }, { false, false, true, false, false } };
		int expectedNumMines = 3;

		Random studentRandGen = new Random(123);
		boolean[][] studentMap = new boolean[5][5];
		int studentNumMines = MineSweeper.placeMines(studentMap, studentRandGen);

		if (studentNumMines != expectedNumMines) {
			error = true;
			System.out.println(
					"testPlaceMines 1: expectedNumMines=" + expectedNumMines + " studentNumMines=" + studentNumMines);
		}
		int diffCells = getDiffCells(expectedMap, studentMap);
		if (diffCells != 0) {
			error = true;
			System.out.println("testPlaceMines 2: mine map differs.");
		}

		// Can you think of other tests that would make sure your method works
		// correctly?
		// if so, add them.

		// try different numbers

		if (error) {
			System.out.println("testPlaceMines: failed");
		} else {
			System.out.println("testPlaceMines: passed");
		}

	}

	/**
	 * This runs some tests on the numNearbyMines method. 1. FIXME describe each
	 * test in your own words. 2.
	 */
	private static void testNumNearbyMines() {
		boolean error = false;

		boolean[][] mines = new boolean[][] { { false, false, true, false, false },
				{ false, false, false, false, false }, { false, true, false, true, true },
				{ false, false, false, false, false }, { false, false, true, false, false } };
		int numNearbyMines = MineSweeper.numNearbyMines(mines, 1, 1);

		if (numNearbyMines != 2) {
			error = true;
			System.out.println("testNumNearbyMines 1: numNearbyMines=" + numNearbyMines + " expected mines=2");
		}

		numNearbyMines = MineSweeper.numNearbyMines(mines, 2, 1);

		if (numNearbyMines != 0) {
			error = true;
			System.out.println("testNumNearbyMines 2: numNearbyMines=" + numNearbyMines + " expected mines=0");
		}

		// Can you think of other tests that would make sure your method works
		// correctly?
		// if so, add them.
		// try different numbers

		if (error) {
			System.out.println("testNumNearbyMines: failed");
		} else {
			System.out.println("testNumNearbyMines: passed");
		}
	}

	/**
	 * This runs some tests on the showMines method. 1. FIXME describe each test in
	 * your own words. 2.
	 */
	private static void testShowMines() {
		boolean error = false;

		boolean[][] mines = new boolean[][] { { false, false, true, false, false },
				{ false, false, false, false, false }, { false, true, false, false, false },
				{ false, false, false, false, false }, { false, false, true, false, false } };

		char[][] map = new char[mines.length][mines[0].length];
		map[0][2] = Config.UNSWEPT;
		map[2][1] = Config.UNSWEPT;
		map[4][2] = Config.UNSWEPT;

		MineSweeper.showMines(map, mines);
		if (!(map[0][2] == Config.HIDDEN_MINE && map[2][1] == Config.HIDDEN_MINE && map[4][2] == Config.HIDDEN_MINE)) {
			error = true;
			System.out.println("testShowMines 1: a mine not mapped");
		}
		if (map[0][0] == Config.HIDDEN_MINE || map[0][4] == Config.HIDDEN_MINE || map[4][4] == Config.HIDDEN_MINE) {
			error = true;
			System.out.println("testShowMines 2: unexpected showing of mine.");
		}

		// Can you think of other tests that would make sure your method works
		// correctly?
		// if so, add them.

		if (error) {
			System.out.println("testShowMines: failed");
		} else {
			System.out.println("testShowMines: passed");
		}
	}

	/**
	 * This is intended to run some tests on the allSafeLocationsSwept method. 1.
	 * This tests if all the safe locations have been swept. In this test not all
	 * safe locations have been swept, so the allSafeLocationsSwept method should
	 * return false. 2.
	 */
	private static void testAllSafeLocationsSwept() {
		// Review the allSafeLocationsSwept method header carefully and write
		// tests to check whether the method is working correctly.
		boolean error = false;

		boolean[][] mines = new boolean[][] { { false, false, true, false, false },
				{ false, false, false, false, false }, { false, true, false, false, false },
				{ false, false, false, false, false }, { false, false, true, false, false } };

		char[][] map = new char[mines.length][mines[0].length];
		map[0][1] = Config.UNSWEPT;
		map[2][0] = Config.UNSWEPT;
		map[4][1] = Config.UNSWEPT;

		error = MineSweeper.allSafeLocationsSwept(map, mines);
		if (error) {
			System.out.println("testAllSafeLocationsSwept: failed");
		} else {
			System.out.println("testAllSafeLocationsSwept: passed");
		}
	}

	/**
	 * This is intended to run some tests on the sweepLocation method. 1. This tests
	 * if the method sweepLocation returns the right number of nearby mines. For
	 * this test to be passed, MineSweeper.sweepLocation() should return 1 because
	 * there are one nearby mine in the map location (3,3). 2.
	 */
	private static void testSweepLocation() {
		// Review the sweepLocation method header carefully and write
		// tests to check whether the method is working correctly.
		int row = 3;
		int col = 3;
		int numMinesNearby = 0;// Stores the number of nearby mines
		boolean[][] mines = new boolean[][] { { false, false, true, false, false },
				{ false, false, false, false, false }, { false, true, false, false, false },
				{ false, false, false, false, false }, { false, false, true, false, false } };

		char[][] map = new char[mines.length][mines[0].length];
		map[row - 1][col - 1] = Config.UNSWEPT;
		numMinesNearby = MineSweeper.sweepLocation(map, mines, row - 1, col - 1);

		if (numMinesNearby == 1) {
			System.out.println("testSweepLocation: passed");
		} else {
			System.out.println("testSweepLocation: failed");
		}

	}

	/**
	 * This is intended to run some tests on the sweepAllNeighbors method. 1. FIXME
	 * describe each test in your own words. 2.
	 */
	private static void testSweepAllNeighbors() {
		// Review the sweepAllNeighbors method header carefully and write
		// tests to check whether the method is working correctly.
		int row = 3;
		int col = 3;
		int numMinesNearby = 1;// Stores the number of nearby mines
		boolean[][] mines = new boolean[][] { { false, false, true, false, false },
				{ false, false, false, false, false }, { false, true, false, false, false },
				{ false, false, false, false, false }, { false, false, true, false, false } };

		char[][] map = new char[5][5];
		map[row - 1][col - 1] = Config.UNSWEPT;
		MineSweeper.sweepAllNeighbors(map, mines, row, col);

		if (numMinesNearby == 1) {
			System.out.println("testSweepAllNeighbors: passed");
		} else {
			System.out.println("testSweepLocation: failed");
		}

	}
}
