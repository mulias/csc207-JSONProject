package edu.grinnell.callaway;

import java.util.HashMap;
import java.util.Vector;

public class ToString
{
  @SuppressWarnings("unchecked")
  public String toStr(Object obj)
  {
    if (obj == null)
      return "null";
    else if (obj.getClass().equals(HashMap.class))
      return objToString((HashMap<String, Object>) obj);
    else if (obj.getClass().equals(Vector.class))
      return vecToString((Vector<?>) obj);
    else if (obj.getClass().equals(String.class))
      return "\"" + obj.toString() + "\"";
    else
      return obj.toString();
  }

  // /http://stackoverflow.com/questions/10462819/get-keys-from-hashmap-in-java
  @SuppressWarnings("unchecked")
  public String objToString(HashMap<String, Object> hash)
  {
    String str = "{";
    for (String key : hash.keySet())
      {
        Object val = hash.get(key);
        str += "\"" + key + "\":";

        if (val == null)
          str += val + ",";
        else if (val.getClass().equals(HashMap.class))
          str += objToString((HashMap<String, Object>) val) + ",";
        else if (val.getClass().equals(String.class))
          str += "\"" + val + "\",";
        else if (val.getClass().equals(Vector.class))
          str += vecToString((Vector<?>) val) + ",";
        else
          str += val + ",";
      } // for (key)
    str = str.substring(0, str.length() - 1) + "}";
    return str;
  } // toStr(HashMap)

  @SuppressWarnings("unchecked")
  public String vecToString(Vector<?> vec)
  {
    String str = "[";

    for (int i = 0; i < vec.size(); i++)
      {
        Object val = vec.get(i);

        if (val == null)
          str += val + ",";
        else if (val.getClass().equals(Vector.class))
          str += vecToString((Vector<?>) val) + ",";
        else if (val.getClass().equals(HashMap.class))
          str += objToString((HashMap<String, Object>) val) + ",";
        else if (val.getClass().equals(String.class))
          str += "\"" + val + "\",";
        else
          str += val + ",";
      } // for (i)
    str = str.substring(0, str.length() - 1) + "]";
    return str;
  } // vecToString(Vector)
} // class ToString
