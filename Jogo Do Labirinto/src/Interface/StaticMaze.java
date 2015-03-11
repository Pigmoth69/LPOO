package Interface;

import java.util.Random;

import Elements.Exit;

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
		

	}



	void GenerateExit(){

		int saida;
		char exitLocation;
		do{
			Random rand = new Random();
			saida = rand.nextInt(4*size - 4)+1;
		}while(saida%2 != 0);//while(saida == 1 || saida == size || saida == (2*size -1) || saida == (3*size-2));

		//	System.out.println("saida = " + saida);

		int x, y;

		if (saida <= size)
			exitLocation = 'T';
		else if (saida <= 2*size-1)
			exitLocation = 'R';
		else if (saida <= 3*size-2)
			exitLocation = 'D';
		else
			exitLocation = 'L';

		//calculo do local da saída
		if (exitLocation == 'T'){
			y = 0;
			x = saida-1;
		}
		else if (exitLocation == 'R'){
			x = size-1;
			y = saida-size;
		}
		else if (exitLocation == 'D'){
			x = 3*size-2 - saida;
			y = size-1;
		}
		else{ //exitLocation == 'L'
			x = 0;
			y = 4*size-4 - saida + 1;
		}

		exit = new Exit(x, y);
	}
}