package Logic;

import java.util.Scanner;

public class AskUser {
	 
	public static int chooseMazeType()
	{
		int choice;
		System.out.println("Which game mode you want? (0 = StaticMaze, 1 = RandomMaze)");
		do
		{
		Scanner sc = new Scanner(System.in);
		choice = sc.nextInt();
		}while(choice != 0 && choice !=1);
		return choice;		
	}
	
	public static int chooseDragonsNum()
	{
		int numDragons;
		System.out.println("Number of dragons you want?");
		do
		{
			Scanner sc = new Scanner(System.in);
			numDragons = sc.nextInt();
		}while( numDragons >10 && numDragons <0);
		return numDragons; 

	}

	public static int chooseDragonType() // falta implementar
	{
		int choice;
		System.out.println("What dragon type do you want?");
		System.out.println("0 - FROZEN DRAGON");
		System.out.println("1 - RANDOM MOVING DRAGON");
		System.out.println("2 - RANDOM MOVING DRAGON + SLEEPING");
		do
		{
			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
		}while(choice != 0 && choice !=1 && choice != 2);
		return choice;
	}
	
	public static char readChar()
	{
		char move;
		Scanner sc = new Scanner(System.in);
		move = sc.next().charAt(0);
		return move;
	}
	
	
	
	
}
