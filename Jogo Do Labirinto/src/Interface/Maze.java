package Interface;

import java.io.Serializable;
import java.util.Random;

import Elements.Exit;
import Elements.Sword;

public class Maze {

	public Exit exit;
	public Sword sword;
	int type;
	char board[][];
	int size;
	
	
	public char[][] getBoard()
	{
		return board;
	}
	
	public int getSize(){
		return size;
	}
	
	
	public void PrintLab()
	{
		System.out.println("Size: " + size);
		for(int y = 0; y < size; y++)
		{
			for(int x = 0; x < size;x++ ){
					System.out.print(board[y][x]+" ");
			}
			System.out.println();
		}
	}
	
	public void SetExit(int x, int y){
		exit = new Exit(x, y);
	}
	
	public Exit getExit(){
		return exit;
	}
	
	public void SetSword(int x, int y){
		sword = new Sword(x, y);
	}
	
	public void GenerateSword()
	{
		Random rand = new Random();
		boolean condition = true;
		int labSize = size;
		while(condition)
		{
			int x_pos = rand.nextInt(labSize);
			int y_pos =rand.nextInt(labSize);
			if(board[y_pos][x_pos] == ' ' )  // esta condição não abrange estar player ao lado do dragao
			{
				SetSword(x_pos, y_pos);
				board[y_pos][x_pos] = sword.getEstado();
				condition = false;
			}
		}
	}

	


}
