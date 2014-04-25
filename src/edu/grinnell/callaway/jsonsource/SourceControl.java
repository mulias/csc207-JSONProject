package edu.grinnell.callaway.jsonsource;

import edu.grinnell.callaway.JSONParser;

/*
 * Citation: http://www.mkyong.com/java/how-to-get-url-content-in-java/
 * http://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
 */
public class SourceControl
{
 
  
  SourceModel model;
  SourceView view;

  public SourceControl(SourceModel model, SourceView view)
  {
    this.model = model;
    this.view = view;
  }//SourceControl(SourceModel model, SourceView view)
  

  public Object control(){
    Object result =null;
     SourceView A = new SourceView.getInputType();
     String inputSignal = A.parseSignal;
     String value = A.JSONString;
    if (inputSignal.equals("A")){
      result=JSONParser.parse(value);
    }
    else if (inputSignal.equals("B")){
      result= JSONParser.parseFromSource(value);
    }
    else{
      throw new Exception ("wrong input");
    }
   return result;
  }//control() 
}//SourceControl
