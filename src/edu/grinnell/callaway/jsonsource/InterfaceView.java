package edu.grinnell.callaway.jsonsource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class InterfaceView
{
  String inputString;
  String parseSignal;

  public InterfaceView(String inputString, String parseSignal)
  {
    this.inputString = inputString;
    this.parseSignal = parseSignal;
  }

  public InterfaceView getInputType()
    throws IOException
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader in;
    pen.println("Please choose parse option");
    pen.println("Parse options: (A) JSONString (B) File location");

    in = new BufferedReader(new InputStreamReader(System.in));
    try
      {
        this.parseSignal = in.readLine();
      }
    catch (IOException e)
      {
        throw new IOException("IOException: " + e);
      }
    pen.println("Please enter JSONString or the JSONString file location(URL or local file");

     in = new BufferedReader(new InputStreamReader(System.in));
    this.inputString =in.readLine();
    in.close();
    return this;
  }//String getURL()
}//SourceView
