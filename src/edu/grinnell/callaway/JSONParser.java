package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

public class JSONParser
{
  public JSONParser()
  {

  }

  public JSONValue parse(String str)
    throws Exception
  {
    BufferedReader text = new BufferedReader(new StringReader(str));
    return this.parse(text);
  }

  public JSONValue parse(BufferedReader str)
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
            case '{':
              // object
              // return parseObject(str);
            case '[':
              // array
              // return parseArray(str);
            case '\\':
              // string
              // return parseString(str);
            case 't':
              // true
              str.reset();
              return parseTrue(str);
            case 'f':
              // false
              str.reset();
              return parseFalse(str);
            case 'n':
              // null
              str.reset();
              return parseNull(str);
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
            case '-':
            case '+':
            case '.':
              // number
              str.reset();
              // return parseNum(str);
              // otherwise error
            default:
              throw new Exception("1");
          }
        str.mark(1);
        c = str.read();
      }
    throw new Exception("2");
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
            case 'E':
            case '-':
            case '+':
            case '.':
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
        throw new Exception("3 " + e);
      }
  }

  private JSONNull parseNull(BufferedReader str)
    throws Exception
  {
    if (str.read() == 'n' && str.read() == 'u' && str.read() == 'l'
        && str.read() == 'l')
      {
        return new JSONNull();
      }
    else
      {
        throw new Exception("4");
      }
  }

  private JSONValue parseTrue(BufferedReader str)
    throws Exception
  {
    if (str.read() == 't' && str.read() == 'r' && str.read() == 'u'
        && str.read() == 'e')
      {
        return new JSONTrue();
      }
    else
      {
        throw new Exception("5");
      }
  }

  private JSONValue parseFalse(BufferedReader str)
    throws Exception
  {
    if (str.read() == 'f' && str.read() == 'a' && str.read() == 'l'
        && str.read() == 's' && str.read() == 'e')
      {
        return new JSONFalse();
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

  public Object parseObject(BufferedReader str)
    throws Exception
  {
    HashMap<String, Object> table = new HashMap<String, Object>();
    int c;
    String key = null;
    str.mark(1);
    c = str.read();
    if (c != '"')
      {
        throw new Exception("array can not start with key should be a string");
      }
    // not sure how to advance the bufferedreader so that it goes past the fist
    // ". str.read()?
    else
      {

        while ((c = str.read()) != '}') // <---------------------I have reason
                                        // to believe that this is buggy
          { // | ^
            while ((c = str.read()) != '"') // |<----------------|
              { // | |
                key = key + c; // _______________|<----------------|

              }
            if (c == ':')
              {
                str.read();
                table.put(key, str);
              }
          }
      }
    return table;
  }

  public static void main(String[] args)
    throws Exception
  {
    JSONParser parser = new JSONParser();
    JSONValue val = parser.parse("null");
    System.out.println(val.toJSON());
  }
}
