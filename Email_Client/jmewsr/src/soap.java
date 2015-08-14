import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;


public class soap
{
	private String url = null;
	private String response = "";
	private boolean printResponse = false;
			
	public soap ( String url )
	{
		setUrl(url);
	}
	
	private void setUrl ( String url )
	{
		this.url = url;
	}
	
	private String getUrl ( )
	{
		return this.url;
	}
	
	public void setPrintResponse ( boolean value )
	{
		this.printResponse = value;
	}
	
	private boolean getPrintResponseValue ( )
	{
		return this.printResponse;
	}
	
	public String getResponse ( )
	{
		return this.response;
	}
	
	public void sendRequest ( soapMessage sm )
	{
		String lvSoapMessage = sm.getSoapMessage();
        
        //Create connection
        String URLString = getUrl();
        URL URLForSOAP = null;
		try {
			URLForSOAP = new URL(URLString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        URLConnection URLConnectionForSOAP = null;
		try {
			URLConnectionForSOAP = URLForSOAP.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                
        HttpsURLConnection Connection = (HttpsURLConnection) URLConnectionForSOAP;
        //Adjust connection
        Connection.setDoOutput(true);
        Connection.setDoInput(true);
        try {
			Connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //missing host name here
        Connection.setRequestProperty("Host", "");
        Connection.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
        Connection.setRequestProperty("Connection","Keep-Alive");
        //Send the request
        OutputStreamWriter soapRequestWriter = null;
		try {
			soapRequestWriter = new OutputStreamWriter(Connection.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			soapRequestWriter.write(lvSoapMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(lvSoapMessage);
        try {
			soapRequestWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Read the reply
        BufferedReader soapRequestReader = null;
		try {
			soapRequestReader = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String line;
        try {
			while ((line = soapRequestReader.readLine()) != null) {
			    if ( getPrintResponseValue() ) {
			    	System.out.println(line);
			    }
			    this.response = this.response.concat(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			soapRequestWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			soapRequestReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Connection.disconnect();
	}
}
