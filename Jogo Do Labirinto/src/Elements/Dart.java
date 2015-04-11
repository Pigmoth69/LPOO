/**
 * Dart.java - this file is related to the dart's objects
 * @author Daniel Reis
 * @author João Baião
 */
package Elements;

public class Dart extends Element{

	/**  
	 * create a dart
	 * @param x - x coordinates
	 * @param y - y coordinates
	 */ 
	public Dart(int x, int y){
		estado = 'd';
		this.x = x;
		this.y = y;
	}
	
	/**
	 * remove the dart from the board
	 */
	public void removeDart()
	{
		estado = ' ';
	}
}
