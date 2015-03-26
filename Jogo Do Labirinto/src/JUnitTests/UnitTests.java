package JUnitTests;
import org.junit.Test;

import Logic.GameState;


public class UnitTests {
	
	public void GenerateTestField(){
		GameState g = new GameState();
		g.SetMaze(0); //Já gera saida e espada
		g.setDragonsSize(1);
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
}
