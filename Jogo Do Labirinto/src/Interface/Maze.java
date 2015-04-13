/**
 * Maze.java - this file is related to the board of the game
 * @author Daniel Reis
 * @author João Baião
 */
package Interface;

import java.io.Serializable;
import java.util.Random;

import Elements.Exit;
import Elements.Sword;

public class Maze implements Serializable{

	public Exit exit;
	public Sword sword;
	int type;
	char board[][];
	int size;
	
	/**  
	 * return the board
	 * @return the char relative to the board
	 */ 
	public char[][] getBoard()
	{
		return board;
	}
	
	/**  
	 * return the size of the board
	 * @return the size of the board   
	 */ 
	public int getSize(){
		return size;
	}
	
	/**  
	 *  draws the maze into the console
	 */ 
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
	
	/**  
	 * set the coordinates of the Exit
	 * @param x - x coordinates
	 * @param y - y coordinates
	 */ 
	public void SetExit(int x, int y){
		exit = new Exit(x, y);
	}
	
	/**  
	 * return the object of the exit
	 * @return the object of the exit  
	 */ 
	public Exit getExit(){
		return exit;
	}
	
	/**  
	 * Create a sword at (x, y)
	 * @param x - x coordinates
	 * @param y - y coordinates
	 */ 
	public void SetSword(int x, int y){
		sword = new Sword(x, y);
	}
	
	/**  
	 * Generate a sword at a random location 
	 */ 
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
