package edu.grinnell.callaway;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

// This needs to be moved to the other class at some point...
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
    else
      return obj.toString();
  }

  // /http://stackoverflow.com/questions/10462819/get-keys-from-hashmap-in-java
  @SuppressWarnings("unchecked")
  public String objToString(HashMap<String, Object> hash)
  {
    // HashMap<String, Object> hash = (HashMap<String, Object>) obj;
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

  public static void main(String args[])
  {
    HashMap<String, Object> htable = new HashMap<String, Object>(8);

    Vector<Serializable> vector = new Vector<Serializable>(3);
    vector.add(1);
    vector.add(2);

    Vector<Serializable> vector2 = new Vector<Serializable>(3);
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

    ToString str = new ToString();

    System.out.println(str.toStr((Object) htable));
  } // main
} // class ToString
