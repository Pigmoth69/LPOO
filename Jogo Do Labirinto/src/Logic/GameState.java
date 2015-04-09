package Logic;

import java.util.ArrayList;
import java.util.Random;

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

public class GameState {

	// Global GameState
	// public static GameState g;

	// Private Objects
	private static Maze labirinto;
	private static int dragonsSize;
	private static int dragonsType;
	private static Sword sword;
	private static Exit saida;
	private static ArrayList<Dragon> dragons = new ArrayList<Dragon>();
	private static ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
	private static Player player;
	private static ArrayList<Dart> dardos = new ArrayList<Dart>();
	private static int dardosJogador = 0;
	private static Shield shield;
	private static boolean escudo = false;

	public static void runGame() {
		saida = labirinto.exit;
		sword = labirinto.sword;
		GeneratePlayer();
		GenerateDragons();
		GenerateDarts();
		GenerateShield();

	}

	// checked
	public static void SetMaze(int mazeType) {
		if (mazeType == 0)
			labirinto = new StaticMaze();
		else if (mazeType == 1)
			labirinto = new RandomMaze();
	}
	
	//checked
	public static void SetRandomMaze(int size){
		labirinto = new RandomMaze(size);
	}

	// checked
	public static int getNumDardos() {
		return dardos.size();
	}

	//checked
	public static void restartFireballs(){
		fireballs = new ArrayList<Fireball>();
	}
	
	//checked
	public static void restartDarts(){
		dardos = new ArrayList<Dart>();
	}
	
	//checked
	public static void restartDragons(){
		dragons = new ArrayList<Dragon>();
	}
	
	//checked
	public static Shield getShield(){
		return shield;
	}
	
	//checked
	public static ArrayList<Dart> getDarts(){
		return dardos;
	}

	//checked
	public static ArrayList<Dragon> getDragons(){
		return dragons;
	}
	
	// checked
	public static void setDragonsType(int type) {
		dragonsType = type;
	}
	
	//checked
	public static void setAllDragonsType(){
		for (int i = 0; i < dragons.size(); i++){
			dragons.get(i).setType(dragonsType);
		}
	}

	// checked
	public static void GenerateDragons() {
		Random rand = new Random();
		for (int i = 0; i < dragonsSize; i++) {
			boolean condition = true;
			int labSize = labirinto.getSize();

			while (condition) {
				int x_pos = rand.nextInt(labSize);
				int y_pos = rand.nextInt(labSize);
				if (checkDragonAdjacentWithPlayer(x_pos, y_pos))
					continue;
				
				boolean repetido = false;
				for (int j = 0; j < dragons.size(); j++){
					if (x_pos == dragons.get(j).getX() && y_pos == dragons.get(j).getY())
						repetido = true;
				}
				if (repetido)
					continue;
				
				if (labirinto.getBoard()[y_pos][x_pos] == ' ') {
					dragons.add(new Dragon(x_pos, y_pos, dragonsType));
					condition = false;
				}
			}
		}
	}

	// checked
	private static boolean checkDragonAdjacentWithPlayer(int x_pos, int y_pos) {
		if (player.getX() == x_pos) {
			if (Math.abs(player.getY() - y_pos) == 1)
				return true;
			else
				return false;
		}
		if (player.getY() == y_pos) {
			if (Math.abs(player.getX() - x_pos) == 1)
				return true;
			else
				return false;
		}
		return false;
	}

	// checked
	public static void setDragonsSize(int size) {
		dragonsSize = size;
	}

	// checked
	public static void AddDragons(int x, int y) {
		dragons.add(new Dragon(x, y, dragonsType));
	}

	// checked
	public static Player getPlayer() {
		return player;
	}

	// checked
	public static int getDragonSize() {
		return dragonsSize;
	}

	// checked
	public static void GeneratePlayer() {
		Random rand = new Random();
		boolean condition = true;
		int labSize = labirinto.getSize();

		while (condition) {
			int x_pos = rand.nextInt(labSize);
			int y_pos = rand.nextInt(labSize);
			if (labirinto.getBoard()[y_pos][x_pos] == ' ') {
				NewPlayer(x_pos, y_pos);
				labirinto.getBoard()[y_pos][x_pos] = player.getEstado();
				condition = false;
			}
		}
	}

