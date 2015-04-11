/**
 * StaticMaze.java - this file is related to the creation of a static maze
 * @author Daniel Reis
 * @author João Baião
 */
package Interface;


public class StaticMaze extends Maze{

	/**  
	 * Constructor of the class
	 */ 
	public StaticMaze()
	{
		type = 0;
		generateMaze();
	}

	/**  
	 * return the type of maze
	 * @returns 0 or 1, static or random
	 */ 
	int getType()
	{
		return type;
	}

	/**  
	 * Generates the static maze
	 */ 
	void generateMaze()
	{
		char board[][] = {
				{'X','X','X','X','X','X','X','X','X','X'},
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X',' ',' ',' ',' ',' ',' ','X',' ','X'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X',' ','X','X',' ',' ',' ',' ',' ','X'},
				{'X','X','X','X','X','X','X','X','X','X'}
		};
		this.board = board;
		size = 10;
		GenerateExit();
		SetSword(1,8);
		

	}

	/**  
	 * Create an exit at (9, 5)
	 */ 
	void GenerateExit(){
		SetExit(9, 5);
	}
}