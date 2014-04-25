package edu.grinnell.callaway;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Vector;

/**
 * Parse JSON string to java object(HashTable)
 * 
 * @author Shaun, Mataire
 * @author Mulhall, Elias
 * @author Callaway, Erin M
 * 
 */
public class JSONParser
{

  /*
   * CITATION:
   * http://stackoverflow.com/questions/15842239/how-to-cast-a-string-to
   * -an-url-in-java http://www.mkyong.com/java/how-to-get-url-content-in-java/
   * http
   * ://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
   * http://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
   */
  public Object parseFromSource(String jsonSource)
    throws Exception
  {
    // return normal empty string error, do not check first char
    if (jsonSource.isEmpty())
      {
        return parse(new StringReader(jsonSource));
      }
    // URL start with an "h", as in "http(s)..."
    // file locations start with a "/", "/home/..."
    // otherwise assume source is a json string
    try
      {
        char firstChar = jsonSource.charAt(0);
        if (firstChar == 'h')
          {
            // get URL, open connection
            URL url = new URL(jsonSource);
            URLConnection connect = url.openConnection();
            return parse(new InputStreamReader(connect.getInputStream()));
          }
        else if (firstChar == '/')
          {
            // a local file
            return parse(new FileReader(jsonSource));
          }
        else
          {
            // a string
            return parse(new StringReader(jsonSource));
          }
      }
    catch (MalformedURLException e)
      {
        throw new MalformedURLException("MalformedURLException: " + e);
      }
    catch (FileNotFoundException e)
      {
        throw new FileNotFoundException("");
      }
  }// parseFromSource(String)

  public Object parse(Reader in)
    throws Exception
  {
    IndexedBufferedReader buffer = new IndexedBufferedReader(in);
    Object json = parse(buffer);
    buffer.close();
    return json;
  }// Object parse(String str)