	// checked
	public static void NewPlayer(int x, int y) {
		player = new Player(x, y);
	}

	// checked
	public static Maze getMaze() {
		return labirinto;
	}

	// checked
	public static void GenerateDarts() {

		int quantidade;
		Random rand2 = new Random();
		quantidade = rand2.nextInt(6);

		for (int i = 0; i < quantidade; i++) {
			Random rand = new Random();
			boolean condition = true;
			int labSize = labirinto.getSize();

			while (condition) {
				int x_pos = rand.nextInt(labSize);
				int y_pos = rand.nextInt(labSize);
				
				boolean repetido = false;
				for (int j = 0; j < dardos.size(); j++){
					if (x_pos == dardos.get(j).getX() && y_pos == dardos.get(j).getY())
						repetido = true;
				}
				if (repetido)
					continue;
				
				if (labirinto.getBoard()[y_pos][x_pos] == ' ') {
					AddDart(x_pos, y_pos);
					condition = false;
				}
			}
		}
	}

	// checked
	public static void AddDart(int x, int y) {
		dardos.add(new Dart(x, y));
	}
	
	public static int AddDartsToPlayer(int ammount){
		dardosJogador+= ammount;
		return dardosJogador;
	}

	// checked
	public static void GenerateShield() {
		Random rand = new Random();
		boolean condition = true;
		int labSize = labirinto.getSize();
		while (condition) {
			int x_pos = rand.nextInt(labSize);
			int y_pos = rand.nextInt(labSize);
			if (labirinto.getBoard()[y_pos][x_pos] == ' ') // esta condição não
															// abrange estar
															// player ao lado do
															// dragao
			{
				SetShield(x_pos, y_pos);
				labirinto.getBoard()[y_pos][x_pos] = shield.getEstado();
				condition = false;
			}
		}
	}

	// checked
	public static void SetShield(int x, int y) {
		shield = new Shield(x, y);
	}

	public static void RemoveShieldFromBoard(){
		shield.removeShield();
	}

	// checked
	public static void activateEscudo() {
		escudo = true;
	}

