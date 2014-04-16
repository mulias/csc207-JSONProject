package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

public class JSONParser1
{

  public JSONParser1()
  {

  }

  public Object parse(String str)
    throws Exception
  {
    BufferedReader text = new BufferedReader(new StringReader(str));
    return this.parse(text);
  }

  public Object parse(BufferedReader buffer)
    throws Exception
  {
    // mark each space in buffer before advancing one
    // advance through each char in buffer
    // ignore whitespace
    // if char signals the opener of a Object, such as {, [, or "
    // // parse that object starting at the next char
    // if char is the first char in a Object, such as t in true or 1 in 125
    // // use the mark to back up one char and then parse the object
    // if the end of the buffer is reached before an object is found
    // // throw exception
    boolean buffer_end = false;
    while (!buffer_end)
      {
        buffer.mark(1);
        int c = buffer.read();
        switch (c)
          {
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
              // ignore whitespace chars
              break;
            case '{':
              // object
              return parseObject(buffer);
            case '[':
              // array
              return parseArray(buffer);
            case '\"':
              // string
              return parseString(buffer);
            case 't':
              // true
              buffer.reset();
              return parseTrue(buffer);
            case 'f':
              // false
              buffer.reset();
              return parseFalse(buffer);
            case 'n':
              // null
              buffer.reset();
              return parseNull(buffer);
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
              buffer.reset();
              return parseNum(buffer);
            case -1:
              // end of buffer
              buffer_end = true;
              break;
            // otherwise error
            default:
              throw new Exception("1");
          }
      }
    throw new Exception("2");
  }

  public BigDecimal parseNum(BufferedReader buffer)
    throws Exception
  {
    // use a StringBuilder to stick nums together
    // when we reach a non-number char
    // // stop, use str.reset() to backup one
    // try to make a BigDecimal
    // if the syntax is bad it will throw an exception
    StringBuilder builder = new StringBuilder();
    boolean num_end = false;
    while (!num_end)
      {
        buffer.mark(1);
        int n = buffer.read();
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
              builder.append((char) n);
              break;
            // any non-number char, including buffer end
            default:
              num_end = true;
              buffer.reset();
              break;
          }
      }
    try
      {
        return new BigDecimal(builder.toString());
      }
    catch (NumberFormatException e)
      {
        throw new Exception("3 " + e);
      }
  }

  public Object parseNull(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'null', return null
    // otherwise throw exception
    if (buffer.read() == 'n' && buffer.read() == 'u' && buffer.read() == 'l'
        && buffer.read() == 'l')
      {
        return null;
      }
    else
      {
        throw new Exception("4");
      }
  }

  public boolean parseTrue(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
    if (buffer.read() == 't' && buffer.read() == 'r' && buffer.read() == 'u'
        && buffer.read() == 'e')
      {
        return true;
      }
    else
      {
        throw new Exception("5");
      }
  }

  public boolean parseFalse(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
    if (buffer.read() == 'f' && buffer.read() == 'a' && buffer.read() == 'l'
        && buffer.read() == 's' && buffer.read() == 'e')
      {
        return false;
      }
    else
      {
        throw new Exception("");
      }
  }

  public String parseString(BufferedReader buffer)
    throws Exception
  {
    // save each char to a StringBuilder
    // if c = \
    // // check if next is " accepted non-whitespace escape char, add it
    // // else throw exception
    // if c = "
    // // done
    // if end of buffer
    // throw no closing " error
    // if c is any other char
    // // add it
    StringBuilder builder = new StringBuilder();
    boolean str_end = false;
    while (!str_end)
      {
        int c = buffer.read();
        switch (c)
          {
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
              // ignore whitespace chars
              break;
            case '\\':
              // escape char for " or \
              // json should support \/, but java doesn't
              c = buffer.read();
              switch (c)
                {
                  case '"':
                  case '\\':
                    builder.append((char) c);
                    break;
                  default:
                    throw new Exception("");
                }
              break;
            case '"':
              // string is done
              str_end = true;
              break;
            case -1:
              // reach end of json before ending string
              throw new Exception("no closing \" before end of input");
            default:
              // add all chars to string
              builder.append((char) c);
              break;
          }
      }
    return builder.toString();
  }

  public Vector<Object> parseArray(BufferedReader buffer)
    throws Exception
  {
    // save values in array to a vector
    // go through each char until closing ']' is found
    // if we have not found a value
    // // search for any char, send it to parse
    // // save value to vector
    // if we have found a value
    // // search for ','
    // // throw exception if not found
    // // search for new value
    // if end of buffer
    // // throw exception
    Vector<Object> vec = new Vector<Object>();
    boolean array_end = false;
    boolean value_found = false;
    while (!array_end)
      {
        buffer.mark(1);
        int c = buffer.read();
        switch (c)
          {
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
              // ignore whitespace chars
              break;
            case ',':
              // if we were looking for ',' find a new value
              if (value_found)
                {
                  value_found = false;
                }
              else
                {
                  throw new Exception("");
                }
              break;
            case ']':
              if (value_found)
                {
                  array_end = true;
                }
              else
                {
                  // last char was ',' bad syntax
                  throw new Exception("");
                }
              break;
            case -1:
              // end of buffer before array end
              throw new Exception("");
            default:
              // parse value
              buffer.reset();
              vec.add(parse(buffer));
              value_found = true;
              break;
          }
      }
    return vec;
  }

  public HashMap<String, Object> parseObject(BufferedReader buffer)
    throws Exception
  {
    // save values in object to a HashMap
    // go through each char until closing '}' is found
    // if we have not found a key
    // // search for " to start a string
    // // if found, send to parse, else throw exception
    // if we have found a key
    // // search for ':' to start a value
    // // if found, send to parse, else throw exception
    // if we have a key and a value
    // // save to hash
    // // look for new pair
    // if end of buffer
    // // throw exception
    HashMap<String, Object> hash = new HashMap<String, Object>();
    boolean key_found = false;
    boolean value_found = false;
    boolean hash_end = false;
    String key = null;
    Object value = null;
    while (!hash_end)
      {
        buffer.mark(1);
        int c = buffer.read();
        // if key is not found
        switch (c)
          {
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
              // ignore whitespace chars
              break;
            case '"':
              if (!key_found)
                {
                  buffer.reset();
                  key = (String) parse(buffer);
                  key_found = true;
                }
              else
                {
                  throw new Exception("");
                }
              break;
            case ':':
              if (key_found && !value_found)
                {
                  value = parse(buffer);
                  value_found = true;
                }
              else
                {
                  throw new Exception("");
                }
              break;
            case '}':
              if (key_found)
                {
                  throw new Exception("unresolved key value pair");
                }
              else
                {
                  hash_end = true;
                }
              break;
            case -1:
              throw new Exception("");
            default:
              throw new Exception("");
          }
        if (key_found && value_found)
          {
            hash.put(key, value);
            key_found = false;
            value_found = false;
          }
      }
    return hash;
  }

  public static void main(String[] args)
    throws Exception
  {
    JSONParser1 parser = new JSONParser1();
    HashMap<String, Object> val = (HashMap<String, Object>) parser.parse("{ \"test\":false \"One\":2 \"obJ\":{ \"1\":1e34 } \"arr\":[ 1, 2, 3, true, null ] }");
    System.out.println(ToString.toStr(val));
  }
}
