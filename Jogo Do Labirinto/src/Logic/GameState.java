package Logic;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Elements.Dart;
import Elements.Dragon;
import Elements.Exit;
import Elements.Player;
import Elements.Shield;
import Elements.Sword;
import Interface.Maze;
import Interface.RandomMaze;
import Interface.StaticMaze;
import Logic.AskUser;


public class GameState {
	
	//Global GameState
	public static GameState g;
	 
	//Private Objects
	private Maze labirinto;
	private int dragonsSize;
	private int dragonsType;
	private Sword sword;
	private Exit saida;
	private ArrayList<Dragon> dragons = new ArrayList<Dragon>();
	private Player player;
	private ArrayList<Dart> dardos = new ArrayList<Dart>();
	private int dardosJogador = 0;
	private Shield shield;
	private boolean escudo = false;
	 
	public static void main(String[] args){
		g = new GameState();
		
		//escolhe labirinto
		int mazeType = AskUser.chooseMazeType();
		g.SetMaze(mazeType);
		
		g.dragonsSize = AskUser.chooseDragonsNum();
		g.dragonsType = AskUser.chooseDragonType();
		
		g.saida = g.labirinto.exit;
		g.sword = g.labirinto.sword;
		g.GenerateDragons();
		g.GeneratePlayer();
		g.GenerateDarts();
		g.GenerateShield();
		int result = g.Jogar();
		
		switch (result){
		case 0:
			System.out.println("Winner, Winner, Chicken Dinner!");
			break;
		case 1:
			System.out.println("You got Slaughtered!");
			break;
		case 2:
			System.out.println("BURRRRRRNN!!!");
			break;
		}
			
		return;
	}

	public void SetMaze(int mazeType){
		if (mazeType == 0)
			this.labirinto= new StaticMaze();
		else if (mazeType == 1)
			this.labirinto = new RandomMaze();
	}

	public void setDragonsType(int type)
	{
		dragonsType = type;
	}
	
	public void GenerateDragons() 
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

	public void setDragonsSize(int dragonsSize) {
		this.dragonsSize = dragonsSize;
	}

	public void AddDragons(int x, int y){
		dragons.add(new Dragon(x, y, dragonsType));
	}

	public Player getPlayer(){
		return player;
	}
	
	public int getDragonSize(){
		return dragonsSize;
	}
	
