package CLI;

import java.io.IOException;
import java.util.Scanner;

import Logic.GameState;

public class AskUser {




	public static void main(String args[]){
		boolean jogar = true;
		char move;
		int jogo=0;

		chooseMazeType();
		chooseDragonsNum();
		chooseDragonType();		
		GameState.runGame();

		while(jogar){
			GameState.RefreshElements();
			System.out.println("Fireballs: " + GameState.getFireballs().size());
			System.out.println("Use the following keys to play the game! ");
			System.out.println("Move UP = 'W' Move Down = 'S' Move LEFT = 'A' Move RIGHT = 'D' Shoot DART = 'E'");
			System.out.println(GameState.getDardosJogador()+ " Dardos na mochila");
			if (GameState.hasShield())
				System.out.println("Possui escudo");

			move= readChar();
			if (move == 'e'){
				System.out.println("Choose a direction (w, a, s, d): ");
				char direction;
				direction = AskUser.readChar();
				GameState.ShootDarts(direction);
			}
			else
			{
				jogo = GameState.Jogar(move);
				if(jogo == 0)
					continue;
				else
					break;
			}
		}
		
		switch (jogo){
		case 1:
			System.out.println("You got Slaughtered!");
			break;
		case 2:
			System.out.println("Winner, Winner, Chicken Dinner!");
			break;
		case 3:
			System.out.println("BURRRRRRNN!!!");
			break;
		}
	

		return;

	}
	 
	public static void chooseMazeType()
	{
		GameState.SetMaze(readInteger(0, 1, "Which game mode you want? (0 = StaticMaze, 1 = RandomMaze)\n"));
	}
	
	public static void chooseDragonsNum()
	{
		GameState.setDragonsSize(readInteger(0, 10, "Number of dragons you want?\n"));
	}
	
	private static Scanner in;
	
	private static int readInteger(int i, int j, String prompt)
	{
		int userInput;
		do
		{

			System.out.print(prompt);
			Scanner in = new Scanner(System.in);
			userInput = in.nextInt();
		} while (userInput < i || userInput > j);

		return userInput;
	}

	public static void chooseDragonType() 
	{
		System.out.println("0 - FROZEN DRAGON");
		System.out.println("1 - RANDOM MOVING DRAGON");
		System.out.println("2 - RANDOM MOVING DRAGON + SLEEPING");
		GameState.setDragonsType(readInteger(0, 2, "What dragon type do you want?\n"));
	}
	
	public static char readChar()
	{
		Scanner in = new Scanner(System.in);
		return in.next().charAt(0);
	}
	

	
	
	
	
}
