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
    StringBuilder builder = new StringBuilder();
    builder.append('[');
    Iterator<JSONValue> iterator = this.array.iterator();
    if (iterator.hasNext())
      {
        builder.append(iterator.next().toJSON());
      }
    while (iterator.hasNext())
      {
        builder.append(", ");
        builder.append(iterator.next().toJSON());
      }
    builder.append(']');
    return builder.toString();
  }

}
