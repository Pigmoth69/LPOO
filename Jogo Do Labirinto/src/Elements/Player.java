package Elements;

public class Player extends Element {

	public Player(int x, int y){
		estado = 'H';
		this.x = x;
		this.y = y;
	}
	

	public void setArmado()
	{
		estado = 'A';
	}
	
	public void setDesarmado()
	{
		estado = 'H';
	}
	
	public void moveUP()
	{
		y--;
	}
	
	public void moveDOWN()
	{
		y++;
	}
	
	public void moveLEFT()
	{
		x--;
	}
	
	public void moveRIGHT()
	{
		x++;
	}
}