  /**
   * parse JSON buffer to java Object
   * 
   * @param buffer
   * @return Object
   * @pre buffer is formated JSON code
   * @post the JSON string has been translated into a java object
   * @throws Exception
   */
  public Object parse(IndexedBufferedReader buffer)
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
    int c;
    while ((c = buffer.peek()) != -1)
      {
        switch (c)
          {
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
              // ignore whitespace chars
              // advance to next char
              buffer.read();
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
              return parseTrue(buffer);
            case 'f':
              // false
              return parseFalse(buffer);
            case 'n':
              // null
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
              return parseNum(buffer);
            default:
              // otherwise error
              throw new Exception(jsonError("JSON ERROR",
                                            "Invalid input character "
                                                + (char) c, buffer));
          } // switch(c)
      } // while
    throw new Exception('\n' + "JSON ERROR: no json values found in input");
  } // parse(IndexedBufferedReader)

  /**
   * Parse JSON string/bufferedReader into a java BigDecimal
   * 
   * @pre buffer is an element of REAL NUMBERS
   * @post string/bufferedreader is parsed to java BigDecimal
   * @param buffer
   * @return
   * @throws Exception
   */
  public BigDecimal parseNum(IndexedBufferedReader buffer)
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
        switch (buffer.peek())
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
              builder.append((char) buffer.read());
              break;
            // any non-number char, including buffer end
            default:
              num_end = true;
              break;
          } // switch (n)
      } // while (!num_end)
    String number = builder.toString();
    try
      {
        return new BigDecimal(number);
      } // try
    catch (NumberFormatException e)
      {
        throw new Exception(jsonError("JSON NUMBER ERROR", "invalid number "
                                                           + number, buffer,
                                      -number.length()));
      } // catch
  } // parseNum(BufferedReader)

  /**
   * Parse JSON string to java null
   * 
   * @param buffer
   * @pre buffer must be "null"
   * @return
   * @throws Exception
   */
  public Object parseNull(IndexedBufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'null', return null
    // otherwise throw exception
    if (buffer.read() == 'n' && buffer.read() == 'u' && buffer.read() == 'l'
        && buffer.read() == 'l')
      {
        return null;
      } // if
    else
      {
        throw new Exception(
                            jsonError("JSON NULL ERROR",
                                      "precondtion not met, input not \"null\"",
                                      buffer));
      }// else
  } // Object parseNull(IndexedBufferedReader)

  /**
   * Parse JSON to java boolean (true)
   * 
   * @param buffer
   * @pre buffer must be "true"
   * @throws Exception
   */
  public Boolean parseTrue(IndexedBufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
    if (buffer.read() == 't' && buffer.read() == 'r' && buffer.read() == 'u'
        && buffer.read() == 'e')
      {
        return true;
      } // if
    else
      {
        throw new Exception(
                            jsonError("JSON BOOLEAN ERROR",
                                      "precondition not met, input not \"true\"",
                                      buffer));
      }// else
  }// parseTrue(IndexedBufferedReader)

  /**
   * Parse JSON to java boolean (false)
   * 
   * @param buffer
   * @pre buffer must be "false"
   * @throws Exception
   */
  public Boolean parseFalse(IndexedBufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
    if (buffer.read() == 'f' && buffer.read() == 'a' && buffer.read() == 'l'
        && buffer.read() == 's' && buffer.read() == 'e')
      {
        return false;
      } // if
    else
      {
        throw new Exception(
                            jsonError("JSON BOOLEAN ERROR",
                                      "precondition not met, value not \"false\"",
                                      buffer));
      } // else
  } // parseFalse(IndexedBufferedReader)

  /**
   * Parse JSON string
   * 
   * @pre buffer is 'chain'(string) of characters and must start with either '"'
   *      or '\'
   * @param buffer
   * @return Java string
   * @throws Exception
   */
  public String parseString(IndexedBufferedReader buffer)
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
    int c;
    if (buffer.read() != '"')
      {
        throw new Exception("");
      }
    while ((c = buffer.peek()) != '"')
      {
        switch (c)
          {
            case '\\':
              builder.append(parseEscapeChar(buffer));
              break;
            case -1:
              // reach end of json before ending string
              throw new Exception(
                                  jsonError("JSON STRING ERROR",
                                            "no closing \" before end of input",
                                            buffer, 1));
            default:
              // add all chars to string
              builder.append((char) buffer.read());
              break;
          }// switch(C)
      }// while
    buffer.read();
    return builder.toString();
  }// String parseString(IndexedBufferedReader)

  public char parseEscapeChar(IndexedBufferedReader buffer)
    throws Exception
  {
    int c = buffer.read();
    if (c == -1)
      {
        throw new Exception(jsonError("JSON STRING ERROR",
                                      "no closing \" before end of input",
                                      buffer));
      }
    else if (c != '\\')
      {
        throw new Exception("");
      }
    else
      {
        c = buffer.read();
        if (c == '"' || c == '\\' || c == '/')
          {
            return (char) c;
          }
        else if (c == 'u')
          {
            String unicode = "";
            for (int i = 0; i < 4; i++)
              {
                char num = (char) buffer.read();
                if (Character.isDigit(num) || Character.isAlphabetic(num))
                  {
                    unicode += num;
                  }
                else
                  {
                    throw new Exception("");
                  }
              }
            return (char) Integer.parseInt(unicode, 16);
          }
        else
          {
            throw new Exception(jsonError("JSON STRING ERROR",
                                          "invalid escape character \\"
                                              + (char) c, buffer));
          }
      } // else
  }

  /**
   * Parse Jason Array
   * 
   * @param buffer
   * @pre buffer must start with '[' '[' can not be followed by ','
   * @return Vector
   * @throws Exception
   */
  public Vector<Object> parseArray(IndexedBufferedReader buffer)
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
    int c;
    if (buffer.read() != '[')
      {
        throw new Exception("");
      }
    while ((c = buffer.peek()) != ']')
      {
        switch (c)
          {
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
            case ',':
              // ignore whitespace chars
              // advance to next char
              buffer.read();
              break;
            case -1:
              // end of buffer before array end
              throw new Exception(jsonError("JSON ARRAY ERROR",
                                            "no closing ] before end of input",
                                            buffer));
            default:
              // parse value
              vec.add(parse(buffer));
              break;
          }// switch (c)
      }// while
    buffer.read();
    return vec;
  }// parseArray(IndexedBufferedReader)

  /**
   * Parse Jason Object
   * 
   * @param buffer
   * @pre buffer must start with '{' '{' must be followed by type 'string' i.e a
   *      '"' and a ':'
   * @return HashMap
   * @throws Exception
   */
  public HashMap<String, Object> parseObject(IndexedBufferedReader buffer)
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
    String key = null;
    Object value = null;
    int c;
    // check that first char opens the object with '{'
    if (buffer.read() != '{')
      {
        throw new Exception("");
      }
    while ((c = buffer.peek()) != '}')
      {
        switch (c)
          {
            case ' ':
            case '\n':
            case '\t':
            case '\r':
            case '\b':
            case '\f':
            case ',':
              // ignore whitespace chars
              // advance to next char
              buffer.read();
              break;
            case -1:
              throw new Exception(jsonError("JSON Object ERROR",
                                            "no closing } before end of input",
                                            buffer));
            default:
              if (!key_found)
                {
                  if (c == '"')
                    {
                      key = (String) parse(buffer);
                      key_found = true;
                    }
                  else
                    {
                      throw new Exception(
                                          jsonError("JSON OBJECT ERROR",
                                                    "invalid character '"
                                                        + (char) c
                                                        + "', expected key string",
                                                    buffer));
                    }
                }
              // else if we don't have a value, the next char should be ':'
              else if (!value_found)
                {
                  if (c == ':')
                    {
                      buffer.read();
                      value = parse(buffer);
                      value_found = true;
                    }
                  else
                    {
                      throw new Exception(
                                          jsonError("JSON OBJECT ERROR",
                                                    "invalid character '"
                                                        + (char) c
                                                        + "', expected value for key:value pair",
                                                    buffer));
                    }
                }
              break;
          }
        if (key_found && value_found)
          {
            hash.put(key, value);
            key_found = false;
            value_found = false;
          }
      } // while
    buffer.read();
    return hash;
  }// parseObject(IndexedBufferedReader)

  public String jsonError(String header, String body,
                          IndexedBufferedReader buffer)
    throws IOException
  {
    return jsonError(header, body, buffer, 0);
  }

  public String jsonError(String header, String body,
                          IndexedBufferedReader buffer, int offset)
    throws IOException
  {
    // the line and character index of the error
    String index = "L" + buffer.line() + ":" + "C" + (buffer.index() + offset);
    // a pointer to mark the error in a string
    String pointer = "";
    int spaces = buffer.index + offset;
    for (int i = 0; i < spaces; i++)
      {
        pointer += ' ';
      }
    pointer += '^';
    // full error string
    return '\n' + header + '(' + index + "): " + body + '\n'
           + buffer.currentLine() + '\n' + pointer;
  }

  /**
   * Converts a given JSON value (in the form of a Java object) into a string
   * 
   * @param obj
   * @return
   */
  @SuppressWarnings("unchecked")
  public String toStr(Object obj)
  {
    if (obj == null)
      return "null";
    else if (obj.getClass().equals(HashMap.class))
      return objToString((HashMap<String, Object>) obj);
    else if (obj.getClass().equals(Vector.class))
      return vecToString((Vector<?>) obj);
    else if (obj.getClass().equals(String.class))
      return "\"" + obj.toString() + "\"";
    else
      return obj.toString();
  }

  /*
   * We got some ideas for how to get the key value from:
   * http://stackoverflow.com/questions/10462819/get-keys-from-hashmap-in-java
   */
  /**
   * Converts the key-value pairs from the given hashMap into a String
   * 
   * @param hash
   * @return String
   */
  @SuppressWarnings("unchecked")
  public String objToString(HashMap<String, Object> hash)
  {
    String str = "{";
    for (String key : hash.keySet())
      {
        Object val = hash.get(key);
        str += "\"" + key + "\":";

        if (val == null)
          str += val + ",";
        else if (val.getClass().equals(HashMap.class))
          str += objToString((HashMap<String, Object>) val) + ",";
        else if (val.getClass().equals(String.class))
          str += "\"" + val + "\",";
        else if (val.getClass().equals(Vector.class))
          str += vecToString((Vector<?>) val) + ",";
        else
          str += val + ",";
      } // for (key)
    str = str.substring(0, str.length() - 1) + "}";
    return str;
  } // toStr(HashMap)

  /**
   * Converts a vector into a string.
   * 
   * @param vec
   * @return String
   */
  @SuppressWarnings("unchecked")
  public String vecToString(Vector<?> vec)
  {
    String str = "[";

    for (int i = 0; i < vec.size(); i++)
      {
        Object val = vec.get(i);

        if (val == null)
          str += val + ",";
        else if (val.getClass().equals(Vector.class))
          str += vecToString((Vector<?>) val) + ",";
        else if (val.getClass().equals(HashMap.class))
          str += objToString((HashMap<String, Object>) val) + ",";
        else if (val.getClass().equals(String.class))
          str += "\"" + val + "\",";
        else
          str += val + ",";
      } // for (i)
    str = str.substring(0, str.length() - 1) + "]";
    return str;
  } // vecToString(Vector)

}// JSONParser
