package edu.grinnell.callaway.jsonsource;

public class InterfaceExp
{
  /**
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception
  {
    InterfaceView view = new InterfaceView(null, null);
    InterfaceControl interact = new InterfaceControl(view);
      interact.control();
  }
}
