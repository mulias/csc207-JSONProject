package edu.grinnell.callaway.jsonvalues;

import java.util.HashMap;
import java.util.Set;

public class JSONObject
    implements
      JSONValue
{

  HashMap<JSONString, JSONValue> object;

  public JSONObject(HashMap<JSONString, JSONValue> object)
  {
    this.object = object;
  }

  public String toJSON()
  {
    StringBuilder str = new StringBuilder();
    str.append('{');
    
    HashMap<JSONString, JSONValue> hash = this.object;
    Set<JSONString> keys = hash.keySet();
    for ( JSONString key : keys )
      {
        hash
      }
    
    return null;
  }
}