package webIntegration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class variableSender {
	
	public static void sendVariable(String var1, String var2) {
    	try {
        URL url = new URL("http://217.122.119.120/walksimulator/signup.php?uid="+var1+"&dist="+var2);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	System.exit(0);
	}
}