package edu.grinnell.callaway.jsonsource;

import edu.grinnell.callaway.JSONParser;

/*
 * Citation: http://www.mkyong.com/java/how-to-get-url-content-in-java/
 * http://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
 */
public class InterfaceControl
{

  InterfaceView view;

  public InterfaceControl(InterfaceView view)
  {
    this.view = view;
  }//SourceControl(SourceModel model, SourceView view)

  public Object control()
    throws Exception
  {
    JSONParser parser = new JSONParser();
    
    Object result = null;
    String read = null;
    String string = null;
    
    InterfaceView inputPackage = new InterfaceView(read, string);
    inputPackage.getInputType();
    String inputSignal = inputPackage.parseSignal;
    String packageString = inputPackage.inputString;
    
    if (inputSignal.equals("A"))
      {
        result = parser.parseFromSource(packageString);////test this guy
      }
    else if (inputSignal.equals("B"))
      {
        result = parser.parseFromSource(packageString);
      }
    else
      {
        throw new Exception("wrong input");
      }
    return result;
  }//control() 
}//SourceControl
