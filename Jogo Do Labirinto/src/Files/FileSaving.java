package Files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSaving {
	
	
	public static void SaveGameState()
	{
		GameStateCopy g = new GameStateCopy();
		try
		{
			FileOutputStream fileOut =
					new FileOutputStream("GameStateSave.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(g);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in GameStateSave.ser");
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	public static boolean LoadGameState()
	{
		GameStateCopy load = null;
		try
		{
			FileInputStream fileIn = new FileInputStream("GameStateSave.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			load = (GameStateCopy) in.readObject();
			in.close();
			fileIn.close();
			load.LoadCopy();
			return true;
		}catch(IOException i)
		{
			System.out.println("ficheiro não encontrado");
			i.printStackTrace();
			return false;
		}catch(ClassNotFoundException c)
		{
			System.out.println("GameStateCopy class not found");
			c.printStackTrace();
			return false;
		}
		
		
	}

}
