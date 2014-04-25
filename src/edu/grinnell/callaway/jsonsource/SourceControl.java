package edu.grinnell.callaway.jsonsource;

import java.io.BufferedReader;
import java.io.Reader;

import edu.grinnell.callaway.JSONParser;

/*
 * Citation: http://www.mkyong.com/java/how-to-get-url-content-in-java/
 * http://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
 */
public class SourceControl
{

  SourceView view;

  public SourceControl(SourceView view)
  {
    this.view = view;
  }//SourceControl(SourceModel model, SourceView view)

  public Object control()
    throws Exception
  {
    JSONParser parser = new JSONParser();
    Object result = null;
    Reader read = null;
    String string = null;
    SourceView A = new SourceView(read, string);
    A.getInputType();
    String inputSignal = string;
    Reader value = read;
    if (inputSignal.equals("A"))
      {
        result = parser.parse((BufferedReader) value);////test this guy
      }
    else if (inputSignal.equals("B"))
      {
        result = parser.parseFromSource(value);
      }
    else
      {
        throw new Exception("wrong input");
      }
    return result;
  }//control() 
}//SourceControl
