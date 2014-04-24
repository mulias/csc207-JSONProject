package edu.grinnell.callaway.jsonsource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SourceView
{
  PrintWriter pen = new PrintWriter(System.out, true);
  String jsonURL;
  String jsonFile;



  /*
   * Gets the JSON file URL from user
   * @pre
   * @post
   */
  public String getURL()
    throws IOException

  {
    BufferedReader in;

    pen.println("URL example: http://yourDomain.com/file.json");
    pen.println("Please enter URL:");
    in = new BufferedReader(new InputStreamReader(System.in));

    try
      {
        jsonURL = in.readLine();
      }
    catch (IOException e)
      {
        throw new IOException("IOException: " + e);
      }
    in.close();
    return jsonURL;
  }//String getURL()

  /*
   * Signals that the content of the URL file has been sent the the Parser
   */
  public void finishSignal()
  {
    pen.println("Done");
  }//finishSignal()
}//SourceView
