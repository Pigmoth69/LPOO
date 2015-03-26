package Interface;
import java.util.Random;
import java.util.Scanner;

import Elements.Exit;


public class RandomMaze extends Maze {
	
	
	char[][] visitedCells;
	int [] guideCell = new int[2];
	int visitedCellsDimension;
	int [][] pathHistory;
	char exitLocation;
	int pathPlace = 0;
	
	
	public RandomMaze()
	{
		type = 1;
		generateMaze();
	}
	
	void generateMaze()
	{
		System.out.println("Insert the size you want to the maze: ");
		Scanner sc = new Scanner(System.in);
		size = sc.nextInt();
		board = new char[size][size];
		visitedCellsDimension = (size-1)/2;
		visitedCells = new char[visitedCellsDimension][visitedCellsDimension];
		GenerateExit();
		InicialWalls();
		GeneratePaths();
	}
	
	void PrintVerified(){
		for(int i = 0; i < visitedCellsDimension; i++){
			System.out.print(i+" ");
		}
		System.out.println();
		for(int y = 0; y < visitedCellsDimension; y++){
			for (int x = 0; x < visitedCellsDimension; x++){
				System.out.print(visitedCells[y][x]+" ");
			}
			System.out.println();
		}
	}

	
	void GenerateExit(){
		
		int saida;
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
	
	
	
	
	
	void InicialWalls(){
		for (int i = 0; i < size; i++){
			board[0][i] = 'X'; //Topo
			board[i][size-1] = 'X'; //direita
			board[i][0] = 'X'; //esquerda
			board[size-1][i] = 'X'; //baixo
		}
		for (int i = 2; i < size-1; i+=2){ //paredes de dentro
			for (int j = 0; j < size; j++){
				board[i][j] = 'X';
				board[j][i] = 'X';
			}
		}
	}
	
	void GeneratePaths(){
		guideCell[0] = (exit.x-1)/2;
		guideCell[1] = (exit.y-1)/2;
		visitedCells[guideCell[1]][guideCell[0]] = '+';
		
		pathHistory = new int[2*visitedCellsDimension*visitedCellsDimension][2];
		
		
		pathHistory[pathPlace] = guideCell;
		pathPlace++;
		
		
		while (pathPlace>=0){
			int direction;
			do{
				while (!CanMove()){
					pathPlace--;
					
					if (pathPlace<0)
						return;
					guideCell[0] = pathHistory[pathPlace][0];
					guideCell[1] = pathHistory[pathPlace][1];
				//	System.out.println("pathPlace: " + pathPlace);
				}

				Random rand = new Random();
				direction = rand.nextInt(4); //0 cima, 1 direita, 2 baixo, 3 esquerda
			}while(!CanMoveDir(direction));

			
			//PrintLab();
			//PrintVerified();
			//System.out.println("Guide: " + guideCell[0] +", " + guideCell[1]);
			//System.out.println(direction);
			MovePath(direction);
		}
	}
	
	boolean CanMove(){
		if (!CanMoveDir(0) && !CanMoveDir(1) && !CanMoveDir(2) && !CanMoveDir(3))
			return false;
		else
			return true;
	}
	
	boolean CanMoveDir(int direction){
		if (direction == 0){ //cima
			if (guideCell[1] == 0)
				return false;
			else if (visitedCells[guideCell[1]-1][guideCell[0]] == '+'){
				return false;
			}
			else
				return true;
		}
		else if (direction == 1){ //direita
			if (guideCell[0] == (visitedCellsDimension - 1))
				return false;
			else if (visitedCells[guideCell[1]][guideCell[0] + 1] == '+'){
				return false;
			}
			else
				return true;
		}
		else if (direction == 2){ //baixo
			if (guideCell[1] == (visitedCellsDimension - 1))
				return false;
			else if (visitedCells[guideCell[1]+1][guideCell[0]] == '+')
				return false;
			else
				return true;
		}
		else if (direction == 3){ //esquerda
			if (guideCell[0] == 0)
				return false;
			else if (visitedCells[guideCell[1]][guideCell[0] - 1] == '+')
				return false;
			else
				return true;
		}
		else
			return false;
		
	}
	
	void MovePath(int direction){
		if (direction == 0){ //cima
			visitedCells[guideCell[1]-1][guideCell[0]] = '+';
			board[guideCell[1]*2][guideCell[0]*2+1] = ' ';
			guideCell[1]--;
			pathPlace++;
			pathHistory[pathPlace][0] = guideCell[0];
			pathHistory[pathPlace][1] = guideCell[1];
		}
		else if (direction == 1){ //direita
			visitedCells[guideCell[1]][guideCell[0] + 1] = '+';
			board[guideCell[1]*2+1][guideCell[0]*2+2] = ' ';
			guideCell[0]++;
			pathPlace++;
			pathHistory[pathPlace][0] = guideCell[0];
			pathHistory[pathPlace][1] = guideCell[1];
		}
		else if (direction == 2){ //baixo
			visitedCells[guideCell[1]+1][guideCell[0]] = '+';
			board[guideCell[1]*2+2][guideCell[0]*2+1] = ' ';
			guideCell[1]++;
			pathPlace++;
			pathHistory[pathPlace][0] = guideCell[0];
			pathHistory[pathPlace][1] = guideCell[1];
		}
		else if (direction == 3){ //esquerda
			visitedCells[guideCell[1]][guideCell[0] - 1] = '+';
			board[guideCell[1]*2+1][guideCell[0]*2] = ' ';
			guideCell[0]--;
			pathPlace++;
			pathHistory[pathPlace][0] = guideCell[0];
			pathHistory[pathPlace][1] = guideCell[1];
		}
	}
	int getType()
	{
		return type;
	}
	
}
