package Logic;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Elements.Dragon;
import Elements.Exit;
import Elements.Player;
import Elements.Sword;
import Interface.Maze;
import Interface.RandomMaze;
import Interface.StaticMaze;


public class GameState {
	
	Player player = new Player(0,0);
	int dragonsSize;
	int dragonsType;
	Sword sword = new Sword(0,0);
	Maze labirinto = new Maze();
	ArrayList<Dragon> dragons = new ArrayList<Dragon>();
	
	
	public static void main(String[] args)
	{
		GameState g = new GameState();
		
		
		int type = g.chooseMazeType();
		if (type == 0)
			g.labirinto= new StaticMaze();
		else if (type == 1)
			g.labirinto = new RandomMaze();
		//setExit(g.labirinto.exit);
		
		
		
		g.chooseDragonsNum();
		g.chooseDragonType();
		g.generateElements();
		g.Jogar();
		
		//g.labirinto.PrintLab();

		//System.out.println("Saida: " + c.exit.x + ";" + c.exit.y);
		
		//Board b = new Board();
		//c.board = new char[c.size][c.size];
		//b.board = c.board;
		//b.Jogar(); 
		return;
	}
	
	//funções ainda não alteradas
	
	

	void Jogar()
	{
		boolean jogar = true;
		char move = ' ';

		while(jogar)
		{
			
			System.out.println("Use the following keys to play the game! ");
			System.out.println("Move UP = 'W' Move Down = 'S' Move LEFT = 'A' Move RIGHT = 'D'");
			
			if(checkDragonsColision())
			{
				System.out.println("GAME OVER!");
				jogar = false;
			}
			
			if(checkPlayerWin())
			{
				System.out.println("YOU WON!!!");
				jogar = false;
			}
			
			PrintExit();
			PrintSword();
			PrintDragons();
			PrintPlayer();
			labirinto.PrintLab();
			
			//PrintLab();
			
			Scanner sc = new Scanner(System.in);
			move = sc.next().charAt(0);
			if(move == 'a'||move == 'w'|| move == 's'||move == 'd')
			{
				if(!movePlayer(move))
					System.out.println("Can´t move that direction!");
				else
					moveDragons();
			}
			else{
				System.out.println("key = " + move + "\n");
				System.out.println("Invalid Key!1");
			}
				
		}
		
	}
	
	// ve colisao com o dragão
    //tiver a espada mata o dragao
    // se for morto pelo dragao retorna true
	
	





	boolean checkDragonsColision() 
	{
		for(int i = 0; i < dragonsSize;i++)
		{
			if(dragons.get(i).getX() == sword.getX() && dragons.get(i).getY()== sword.getY())
			{
				dragons.get(i).setSwordAndDragon();
				
			}
			if(Math.abs(player.getX()-dragons.get(i).getX()) == 0 && Math.abs(player.getY()-dragons.get(i).getY()) == 1 
					||Math.abs(player.getX()-dragons.get(i).getX()) == 1 && Math.abs(player.getY()-dragons.get(i).getY()) == 0
					||Math.abs(player.getX()-dragons.get(i).getX()) == 0 && Math.abs(player.getY()-dragons.get(i).getY()) == 0)
			{
				
				if(player.getEstado() == 'H')
				{
					if(dragons.get(i).getEstado()== 's') //dragao a dormir
						continue;
					else
						return true;

				}
				else
				{
					dragons.remove(i);
					dragonsSize--;
					i--;					
				}
				
			}
			
			
			
			
			
		}
		return false;
		
	}
	
	
	
	
	
	
	
	
	boolean checkPlayerWin()
	{
		
		if(player.getX() == labirinto.exit.getX() && player.getY() == labirinto.exit.getY())
				return true;
		else
			return false;
	}
	
	
	//fim destas funcoes
	
	void PrintPlayer()
	{
		labirinto.getBoard()[player.getY()][player.getX()]= player.getEstado();
	}
	
