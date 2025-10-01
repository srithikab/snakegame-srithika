import java.io.*;
import java.util.Scanner;

/**
 * Snake Game - Snake Game is a game that implements Singly Linked Lists,
 * to move a snake around, similar to the 1980's game played. Snake Game
 * is comprised of a head '@' and a body '*'. Every time a target '+' is 
 * eaten by the snake, the snake grows by one, and your score increases by
 * one as well. The snake will die if it hits itself, if it hits the border,
 * if there is 5 spaces left, or if the snake is surronded by itself.
 * There is also a save and restore feature, where you can save your 
 * progress, and restore it when you want it to.
 * 
 * @author Srithika Barakam
 * @since 05/10/2025
 */
public class SnakeGame {

	private Snake snake; // the snake in the game
	private SnakeBoard board; // the game board
	private Coordinate target; // the target for the snake
	private int score; // the score of the game
	private int height;
	private int width;

  /** 
   * A constructor that creates the board, randomly creates the snake, and
   * target. The snake is of size 4 for the start, and you start with a 
   * score of 0.
   */
	public SnakeGame() {
		height = 20;
		width = 25;
		board = new SnakeBoard(height, width);
		int row = (int) (Math.random() * (height	- 6)) + 1;
		int col = (int) (Math.random() * (width-2)) + 1;
		Coordinate cd = new Coordinate(row, col);
		snake = new Snake(cd);
		int r = (int) (Math.random() * height) + 1;
		int c = (int) (Math.random() * width) + 1;
		while ((r == row || r == row + 1 || r == row + 2 || r == row + 3 || r == row + 4)) {
			r = (int) (Math.random() * height) + 1;
		}
		Coordinate tar = new Coordinate(r, c);
		target = tar;
		score = 0;
	}

	/* Main method */
	public static void main(String[] args) {
		SnakeGame sna = new SnakeGame();
		sna.printIntroduction();
		sna.runner();
	}

	/**
	 * This method essentially moves the snake to its previous position
	 * to move it. It goes through a for loop, and stops before it hits 
	 * the head, since the head is being moved after this for loop manually.
	 * When the snake eats the target, the star is added towards the end
	 * of the snake, and your score is increased. 
	 *
	 * @param command 		A string of where the user wants to move
	 * @return void
	 */
	private void moveSnake(String command) {
		// Move body segments
		for (int i = snake.size() - 1; i > 0; i--) {
			int row = snake.get(i - 1).getValue().getX();
			int col = snake.get(i - 1).getValue().getY();
			Coordinate newC = new Coordinate(row, col);
			snake.set(i, newC);
		}

		// Move head based on command
		Coordinate head = snake.get(0).getValue();
		Coordinate newHead = null;

		switch (command) {
			case "w": // North
				newHead = new Coordinate(head.getX() - 1, head.getY());
				break;
			case "s": // South
				newHead = new Coordinate(head.getX() + 1, head.getY());
				break;
			case "d": // East
				newHead = new Coordinate(head.getX(), head.getY() + 1);
				break;
			case "a": // West
				newHead = new Coordinate(head.getX(), head.getY() - 1);
				break;
		}

		if (newHead != null) {
			snake.set(0, newHead);
		}
	}
	
