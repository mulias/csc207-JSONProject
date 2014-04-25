package edu.grinnell.callaway.jsonsource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import edu.grinnell.callaway.JSONParser;

/*
 * CITATION:http://stackoverflow.com/questions/15842239/how-to-cast-a-string-to-an-url-in-java
 *              http://www.mkyong.com/java/how-to-get-url-content-in-java/
 *              http://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
 * @author Shaun, Mataire
 */
public class SourceModel
{
  String jsonOut;

  public String handleJSONSource(String jsonSource)
    throws Exception
  {
    String fileLine;

    //URL start with an "h", as in "http(s)..."
    //file locations start with a "/", "/home/..."
    char firstChar = jsonSource.charAt(0);
    if (firstChar == 'h')
      {
        //get URL
        try
          {
            //open connection
            URL url = new URL(jsonSource);
            URLConnection connect = url.openConnection();
            BufferedReader in =
                new BufferedReader(
                                   new InputStreamReader(
                                                         connect.getInputStream()));
            while ((fileLine = in.readLine()) != null)
              {
                jsonOut = jsonOut + fileLine;
              }//while
            
            URL url = new URL(jsonSource);
            URLConnection connect = url.openConnection();
            JSONParser parser = new JSONParser();
            return parser.parseFromSource(new InputStreamReader(connect.getInputStream()));
            
            
            
            
            
            in.close();
          }//try
        catch (MalformedURLException e)
          {
            throw new MalformedURLException("MalformedURLException: " + e);
          }//catch
          
        return jsonOut;
      }
    else if (firstChar == 'h')
      {
        BufferedReader in = new BufferedReader(new FileReader(jsonSource));
        while ((fileLine = in.readLine()) != null)
          {
            jsonOut = jsonOut + fileLine;
          }//while
        in.close();
        return jsonOut;
      }
    else
      {
        throw new Exception(
                            "Entered string is neither a URL nor a file location");
      }
  }//handleURL( String jsonURL)
}//SourceModel
