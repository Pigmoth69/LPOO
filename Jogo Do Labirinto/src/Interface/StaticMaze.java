package Interface;


public class StaticMaze extends Maze{

	public StaticMaze()
	{
		type = 0;
		generateMaze();
	}

	int getType()
	{
		return type;
	}

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



	void GenerateExit(){
		SetExit(9, 5);
	}
}