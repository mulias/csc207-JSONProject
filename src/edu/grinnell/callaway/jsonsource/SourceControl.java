package edu.grinnell.callaway.jsonsource;

/*
 * Citation: http://www.mkyong.com/java/how-to-get-url-content-in-java/
 * http://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
 */
public class SourceControl
{
  /*
    ****** <----Call View----(2)-- *********** <----CAll---(1)----- ********
    |VIEW| -(3)-send URl addr----> | CONTROL | -return string-(6)-> | CALL |
    ****** <-Completion signal-(6) ***********                      ********
                                      |     ^
                       Send URL/fileLoc(4)  |    
                                      |     |
                                      |   Send file location(5)
                                      v     |
                                    **********
                                    *  MODEL * maybe 
                                    **********
   */
  
  SourceModel model;
  SourceView view;

  public SourceControl(SourceModel model, SourceView view)
  {
    this.model = model;
    this.view = view;
  }//SourceControl(SourceModel model, SourceView view)
  
  /*
   * 
   */
  public void control() throws Exception{
    model.handleJSONSource(view.getJSONSource());
    view.finishSignal();
  }//control() 
}//SourceControl
