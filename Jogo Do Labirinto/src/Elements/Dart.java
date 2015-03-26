package Elements;

public class Dart extends Element{

	public Dart(int x, int y){
		estado = 'd';
		this.x = x;
		this.y = y;
	}
	
	public void removeDart()
	{
		estado = ' ';
	}
}
