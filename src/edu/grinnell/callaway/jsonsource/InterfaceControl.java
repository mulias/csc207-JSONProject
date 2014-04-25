package edu.grinnell.callaway.jsonsource;

import java.io.PrintWriter;

import edu.grinnell.callaway.JSONParser;

//Citation: http://www.mkyong.com/java/how-to-get-url-content-in-java/
//http://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
/**
 * Changes the user input obtained by an InterfaceView into a JSON object 
 * and displays the object as a string.
 */
public class InterfaceControl
{
  /**
   * The InterfaceView used to obtain a JSON string or location.
   */
  InterfaceView view;

  /**
   * A constructor for InterfaceControl.
   * @param view
   *          an InterfaceView
   */
  public InterfaceControl(InterfaceView view)
  {
    this.view = view;
  }//SourceControl(SourceModel model, SourceView view)

  /**
   * Changes the given JSON string into a Java Object representing a 
   * JSON Object and displays the result as a string.
   * @return
   * @throws Exception
   *            if the input is not a JSON string or the location of one
   */
  public Object control()
    throws Exception
  {
    JSONParser parser = new JSONParser();
    PrintWriter pen = new PrintWriter(System.out, true);

    Object result = null;

    InterfaceView inputPackage = new InterfaceView();
    inputPackage.getInputType();
    String inputSignal = inputPackage.parseSignal;
    String packageString = inputPackage.inputString;

    if (inputSignal.equalsIgnoreCase("A"))
      {
        result = parser.parseFromSource(packageString);
        pen.println("The JSON you entered was: ");
      } // if (A)
    else if (inputSignal.equalsIgnoreCase("B"))
      {
        result = parser.parseFromSource(packageString);
        pen.println("The JSON in the file was: ");
      } // else if (B)
    else
      throw new Exception("wrong input");

    pen.println(parser.toJSONString(result));
    return result;
  }//control() 
}//SourceControl
