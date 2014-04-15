package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;

public class JSON2
{
  public Object parse(String str)
    throws Exception
  {
    BufferedReader text = new BufferedReader(new StringReader(str));
    return this.parse(text);
  }

  public Object parse(BufferedReader str)
    throws Exception
  {
    int c;
    Object result;
    while ((c = str.read()) != -1)
      {
        switch (c)
          {
          // ignore whitespace chars
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
              break;
            // object
            case '{':
              result = parseObject(str);
              break;
            // array
            case '[':
              result = parseArray(str);
              break;
            // string
            case '\\':
              result = null;
              break;
            // true
            case 't':
              result = null;
              break;
            // false
            case 'f':
              result = null;
              break;
            // null
            case 'n':
              result = null;
              break;
            // otherwise number or error
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':

              break;
            default:

              throw new Exception("");
          }
      }
    return result;
  }
}
