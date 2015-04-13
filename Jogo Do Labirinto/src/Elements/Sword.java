/**
 * Sword.java - this file is related to the sword object
 * @author Daniel Reis
 * @author João Baião
 */
package Elements;

import java.io.Serializable;

public class Sword extends Element{

	/**  
	 * create a sword
	 * @param x - x coordinates
	 * @param y - y coordinates
	 */ 
	public Sword(int x, int y){
		estado = 'E';
		this.x = x;
		this.y = y;
	}
	
	/**  
	 * remove the sword from the board
	 */ 
	public void removeSword()
	{
		estado = ' ';
	}
	
	/**  
	 * set the state of the sword as 'F'
	 */ 
	public void swordDragon()
	{
		estado = 'F';
	}
	
	/**  
	 * set the state of the sword as 'E'
	 */ 
	public void swordArmada()
	{
		estado = 'E';
	}	
	
}
