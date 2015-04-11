/**
 * Dragon.java - this file is related to the dragon's objects
 * @author Daniel Reis
 * @author João Baião
 */
package Elements;


public class Dragon extends Element {
	
	int type; //tipo de dragao
	int numPlaysSleeping; //numero de jogadas que o dragão está a dormir
	
	/**  
	 * create a dragon
	 * @param x - x coordinates
	 * @param y - y coordinates
	 * @param type of dragon
	 */ 
	public Dragon(int x,int y, int type)
	{
		estado = 'D';
		this.x = x;
		this.y = y;
		this.type = type;
		numPlaysSleeping = 0;
	}
	
	/**  
	 * set the type of the dragon
	 * @param type pretended
	 */ 
	public void setType(int type)
	{
		this.type=type;
	}
	
	/**  
	 * set the state of the dragon as dead
	 */ 
	public void setDead(){
		estado = ' ';
	}
	
	/**  
	 * set the state of the dragon as 'D'
	 */ 
	public void setNormalDragon()
	{
		estado = 'D';
	}
	
	/**  
	 * set the state of the dragon as 'F'
	 */ 
	public void setSwordAndDragon()
	{
		estado = 'F';
	}
	
	/**  
	 * set the state of the dragon as 's'
	 */ 
	public void setSleep()
	{
		estado = 's';
	}
	
	/**  
	 * set the time of dragon sleeping
	 * @param numSleep - number of turns sleeping
	 */ 
	public void setSleepTime(int numSleep)
	{
		numPlaysSleeping = numSleep;
	}
	
	/**  
	 * returns the number of turns it will be sleeping
	 * @returns the number of turns it will be sleeping
	 */ 
	public int getNumPlaysSleeping()
	{
		return numPlaysSleeping;
	}
	
	/**  
	 * sub 1 turn sleeping
	 */ 
	public void subbPlaySleeping()
	{
		numPlaysSleeping--;
	}
	
	/**  
	 * return the type of dragon
	 * @returns the type of dragon
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
