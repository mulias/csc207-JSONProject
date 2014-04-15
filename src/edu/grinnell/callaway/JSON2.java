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
    str.mark(1);
    int c = str.read();
    while (c != -1)
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
              return parseObject(str);
            // array
            case '[':
              return parseArray(str);
            // string
            case '\\':
              return parseString(str);
            // boolean
            case 't':
            case 'f':
              str.reset();
              return parseBool(str);
            // null
            case 'n':
              str.reset();
              return parseNull(str);
            // number
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
              str.reset();
              return parseNum(str);          
            // otherwise error
            default:
              throw new Exception("");
          }
        str.mark(1);
        c = str.read();
      }
    throw new Exception("");
  }

  private Object parseNum(BufferedReader str)
  {
    // TODO Auto-generated method stub
    return null;
  }

  private Object parseNull(BufferedReader str)
  {
    // TODO Auto-generated method stub
    return null;
  }

  private Object parseBool(BufferedReader str)
  {
    // TODO Auto-generated method stub
    return null;
  }

  private Object parseString(BufferedReader str)
  {
    // TODO Auto-generated method stub
    return null;
  }

  private Object parseObject(BufferedReader str)
  {
    // TODO Auto-generated method stub
    return null;
  }

  
  
  /*
   * if (str.read() == 'r' && str.read() == 'u' && str.read() == 'e')
                {
                  result = true;
                }
              else
                {
                  throw new Exception("");
                }
              break;
            // false
            case 'f':
              if (str.read() == 'a' && str.read() == 'l' && str.read() == 's'
                  && str.read() == 'e')
                {
                  result = false;
                }
              else
                {
                  throw new Exception("");
                }
              break;
              if (str.read() == 'u' && str.read() == 'l' && str.read() == 'l')
                {
                  result = null;
                }
              else
                {
                  throw new Exception("");
                }
                              int next = str.read();
              if (next == '"')
                {
                  result = parseString(str);
                }
              else
                {
                  throw new Exception("Invalid character " + next);
                }
   */

}
