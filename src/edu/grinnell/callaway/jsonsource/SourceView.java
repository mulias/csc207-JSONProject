package edu.grinnell.callaway.jsonsource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

public class SourceView
{
  Reader JSONString;
  String parseSignal;

  public SourceView(Reader JSONString, String parseSignal)
  {
    this.JSONString = JSONString;
    this.parseSignal = parseSignal;
  }

  public SourceView getInputType()
    throws IOException
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    SourceView JSONpackage = new SourceView(JSONString, parseSignal);
    BufferedReader in;
    pen.println("Please choose parse option");
    pen.println("Parse options: (A) JSONString (B) File location");

    in = new BufferedReader(new InputStreamReader(System.in));
    try
      {
        JSONpackage.parseSignal = in.readLine();
      }
    catch (IOException e)
      {
        throw new IOException("IOException: " + e);
      }
    pen.println("Please enter JSONString or the JSONString file location(URL or local file");

    JSONpackage.JSONString = new BufferedReader(new InputStreamReader(System.in));
    
    in.close();
    return JSONpackage;
  }//String getURL()
}//SourceView
