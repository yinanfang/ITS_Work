package vega.fileIO;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import vega.listers.genericList;


public class filereader
{	
	
	public filereader ( )
	{
		
	}
	
	public HashMap<String, String> getLineMap ( String filename )
	{
		HashMap<String, String> strLineMap = new HashMap<String, String>();
		
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;

			while ( ( strLine = br.readLine() ) != null ) {
				if ( !strLine.startsWith("#") ) {
					if ( !strLine.isEmpty() ) {
						strLine.replaceAll(" ", "");
						String key = strLine.substring(0, strLine.indexOf("="));
						String value = strLine.substring(strLine.indexOf("=")+1, strLine.length());
						strLineMap.put(key, value);
					}
				}
			}
			in.close();
		} catch ( Exception e ) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return strLineMap;
	}
	
	public genericList getLineList ( String filename )
	{
		genericList gl = new genericList();
		
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			while ( ( strLine = br.readLine() ) != null ) {
				if ( !strLine.startsWith("#") ) {
					if ( !strLine.isEmpty() ) {
						strLine.replaceAll(" ", "");
						String value = strLine; 
						gl.addEntry(value);
					}
				}
			}
			in.close();
		} catch ( Exception e ) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return gl;
	}
	
	public String getMatchingLine ( String filename, String key )
	{
		String line = null;
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			while ( ( strLine = br.readLine() ) != null ) {
				if ( strLine.startsWith(key) ) {
					line = strLine;
					break;
				}
			}
			in.close();
		} catch ( Exception e ) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return line;
	}
	
	public String getConcatSubsequentLines ( String filename, String beginKey, String endKey, String excludeKey )
	{
		String line = null;
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			boolean keyOverride = false;
			while ( ( strLine = br.readLine() ) != null ) {
				if ( strLine.contains(endKey) ) {
					break;
				}
				if ( strLine.startsWith(beginKey) || keyOverride == true ) {
					if ( !strLine.contains(beginKey) && !strLine.contains(excludeKey) ) { // Do not include the "From:" line
						line = line+strLine;
					}
					keyOverride = true;
				}
			}
			in.close();
		} catch ( Exception e ) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return line;
	}
	
	public String getConcatSubsequentLinesMultiExclude ( String filename, String beginKey, String endKey, String excludeKey1, String excludeKey2, String excludeKey3, String excludeKey4 )
	{
		String line = null;
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			boolean keyOverride = false;
			while ( ( strLine = br.readLine() ) != null ) {
				if ( strLine.contains(endKey) ) {
					break;
				}
				if ( strLine.startsWith(beginKey) || keyOverride == true ) {
					if ( !strLine.contains(excludeKey1) && !strLine.contains(excludeKey2) && !strLine.contains(excludeKey3) && !strLine.contains(excludeKey4) ) { // Include To line, but not the endkey
						line = line+strLine;
					}
					keyOverride = true;
				}
			}
			in.close();
		} catch ( Exception e ) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return line;
	}
	
	public String getLineBetweenKeys ( String filename, String beginKey, String endKey )
	{
		String line = null;
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			boolean saveLine = false;
			while ( ( strLine = br.readLine() ) != null ) {
				if ( saveLine == true ) {
					if ( strLine.contains(endKey) ) {
						line = line+strLine;
						break;
					}
					/*if ( strLine.contains(endKey) ) {
						break;
					}*/
				}
				if ( strLine.contains(beginKey) ) {
					saveLine = true;
				}
			}
			in.close();
		} catch ( Exception e ) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return line;
	}
	
	public String getTargetLineBetweenKeys ( String filename, String beginKey, String endKey )
	{
		String line = null;
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			boolean saveLine = false;
			while ( ( strLine = br.readLine() ) != null ) {
				if ( strLine.contains(endKey) && saveLine == true) {
					line = strLine;
					saveLine = false;
					break;
				}
				
				if ( strLine.contains(beginKey)) {
					saveLine = true;
				}
			}
			in.close();
		} catch ( Exception e ) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return line;
	}
}