	/**
	 * This is a runner method that runs the program, asks for user input,
	 * and essentially plays the game. There are many different ifs and elses,
	 * for if the user decides to quit, to save, to restore, to help,
	 * or if just to move in general. If the snake is surronded by itself,
	 * only has 5 squares left to move into, or runs into the border,
	 * the while loop is stopped, and the ending message is printed. If
	 * r is inputted by the user, this method reads the savedGame.txt file
	 * for the input on where the snake was in that saved file. A new target
	 * is already created when the head eats the orginal target.
	 * 
	 * @return void
	 */
	public void runner() {
		board.printBoard(snake, target);
		Prompt pm = new Prompt();
		String command = "";
		while (!(command.equals("q")) && (width * height - snake.size() > 5)) {
			command = getCommand(pm);
			//board.printBoard(snake, target);
			if (command.equals("q")) {
				// quit the game
			} else if (command.equals("f")) {
				// save game to file
				PrintWriter pw = FileUtils.openToWrite("savedGame.txt");
				pw.print("Score " + score + "\n");
				pw.print("Target\n" + target.getX() + " " + target.getY() + "\n");
				pw.print("Snake " + snake.size() + "\n");
				for (int i = 0; i < snake.size(); i++) {
					pw.print(snake.get(i).getValue().getX() + " " + snake.get(i).getValue().getY() + "\n");
				}
				pw.close();
				System.out.println("\nGame saved to savedGame.txt\n");
			} else if (command.equals("r")) {
				Scanner scan = FileUtils.openToRead("savedGame.txt");
				scan.next();
				score = scan.nextInt();
				scan.next();
				target = new Coordinate(scan.nextInt(), scan.nextInt());
				scan.next();
				int size = scan.nextInt();
				snake.clear();
				for (int i = 0; i < size; i++) {
					int X = scan.nextInt();
					int Y = scan.nextInt();
					Coordinate coord = new Coordinate(X, Y);
					snake.add(coord);
				}
				board.printBoard(snake, target);

				if (!canMove(snake)) {
					command = "q";
				}
			} else if (command.equals("h")) {
				helpMenu();
			} else {
				Coordinate coordL = new Coordinate(snake.get(snake.size() - 1).getValue().getX(),
							snake.get(snake.size() - 1).getValue().getY());
				moveSnake(command);
				Coordinate head = snake.get(0).getValue();
				if (head.equals(target)) {
					score++;
					Coordinate nC = coordL;
					snake.add(nC);
					int r = (int) (Math.random() * height) + 1;
					int c = (int) (Math.random() * width) + 1;
					for (int i = 0; i < snake.size(); i++) {
						if (r == snake.get(i).getValue().getX() && c == snake.get(i).getValue().getY()) {
							r = (int) (Math.random() * height) + 1;
							c = (int) (Math.random() * width) + 1;
							i = 0;
						}
					}
					Coordinate tar = new Coordinate(r, c);
					target = tar;
				}
				board.printBoard(snake, target);

				// check if snake hit the wall
				int row = head.getX();
				int col = head.getY();
				if (row == 0 || row == board.getHeight() - 1 || col == 0 || col == board.getWidth() - 1)
					command = "q";
				for (int i = 1; i < snake.size(); i++) {
					Coordinate coo = snake.get(i).getValue();
					if (row == coo.getX() && col == coo.getY())
						command = "q";
				}

				if (!canMove(snake)) {
					command = "q";
				}
			}
		}
		System.out.println("\nThanks for playing SnakeGame!!! Your score is: "+score+"\n\n");
	}
	
	/**
	 * This method simply gets the user input from the user on where they
	 * want to go, or what they want to do.
	 * 
	 *
	 * @param pm 	Prompt class used to call getString
	 * @return 		The string that the user entered.
	 */

	private String getCommand(Prompt pm) {
		String command = pm.getString("Score: " + score + " (w - North, s - South, d - East, a - West, h - Help) ");
		if (command.equals("q")) {
			command = pm.getString("Do you really want to quit? (y or n) -> ");
			if (command.equals("n")) {
				command = getCommand(pm);
			} else if (command.equals("y")) {
				command = "q";
			}
		}
		else if (!(command.equals("w") || command.equals("a") || 
				   command.equals("s") || command.equals("d") ||
				   command.equals("h") || command.equals("f") ||
				   command.equals("r") || command.equals("q"))) {
					   command = getCommand(pm);
					  }
						
		return command;
	}
	
	/**
	 * This method checks if the snake can move or not, and returns
	 * true or false depending on where the snake is.
	 * 
	 * @param Snake		Snake instance usesd to check if it can move
	 * @return			boolean wether the snake can move
	 */

	private boolean canMove(Snake snake) {		
		// Check if any direction is available
		return canMoveNorth(snake) || canMoveSouth(snake) || 
			   canMoveEast(snake) || canMoveWest(snake);
	}
	