	void PrintSword()
	{
		labirinto.getBoard()[sword.getY()][sword.getX()]= sword.getEstado();
	}
	
	void PrintExit()
	{
			if(dragonsSize == 0)
				labirinto.exit.setOpen();
			labirinto.getBoard()[labirinto.exit.getY()][labirinto.exit.getX()]=labirinto.exit.getEstado();
	}
	
	void PrintDragons()
	{
		for(int i = 0; i < dragonsSize;i++)
			labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()]=dragons.get(i).getEstado();
	}
	
	
	 int chooseMazeType()
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
	 
	 
	 void chooseDragonType() // falta implementar
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
			dragonsType = choice;
	 }
	 
	 void chooseDragonsNum()
	 {
		 	int numDragons;
			System.out.println("Number of dragons you want?");
			do
			{
			Scanner sc = new Scanner(System.in);
			numDragons = sc.nextInt();
			}while( numDragons >10 && numDragons <0);
			dragonsSize = numDragons; 
			
	 }
	 
	 
	
	void generateElements() // gera os elementos de jogo como o dragão, heroi, espada
	{
		generateSword();
		generateDragons();
		generatePlayer();
		return;
	}
	
	 
	 void generateSword()
	 {
		 Random rand = new Random();
		 boolean condition = true;
		 int labSize = labirinto.getSize();
		 while(condition)
		 {
			 int x_pos = rand.nextInt(labSize);
			 int y_pos =rand.nextInt(labSize);
			 if(labirinto.getBoard()[y_pos][x_pos] == ' ' )  // esta condição não abrange estar player ao lado do dragao
			 {
				 sword = new Sword(x_pos,y_pos);
				 labirinto.getBoard()[y_pos][x_pos] = sword.getEstado();
				 condition = false;
			 }
		 }
	 }
	 
	 

	 void generateDragons()
	 {
		 for(int i = 0; i < dragonsSize;i++)
		 {
			 Random rand = new Random();
			 boolean condition = true;
			 int labSize = labirinto.getSize();

			 while(condition)
			 {
				 int x_pos = rand.nextInt(labSize);
				 int y_pos =rand.nextInt(labSize);
				 if(labirinto.getBoard()[y_pos][x_pos] == ' ' )  // esta condição não abrange estar player ao lado do dragao
				 {
					 dragons.add(new Dragon(x_pos,y_pos,dragonsType));
					 condition = false;
				 } 
			 }
		 }
	 }
	 
	 
	 
	 void generatePlayer()
	 {
		 Random rand = new Random();
		 boolean condition = true;
		 int labSize = labirinto.getSize();
		 
		 while(condition)
		 {
			 int x_pos = rand.nextInt(labSize);
			 int y_pos =rand.nextInt(labSize);
			 if(labirinto.getBoard()[y_pos][x_pos] == ' ' )  // esta condição não abrange estar player ao lado do dragao
			 {
				 player = new Player(x_pos,y_pos);
				 labirinto.getBoard()[y_pos][x_pos] = player.getEstado();
				 condition = false;
			 }
		 }
	 }
	 
	 void generateExit() // falta implementar esta errado porque é nas boarders que tem a exit
	 {
		return;
	 }
	
	 
	 /*
	 0 - FROZEN DRAGON
	 1 - RANDOM MOVING DRAGON
	 2 - RANDOM MOVING DRAGON + SLEEPING;
	 */
	 

	 
	 void moveDragons() // move o dragão tendo em conta os varios tipos de dragoes ---->falta implementar
	{
		 for(int i = 0; i < dragonsSize;i++)
		 {
			 if(dragons.get(i).getType()==0)
				 return;
			 else
			 {
				 if(dragons.get(i).getNumPlaysSleeping() > 0){
					 dragons.get(i).subbPlaySleeping();
					 continue;
				 }
				 else
				 {
					 dragons.get(i).setNormalDragon();
					 moveDragon(i);	
				 }
			 }
		 }
	}
	void moveDragon(int i)
	{
		
			Random rand = new Random();
			boolean choice = true;
			
			labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()]=' ';
			
			while(choice)
			{
				// move para cima, move para baixo, move para a esquerda, move para a direita, fica parado, fica a dormir
				int move;
				
				if(dragons.get(i).getType() == 1)
					move = rand.nextInt(4)+0; 
				else
					move = rand.nextInt(5)+0;

				if(move == 0) //move UP
				{ 
					if(labirinto.getBoard()[dragons.get(i).getY()-1][dragons.get(i).getX()]!=' ')
						continue;
					else
					{
						dragons.get(i).moveUP();
						choice = false;
						break;
					}

				}
				else if(move == 1) // move DOWN
				{
					if(labirinto.getBoard()[dragons.get(i).getY()+1][dragons.get(i).getX()]!=' ')
						continue;
					else
					{
						dragons.get(i).moveDOWN();
						choice = false;
						break;
					}

				}
				else if(move == 2) //move LEFT
				{
					if(labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()-1]!=' ')
						continue;
					else
					{
						dragons.get(i).moveLEFT();
						choice = false;
						break;
					}
				}
				else if(move == 3) //move RIGHT
				{
					if(labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()+1]!=' ')
						continue;
					else
					{ 
						dragons.get(i).moveRIGHT();
						choice = false;
						break;
					}
				}
				else if (move == 4 && dragons.get(i).getType() == 2)
				{
					//numero de jogadas que vai ficar a dormir
					//numero maximo de jogadas que vai ficar a dormir são 5
					int sleep = rand.nextInt(5) + 1;
					dragons.get(i).setSleepTime(sleep); // dragons[dragonPos].setSleepTime(sleep);
					dragons.get(i).setSleep();
					choice = false;
					break;
				}
				else
				{
					choice=false;
					break;
				}
			}

			return;
	}
	 
	 
	 
	 boolean movePlayer(char move)
		{
			if(move == 'a')
			{
				if(labirinto.getBoard()[player.getY()][player.getX()-1] == 'X'||
						(labirinto.getBoard()[player.getY()][player.getX()-1] == 's' && player.getEstado()!= 'A'))
					return false;
				if(labirinto.getBoard()[player.getY()][player.getX()-1] =='E')
					{
					player.setArmado();
					sword.removeSword();
					}

			}
			else if(move == 'w')
			{
				if(labirinto.getBoard()[player.getY()-1][player.getX()] == 'X'||
						(labirinto.getBoard()[player.getY()-1][player.getX()] == 's' && player.getEstado()!= 'A'))
					return false;
				if(labirinto.getBoard()[player.getY()-1][player.getX()] == 'E')
					{
					player.setArmado();
					sword.removeSword();
					}

			}
			else if(move == 's')
			{
				if(labirinto.getBoard()[player.getY()+1][player.getX()] == 'X'||
						(labirinto.getBoard()[player.getY()+1][player.getX()] == 's' && player.getEstado()!= 'A'))
					return false;
				if(labirinto.getBoard()[player.getY()+1][player.getX()] == 'E')
					{
					player.setArmado();
					sword.removeSword();
					}

				
			}
			else// (move == 'd')
			{
				if(labirinto.getBoard()[player.getY()][player.getX()+1] == 'X'||
						(labirinto.getBoard()[player.getY()][player.getX()+1] == 's' && player.getEstado()!= 'A'))
					return false;
				if(labirinto.getBoard()[player.getY()][player.getX()+1] == 'E')
					{
					player.setArmado();
					sword.removeSword();
					}

			}
			

			labirinto.getBoard()[player.getY()][player.getX()]= ' ';
			
			
			switch(move)
			{
			case'a':
				player.moveLEFT();
				break;
			case'w':
				player.moveUP();
				break;
			case's':
				player.moveDOWN();
				break;
			case'd':
				player.moveRIGHT();
				break;
						
			};
			return true;
			
				
		}
}
