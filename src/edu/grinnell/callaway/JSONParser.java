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
   * http://stackoverflow.com/questions/15842239/how-to-cast-a-string-to-an-url-in-java http://www.mkyong.com/java/how-to-get-url-content-in-java/
   */
  /**
   * Read and parse json located at a web address and return a java object representing the json code.
   *  
   * @param address - the url pointing to the json file
   * @return Object - the object described by json code
   * @throws Exception
   */
  public Object parseFromHTTP(String address)
    throws Exception
  {
    // make a new stream reader to read the file
    // pass the reader to the parse(Reader) method
    try
      {
        // get URL, open connection
        URL url = new URL(address);
        URLConnection connect = url.openConnection();
        return parse(new InputStreamReader(connect.getInputStream()));
      } // try
    catch (MalformedURLException e)
      {
        throw new MalformedURLException("MalformedURLException: " + e);
      } // catch bad url
  } // parseFromHTTP(String)

  /*
   * CITATION:
   * http://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
   */
  /**
   * Read and parse json from a file and return a java object representing the json code.
   *
   * @param path - file path to the file to read 
   * @return Object - the object described by json code
   * @throws Exception
   */
  public Object parseFromFile(String path)
    throws Exception
  {
    // make a new file reader to read the file
    // pass the reader to the parse(Reader) method
    try
      {
        return parse(new FileReader(path));
      } // try
    catch (FileNotFoundException e)
      {
        throw new FileNotFoundException("");
      } // catch bad file name
  } // parseFromFile(String)

  /**
   * Read and parse a string of json and return a java object representing the json code.
   * 
   * @param str - string of json code
   * @return Object  - the object described by json code
   * @throws Exception
   */
  public Object parseFromString(String str)
    throws Exception
  {
    // make a new string reader to pass to parse(Reader)
    // take any string, parse function will return errors
    return parse(new StringReader(str));
  } // parseFromString(String)

  /**
   * Read and parse json from any java character stream reader, 
   * including FileReader, InputStreamReader, StringReader, 
   * BufferedReader, and CharArrayReader. Return a java object 
   * representing the json code.
   * @param in - any Reader
   * @return Object - the object described by json code
   * @throws Exception
   */
  public Object parse(Reader in)
    throws Exception
  {
    // pass the reader into an IndexedBufferedReader
    // get the json object from the input
    // close the buffer, which also closes the stream
    IndexedBufferedReader buffer = new IndexedBufferedReader(in);
    Object json = parse(buffer);
    buffer.close();
    return json;
  } // Object parse(String str)

  /**
   * Read the buffer and return each json value found as an object
   * 
   * @param buffer - buffered reader to track location in input, 
   *  as well as improve error messages
   * @return Object - the object described by json code
   * @pre buffer is formated JSON code
   * @post the JSON string has been translated into a java object
   * @throws Exception
   */
  public Object parse(IndexedBufferedReader buffer)
    throws Exception
  {
    // for each character in the buffer
    // ignore whitespace, identify json objects, 
    // arrays, strings, booleans, null, and numbers
    // and parse each value found
    // if a char can not be identified as the start of
    // a json value, throw exception
    // if the end of the buffer is reached before finding
    // a json value, throw an exception
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
              buffer.read();
              throw new Exception(jsonError("JSON ERROR",
                                            "Invalid input character "
                                                + (char) c, buffer));
          } // switch(c)
      } // while
    throw new Exception('\n' + "JSON ERROR: no json values found in input");
  } // parse(IndexedBufferedReader)

  /**
   * Read the buffer for json number values
   * return json number as a java BigDecimal
   * 
   * @pre buffer is an element of REAL NUMBERS
   * @post string/bufferedreader is parsed to java BigDecimal
   * @param buffer - buffered reader of json code
   * @return BigDecimal - json number
   * @throws Exception
   */
  public BigDecimal parseNum(IndexedBufferedReader buffer)
    throws Exception
  {
    // use a StringBuilder to stick nums together
    // move through the buffer until a non-number 
    // value is found
    // try to make a BigDecimal from the string
    // if the number is formatted right it will succeed
    // otherwise throw exception
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
                                      -number.length() + 1));
      } // catch
  } // parseNum(IndexedBufferedReader)

  /**
   * Read the buffer for json null value
   * return json null as a java null
   * 
   * @param buffer - buffered reader of json code
   * @pre first three buffer chars must be "null"
   * @return
   * @throws Exception
   */
  public Object parseNull(IndexedBufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'null', return null
    // otherwise throw exception
    char nullarr[] = { 'n', 'u', 'l', 'l' };
    for (char c : nullarr)
      {
        if (buffer.read() != c)
          {
            throw new Exception(jsonError("JSON NULL ERROR",
                                          "expected input \"null\"", buffer));
          } // if
      } // for
    return null;
  } // Object parseNull(IndexedBufferedReader)

  /**
   * Read the buffer for json true value
   * return json true as a java boolean
   * 
   * @param buffer - buffered reader of json code
   * @pre first four buffer chars must be "true"
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
        throw new Exception(jsonError("JSON BOOLEAN ERROR",
                                      "expected input \"true\"", buffer));
      }// else
  }// parseTrue(IndexedBufferedReader)

  /**
   * Read the buffer for json false value
   * return json true as a java boolean
   * 
   * @param buffer - buffered reader of json code
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
        throw new Exception(jsonError("JSON BOOLEAN ERROR",
                                      "expected input \"false\"", buffer));
      } // else
  } // parseFalse(IndexedBufferedReader)

  /**
   * Read the buffer for a json string value
   * return json string as a java String
   * 
   * @param buffer - buffered reader of json code
   * @return Java string
   * @throws Exception
   */
  public String parseString(IndexedBufferedReader buffer)
    throws Exception
  {
    // save each char to a stringbuilder
    // string must start with "
    // if an escape is found, parse with parseEscapeChar
    // if the end of the buffer is reached, throw exception
    // otherwise add each char until closing quote is found
    StringBuilder builder = new StringBuilder();
    int c;
    if (buffer.read() != '"')
      {
        throw new Exception(jsonError("JSON STRING ERROR",
                                      "string must start with \"", buffer));
      } // if
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
                                            buffer));
            default:
              // add all chars to string
              builder.append((char) buffer.read());
              break;
          }// switch(C)
      }// while
    // read closing quote
    buffer.read();
    return builder.toString();
  }// String parseString(IndexedBufferedReader)

  /**
   * Read the buffer for a json valid escape character
   * return escape char as a java char
   * 
   * @param buffer - buffered reader of json code
   * @return
   * @throws Exception
   */
  public char parseEscapeChar(IndexedBufferedReader buffer)
    throws Exception
  {
    // escape char must start with \
    // if it's " or \ or /, return the char
    // if it's 'u', make a new unicode hex-based char
    // if the end of the buffer is reached before resolving, throw exception
    // if the char is not a valid escape char, throw exception
    int c = buffer.read();
    if (c != '\\')
      {
        throw new Exception(jsonError("JSON STRING ERROR",
                                      "escape characters must start with \\",
                                      buffer));
      } // if
    else
      {
        c = buffer.read();
        if (c == '"' || c == '\\' || c == '/')
          {
            // basic escape chars, return value
            return (char) c;
          } // if
        else if (c == 'u')
          {
            //get the four hex numbers
            String unicode = "";
            for (int i = 0; i < 4; i++)
              {
                char num = (char) buffer.read();
                if (Character.isDigit(num) || Character.isAlphabetic(num))
                  {
                    // valid unicode
                    unicode += num;
                  } // if
                else
                  {
                    // invalid
                    throw new Exception(
                                        jsonError("JSON STRING ERROR",
                                                  "invalid unicode char \\u"
                                                      + unicode
                                                      + num
                                                      + ", "
                                                      + num
                                                      + " is not a valid hex-number input",
                                                  buffer));
                  } // else
              } // for
            return (char) Integer.parseInt(unicode, 16);
          } // else if u
        else if (c == -1)
          {
            throw new Exception(
                                jsonError("JSON STRING ERROR",
                                          "unfinished escape character, no closing \" before end of input",
                                          buffer));
          } // else if -1
        else
          {
            throw new Exception(jsonError("JSON STRING ERROR",
                                          "invalid escape character \\"
                                              + (char) c, buffer, -1));
          } // else
      } // else
  } // parseEscapeChar(IndexedBufferedReader)

  /**
   * Read the buffer for a json array of json values
   * return json array as java Vector of Objects
   * 
   * @param buffer - buffered reader of json code
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
    // // search for new value
    // if unexpected end of buffer
    // // throw exception
    Vector<Object> vec = new Vector<Object>();
    int c;
    if (buffer.read() != '[')
      {
        throw new Exception("");
      } // if
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
    // read closing ]
    buffer.read();
    return vec;
  }// parseArray(IndexedBufferedReader)

  /**
   * Read the buffer for a json object of json key:value pairs
   * return json object as java HashMap
   * 
   * @param buffer - buffered reader of json code
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
    // if unexpected end of buffer
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
      } // if
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
                    } // if
                  else
                    {
                      throw new Exception(
                                          jsonError("JSON OBJECT ERROR",
                                                    "invalid character '"
                                                        + (char) c
                                                        + "', expected start of key string",
                                                    buffer));
                    } // else
                } // if
              // else if we don't have a value, the next char should be ':'
              else if (!value_found)
                {
                  if ((c = buffer.read()) == ':')
                    {
                      value = parse(buffer);
                      value_found = true;
                    } // if
                  else
                    {
                      throw new Exception(
                                          jsonError("JSON OBJECT ERROR",
                                                    "invalid character '"
                                                        + (char) c
                                                        + "', expected value for key:value pair",
                                                    buffer));
                    } // else
                } // else if
              break;
          } // switch(c)
        if (key_found && value_found)
          {
            hash.put(key, value);
            key_found = false;
            value_found = false;
          } // if
      } // while
    // read closing }
    buffer.read();
    return hash;
  } // parseObject(IndexedBufferedReader)

  /**
   * Generate a json error message, specifying what kind of json value had 
   * an error, what the error was, and where in the json code the error occurred
   * 
   * @param header - kind of error (string, boolean, number, et)
   * @param body - error specifics
   * @param buffer - the IndexedBufferedReader of json code
   * @return
   * @throws IOException
   */
  public String jsonError(String header, String body,
                          IndexedBufferedReader buffer)
    throws IOException
  {
    // pass to jsonError method with offset of 0
    return jsonError(header, body, buffer, 0);
  }

  /**
   * Generate a json error message, specifying what kind of json value had 
   * an error, what the error was, and where in the json code the error occurred
   * 
   * @param header - kind of error (string, boolean, number, et)
   * @param body - error specifics
   * @param buffer - the IndexedBufferedReader of json code
   * @param offset - number of spaces to move the error pointer over, 
   * to point at the beginning of an error
   * @return
   * @throws IOException
   */
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
  public String toJSONString(Object obj)
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