	/**
	 * This method is used by canMove, and checks to see if this 
	 * specific direciton is avaliable to move. 
	 * 
	 * @param Snake		Snake instance usesd to check if it can move
	 * @return			boolean wether the snake can move
	 */
	private boolean canMoveNorth(Snake snake) {
		Coordinate head = snake.get(0).getValue();
		int newRow = head.getX() - 1;
		int col = head.getY();
		
		// Check wall collision
		if (newRow < 1) return false;
		

		// Check snake body collision
		for (int i = 1; i < snake.size(); i++) {
			Coordinate body = snake.get(i).getValue();
			if (newRow == body.getX() && col == body.getY()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method is used by canMove, and checks to see if this 
	 * specific direciton is avaliable to move. 
	 * 
	 * @param Snake		Snake instance usesd to check if it can move
	 * @return			boolean wether the snake can move
	 */
	private boolean canMoveSouth(Snake snake) {
		Coordinate head = snake.get(0).getValue();
		int newRow = head.getX() + 1;
		int col = head.getY();
		
		// Check wall collision
		if (newRow >= board.getHeight() - 1) return false;
		
		// Check snake body collision
		for (int i = 1; i < snake.size(); i++) {
			Coordinate body = snake.get(i).getValue();
			if (newRow == body.getX() && col == body.getY()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method is used by canMove, and checks to see if this 
	 * specific direciton is avaliable to move. 
	 * 
	 * @param Snake		Snake instance usesd to check if it can move
	 * @return			boolean wether the snake can move
	 */
	private boolean canMoveEast(Snake snake) {
		Coordinate head = snake.get(0).getValue();
		int row = head.getX();
		int newCol = head.getY() + 1;
		
		// Check wall 
		if (newCol >= board.getWidth() - 1) return false;
		
		// Check snake body 
		for (int i = 1; i < snake.size(); i++) {
			Coordinate body = snake.get(i).getValue();
			if (row == body.getX() && newCol == body.getY()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method is used by canMove, and checks to see if this 
	 * specific direciton is avaliable to move. 
	 * 
	 * @param Snake		Snake instance usesd to check if it can move
	 * @return			boolean wether the snake can move
	 */
	private boolean canMoveWest(Snake snake) {
		Coordinate head = snake.get(0).getValue();
		int row = head.getX();
		int newCol = head.getY() - 1;
		
		// Check wall 
		if (newCol <= 0) return false;
		
		// Check snake body 
		for (int i = 1; i < snake.size(); i++) {
			Coordinate body = snake.get(i).getValue();
			if (row == body.getX() && newCol == body.getY()) {
				return false;
			}
		}
		return true;
	}

	/** Print the game introduction */
	public void printIntroduction() {
		System.out.println("  _________              __            ________");
		System.out.println(" /   _____/ ____ _____  |  | __ ____  /  _____/_____    _____   ____");
		System.out.println(" \\_____  \\ /    \\\\__  \\ |  |/ // __ \\/   \\  ___\\__  \\  /     \\_/ __ \\");
		System.out.println(" /        \\   |  \\/ __ \\|    <\\  ___/\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/");
		System.out.println("/_______  /___|  (____  /__|_ \\\\___  >\\______  (____  /__|_|  /\\___  >");
		System.out.println("        \\/     \\/     \\/     \\/    \\/        \\/     \\/      \\/     \\/");
		System.out.println("\nWelcome to SnakeGame!");
		System.out.println("\nA snake @****** moves around a board " +
				"eating targets \"+\".");
		System.out.println("Each time the snake eats the target it grows " +
				"another * longer.");
		System.out.println("The objective is to grow the longest it can " +
				"without moving into");
		System.out.println("itself or the wall.");
		System.out.println("\n");
	}

	/** Print help menu */
	public void helpMenu() {
		System.out.println("\nCommands:\n" +
				"  w - move north\n" +
				"  s - move south\n" +
				"  d - move east\n" +
				"  a - move west\n" +
				"  h - help\n" +
				"  f - save game to file\n" +
				"  r - restore game from file\n" +
				"  q - quit");
		Prompt.getString("Press enter to continue");
	}
}
