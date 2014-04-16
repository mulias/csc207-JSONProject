package edu.grinnell.callaway;

public class JSONTrue
    implements
      JSONValue
{
  public JSONTrue()
  {
    // no values
  }

  public String toJSON()
  {
    return "true";
  }
}
