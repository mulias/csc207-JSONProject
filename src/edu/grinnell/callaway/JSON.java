package edu.grinnell.callaway;

import java.util.HashMap;
import java.util.Vector;

public class JSON
{
  public Object parse(String str)
  {
    switch (str.charAt(0))
      {
        case '{':
          HashMap<String, Object> object =
              (HashMap<String, Object>) parse(str.substring(1)); // ??
          break;
        // return object;
        case '}':
          return object; // This should end the object
        case '[':
          Vector<Object> array = new Vector<Object>();
          array = (Vector<Object>) parse(str.substring(1));
          break;
        case ']':

      }
  }

}
