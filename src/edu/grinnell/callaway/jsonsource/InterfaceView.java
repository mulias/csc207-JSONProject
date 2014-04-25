package edu.grinnell.callaway.jsonsource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


/**
 * Displays messages for the user and reads user input to be used 
 * in the controller.
 */
public class InterfaceView
{
  /**
   * Variable to used to store the given JSON string or the 
   * location of one.
   */
  String inputString;

  /**
   * Stores the type of the information given to inputString 
   * (a JSON string or location).
   */
  String parseSignal;

  /**
   * Constructs an InterfaceView
   */
  public InterfaceView()
  {
    this.inputString = null;
    this.parseSignal = null;
  } // InterfaceView(input, parse)

  /**
   * Determines whether the user wishes to enter a JSONString or the 
   * location of a file containing a JSON string and stores either 
   * the desired string or location into a variable.
   * @return InterfaceView
   * @throws IOException
   */
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
      } // try
    catch (IOException e)
      {
        throw new IOException("IOException: " + e);
      } // catch (e)

    pen.println("Please enter JSONString or the JSONString file location(URL or local file)");

    in = new BufferedReader(new InputStreamReader(System.in));
    this.inputString = in.readLine();

    in.close();
    return this;
  }//String getURL()
}//SourceView
