/**
 * Element.java - this file is related to the elements
 * @author Daniel Reis
 * @author João Baião
 */
package Elements;

import java.io.Serializable;

public class Element implements Serializable{
	char estado = ' ';
	public int x;
	public int y;

	/**  
	 * return the state of the element
	 * @returns the state of the element
	 */ 
	public char getEstado()
	{
		return estado;
	}

	/**  
	 * return the x coordinate
	 * @returns the x coordinate
	 */ 
	public int getX()
	{
		return x;
	}
	
	/**  
	 * return the y coordinate
	 * @returns the y coordinate
	 */ 
	public int getY()
	{
		return y;
	}
	
	/**  
	 * set the x coordinate
	 * @param x - x coordinate
	 */ 
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**  
	 * set the y coordinate
	 * @param y - y coordinate
	 */ 
	public void setY(int y)
	{
		this.y = y;
	}

}