	public static boolean movePlayer(char move) {
		if (move == 'a') {
			if (labirinto.getBoard()[player.getY()][player.getX() - 1] == 'X'
			||	labirinto.getBoard()[player.getY()][player.getX() - 1] == 'C'
					|| (labirinto.getBoard()[player.getY()][player.getX() - 1] == 's' && player
							.getEstado() != 'A'))
				return false;
			else if (labirinto.getBoard()[player.getY()][player.getX() - 1] == 'E') {
				player.setArmado();
				labirinto.sword.removeSword();
			} else if (labirinto.getBoard()[player.getY()][player.getX() - 1] == 'd') {
				dardosJogador++;
				for (int i = 0; i < dardos.size(); i++) {
					if (dardos.get(i).getX() == (player.getX() - 1)
							&& dardos.get(i).getY() == player.getY()) {
						dardos.remove(i);
						break;
					}
				}
			} else if (labirinto.getBoard()[player.getY()][player.getX() - 1] == '0') {
				escudo = true;
				shield.removeShield();
			}

		} else if (move == 'w') {
			if (labirinto.getBoard()[player.getY() - 1][player.getX()] == 'X'
			||	labirinto.getBoard()[player.getY() - 1][player.getX()] == 'C'
					|| (labirinto.getBoard()[player.getY() - 1][player.getX()] == 's' && player
							.getEstado() != 'A'))
				return false;
			else if (labirinto.getBoard()[player.getY() - 1][player.getX()] == 'E') {
				player.setArmado();
				labirinto.sword.removeSword();
			} else if (labirinto.getBoard()[player.getY() - 1][player.getX()] == 'd') {
				dardosJogador++;
				for (int i = 0; i < dardos.size(); i++) {
					if (dardos.get(i).getX() == player.getX()
							&& dardos.get(i).getY() == (player.getY() - 1)) {
						dardos.remove(i);
						break;
					}
				}
			} else if (labirinto.getBoard()[player.getY() - 1][player.getX()] == '0') {
				escudo = true;
				shield.removeShield();
			}

		} else if (move == 's') {
			if (labirinto.getBoard()[player.getY() + 1][player.getX()] == 'X'
			||	labirinto.getBoard()[player.getY() + 1][player.getX()] == 'C'
					|| (labirinto.getBoard()[player.getY() + 1][player.getX()] == 's' && player
							.getEstado() != 'A'))
				return false;
			else if (labirinto.getBoard()[player.getY() + 1][player.getX()] == 'E') {
				player.setArmado();
				labirinto.sword.removeSword();
			} else if (labirinto.getBoard()[player.getY() + 1][player.getX()] == 'd') {
				dardosJogador++;
				for (int i = 0; i < dardos.size(); i++) {
					if (dardos.get(i).getX() == player.getX()
							&& dardos.get(i).getY() == (player.getY() + 1)) {
						dardos.remove(i);
						break;
					}
				}
			} else if (labirinto.getBoard()[player.getY() + 1][player.getX()] == '0') {
				escudo = true;
				shield.removeShield();
			}

		} else// (move == 'd')
		{
			if (labirinto.getBoard()[player.getY()][player.getX() + 1] == 'X'
			||	labirinto.getBoard()[player.getY()][player.getX() + 1] == 'C'
					|| (labirinto.getBoard()[player.getY()][player.getX() + 1] == 's' && player
							.getEstado() != 'A'))
				return false;
			else if (labirinto.getBoard()[player.getY()][player.getX() + 1] == 'E') {
				player.setArmado();
				labirinto.sword.removeSword();
			} else if (labirinto.getBoard()[player.getY()][player.getX() + 1] == 'd') {
				dardosJogador++;
				for (int i = 0; i < dardos.size(); i++) {
					if (dardos.get(i).getX() == (player.getX() + 1)
							&& dardos.get(i).getY() == player.getY()) {
						dardos.remove(i);
						break;
					}
				}
			} else if (labirinto.getBoard()[player.getY()][player.getX() + 1] == '0') {
				escudo = true;
				shield.removeShield();
			}

		}

		labirinto.getBoard()[player.getY()][player.getX()] = ' ';

		switch (move) {
		case 'a':
			player.moveLEFT();
			break;
		case 'w':
			player.moveUP();
			break;
		case 's':
			player.moveDOWN();
			break;
		case 'd':
			player.moveRIGHT();
			break;

		};
		return true;

	}

