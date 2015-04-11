/**
 * Dart.java - this file is related to the dart's objects
 * @author Daniel Reis
 * @author João Baião
 */
package Elements;

public class Fireball extends Element{
	private int life; //vida da bola de fogo
	private int type; //tipo de fireball Horizontal, Vertical,com incremento/decremento no x ou y

	/**  
	 * create a fireball
	 * @param type of the fireball
	 * @param x - x coordinates
	 * @param y - y coordinates
	 */ 
	public Fireball(int type, int x,int y)
	{
		this.life = 2;
		this.estado= '*';
		this.type=type;
		this.x = x;
		this.y = y;
	}
	
	/**  
	 * returns the current life of the fireball
	 * @returns the current life of the fireball
	 */ 
	public int getLife()
	{
		return life;
	}
	
	/**  
	 * returns the type of the fireball
	 * @returns the type of the fireball
	 */ 
	public int getType()
	{
		return type;
	}
	
	/**  
	 * move up
	 */ 
	public void moveUP()
	{
		y--;
		life--;
	}
	
	/**  
	 * move down
	 */ 
	public void moveDOWN()
	{
		y++;
		life--;
	}
	
	/**  
	 * move left
	 */ 
	public void moveLEFT()
	{
		x--;
		life--;
	}
	
	/**  
	 * move right
	 */ 
	public void moveRIGHT()
	{
		x++;
		life--;
	}
}
