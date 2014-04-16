package edu.grinnell.callaway;

public class JSONString
    implements
      JSONValue
{

  String str;

  public JSONString(String str)
  {
    this.str = str;
  }

  public String toJSON()
  {
    return this.str;
  }
}
