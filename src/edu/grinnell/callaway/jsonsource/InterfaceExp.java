package edu.grinnell.callaway.jsonsource;

/**
 * A simple experiment to show that the interface 
 * for JSON is working properly.
 * @author Shaun, Mataire
 * @author Mulhall, Elias
 * @author Callaway, Erin M
 */
public class InterfaceExp
{
  /**
   * Creates an InterfaceView and InterfaceControl to 
   * display a user interface.
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args)
    throws Exception
  {
    InterfaceView view = new InterfaceView();
    InterfaceControl interact = new InterfaceControl(view);
    interact.control();
  } // main
} // class InterfaceExpt
