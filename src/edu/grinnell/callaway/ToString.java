package edu.grinnell.callaway;

import java.util.HashMap;
import java.util.Vector;

// This needs to be moved to the other class at some point...
public class ToString
{
  // /http://stackoverflow.com/questions/10462819/get-keys-from-hashmap-in-java
  @SuppressWarnings("unchecked")
  public static String toStr(HashMap<String, Object> hash)
  {
    String str = "{";
    for (String key : hash.keySet())
      {
        Object val = hash.get(key);
        str += "\"" + key + "\":";

        if (val == null)
          str += val + ",";
        else if (val.getClass().equals(HashMap.class))
          str += toStr((HashMap<String, Object>) val) + ",";
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
  public static String vecToString(Vector<?> vec)
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
          str += toStr((HashMap<String, Object>) val) + ",";
        else if (val.getClass().equals(String.class))
          str += "\"" + val + "\",";
        else
          str += val + ",";
      } // for (i)
    str = str.substring(0, str.length() - 1) + "]";
    return str;
  } // vecToString(Vector)

  public static void main(String args[])
  {
    HashMap<String, Object> htable = new HashMap<String, Object>(8);
    
    Vector vector = new Vector(3);
    vector.add(1);
    vector.add(2);

    Vector vector2 = new Vector(3);
    vector2.add(vector);
    vector2.add(2);

    HashMap<String, Object> htab = new HashMap<String, Object>(3);

    htab.put("x", "it");
    htab.put("y", 2.7e-4);

    vector.add(htab);

    htable.put("a", true);
    htable.put("b", 2.7e-4);
    htable.put("c", "The");
    htable.put("j", vector2);
    htable.put("d", vector);
    htable.put("e", null);
    htable.put("f", htab);

    System.out.println(toStr(htable));
  } // main
} // class ToString
