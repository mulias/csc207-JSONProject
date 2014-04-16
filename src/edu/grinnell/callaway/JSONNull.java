package edu.grinnell.callaway;

public class JSONNull
    implements
      JSONValue
{
  public JSONNull()
  {
    // no values
  }

  public String toJSON()
  {
    return "null";
  }
}
