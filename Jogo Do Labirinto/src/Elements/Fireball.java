package Elements;

public class Fireball extends Element{
	private int life; //vida da bola de fogo
	private int type; //tipo de fireball Horizontal, Vertical,com incremento/decremento no x ou y

	
	public Fireball(int type, int x,int y)
	{
		this.life = 2;
		this.estado= '*';
		this.type=type;
		this.x = x;
		this.y = y;
	}
	
	
	public int getLife()
	{
		return life;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void moveUP()
	{
		y--;
		life--;
	}
	
	public void moveDOWN()
	{
		y++;
		life--;
	}
	
	public void moveLEFT()
	{
		x--;
		life--;
	}
	
	public void moveRIGHT()
	{
		x++;
		life--;
	}
}
