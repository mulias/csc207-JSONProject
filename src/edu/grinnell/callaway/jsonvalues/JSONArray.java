package edu.grinnell.callaway.jsonvalues;

import java.util.Iterator;
import java.util.Vector;

public class JSONArray
    implements
      JSONValue
{

  Vector<JSONValue> array;

  public JSONArray(Vector<JSONValue> vec)
  {
    this.array = vec;
  }

  public String toJSON()
  {
    StringBuilder str = new StringBuilder();
    str.append('[');
    Iterator<JSONValue> iterator = this.array.iterator();
    while (iterator.hasNext())
      {
        str.append(iterator.next().toJSON());
      }
    str.append(']');
    return str.toString();
  }

}
