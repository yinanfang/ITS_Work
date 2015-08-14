package vega.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import vega.config.configuration;
import vega.fileIO.filewriter;

public class logger
{
	private configuration conf = new configuration();
	
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
	private String logLevel = conf.getLogLevel();
	private String logfile = conf.getLogFile();
	public logger ( )
	{
		
	}
	
	public void log ( String level, String line )
	{
		// A switch statement cannot be used on a String data type in versions of java below 1.7
		
		if ( logLevel.equals("ALL") ) {
			String dateTime = getDateTime();
			filewriter fw = getFileWriter();
			fw.write(level+" -- "+dateTime+" -- "+line);
			fw.close();
		} else if ( logLevel.equals("INFO") ) {
			if ( level.equals(logLevel) ) {
				String dateTime = getDateTime();
				filewriter fw = getFileWriter();
				fw.write(level+" -- "+dateTime+" -- "+line);
				fw.close();
			}
		} else if ( logLevel.equals("ERROR") ) {
			if ( level.equals(logLevel) ) {
				String dateTime = getDateTime();
				filewriter fw = getFileWriter();
				fw.write(level+" -- "+dateTime+" -- "+line);
				fw.close();
			}
		} else if ( logLevel.equals("DEBUG") ) {
			if ( level.equals(logLevel) ) {
				String dateTime = getDateTime();
				filewriter fw = getFileWriter();
				fw.write(level+" -- "+dateTime+" -- "+line);
				fw.close();
			}
		} else {
			// Log nothing
		}
	}
	
	private String getDateTime ( )
	{
		Calendar cal = Calendar.getInstance();
		String dateTime = dateFormat.format(cal.getTime());
		
		return dateTime;
	}
	
	private filewriter getFileWriter ( )
	{
		filewriter fw = new filewriter(this.logfile);
		return fw;
	}
}
