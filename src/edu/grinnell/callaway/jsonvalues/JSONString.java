package edu.grinnell.callaway.jsonvalues;

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
    return '"' + this.str + '"';
  }
  
  public int hashCode()
  {
    return this.str.hashCode();
  }
}
