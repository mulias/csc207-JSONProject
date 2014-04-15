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
      return this.parse(new Object(), text);
    }

    public Object parse(Object result, BufferedReader str)
      throws Exception
    {
      int c;
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
                result = parse(new HashMap<String, Object>(), str);
                break;
              // array
              case '[':
                result = parse(new Vector<Object>(), str);
                break;
              // number
              case '1':
                result = null;
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
              // otherwise error
              default:
                throw new Exception("");
            }
        }
      return result;
    }
}
