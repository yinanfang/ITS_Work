package vega.controllers;

public class sanity
{
	public static String configFile = "";
	
	public sanity ( )
	{
		
	}
	
	public void check ( String[] args )
    {
    	if ( args.length > 0) {
    		if (args[0].equalsIgnoreCase("-h") ) {
        		System.out.println("You must run with -c [configurationFile]. Please note there is a space after the \"-c\".");
        		System.out.println("-c\t\t\tspecify a configuration file");
        		System.exit(-1);
        	} else {
        		setArgs(args);
        	}
    	} else {
    		System.out.println("Run this application with the \"-h\" flag for help");
    		System.exit(-1);
    	}
    }
	
	public void setArgs ( String args[] )
	{
		if ( args.length > 0 ) {
			for ( int i = 0; i < args.length; i++ ) {
				if ( args[i].equalsIgnoreCase("-c") ) {
					configFile = args[i+1];
				}
			}
		}
	}
}
