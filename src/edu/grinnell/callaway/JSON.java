package edu.grinnell.callaway;

import java.util.HashMap;
import java.util.Vector;

public class JSON
{
  // parse determines the class of the object returned from parsing a JSON
  // string. After determining if the object is a Hashmap representation of an
  // object or a Vector representation of an array, parse recursively calls
  // parseElements to set the contents of the object or array.
  public Object parse(String str)
    throws Exception
  {
    int length = str.length();
    char start = str.charAt(0);
    char end = str.charAt(length);
    if (start == '{' && end == '}')
      {
        return parseObject(str, 1, length);
      }
    else if (start == '[' && end == ']')
      {
        return parseArray(str, 1, length);
      }
    else if (start == '{' || start == '[' || end == '}' || end == ']')
      {
        throw new Exception(
                            "Invalid syntax: Start and end braces do not match "
                                + start + " ... " + end);
      }
    else
      {
        throw new Exception("Invalid syntax: No start and end braces");
      }
  }

  public static HashMap<String, Object> parseObject(String str, int start,
                                                    int end)
    throws Exception

  {
    // make a new hashmap to store key value pairs
    // go through each char in the object's substring
    // if we have not found a key
    // // if the first char is an open quote
    // // // find the close quote
    // // else throw exception, objects should start with key
    // if we have found a key
    // // get the value
    HashMap<String, Object> newObject = new HashMap<String, Object>();
    boolean key_found = false;
    for (int i = start; i < end; i++)
      {
        char c = str.charAt(i);
        if (!key_found)
          {
            switch (c)
              {
                // these are formatting and can be ignored
                case ' ':
                case '\n':
                case '\t':
                case '\r':
                case '\b':
                case '\f':
                  break;
                // start of a key string
                case '"':

                  break;
                // otherwise error
                default:
                  throw new Exception("Invalid syntax: object must contain only key value piars");
              }
          }
        else // key found, find value
          {
            
          }

      }
    return newObject;
  }

  public static Vector<Object> parseArray(String str, int start, int end)
  {
    return vec;
  }
}
