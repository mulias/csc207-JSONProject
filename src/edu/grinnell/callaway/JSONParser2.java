package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;
import edu.grinnell.callaway.jsonvalues.JSONArray;
import edu.grinnell.callaway.jsonvalues.JSONFalse;
import edu.grinnell.callaway.jsonvalues.JSONNull;
import edu.grinnell.callaway.jsonvalues.JSONNumber;
import edu.grinnell.callaway.jsonvalues.JSONObject;
import edu.grinnell.callaway.jsonvalues.JSONString;
import edu.grinnell.callaway.jsonvalues.JSONTrue;
import edu.grinnell.callaway.jsonvalues.JSONValue;

public class JSONParser2
{

  public JSONParser2()
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
              return parseObject(str);
            case '[':
              // array
              return parseArray(str);
            case '\"':
              // string
              return parseString(str);
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
              return parseNum(str);
              // otherwise error
            default:
              throw new Exception("1");
          }
        str.mark(1);
        c = str.read();
      }
    throw new Exception("2");
  }

  public JSONNumber parseNum(BufferedReader str)
    throws Exception
  {
    // use a stringbuilder to stick nums together
    // when we reach a non-number char
    // // stop, use str.reset() to backup one
    // try to make a BigDecimal
    // if the syntax is bad it will throw an exception
    StringBuilder builder = new StringBuilder();
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
              // build number
              builder.append(n);
              break;
            default:
              num_end = true;
              str.reset();
              break;
          }
      }
    try
      {
        return new JSONNumber(builder.toString());
      }
    catch (NumberFormatException e)
      {
        throw new Exception("3 " + e);
      }
  }

  public JSONNull parseNull(BufferedReader str)
    throws Exception
  {
    // if the next chars spell out 'null', return null
    // otherwise throw exception
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

  public JSONTrue parseTrue(BufferedReader str)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
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

  public JSONFalse parseFalse(BufferedReader str)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
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

  public JSONString parseString(BufferedReader str)
    throws Exception
  {
    // save each char to a StringBuilder
    // if c = \
    // // check if next is ", if so add " to SB
    // // else throw exception
    // if c = "
    // // done
    StringBuilder builder = new StringBuilder();
    boolean str_end = false;
    while (!str_end)
      {
        int c = str.read();
        switch (c)
          {
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
              // ignore whitespace chars
              break;
            case -1:
              // reach end of json before ending string
              throw new Exception("end of input");
            case '\\':
              // escape char for " or \
              // json should support \/, but java doesn't
              c = str.read();
              switch (c)
                {
                  case '"':
                  case '\\':
                    builder.append(c);
                    break;
                  default:
                    throw new Exception("");
                }
            case '"':
              // string is done
              str_end = true;
              break;
            default:
              // add all chars to string
              builder.append(c);
              break;
          }
      }
    return new JSONString(builder.toString());
  }

  public JSONArray parseArray(BufferedReader str)
    throws Exception
  {
    Vector<JSONValue> array = new Vector<JSONValue>();

    str.mark(1);
    int c = str.read();
    boolean found_value = false;

    while (c != ']')
      {
        if (!found_value)
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
              case 
          }
        else
          {
            
          }
        
            default:
              str.reset();
              array.add(parse(str));
          }
        str.mark(1);
        c = str.read();
      }
    return new JSONArray(array);
  }

  public JSONObject parseObject(BufferedReader str)
    throws Exception
  {
    HashMap<JSONString, JSONValue> table = new HashMap<JSONString, JSONValue>();
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
                table.put(key, value);
              }
          }
      }
    return new JSONObject(table);
  }

  public static void main(String[] args)
    throws Exception
  {
    JSONParser2 parser = new JSONParser2();
    JSONValue val = parser.parse("null");
    System.out.println(val.toJSON());
  }
}
