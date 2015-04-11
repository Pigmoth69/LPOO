/**
 * Shield.java - this file is related to the shield object
 * @author Daniel Reis
 * @author João Baião
 */
package Elements;

public class Shield extends Element{

	/**  
	 * create a shield
	 * @param x - x coordinates
	 * @param y - y coordinates
	 */ 
	public Shield(int x, int y){
		estado = '0';
		this.x = x;
		this.y = y;
	}
	
	/**
	 * remove the shield from the board
	 */
	public void removeShield()
	{
		estado = ' ';
	}
}
