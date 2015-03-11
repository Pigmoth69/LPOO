package Interface;

import Elements.Exit;

public class Maze {
	public Exit exit;
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
	
	
	
}
