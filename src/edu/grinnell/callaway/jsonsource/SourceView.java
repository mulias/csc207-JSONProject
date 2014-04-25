package edu.grinnell.callaway.jsonsource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SourceView
{
PrintWriter pen = new PrintWriter(System.out, true);

  public String getJSONSource()
    throws IOException

  {
    String jsonSource;
    BufferedReader in;
    
    pen.println("URL example: http://yourDomain.com/file.json");
    pen.println("file location example: /home/user/Documents/file.json");
    pen.println("Please enter URL or file location:");
    in = new BufferedReader(new InputStreamReader(System.in));

    try
      {
        jsonSource = in.readLine();
      }
    catch (IOException e)
      {
        throw new IOException("IOException: " + e);
      }
    in.close();
    return jsonSource;
  }//String getURL()

  /*
   * Signals that the content of the URL file has been sent the the Parser
   */
  public void finishSignal()
  {
    pen.println("Done");
  }//finishSignal()
}//SourceView
