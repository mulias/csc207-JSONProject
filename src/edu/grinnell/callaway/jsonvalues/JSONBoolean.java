package edu.grinnell.callaway.jsonvalues;

public class JSONBoolean
    implements
      JSONValue
{
  
  boolean val;
  
  public JSONBoolean(boolean val)
  {
    this.val = val;
  }

  public String toJSON()
  {
    return Boolean.toString(this.val);
  }
}
