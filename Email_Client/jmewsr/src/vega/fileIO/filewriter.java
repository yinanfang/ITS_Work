package vega.fileIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class filewriter
{
	private BufferedWriter out = null;
	private FileWriter fstream = null;
	
	public filewriter ( String filename )
	{
		
		try {
			fstream = new FileWriter(filename, true);
			out = new BufferedWriter(fstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void write ( String line )
	{
		try{
			// Create file 
			out.write("\n"+line);
			//Close the output stream
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void close ( )
	{
		try {
			out.close();
			fstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