	public void GeneratePlayer()
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
				NewPlayer(x_pos, y_pos);
				labirinto.getBoard()[y_pos][x_pos] = player.getEstado();
				condition = false;
			}
		}
	}
	
	public void NewPlayer(int x, int y){
		player = new Player(x, y); 
	}
	
	public Maze getMaze(){
		return labirinto;
	}
	
	public void GenerateDarts(){

		int quantidade;
		Random rand2 = new Random();
		quantidade = rand2.nextInt(6);

		for(int i = 0; i < quantidade;i++)
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
					AddDart(x_pos, y_pos);
					condition = false;
				} 
			}
		}
	}
	
	public void AddDart(int x, int y){
		dardos.add(new Dart(x, y));
	}

	public void GenerateShield(){
		 Random rand = new Random();
		 boolean condition = true;
		 int labSize = labirinto.getSize();
		 while(condition)
		 {
			 int x_pos = rand.nextInt(labSize);
			 int y_pos =rand.nextInt(labSize);
			 if(labirinto.getBoard()[y_pos][x_pos] == ' ' )  // esta condição não abrange estar player ao lado do dragao
			 {
				 SetShield(x_pos,y_pos);
				 labirinto.getBoard()[y_pos][x_pos] = shield.getEstado();
				 condition = false;
			 }
		 }
	 }
	
	public void SetShield(int x, int y){
		shield = new Shield(x, y);
	}
	
	public void activateEscudo()
	{
		escudo = true;
	}
	
	public boolean movePlayer(char move)
	{
		if(move == 'a')
		{
			if(labirinto.getBoard()[player.getY()][player.getX()-1] == 'X'||
					(labirinto.getBoard()[player.getY()][player.getX()-1] == 's' && player.getEstado()!= 'A'))
				return false;
			else if(labirinto.getBoard()[player.getY()][player.getX()-1] =='E')
			{
				player.setArmado();
				labirinto.sword.removeSword();
			}
			else if(labirinto.getBoard()[player.getY()][player.getX()-1] =='d')
			{
				dardosJogador++;
				for (int i = 0; i < dardos.size(); i++){
					if (dardos.get(i).getX() == (player.getX()-1) && 
							dardos.get(i).getY() == player.getY()){
						dardos.remove(i);
						break;
					}
				}
			}
			else if (labirinto.getBoard()[player.getY()][player.getX()-1] =='0')
			{
				escudo = true;
				shield.removeShield();
			}

		}
		else if(move == 'w')
		{
			if(labirinto.getBoard()[player.getY()-1][player.getX()] == 'X'||
					(labirinto.getBoard()[player.getY()-1][player.getX()] == 's' && player.getEstado()!= 'A'))
				return false;
			else if(labirinto.getBoard()[player.getY()-1][player.getX()] == 'E')
			{
				player.setArmado();
				labirinto.sword.removeSword();
			}
			else if(labirinto.getBoard()[player.getY()-1][player.getX()] =='d')
			{
				dardosJogador++;
				for (int i = 0; i < dardos.size(); i++){
					if (dardos.get(i).getX() == player.getX() && 
							dardos.get(i).getY() == (player.getY()-1)){
						dardos.remove(i);
						break;
					}
				}
			}
			else if (labirinto.getBoard()[player.getY()-1][player.getX()] =='0')
			{
				escudo = true;
				shield.removeShield();
			}

		}
		else if(move == 's')
		{
			if(labirinto.getBoard()[player.getY()+1][player.getX()] == 'X'||
					(labirinto.getBoard()[player.getY()+1][player.getX()] == 's' && player.getEstado()!= 'A'))
				return false;
			else if(labirinto.getBoard()[player.getY()+1][player.getX()] == 'E')
			{
				player.setArmado();
				labirinto.sword.removeSword();
			}
			else if(labirinto.getBoard()[player.getY()+1][player.getX()] =='d')
			{
				dardosJogador++;
				for (int i = 0; i < dardos.size(); i++){
					if (dardos.get(i).getX() == player.getX() && 
							dardos.get(i).getY() == (player.getY()+1)){
						dardos.remove(i);
						break;
					}
				}
			}
			else if (labirinto.getBoard()[player.getY()+1][player.getX()] =='0')
			{
				escudo = true;
				shield.removeShield();
			}


		}
		else// (move == 'd')
		{
			if(labirinto.getBoard()[player.getY()][player.getX()+1] == 'X'||
					(labirinto.getBoard()[player.getY()][player.getX()+1] == 's' && player.getEstado()!= 'A'))
				return false;
			else if(labirinto.getBoard()[player.getY()][player.getX()+1] == 'E')
			{
				player.setArmado();
				labirinto.sword.removeSword();
			}
			else if(labirinto.getBoard()[player.getY()][player.getX()+1] =='d')
			{
				dardosJogador++;
				for (int i = 0; i < dardos.size(); i++){
					if (dardos.get(i).getX() == (player.getX()+1) && 
							dardos.get(i).getY() == player.getY()){
						dardos.remove(i);
						break;
					}
				}
			}
			else if (labirinto.getBoard()[player.getY()][player.getX()+1] =='0')
			{
				escudo = true;
				shield.removeShield();
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
	
	public boolean checkDragonsColision() 
	{
		for(int i = 0; i < dragonsSize;i++)
		{
			if(dragons.get(i).getX() == labirinto.sword.getX() && dragons.get(i).getY()== labirinto.sword.getY())
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
	
	public boolean checkPlayerWin()
	{
		
		if(player.getX() == labirinto.exit.getX() && player.getY() == labirinto.exit.getY())
				return true;
		else
			return false;
	}
	
	public void RefreshElements(){
		PrintExit();
		PrintSword();
		PrintDarts();
		PrintDragons();
		PrintPlayer();
	}
	
	public void PrintPlayer()
	{
		labirinto.getBoard()[player.getY()][player.getX()]= player.getEstado();
	}
	
	public void PrintSword()
	{
		labirinto.getBoard()[labirinto.sword.getY()][labirinto.sword.getX()]= labirinto.sword.getEstado();
	}
	
	public void PrintExit()
	{
			if(dragonsSize == 0)
				labirinto.exit.setOpen();
			labirinto.getBoard()[labirinto.exit.getY()][labirinto.exit.getX()]=labirinto.exit.getEstado();
	}
	
	public void PrintDragons()
	{
		for(int i = 0; i < dragonsSize;i++)
			labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()]=dragons.get(i).getEstado();
	}

	public void PrintDarts(){
		for(int i = 0; i < dardos.size();i++)
			labirinto.getBoard()[dardos.get(i).getY()][dardos.get(i).getX()]=dardos.get(i).getEstado();
	}

	public boolean checkDragonsFire(){ 
		boolean cima = true, direita = true, baixo = true, esquerda = true;
		if (escudo)
			return false;
		else{
			for (int i = 1; i <= 3; i++){
				if (player.getX()+i < labirinto.getSize()){
					if (labirinto.getBoard()[player.getY()][player.getX()+i] == 'D' && direita )
						return true;
					else if (labirinto.getBoard()[player.getY()][player.getX()+i] == 'X')
						direita = false;
				}

				if (player.getX()-i > 0){
					if (labirinto.getBoard()[player.getY()][player.getX()-i] == 'D' && esquerda )
						return true;
					else if (labirinto.getBoard()[player.getY()][player.getX()-i] == 'X')
						esquerda = false;
				}

				if (player.getY()-i > 0){
					if (labirinto.getBoard()[player.getY()-i][player.getX()] == 'D' && cima )
						return true;
					else if (labirinto.getBoard()[player.getY()-i][player.getX()] == 'X')
						cima = false;
				}

				if (player.getY()+i < labirinto.getSize()){
					if (labirinto.getBoard()[player.getY()+i][player.getX()] == 'D' && baixo )
						return true;
					else if (labirinto.getBoard()[player.getY()+i][player.getX()] == 'X')
						baixo = false;
				}
			}
		}

		return false;
	}

	public boolean ShootDarts(char direction){

		int xJogador = player.getX();
		int yJogador = player.getY();
		if (dardosJogador == 0)
			return false;
		else{
			if (direction == 'w'){
				int y = yJogador;
				while(true){ //cima
					y++;
					if (labirinto.getBoard()[y][xJogador] == 'X')
						break;
					else if (labirinto.getBoard()[y][xJogador] == 'D' || labirinto.getBoard()[y][xJogador] == 's'){
						for (int i = 0; i < dragonsSize; i++){
							if (dragons.get(i).getX() == xJogador && dragons.get(i).getY() == y){
								dragons.get(i).setDead();
								labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()]=dragons.get(i).getEstado();
								System.out.println("Dragão morto");
								dragons.remove(i);
								dragonsSize--;
								//PrintDragons();
								//labirinto.PrintLab();
								dardosJogador--;
								return true;
							}
						}
					}
				}
			}
			else if (direction == 's'){
				int y = yJogador;
				while(true){ //baixo
					y--;
					if (labirinto.getBoard()[y][xJogador] == 'X')
						break;
					else if (labirinto.getBoard()[y][xJogador] == 'D' || labirinto.getBoard()[y][xJogador] == 's'){
						for (int i = 0; i < dragonsSize; i++){
							if (dragons.get(i).getX() == xJogador && dragons.get(i).getY() == y){
								dragons.get(i).setDead();
								labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()]=dragons.get(i).getEstado();
								System.out.println("Dragão morto");
								dragons.remove(i);
								dragonsSize--;
								//PrintDragons();
								//labirinto.PrintLab();
								dardosJogador--;
								return true;
							}
						}
					}
				}
			}
			else if (direction == 'd'){
				int x = xJogador;
				while(true){ //direita
					x++;
					if (labirinto.getBoard()[yJogador][x] == 'X')
						break;
					else if (labirinto.getBoard()[yJogador][x] == 'D' || labirinto.getBoard()[yJogador][x] == 's'){
						for (int i = 0; i < dragonsSize; i++){
							if (dragons.get(i).getX() == x && dragons.get(i).getY() == yJogador){
								dragons.get(i).setDead();
								labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()]=dragons.get(i).getEstado();
								System.out.println("Dragão morto");
								dragons.remove(i);
								dragonsSize--;
								//PrintDragons();
								//labirinto.PrintLab();

								dardosJogador--;
								return true;
							}
						}
					}
				}
			}
			else if (direction == 'a'){
				int x = xJogador;
				while(true){ //esquerda
					x--;
					if (labirinto.getBoard()[yJogador][x] == 'X')
						break;
					else if (labirinto.getBoard()[yJogador][x] == 'D' || labirinto.getBoard()[yJogador][x] == 's'){
						for (int i = 0; i < dragonsSize; i++){
							if (dragons.get(i).getX() == x && dragons.get(i).getY() == yJogador){
								dragons.get(i).setDead();
								labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()]=dragons.get(i).getEstado();
								System.out.println("Dragão morto");
								dragons.remove(i);
								dragonsSize--;
								//PrintDragons();
								//labirinto.PrintLab();
								dardosJogador--;
								return true;
							}
						}
					}
				}
			}
			dardosJogador--;
			return false;
		}
	}

	public void moveDragons()
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

	public void moveDragon(int i){

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
				if(labirinto.getBoard()[dragons.get(i).getY()-1][dragons.get(i).getX()]=='X')
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
				if(labirinto.getBoard()[dragons.get(i).getY()+1][dragons.get(i).getX()]=='X')
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
				if(labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()-1]=='X')
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
				if(labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()+1]=='X')
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

	public int Jogar()
	{
		boolean jogar = true;
		char move = ' ';

		while(jogar)
		{

			System.out.println("Use the following keys to play the game! ");
			System.out.println("Move UP = 'W' Move Down = 'S' Move LEFT = 'A' Move RIGHT = 'D' Shoot DART = 'E'");

			if(checkDragonsColision())
			{
				return 1; //jogador morre
			}


			if(checkPlayerWin())
			{
				return 0; //jogador ganha
			}

			RefreshElements();
			labirinto.PrintLab();
			System.out.println(dardosJogador + " Dardos na mochila");
			if (escudo)
				System.out.println("Possui escudo");


			if(checkDragonsFire())
			{
				return 2; //jogador morre queimad0
			}
			
			move = AskUser.readChar();

			if (move == 'e'){
				System.out.println("Choose a direction (w, a, s, d): ");
				char direction = AskUser.readChar();
				ShootDarts(direction);
			}
			else if(move == 'a'||move == 'w'|| move == 's'||move == 'd')
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

		return 3;

	}

}
	 
	 
