/**
 * Exit.java - this file is related to the exit object
 * @author Daniel Reis
 * @author João Baião
 */
package Elements;

public class Exit extends Element {
	
	/**  
	 * create an exit
	 * @param x - x coordinates
	 * @param y - y coordinates
	 */ 
	public Exit(int x, int y){
		estado = 'C';
		this.x = x;
		this.y = y;
	}
	
	/**  
	 * set the state of an exit as open ('O')
	 */ 
	public void setOpen()
	{
		estado = 'O';
	}
}
