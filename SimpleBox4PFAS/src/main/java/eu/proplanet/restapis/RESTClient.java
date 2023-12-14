package eu.proplanet.restapis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.zkoss.util.media.AMedia;

public class RESTClient {
	
	public static AMedia getTutorial(String tutorial) throws Exception
	{
		try {
	
			URL url = new URL("http://enaloscloud.novamechanics.com/novamechanicssystem/apis/tutorial/" + tutorial);
			//URL url = new URL("http://localhost:8080/licensingapp/apis/tutorial/" + tutorial);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", MediaType.APPLICATION_OCTET_STREAM);
	
			if (conn.getResponseCode() != 200) {
				throw new Exception(conn.getResponseMessage());
//				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			byte[] bytes = IOUtils.toByteArray(conn.getInputStream());

			AMedia med = new AMedia("Tutorial", "pdf", "application/pdf", bytes	);//(new InputStreamReader(()));
	
			conn.disconnect();
			
			return med;
	
		  } catch (MalformedURLException e) {
	
			e.printStackTrace();
	
		  } catch (IOException e) {
	
			e.printStackTrace();
			
		  }
		return null;
	}

}
