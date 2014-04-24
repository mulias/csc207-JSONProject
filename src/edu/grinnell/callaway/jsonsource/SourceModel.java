package edu.grinnell.callaway.jsonsource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * CITATION:http://stackoverflow.com/questions/15842239/how-to-cast-a-string-to-an-url-in-java
 *              http://www.mkyong.com/java/how-to-get-url-content-in-java/
 * @author Shaun, Mataire
 */
public class SourceModel
{
  String jsonOut;

  public String handleURL(String jsonURL) throws IOException 
  {
    String fileLine;
    //get URL
    try
      {
        //open connection
        URL url = new URL(jsonURL);
        URLConnection connect = url.openConnection();
        BufferedReader in =
            new BufferedReader(new InputStreamReader(connect.getInputStream()));
        while ((fileLine = in.readLine()) != null)
          {
            jsonOut = jsonOut + fileLine;
          }

      }
    catch (MalformedURLException e)
      {
       throw new MalformedURLException("MalformedURLException: " + e);
      }
    return jsonOut;
  }//handleURL( String jsonURL)
}//SourceModel
