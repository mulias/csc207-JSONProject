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
              int next = str.read();
              if (next == '"')
                {
                  result = parseString(str);
                }
              else
                {
                  throw new Exception("Invalid character " + next);
                }
              break;
            // true
            case 't':
              if (str.read() == 'r' && str.read() == 'u' && str.read() == 'e')
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
            // null
            case 'n':
              if (str.read() == 'u' && str.read() == 'l' && str.read() == 'l')
                {
                  result = null;
                }
              else
                {
                  throw new Exception("");
                }
              break;
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
              StringBuilder numString = new StringBuilder();
              numString.append(c);
              
              break;
            // otherwise error
            default:

              throw new Exception("");
          }
      }
    return result;
  }

  public Object parseArray(BufferedReader str)
    throws Exception
  {
    Vector<Object> array = new Vector<Object>();

    int c;
    str.mark(1);
    c = str.read();
    while (c != ']')
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
            default:
              str.reset();
              // add to array
              parse(str);
          }
        str.mark(1);
        c = str.read();
      }
  }

}
