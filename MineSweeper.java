
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           (Mine Sweeper) 
// Files:           (a list of all source files used by that program)
// Course:          (CS 200, Fall, 2017)
//
// Author:          (Aniya Allen)
// Email:           (aallen27@wisc.edu)
// Lecturer's Name: (Jim Williams)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (Aramide Dada)
// Partner Email:   (adada@wisc.edu)
// Lecturer's Name: (Jim Williams)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X__ Write-up states that pair programming is allowed for this assignment.
//   _X__ We have both read and understand the course Pair Programming Policy.
//   _X__ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         (Marc: He helped us with calling the right method 
//                         and structuring the code of the methods)
// Online Sources:  (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Random;
import java.util.Scanner;

/**
 * (This purpose of this class is to create a game where you can uncover spots
 * from the user generated map)
 *
 * <p>
 * Bugs: (none known)
 *
 * @author (Aniya Allen, Aramide Dada)
 */
public class MineSweeper {

	/**
	 * This is the main method for Mine Sweeper game! This method contains the
	 * within game and play again loops and calls the various supporting
	 * methods.
	 * 
	 * @param args
	 *       
	 */
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		int min = Config.MIN_SIZE;
		int max = Config.MAX_SIZE;
		int userHeight = 0; // Stores height user inputs
		int userWidth = 0; // Stores width user inputs
		int numMines = 0; // Stores number of mines
		boolean continueGame = false; // Used to repeat or exit game loop
		// Stores user input to continue game or not
		char userContinueGame = ' ';
		boolean exitGame = false; // used to repeat or exit mine sweeping loop
		String prompt1 = "What width of map would you like (" + min + " - " + max + "): ";
		String prompt2 = "What height of map would you like (" + min + " - " + max + "): ";

		// Instance of random used in game
		Random randGen = new Random(Config.SEED);

		// Welcome message
		System.out.println("Welcome to Mine Sweeper!");

