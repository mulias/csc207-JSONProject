package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
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
              str.reset();
              return parseTrue(str);
            case 'f':
              str.reset();
              return parseFalse(str);
              // null
            case 'n':
              str.reset();
              return parseNull(str);
              // number
            case '-':
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
    throws Exception
  {
    StringBuilder numString = new StringBuilder();
    boolean num_end = false;
    while (!num_end)
      {
        str.mark(1);
        int n = str.read();
        switch (n)
        {
          case '-':
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
          case 'e':
            numString.append(n);
            break;
          default:
            num_end = true;
            str.reset();
            break;
        }
      }
    try
    {
      return new BigDecimal(numString.toString());
    }
    catch (NumberFormatException e)
    {
      throw new Exception("" + e);
    }
  }

  private Object parseNull(BufferedReader str)
    throws Exception
  {
    if (str.read() == 'n' && str.read() == 'u' && str.read() == 'l'
        && str.read() == 'l')
      {
        return null;
      }
    else
      {
        throw new Exception("");
      }
  }

  private Object parseTrue(BufferedReader str)
    throws Exception
  {
    if (str.read() == 't' && str.read() == 'r' && str.read() == 'u'
        && str.read() == 'e')
      {
        return true;
      }
    else
      {
        throw new Exception("");
      }
  }

  private Object parseFalse(BufferedReader str)
    throws Exception
  {
    if (str.read() == 'f' && str.read() == 'a' && str.read() == 'l'
        && str.read() == 's' && str.read() == 'e')
      {
        return false;
      }
    else
      {
        throw new Exception("");
      }
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

  public Object parseArray(BufferedReader str)
    throws Exception
  {
    Vector<Object> array = new Vector<Object>();

    int c;
    str.mark(1);
    c = str.read();
    if (c == ',')
      {
        throw new Exception("array can not start with ','");
      }
    while (c != ']')
      {
        switch (c)
          {
          // ignore whitespace chars
            case ',':
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
              break;
            default:
              str.reset();
              array.add(parse(str));
          }
        str.mark(1);
        c = str.read();
      }
    return array;
  }
}
