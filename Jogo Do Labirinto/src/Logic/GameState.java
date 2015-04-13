/**
 * GameState.java - this file is related to the logic of the program.
 * @author Daniel Reis
 * @author João Baião
 */
package Logic;

import java.io.Serializable;
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

	/**  
	 * Base function that calls other functions to generate "Elements" 
	 */ 
	public static void runGame() {
		saida = labirinto.exit;
		sword = labirinto.sword;
		GeneratePlayer();
		GenerateDragons();
		GenerateDarts();
		GenerateShield();
	}

	/**  
	 * Calls the maze constructor as either a "static maze" or a "random maze"
	 * @param mazeType (0 for Static and 1 for Random)
	 * @see Interface package 
	 */ 
	public static void SetMaze(int mazeType) {
		if (mazeType == 0)
			labirinto = new StaticMaze();
		else if (mazeType == 1)
			labirinto = new RandomMaze();
	}
	
	/**  
	 * Create a random maze o a certain size
	 * @param size - size of the maze
	 */ 
	public static void SetRandomMaze(int size){
		labirinto = new RandomMaze(size);
	}

	/**  
	 * returns the number of darts available to pickup
	 * @return the number of darts available to pickup
	 */ 
	public static int getNumDardos() {
		return dardos.size();
	}

	/**  
	 * Reset the fireballs arrayList
	 */ 
	public static void restartFireballs(){
		fireballs = new ArrayList<Fireball>();
	}
	
	/**  
	 * Reset the darts arrayList 
	 */ 
	public static void restartDarts(){
		dardos = new ArrayList<Dart>();
	}
	
	/**  
	 * Reset the dragons arrayList  
	 */ 
	public static void restartDragons(){
		dragons = new ArrayList<Dragon>();
	}
	
	/**  
	 * returns the object corresponding to the shield
	 * @return the shield
	 */ 
	public static Shield getShield(){
		return shield;
	}
	
	/**  
	 * returns the arrayList with all darts available to pickup on the board
	 * @return the arrayList with all darts available to pickup on the board
	 */ 
	public static ArrayList<Dart> getDarts(){
		return dardos;
	}

	/**  
	 * returns the arrayList with all dragons alive on the board
	 * @return returns the arrayList with all dragons alive on the board
	 */ 
	public static ArrayList<Dragon> getDragons(){
		return dragons;
	}
	
	/**  
	 * Set the type of dragons
	 * @param the type of dragons (0 -frozen, 1-moving, 2-moving or sleeping) 
	 */ 
	public static void setDragonsType(int type) {
		dragonsType = type;
	}
	
	/**  
	 * Changes the type of all dragons alive 
	 */ 
	public static void setAllDragonsType(){
		for (int i = 0; i < dragons.size(); i++){
			dragons.get(i).setType(dragonsType);
		}
	}

	/**  
	 * Randomly generate dragons on the board 
	 */ 
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

	/**  
	 * check if the player is adjacent to a certain place
	 * @param x_pos - the x coordinate
	 * @param y_pos - the y coordinate
	 * @return false if the player is not adjacent and true otherwise
	 */ 
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

	/**  
	 * set the ammount of dragons wanted
	 * @param size - ammount of dragons 
	 */ 
	public static void setDragonsSize(int size) {
		dragonsSize = size;
	}

	/**  
	 * add a dragon to a certain location
	 * @param x - x coordinate desired
	 * @param y - y coordinate desired
	 */ 
	public static void AddDragons(int x, int y) {
		dragons.add(new Dragon(x, y, dragonsType));
	}

	/**  
	 * returns the object relative to the player
	 * @return the object relative to the player
	 */ 
	public static Player getPlayer() {
		return player;
	}

	/**  
	 * returns the ammount of dragons defined
	 * @return the ammount of dragons defined 
	 */ 
	public static int getDragonSize() {
		return dragonsSize;
	}

	/**  
	 * Generates a random available position in the board for the player
	 */ 
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

	/**  
	 * places the player in a certain position in the board
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */ 
	public static void NewPlayer(int x, int y) {
		player = new Player(x, y);
	}

	/**  
	 * return the maze object
	 * @return the maze object 
	 */ 
	public static Maze getMaze() {
		return labirinto;
	}

	/**  
	 * generates available places to hold a random ammount of darts  
	 */ 
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

	/**  
	 * add a dart to the dart list
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */ 
	public static void AddDart(int x, int y) {
		dardos.add(new Dart(x, y));
	}
	
	/**  
	 * add a certain ammount of darts to the player pocket
	 * @param ammount of darts to add
	 * @return the total ammount of darts of the player  
	 */ 
	public static int AddDartsToPlayer(int ammount){
		dardosJogador+= ammount;
		return dardosJogador;
	}

	/**  
	 * generates a random available place for the shield 
	 */ 
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

	/**  
	 * set the coordinates of the shield
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */ 
	public static void SetShield(int x, int y) {
		shield = new Shield(x, y);
	}

	/**  
	 *  remove the shield from the board
	 */ 
	public static void RemoveShieldFromBoard(){
		shield.removeShield();
	}

	/**  
	 * give the player a shield 
	 */ 
	public static void activateEscudo() {
		escudo = true;
	}

	/**  
	 * check if the player can move. if he can, it does move him as well
	 * @param move - the char of the direction ('w', 'a', 's', 'd')
	 */ 
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

	/**  
	 * check if a player collided with a dragon 
	 * @return true if the player died or false otherwise  
	 */ 
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

	/**  
	 * check if the player is in the exit place
	 * @return true if the player won, false otherwise
	 */ 
	public static boolean checkPlayerWin() {

		if (player.getX() == labirinto.exit.getX()&& player.getY() == labirinto.exit.getY())
			return true;
		else
			return false;
	}

	/**  
	 * refresh the position and state of all objects 
	 */ 
	public static void RefreshElements() {
		PrintExit();
		PrintShield();
		PrintSword();
		PrintDarts();
		PrintDragons();
		PrintFireballs();
		PrintPlayer();
	}
	
	/**  
	 * print the board to the console 
	 */ 
	public static void PrintLab(){
		labirinto.PrintLab();
	}
	
	/**  
	 * change the board char relative to the shield 
	 */ 
	private static void PrintShield() {
		labirinto.getBoard()[shield.getY()][shield.getX()] = shield.getEstado();
	}

	/**  
	 * return the ammount of darts that the player has
	 * @return ammount of darts available 
	 */ 
	public static int getDardosJogador(){
		return dardosJogador;
	}
	
	/**  
	 * return the arraylist of the fireballs
	 * @return the arraylist of the fireballs   
	 */ 
	public static ArrayList<Fireball> getFireballs(){
		return fireballs;
	}

	/**  
	 * change the board char relative to the player 
	 */ 
	public static void PrintPlayer() {
		labirinto.getBoard()[player.getY()][player.getX()] = player.getEstado();
	}

	/**  
	 * change the board char relative to the sword  
	 */ 
	public static void PrintSword() {
		labirinto.getBoard()[labirinto.sword.getY()][labirinto.sword.getX()] = labirinto.sword.getEstado();
	}

	/**  
	 * change the board char relative to the exit 
	 */ 
	public static void PrintExit() {
		if (dragonsSize == 0)
			labirinto.exit.setOpen();
		labirinto.getBoard()[labirinto.exit.getY()][labirinto.exit.getX()] = labirinto.exit.getEstado();
	}

	/**  
	 * change all board chars relative to all dragons
	 */ 
	public static void PrintDragons() {
		for (int i = 0; i < dragonsSize; i++)
			labirinto.getBoard()[dragons.get(i).getY()][dragons.get(i).getX()] = dragons.get(i).getEstado();
	}

	/**  
	 * change all board chars relative to all darts
	 */ 
	public static void PrintDarts() {
		for (int i = 0; i < dardos.size(); i++)
			labirinto.getBoard()[dardos.get(i).getY()][dardos.get(i).getX()] = dardos.get(i).getEstado();
	}

	/**  
	 * shoot a dart in a certain direction
	 * @param direction to shoot
	 * @return true if kills, false otherwise
	 */ 
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

	/**  
	 * check if the dragons are sleeping, if not, they move 
	 */ 
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

	/**  
	 * move a dragon in a certain position of the dragon list
	 * @param i - index in "dragons" ArrayList
	 * @return true if he successfully moved, false otherwise
	 */ 
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

	/**  
	 * checks if a fireball is in line of the player
	 * @param pos- position of the dragon in dragons ArrayLIst
	 * @return -1 if it is in line, 0 otherwise
	 */ 
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
	
	/**  
	 * checks if a dragon is in line of the player
	 * @param pos- position of the dragon in dragons ArrayLIst
	 * @return -1 if it is in line, 0 otherwise   
	 */ 
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

	/**  
	 * checks if a fireball collided with the player
	 * @return true if it did, false otherwise 
	 */ 
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

	/**  
	 * move all fireballs
	 */ 
	public static void moveFireballs() {
		for (int i = 0; i < fireballs.size(); i++) {
			if (!moveFireball(i))
				i--;
		}
	}

	/**  
	 * move a certain fireball
	 * @param i - pos of a fireball in the fireball ArrayList
	 * @return true if it did move, false if it extinguished 
	 */ 
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

	/**  
	 * change all board chars relative to all fireballs  
	 */ 
	public static void PrintFireballs() {
		for (int i = 0; i < fireballs.size(); i++) {
			labirinto.getBoard()[fireballs.get(i).getY()][fireballs.get(i).getX()] = fireballs.get(i).getEstado();
		}
	}
	
	/**  
	 * check if the player has a shield
	 * @return a boolean that refers to the possession the shield or not
	 */ 
	public static boolean hasShield(){
		return escudo;
	}

	/**  
	 * move the player, all fireballs and dragons and check some possible outcomes
	 * @param move - the direction for the player to move
	 * @return an int refering to a possible outcome (1 - player dies, 2 - player win, 3- player is killed by fire, 0 - none of the others) 
	 */ 
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

	public static Exit getExit() {
		return saida;
	}

	
	public static int getDragonsType() {
		return dragonsType;
	}

	public static Sword getSword() {
		return sword;
	}

	
	public static void setMaze(Maze labirinto2) {
		labirinto = labirinto2;		
	}


	public static void setSword(Sword sword2) {
		sword = sword2;
	}


	public static void setExit(Exit saida2) {
		saida = saida2;
	}

	
	public static void setDragonsArray(ArrayList<Dragon> dragons2) {
		dragons = dragons2;
	}


	public static void setFireballs(ArrayList<Fireball> fireballs2) {
		fireballs = fireballs2;
	}

	
	public static void setPlayer(Player player2) {
		player = player2;
	}


	public static void setDarts(ArrayList<Dart> dardos2) {
		dardos = dardos2;
	}


	public static void SetShield(Shield shield2) {
		shield = shield2;
	}

	public static void SetShieldBol(boolean escudo2) {
		escudo = escudo2;
	}

	
};
