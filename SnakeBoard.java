/**
 *	Snake Board contains the board that has all of the characters
 *  in it. This program mainly contains methods that prints the board
 * 	to the screen, and nothing more than that.
 *
 *	@author		Srithika Barakam
 *	@since		05/10/25
 */
public class SnakeBoard {
	
	/*	fields	*/
	private char[][] board;			// The 2D array to hold the board
	
	/*	Constructor	*/
	public SnakeBoard(int height, int width) {
		board = new char[height+2][width+2];
		//Snake sn = new Snake();
		
		}
	
	/**
	 *	Print the board to the screen.
	 */
	public void printBoard(Snake snake, Coordinate target) {
		fillGrid(snake, target);
		board[target.getX()][ target.getY()] = '+';
		for (int i = 0; i < snake.size(); i++) {
			Coordinate cd = snake.get(i).getValue();
			if (i==0)
				board[cd.getX()][cd.getY()] = '@';
			else
				board[cd.getX()][cd.getY()] = '*';
			}
		
		
		printGrid();
	}
	
	/* Helper methods go here	*/
	
	/**
	 * This method fills the grid with the border.
	 *
	 * @param Snake			snake used in the program
	 * @param Target		target used in the program
	 */
	private void fillGrid(Snake snake, Coordinate target) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
					if (i == 0)
						board[i][j] = '-';
					else if (j == 0)
							board[i][j] = '|';
					else if (j == board[0].length-1)
						board[i][j] = '|';
					else if (i == board.length-1)
						board[i][j] = '-';
					else
						board[i][j] = ' ';
					
				}
			}
				
			board[0][0] = '+';
			board[board.length-1][0] = '+';
			board[0][board[0].length-1] = '+';
			board[board.length-1][board[0].length-1] = '+';
	}
	
	/**
	 * Prints the board to the terminal
	 */
	private void printGrid() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/*	Accessor methods	*/
	/**
	 * Gets height of the board. 
	 * 
	 * @return int height
	 */
	public int getHeight() {
		return board.length;
	}
	/**
	 * Gets width of the board
	 * 
	 * @return int width
	 */
	public int getWidth() {
		return board[0].length;
	}

	
	/********************************************************/
	/********************* For Testing **********************/
	/********************************************************/
	
	public static void main(String[] args) {
		// Create the board
		//int height = 10, width = 15;
		//SnakeBoard sb = new SnakeBoard(height, width);
		// Place the snake
		//Coordinate cd = new Coordinate(3,3);
		//Snake snake = new Snake(cd);
		// Place the target
		//Coordinate target = new Coordinate(1, 7);
		// Print the board
		//sb.printBoard(snake, target);
	}
}