		// Game loop
		do {
			// Prompt user for map width and height
			userWidth = promptUser(scnr, prompt1, min, max);
			userHeight = promptUser(scnr, prompt2, min, max);

			// Stores mine location
			boolean[][] mines = new boolean[userHeight][userWidth];

			// Stores map dimensions and prints map
			char[][] map = new char[userHeight][userWidth];
			eraseMap(map);

			// Generates number of mines
			numMines = placeMines(mines, randGen);

			// Loop that updates the map after user sweeps location
			do {
				// Prints new line
				System.out.println("");

				// Prints number of mines
				System.out.println("Mines: " + numMines);

				// Prints map
				printMap(map);

				// Prompts user for location to sweep
				int row = promptUser(scnr, "row: ", 1, userHeight);
				int column = promptUser(scnr, "column: ", 1, userWidth);

				// If player hits a mine, they lose. Otherwise, they continue
				// the game until they uncover all safe locations
				if (mines[row - 1][column - 1]) {
					map[row - 1][column - 1] = Config.SWEPT_MINE;
					showMines(map, mines);
					printMap(map);
					System.out.println("Sorry, you lost.");
					exitGame = true;
				} else {
					int numMinesAtLocation = sweepLocation(map, mines, row - 1, column - 1);
					if (numMinesAtLocation == 0) {
						sweepAllNeighbors(map, mines, row - 1, column - 1);
					}
					if ((allSafeLocationsSwept(map, mines))) {
						// map[row - 1][column - 1] =
						// Character.forDigit(sweepLocation(map, mines, row - 1,
						// column - 1),
						// 10);
						showMines(map, mines);
						printMap(map);
						System.out.println("You Win!");
						exitGame = true;
						break;
					}
					exitGame = false;
				}
			} while (!exitGame);

			// Ask user if they would like to continue playing game
			System.out.print("Would you like to play again (y/n)? ");
			userContinueGame = scnr.next().charAt(0);
			if (userContinueGame == 'y' || userContinueGame == 'Y') {
				continueGame = true;
			} else {
				System.out.println("Thank you for playing Mine Sweeper!");
				continueGame = false;
			}
		} while (continueGame);

	}

	/**
	 * This method prompts the user for a number, verifies that it is between
	 * min and max, inclusive, before returning the number.
	 * 
	 * If the number entered is not between min and max then the user is shown
	 * an error message and given another opportunity to enter a number. If min
	 * is 1 and max is 5 the error message is: Expected a number from 1 to 5.
	 * 
	 * If the user enters characters, words or anything other than a valid int
	 * then the user is shown the same message. The entering of characters other
	 * than a valid int is detected using Scanner's methods (hasNextInt) and
	 * does not use exception handling.
	 * 
	 * Do not use constants in this method, only use the min and max passed in
	 * parameters for all comparisons and messages. Do not create an instance of
	 * Scanner in this method, pass the reference to the Scanner in main, to
	 * this method. The entire prompt should be passed in and printed out.
	 *
	 * @param in
	 *            The reference to the instance of Scanner created in main.
	 * @param prompt
	 *            The text prompt that is shown once to the user.
	 * @param min
	 *            The minimum value that the user must enter.
	 * @param max
	 *            The maximum value that the user must enter.
	 * @return The integer that the user entered that is between min and max,
	 *         inclusive.
	 */
	public static int promptUser(Scanner in, String prompt, int min, int max) {
		int userInput = 0; // Stores the number user inputs
		System.out.print(prompt); // Prints prompt
		boolean isNumber = false; // Condition for do - while loop

		// Checks if userInput is an integer between min and max
		do {
			if (in.hasNextInt()) {
				userInput = in.nextInt();
				if (userInput < min || userInput > max) {
					System.out.println("Expected a number from " + min + " to " + max + ".");
					isNumber = false;
					in.nextLine();
				} else {
					isNumber = true;
					in.nextLine();
				}
			} else {
				System.out.println("Expected a number from " + min + " to " + max + ".");
				isNumber = false;
				in.nextLine();
			}
		} while (!isNumber);

		return userInput;
	}

	/**
	 * This initializes the map char array passed in such that all elements have
	 * the Config.UNSWEPT character. Within this method only use the actual size
	 * of the array. Don't assume the size of the array. This method does not
	 * print out anything. This method does not return anything.
	 * 
	 * @param map
	 *            An allocated array. After this method call all elements in the
	 *            array have the same character, Config.UNSWEPT.
	 */
	public static void eraseMap(char[][] map) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col] = Config.UNSWEPT;
			}
		}
		return;
	}

	/**
	 * This prints out a version of the map without the row and column numbers.
	 * A map with width 4 and height 6 would look like the following: . . . . .
	 * . . . . . . . . . . . . . . . . . . . For each location in the map a
	 * space is printed followed by the character in the map location.
	 * 
	 * @param map
	 *            The map to print out.
	 */
	public static void simplePrintMap(char[][] map) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				System.out.print(" " + map[row][col]);
			}
			System.out.println("");
		}
		return;
	}

	/**
	 * This prints out the map. This shows numbers of the columns and rows on
	 * the top and left side, respectively. map[0][0] is row 1, column 1 when
	 * shown to the user. The first column, last column and every multiple of 5
	 * are shown.
	 * 
	 * To print out a 2 digit number with a leading space if the number is less
	 * than 10, you may use: System.out.printf("%2d", 1);
	 * 
	 * @param map
	 *            The map to print out.
	 */
	public static void printMap(char[][] map) {
		System.out.print("  ");
		for (int row = 1; row <= map[1].length; row++) {
			if (row == 1 || row == map[1].length || row % 5 == 0) {
				System.out.printf("%2d", row);
			} else {
				System.out.print("--");
			}
		}
		System.out.println("");

		for (int row = 0; row < map.length; row++) {
			
			
			int c = row;
			if (c == 0) {
				System.out.printf("%2d", c + 1);
			} else if ((c + 1) % 5 == 0) {
				System.out.printf("%2d", c+1);
			} else if (c == map.length - 1) {
				System.out.printf("%2d", c + 1);
			} else {
				System.out.print(" |");
				}
			// row prefix

			for (int col = 0; col < map[row].length; col++) {
				System.out.print(" " + map[row][col]);
			}
			System.out.println("");
		}

		return;
	}

	/**
	 * This method initializes the boolean mines array passed in. A true value
	 * for an element in the mines array means that location has a mine, false
	 * means the location does not have a mine. The MINE_PROBABILITY is used to
	 * determine whether a particular location has a mine. The randGen parameter
	 * contains the reference to the instance of Random created in the main
	 * method.
	 * 
	 * Access the elements in the mines array with row then column (e.g.,
	 * mines[row][col]).
	 * 
	 * Access the elements in the array solely using the actual size of the
	 * mines array passed in, do not use constants.
	 * 
	 * A MINE_PROBABILITY of 0.3 indicates that a particular location has a 30%
	 * chance of having a mine. For each location the result of
	 * randGen.nextFloat() < Config.MINE_PROBABILITY determines whether that
	 * location has a mine.
	 * 
	 * This method does not print out anything.
	 * 
	 * @param mines
	 *            The array of boolean that tracks the locations of the mines.
	 * @param randGen
	 *            The reference to the instance of the Random number generator
	 *            created in the main method.
	 * @return The number of mines in the mines array.
	 */
	public static int placeMines(boolean[][] mines, Random randGen) {
		int numMines = 0; // used to count the number of mines there are
		for (int row = 0; row < mines.length; row++) {
			for (int col = 0; col < mines[row].length; col++) {
				mines[row][col] = randGen.nextFloat() < Config.MINE_PROBABILITY;
				if (mines[row][col]) {
					numMines++;
				} else {
				}
			}
		}
		return numMines;
	}

	/**
	 * This method returns the number of mines in the 8 neighboring locations.
	 * For locations along an edge of the array, neighboring locations outside
	 * of the mines array do not contain mines. This method does not print out
	 * anything.
	 * 
	 * If the row or col arguments are outside the mines array, then return -1.
	 * This method (or any part of this program) should not use exception
	 * handling.
	 * 
	 * @param mines
	 *            The array showing where the mines are located.
	 * @param row
	 *            The row, 0-based, of a location.
	 * @param col
	 *            The col, 0-based, of a location.
	 * @return The number of mines in the 8 surrounding locations or -1 if row
	 *         or col are invalid.
	 */
	public static int numNearbyMines(boolean[][] mines, int row, int col) {
		int nearbyMines = 0; // Counts the number of nearby mines

		if (row < 0 || row > mines.length - 1 || col < 0 || col > mines[row].length - 1) {
			return -1;
		}

		if ((row + 1 < mines.length) && (col - 1 >= 0) && (mines[row + 1][col - 1])) {
			nearbyMines++;
		}
		if ((row + 1 < mines.length) && (mines[row + 1][col])) {
			nearbyMines++;
		}
		if ((row + 1 < mines.length) && (col + 1 < mines[row].length) && (mines[row + 1][col + 1])) {
			nearbyMines++;
		}
		if ((col + 1 < mines[row].length) && (mines[row][col + 1])) {
			nearbyMines++;
		}
		if ((row - 1 >= 0) && (col + 1 < mines[row].length) && (mines[row - 1][col + 1])) {
			nearbyMines++;
		}
		if ((row - 1 >= 0) && (mines[row - 1][col])) {
			nearbyMines++;
		}
		if ((row - 1 >= 0) && (col - 1 >= 0) && (mines[row - 1][col - 1])) {
			nearbyMines++;
		}
		if ((col - 1 >= 0) && (mines[row][col - 1])) {
			nearbyMines++;
		}

		return nearbyMines;
	}

	/**
	 * This updates the map with each unswept mine shown with the
	 * Config.HIDDEN_MINE character. Swept mines will already be mapped and so
	 * should not be changed. This method does not print out anything.
	 * 
	 * @param map
	 *            An array containing the map. On return the map shows unswept
	 *            mines.
	 * @param mines
	 *            An array indicating which locations have mines. No changes are
	 *            made to the mines array.
	 */
	public static void showMines(char[][] map, boolean[][] mines) {
		for (int row = 0; row < mines.length; row++) {
			for (int col = 0; col < mines[row].length; col++) {
				if (mines[row][col] && map[row][col] == Config.UNSWEPT) {
					map[row][col] = Config.HIDDEN_MINE;
				} else {
				}
			}
		}
		return;
	}

	/**
	 * Returns whether all the safe (non-mine) locations have been swept. In
	 * other words, whether all unswept locations have mines. This method does
	 * not print out anything.
	 * 
	 * @param map
	 *            The map showing touched locations that is unchanged by this
	 *            method.
	 * @param mines
	 *            The mines array that is unchanged by this method.
	 * @return whether all non-mine locations are swept.
	 */
	public static boolean allSafeLocationsSwept(char[][] map, boolean[][] mines) {
		for (int row = 0; row < mines.length; row++) {
			for (int col = 0; col < mines[row].length; col++) {
				if (!mines[row][col] && map[row][col] == Config.UNSWEPT) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method sweeps the specified row and col. - If the row and col
	 * specify a location outside the map array then return -3 without changing
	 * the map. - If the location has already been swept then return -2 without
	 * changing the map. - If there is a mine in the location then the map for
	 * the corresponding location is updated with Config.SWEPT_MINE and return
	 * -1. - If there is not a mine then the number of nearby mines is
	 * determined by calling the numNearbyMines method. - If there are 1 to 8
	 * nearby mines then the map is updated with the characters '1'..'8'
	 * indicating the number of nearby mines. - If the location has 0 nearby
	 * mines then the map is updated with the Config.NO_NEARBY_MINE character. -
	 * Return the number of nearbyMines.
	 * 
	 * @param map
	 *            The map showing swept locations.
	 * @param mines
	 *            The array showing where the mines are located.
	 * @param row
	 *            The row, 0-based, of a location.
	 * @param col
	 *            The col, 0-based, of a location.
	 * @return the number of nearby mines, -1 if the location is a mine, -2 if
	 *         the location has already been swept, -3 if the location is off
	 *         the map.
	 */
	public static int sweepLocation(char[][] map, boolean[][] mines, int row, int col) {
		int nearbyMines = 0;
		if (row < 0 || row > map.length - 1 || col < 0 || col > map[row].length - 1) {
			return -3;
		}
		if (map[row][col] != Config.UNSWEPT) {
			return -2;
		}
		if (mines[row][col]) {
			map[row][col] = Config.SWEPT_MINE;
			return -1;
		}
		if (!mines[row][col]) {
			nearbyMines = numNearbyMines(mines, row, col);
			if (nearbyMines == 0) {
				map[row][col] = Config.NO_NEARBY_MINE;
			} else {
				map[row][col] = (char) (nearbyMines + '0');
			}
		}
		return nearbyMines;
	}

	/**
	 * This method iterates through all 8 neighboring locations and calls
	 * sweepLocation for each. It does not call sweepLocation for its own
	 * location, just the neighboring locations.
	 * 
	 * @param map
	 *            The map showing touched locations.
	 * @param mines
	 *            The array showing where the mines are located.
	 * @param row
	 *            The row, 0-based, of a location.
	 * @param col
	 *            The col, 0-based, of a location.
	 */
	public static void sweepAllNeighbors(char[][] map, boolean[][] mines, int row, int col) {
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (r < 0 || r > map.length - 1 || c < 0 || c > map[r].length - 1) {
					continue;
				}
				if (r == row && c == col)
					continue;
				else {
					sweepLocation(map, mines, r, c);
				}
			}
		}
		return;
	}
}