	public static boolean checkDragonsColision() {
		for (int i = 0; i < dragonsSize; i++) {
			if (dragons.get(i).getX() == labirinto.sword.getX()
					&& dragons.get(i).getY() == labirinto.sword.getY()) {
				dragons.get(i).setSwordAndDragon();

			}
			if (Math.abs(player.getX() - dragons.get(i).getX()) == 0
					&& Math.abs(player.getY() - dragons.get(i).getY()) == 1
					|| Math.abs(player.getX() - dragons.get(i).getX()) == 1
					&& Math.abs(player.getY() - dragons.get(i).getY()) == 0
					|| Math.abs(player.getX() - dragons.get(i).getX()) == 0
					&& Math.abs(player.getY() - dragons.get(i).getY()) == 0) {

				if (player.getEstado() == 'H') {
					if (dragons.get(i).getEstado() == 's') // dragao a dormir
						continue;
					else
						return true;

				} else {
					labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()] = ' ';
					dragons.remove(i);
					dragonsSize--;
					i--;
				}

			}
		}
		return false;

	}

	// checked
	public static boolean checkPlayerWin() {

		if (player.getX() == labirinto.exit.getX()&& player.getY() == labirinto.exit.getY())
			return true;
		else
			return false;
	}

	// checked
	public static void RefreshElements() {
		PrintExit();
		PrintShield();
		PrintSword();
		PrintDarts();
		PrintDragons();
		PrintFireballs();
		PrintPlayer();
	}
	
	//checked
	public static void PrintLab(){
		labirinto.PrintLab();
	}
	
	
	private static void PrintShield() {
		labirinto.getBoard()[shield.getY()][shield.getX()] = shield.getEstado();
	}

	public static int getDardosJogador(){
		return dardosJogador;
	}
	
	//checked
	public static ArrayList<Fireball> getFireballs(){
		return fireballs;
	}

	// checked 
	public static void PrintPlayer() {
		labirinto.getBoard()[player.getY()][player.getX()] = player.getEstado();
	}

	// checked
	public static void PrintSword() {
		labirinto.getBoard()[labirinto.sword.getY()][labirinto.sword.getX()] = labirinto.sword.getEstado();
	}

	// checked
	public static void PrintExit() {
		if (dragonsSize == 0)
			labirinto.exit.setOpen();
		labirinto.getBoard()[labirinto.exit.getY()][labirinto.exit.getX()] = labirinto.exit.getEstado();
	}

	// checked
	public static void PrintDragons() {
		for (int i = 0; i < dragonsSize; i++)
			labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()] = dragons.get(i).getEstado();
	}

	// checked
	public static void PrintDarts() {
		for (int i = 0; i < dardos.size(); i++)
			labirinto.getBoard()[dardos.get(i).getY()][dardos.get(i).getX()] = dardos.get(i).getEstado();
	}

	public static boolean ShootDarts(char direction) {

		int xJogador = player.getX();
		int yJogador = player.getY();
		if (dardosJogador == 0)
			return false;
		else {
			if (direction == 'w') {
				int y = yJogador;
				while (true) { // cima
					y--;
					if (labirinto.getBoard()[y][xJogador] == 'X'
					 || labirinto.getBoard()[y][xJogador] == 'C'
					 || labirinto.getBoard()[y][xJogador] == 'O')
						break;
					else if (labirinto.getBoard()[y][xJogador] == 'D'
							|| labirinto.getBoard()[y][xJogador] == 's'
							|| labirinto.getBoard()[y][xJogador] == 'F') {
						for (int i = 0; i < dragonsSize; i++) {
							if (dragons.get(i).getX() == xJogador
									&& dragons.get(i).getY() == y) {
								dragons.get(i).setDead();
								labirinto.getBoard()[dragons.get(i).getY()][dragons
										.get(i).getX()] = dragons.get(i)
										.getEstado();
								dragons.remove(i);
								dragonsSize--;
								// PrintDragons();
								// labirinto.PrintLab();
								dardosJogador--;
								return true;
							}
						}
					}
				}
			} else if (direction == 's') {
				int y = yJogador;
				while (true) { // baixo
					y++;
					if (labirinto.getBoard()[y][xJogador] == 'X'
					||	labirinto.getBoard()[y][xJogador] == 'C'
					||	labirinto.getBoard()[y][xJogador] == 'O')
						break;
					else if (labirinto.getBoard()[y][xJogador] == 'D'
							|| labirinto.getBoard()[y][xJogador] == 's'
							|| labirinto.getBoard()[y][xJogador] == 'F') {
						for (int i = 0; i < dragonsSize; i++) {
							if (dragons.get(i).getX() == xJogador
									&& dragons.get(i).getY() == y) {
								dragons.get(i).setDead();
								labirinto.getBoard()[dragons.get(i).getY()][dragons
										.get(i).getX()] = dragons.get(i)
										.getEstado();
								dragons.remove(i);
								dragonsSize--;
								// PrintDragons();
								// labirinto.PrintLab();
								dardosJogador--;
								return true;
							}
						}
					}
				}
			} else if (direction == 'd') {
				int x = xJogador;
				while (true) { // direita
					x++;
					if (labirinto.getBoard()[yJogador][x] == 'X'
					|| 	labirinto.getBoard()[yJogador][x] == 'C'
					|| 	labirinto.getBoard()[yJogador][x] == 'O')
						break;
					else if (labirinto.getBoard()[yJogador][x] == 'D'
							|| labirinto.getBoard()[yJogador][x] == 's'
							|| labirinto.getBoard()[yJogador][xJogador] == 'F') {
						for (int i = 0; i < dragonsSize; i++) {
							if (dragons.get(i).getX() == x
									&& dragons.get(i).getY() == yJogador) {
								dragons.get(i).setDead();
								labirinto.getBoard()[dragons.get(i).getY()][dragons
										.get(i).getX()] = dragons.get(i)
										.getEstado();
								dragons.remove(i);
								dragonsSize--;
								// PrintDragons();
								// labirinto.PrintLab();

								dardosJogador--;
								return true;
							}
						}
					}
				}
			} else if (direction == 'a') {
				int x = xJogador;
				while (true) { // esquerda
					x--;
					if (labirinto.getBoard()[yJogador][x] == 'X'
					||	labirinto.getBoard()[yJogador][x] == 'C'
					||	labirinto.getBoard()[yJogador][x] == 'O')
						break;
					else if (labirinto.getBoard()[yJogador][x] == 'D'
							|| labirinto.getBoard()[yJogador][x] == 's'
							|| labirinto.getBoard()[yJogador][xJogador] == 'F') {
						for (int i = 0; i < dragonsSize; i++) {
							if (dragons.get(i).getX() == x
									&& dragons.get(i).getY() == yJogador) {
								dragons.get(i).setDead();
								labirinto.getBoard()[dragons.get(i).getY()][dragons
										.get(i).getX()] = dragons.get(i)
										.getEstado();
								dragons.remove(i);
								dragonsSize--;
								// PrintDragons();
								// labirinto.PrintLab();
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

	// checked
	public static void moveDragons() {
		for (int i = 0; i < dragonsSize; i++) {
			if (dragons.get(i).getNumPlaysSleeping() > 0) {
				dragons.get(i).subbPlaySleeping();
				continue;

			} else {
				dragons.get(i).setNormalDragon();
				moveDragon(i);
			}
		}
	}

	// checked
	public static boolean moveDragon(int i) {

		if (i >= dragonsSize)
			return false;
		
		Random rand = new Random();
		boolean choice = true;

		int t = checkIfDragonInLineOfPlayer(i);
		
		
		

		if (t != -1) {
			Fireball f1 = null;

			switch (t) {
			case 0:
				f1 = new Fireball(t, dragons.get(i).getX(), dragons.get(i)
						.getY() - 1);
				break;
			case 1:
				f1 = new Fireball(t, dragons.get(i).getX(), dragons.get(i)
						.getY() + 1);
				break;
			case 2:
				f1 = new Fireball(t, dragons.get(i).getX() + 1, dragons.get(i)
						.getY());
				break;
			case 3:
				f1 = new Fireball(t, dragons.get(i).getX() - 1, dragons.get(i)
						.getY());
				break;
			};

			fireballs.add(f1);
			return false;
		}
		
		if (dragons.get(i).getType() == 0)
			return false;


		labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()] = ' ';
		while (choice) {
			// move para cima, move para baixo, move para a esquerda, move para
			// a direita, fica parado, fica a dormir
			int move;

			if (dragons.get(i).getType() == 1)
				move = rand.nextInt(4) + 0;
			else
				move = rand.nextInt(5) + 0;

			if (move == 0) // move UP
			{
				if (labirinto.getBoard()[dragons.get(i).getY() - 1][dragons.get(i).getX()] == 'X'
				||	labirinto.getBoard()[dragons.get(i).getY() - 1][dragons.get(i).getX()] == 'C')
					continue;
				else {
					dragons.get(i).moveUP();
					return true;
				}

			} else if (move == 1) // move DOWN
			{
				if (labirinto.getBoard()[dragons.get(i).getY() + 1][dragons.get(i).getX()] == 'X'
				||	labirinto.getBoard()[dragons.get(i).getY() + 1][dragons.get(i).getX()] == 'C')
					continue;
				else {
					dragons.get(i).moveDOWN();
					return true;
				}

			} else if (move == 2) // move LEFT
			{
				if (labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX() - 1] == 'X'
				||	labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX() - 1] == 'C')
					continue;
				else {
					dragons.get(i).moveLEFT();
					return true;
				}
			} else if (move == 3) // move RIGHT
			{
				if (labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX() + 1] == 'X'
				|| 	labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX() + 1] == 'C')
					continue;
				else {
					dragons.get(i).moveRIGHT();
					return true;
				}
			} else if (move == 4 && dragons.get(i).getType() == 2) {
				// numero de jogadas que vai ficar a dormir
				// numero maximo de jogadas que vai ficar a dormir são 5
				int sleep = rand.nextInt(5) + 1;
				dragons.get(i).setSleepTime(sleep); // dragons[dragonPos].setSleepTime(sleep);
				dragons.get(i).setSleep();
				return false;
			} else {
				return false;
			}
		}
		
		return false;
	}

	public static int checkIfFireballInLineOfPlayer(int pos){
		int x_pos = dragons.get(pos).getX();
		int y_pos = dragons.get(pos).getY();

		// pode mudar-se a distancia desde quando ele começa a mandar a fireball
		// mudando o labirinto.getSize() para uma constante que indica o range
		for(int ball_pos = 0; ball_pos < fireballs.size();ball_pos++){
			for (int i = x_pos; i < labirinto.getSize(); i++) // intercecção na	// direita
			{
				if (fireballs.get(ball_pos).getX()== i &&fireballs.get(ball_pos).getY()==y_pos)
					return -1;
			}

			for (int i = y_pos; i < labirinto.getSize(); i++) // intercecção na
																// baixo
			{
				if (fireballs.get(ball_pos).getX()== x_pos &&fireballs.get(ball_pos).getY()==i)
					return -1;
			}

			for (int i = x_pos; i != 0; i--) // intercecção na esquerda
			{
				if (fireballs.get(ball_pos).getX()== i &&fireballs.get(ball_pos).getY()==y_pos)
					return -1;
			}

			for (int i = y_pos; i != 0; i--) // intercecção na cima
			{
				if (fireballs.get(ball_pos).getX()== x_pos &&fireballs.get(ball_pos).getY()==i)
					return -1;
			}
		}
		return 0;
	}
	
	public static int checkIfDragonInLineOfPlayer(int pos) {
		
		if(checkIfFireballInLineOfPlayer(pos)==-1)
			return-1 ;
		int x_pos = dragons.get(pos).getX();
		int y_pos = dragons.get(pos).getY();

		// pode mudar-se a distancia desde quando ele começa a mandar a fireball
		// mudando o labirinto.getSize() para uma constante que indica o range
		for (int i = x_pos; i < labirinto.getSize(); i++) // intercecção na
															// direita
		{
			if (labirinto.getBoard()[y_pos][i] == 'X')
				break;
			if (player.getX() == i && player.getY() == y_pos)// labirinto.getBoard()[y_pos][i]=='H')
				return 2;
		}

		for (int i = y_pos; i < labirinto.getSize(); i++) // intercecção na
															// baixo
		{
			if (labirinto.getBoard()[i][x_pos] == 'X')
				break;
			if (player.getY() == i && player.getX() == x_pos)// labirinto.getBoard()[i][x_pos]=='H')
				return 1;
		}

		for (int i = x_pos; i != 0; i--) // intercecção na esquerda
		{
			if (labirinto.getBoard()[y_pos][i] == 'X')
				break;
			if (player.getX() == i && player.getY() == y_pos)// labirinto.getBoard()[y_pos][i]=='H')
				return 3;
		}

		for (int i = y_pos; i != 0; i--) // intercecção na cima
		{
			if (labirinto.getBoard()[i][x_pos] == 'X')
				break;
			if (player.getY() == i && player.getX() == x_pos)// labirinto.getBoard()[i][x_pos]=='H')
				return 0;
		}
		return -1;
	}

	public static boolean checkFireColisionWithPlayer() {

		for (int i = 0; i < fireballs.size(); i++) {

			if (fireballs.get(i).getX() == player.getX() && fireballs.get(i).getY() == player.getY()|| //se a fireball estiver mesmo em cima do player
					fireballs.get(i).getX()-1 == player.getX() && fireballs.get(i).getY() == player.getY()||//esquerda
					fireballs.get(i).getX()+1 == player.getX() && fireballs.get(i).getY() == player.getY()||//direita
					fireballs.get(i).getX() == player.getX() && fireballs.get(i).getY()-1 == player.getY()||//cima
					fireballs.get(i).getX() == player.getX() && fireballs.get(i).getY()+1 == player.getY())//baixo
				return true;
			
		}
		
		return false;
	}

	public static void moveFireballs() {
		for (int i = 0; i < fireballs.size(); i++) {
			if (!moveFireball(i))
				i--;
		}
	}

	public static boolean moveFireball(int i) {

		labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX()] = ' ';

		if (fireballs.get(i).getType() == 0) // move para cima
		{

			if (fireballs.get(i).getLife() == 0) {
				fireballs.remove(i);
				return false;
			} else if (labirinto.getBoard()[fireballs.get(i).getY() - 1][fireballs.get(i).getX()] == 'X'
				|| 	labirinto.getBoard()[fireballs.get(i).getY() - 1][fireballs.get(i).getX()] == 'O'
				|| 	labirinto.getBoard()[fireballs.get(i).getY() - 1][fireballs.get(i).getX()] == 'C'){
				fireballs.remove(i);
				return false;
			} else
				fireballs.get(i).moveUP();

		} else if (fireballs.get(i).getType() == 1) // move para baixo
		{
			if (fireballs.get(i).getLife() == 0) {
				fireballs.remove(i);
				return false;
			} else if (labirinto.getBoard()[fireballs.get(i).getY() + 1][fireballs.get(i).getX()] == 'X'
					|| labirinto.getBoard()[fireballs.get(i).getY() + 1][fireballs.get(i).getX()] == 'C'
					|| labirinto.getBoard()[fireballs.get(i).getY() + 1][fireballs.get(i).getX()] == 'O') {
				fireballs.remove(i);
				return false;
			} else
				fireballs.get(i).moveDOWN();

		} else if (fireballs.get(i).getType() == 2) // move para a direita
		{
			if (fireballs.get(i).getLife() == 0) {
				fireballs.remove(i);
				return false;
			} else if (labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX() + 1] == 'X'
					|| labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX() + 1] == 'C'
					|| labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX() + 1] == 'O') {
				fireballs.remove(i);
				return false;
			} else
				fireballs.get(i).moveRIGHT();
		} else // move para a esquerda
		{
			if (fireballs.get(i).getLife() == 0) {
				fireballs.remove(i);
				return false;
			} else if (labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX() - 1] == 'X'
					|| labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX() - 1] == 'C'
					|| labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX() - 1] == 'O') {
				fireballs.remove(i);
				return false;
			} else
				fireballs.get(i).moveLEFT();
		}
		return true;
	}

	public static void PrintFireballs() {
		for (int i = 0; i < fireballs.size(); i++) {
			labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX()] = fireballs.get(i).getEstado();
		}
	}
	
	public static boolean hasShield(){
		return escudo;
	}

	public static int Jogar(char move) 
	{
			movePlayer(move);
			GameState.moveFireballs();
			GameState.moveDragons();
			
			//System.out.println("Player(X,Y): " + player.getX() + "," + player.getY());
			//System.out.println("Exit(X,Y): " + labirinto.exit.getX() + "," + labirinto.exit.getY());
			
			if (checkDragonsColision()) 
			{
				return 1; // jogador morre
			}

			if (checkPlayerWin()) 
			{
				return 2; // jogador ganha
			}
			
			if (escudo == false)
				if (checkFireColisionWithPlayer())
					return 3; // jogador morre queimad0


		return 0;
	}
};
