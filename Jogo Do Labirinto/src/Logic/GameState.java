package Logic;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Elements.Dart;
import Elements.Dragon;
import Elements.Exit;
import Elements.Fireball;
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
	private ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
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
		g.GeneratePlayer();
		g.GenerateDragons();
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

	//checked
	public void SetMaze(int mazeType){
		if (mazeType == 0)
			this.labirinto= new StaticMaze();
		else if (mazeType == 1)
			this.labirinto = new RandomMaze();
	}

	//checked
	public int getNumDardos(){
		return dardos.size();
	}
	
	//checked
	public void setDragonsType(int type)
	{
		dragonsType = type;
	}
	
	//checked
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
				if(checkDragonAdjacentWithPlayer(x_pos,y_pos))
					continue;
				if(labirinto.getBoard()[y_pos][x_pos] == ' ')  
				{
					dragons.add(new Dragon(x_pos,y_pos,dragonsType));
					condition = false;
				} 
			}
		}
	}

	//checked
	 boolean checkDragonAdjacentWithPlayer(int x_pos, int y_pos) {
		if(player.getX() == x_pos)
		{
			if(Math.abs(player.getY()-y_pos)==1)
				return true;
			else
				return false;
		}
		if(player.getY() == y_pos)
		{
			if(Math.abs(player.getX()-x_pos)==1)
				return true;
			else
				return false;
		}
		return false;
	}

	 //checked
	public void setDragonsSize(int dragonsSize) {
		this.dragonsSize = dragonsSize;
	}

	//checked
	public void AddDragons(int x, int y){
		dragons.add(new Dragon(x, y, dragonsType));
	}

	//checked
	public Player getPlayer(){
		return player;
	}
	
	//checked
	public int getDragonSize(){
		return this.dragonsSize;
	}
	
	//checked
	public void GeneratePlayer()
	{
		Random rand = new Random();
		boolean condition = true;
		int labSize = labirinto.getSize();

		while(condition)
		{
			int x_pos = rand.nextInt(labSize);
			int y_pos =rand.nextInt(labSize);
			if(labirinto.getBoard()[y_pos][x_pos] == ' ' )
			{
				NewPlayer(x_pos, y_pos);
				labirinto.getBoard()[y_pos][x_pos] = player.getEstado();
				condition = false;
			}
		}
	}
	
	//checked
	public void NewPlayer(int x, int y){
		player = new Player(x, y); 
	}
	
	//checked
	public Maze getMaze(){
		return labirinto;
	}
	
	//checked
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
				if(labirinto.getBoard()[y_pos][x_pos] == ' ' ) 
				{
					AddDart(x_pos, y_pos);
					condition = false;
				} 
			}
		}
	}
	
	//checked
	public void AddDart(int x, int y){
		dardos.add(new Dart(x, y));
	}

	//checked
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
	
	//checked
	public void SetShield(int x, int y){
		shield = new Shield(x, y);
	}
	
	//checked
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
	
	//checked
	public boolean checkPlayerWin()
	{
		
		if(player.getX() == labirinto.exit.getX() && player.getY() == labirinto.exit.getY())
				return true;
		else
			return false;
	}
	
	//checked
	public void RefreshElements(){
		PrintExit();
		PrintSword();
		PrintDarts();
		PrintDragons();
		PrintFireballs();
		PrintPlayer();
		labirinto.PrintLab();
	}
	
	//checked
	public void PrintPlayer()
	{
		labirinto.getBoard()[player.getY()][player.getX()]= player.getEstado();
	}
	
	//checked
	public void PrintSword()
	{
		labirinto.getBoard()[labirinto.sword.getY()][labirinto.sword.getX()]= labirinto.sword.getEstado();
	}
	
	//checked
	public void PrintExit()
	{
			if(dragonsSize == 0)
				labirinto.exit.setOpen();
			labirinto.getBoard()[labirinto.exit.getY()][labirinto.exit.getX()]=labirinto.exit.getEstado();
	}
	
	//checked
	public void PrintDragons()
	{
		for(int i = 0; i < dragonsSize;i++)
			labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()]=dragons.get(i).getEstado();
	}

	//checked
	public void PrintDarts(){
		for(int i = 0; i < dardos.size();i++)
			labirinto.getBoard()[dardos.get(i).getY()][dardos.get(i).getX()]=dardos.get(i).getEstado();
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
					y--;
					if (labirinto.getBoard()[y][xJogador] == 'X')
						break;
					else if (labirinto.getBoard()[y][xJogador] == 'D' || labirinto.getBoard()[y][xJogador] == 's' || labirinto.getBoard()[y][xJogador] == 'F'){
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
					y++;
					if (labirinto.getBoard()[y][xJogador] == 'X')
						break;
					else if (labirinto.getBoard()[y][xJogador] == 'D' || labirinto.getBoard()[y][xJogador] == 's'|| labirinto.getBoard()[y][xJogador] == 'F'){
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
					else if (labirinto.getBoard()[yJogador][x] == 'D' || labirinto.getBoard()[yJogador][x] == 's'|| labirinto.getBoard()[yJogador][xJogador] == 'F'){
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
					else if (labirinto.getBoard()[yJogador][x] == 'D' || labirinto.getBoard()[yJogador][x] == 's'|| labirinto.getBoard()[yJogador][xJogador] == 'F'){
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

	//checked
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

	//checked
	public void moveDragon(int i){

		Random rand = new Random();
		boolean choice = true;

		
		
		
		int t = checkIfDragonInLineOfPlayer(i);
		
		if(t!= -1)
		{
			Fireball f1 = null;
			
			switch(t){
			case 0:
				f1 = new Fireball(t,dragons.get(i).getX(),dragons.get(i).getY()-1);
				break;
			case 1:
				f1 = new Fireball(t,dragons.get(i).getX(),dragons.get(i).getY()+1);
				break;
			case 2:
				f1 = new Fireball(t,dragons.get(i).getX()+1,dragons.get(i).getY());
				break;
			case 3:
				f1 = new Fireball(t,dragons.get(i).getX()-1,dragons.get(i).getY());
				break;
			};
			
			fireballs.add(f1);
			return;
		}
		
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

	public int checkIfDragonInLineOfPlayer(int pos) {
		int x_pos = dragons.get(pos).getX();
		int y_pos = dragons.get(pos).getY();


		for(int i = x_pos ; i < labirinto.getSize();i++) // intercecção na direita
		{
			if(labirinto.getBoard()[y_pos][i]=='X')
				break;
			if(player.getX() == i && player.getY() == y_pos)//labirinto.getBoard()[y_pos][i]=='H')
				return 2;
		}

		for(int i = y_pos ; i < labirinto.getSize();i++) // intercecção na baixo
		{
			if(labirinto.getBoard()[i][x_pos]=='X')
				break;
			if(player.getY()==i && player.getX() == x_pos)//labirinto.getBoard()[i][x_pos]=='H')
				return 1;
		}


		for(int i = x_pos; i!=0;i--) // intercecção na esquerda
		{
			if(labirinto.getBoard()[y_pos][i]=='X')
				break;
			if(player.getX()==i && player.getY()==y_pos)//labirinto.getBoard()[y_pos][i]=='H')
				return 3;
		}

		for(int i = y_pos; i!=0;i--) //intercecção na cima
		{
			if(labirinto.getBoard()[i][x_pos]=='X')
				break;
			if(player.getY()==i && player.getX()==x_pos)//labirinto.getBoard()[i][x_pos]=='H')
				return 0;
		}
		return -1;
	}

	public boolean checkFireColisionWithPlayer(){ 

		for(int i = 0; i < fireballs.size();i++)
		{

			if(fireballs.get(i).getX() == player.getX() && fireballs.get(i).getY() == player.getY())
				return true;
		}
		return false;
	}

	public void moveFireballs()
	{
		for(int i = 0; i < fireballs.size();i++)
		{
			if(!moveFireball(i))
				i--;
		}
	}

	public boolean moveFireball(int i) {


		labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX()]=' ';

		if(fireballs.get(i).getType()==0) // move para cima
		{
			
			
			if(fireballs.get(i).getLife()==0)
			{
				fireballs.remove(i);
				return false;
			}
			else if (labirinto.getBoard()[fireballs.get(i).getY()-1][fireballs.get(i).getX()]=='X')
			{
				fireballs.remove(i);
				return false;
			}
			else 
				fireballs.get(i).moveUP();

		}
		else if(fireballs.get(i).getType()==1) // move para baixo
		{
			if(fireballs.get(i).getLife()==0)
			{
				fireballs.remove(i);
				return false;
			}
			else if (labirinto.getBoard()[fireballs.get(i).getY()+1][fireballs.get(i).getX()]=='X')
			{
				fireballs.remove(i);
				return false;
			}
			else
				fireballs.get(i).moveDOWN();

		}
		else if(fireballs.get(i).getType()==2) // move para a direita
		{
			if(fireballs.get(i).getLife()==0)
			{
				fireballs.remove(i);
				return false;
			}
			else if (labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX()+1]=='X')
			{
				fireballs.remove(i);
				return false;
			}
			else
				fireballs.get(i).moveRIGHT();
		}
		else // move para a esquerda
		{
			if(fireballs.get(i).getLife()==0)
			{
				fireballs.remove(i);
				return false;
			}
			else if (labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX()-1]=='X')
			{
				fireballs.remove(i);
				return false;
			}
			else
				fireballs.get(i).moveLEFT();
		}
		return true;
	}

	public void PrintFireballs()
	{
		for(int i = 0; i < fireballs.size();i++)
		{
			labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX()]=fireballs.get(i).getEstado();
		}
	}

	public int Jogar()
	{
		boolean jogar = true;
		char move = ' ';

		while(jogar)
		{
			RefreshElements();
			System.out.println("Fireballs: " + fireballs.size());
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

			
			System.out.println(dardosJogador + " Dardos na mochila");
			if (escudo)
				System.out.println("Possui escudo");
			
			if(escudo == false)
				if(checkFireColisionWithPlayer())
					return 2; //jogador morre queimad0

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
				{
					moveFireballs();
					moveDragons();
				}
			}
			else{
				System.out.println("key = " + move + "\n");
				System.out.println("Invalid Key!1");
			}

 
		}

		return 3;

	}

}
	 
	 
