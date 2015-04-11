/**
 * Player.java - this file is related to the player object
 * @author Daniel Reis
 * @author João Baião
 */
package Elements;

public class Player extends Element {

	/**  
	 * create a player
	 * @param x - x coordinates
	 * @param y - y coordinates
	 */ 
	public Player(int x, int y){
		estado = 'H';
		this.x = x;
		this.y = y;
	}
	
	/**  
	 * set the state of the player as armed
	 */ 
	public void setArmado()
	{
		estado = 'A';
	}
	
	/**  
	 * set the state of the player as unarmed
	 */ 
	public void setDesarmado()
	{
		estado = 'H';
	}
	
	/**  
	 * move up
	 */ 
	public void moveUP()
	{
		y--;
	}
	
	/**  
	 * move down
	 */ 
	public void moveDOWN()
	{
		y++;
	}
	
	/**  
	 * move left
	 */ 
	public void moveLEFT()
	{
		x--;
	}
	
	/**  
	 * move right
	 */ 
	public void moveRIGHT()
	{
		x++;
	}
}
