package edu.grinnell.callaway;

public class JSONFalse
    implements
      JSONValue
{
  public JSONFalse()
  {
    // no values
  }

  public String toJSON()
  {
    return "false";
  }
}