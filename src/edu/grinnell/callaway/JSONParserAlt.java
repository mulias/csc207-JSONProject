package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;

import edu.grinnell.callaway.jsonvalues.JSONArray;
import edu.grinnell.callaway.jsonvalues.JSONNull;
import edu.grinnell.callaway.jsonvalues.JSONNumber;
import edu.grinnell.callaway.jsonvalues.JSONObject;
import edu.grinnell.callaway.jsonvalues.JSONString;
import edu.grinnell.callaway.jsonvalues.JSONBoolean;
import edu.grinnell.callaway.jsonvalues.JSONValue;

/**
 * Parse JSON string to java object(HashTable)
 * 
 * @author Shaun, Mataire
 * @author Mulhall, Elias
 * @author Callaway, Erin M
 * 
 */
public class JSONParserAlt
{

  /**
   * parse JSON string to java JSONValue object
   * 
   * @param String
   * @return JSONValue
   * @pre string is formated JSON code
   * @post the JSON string has been translated to a JSONValue
   * @throws Exception
   */
  public JSONValue parse(String str)
    throws Exception
  {
    BufferedReader text = new BufferedReader(new StringReader(str));
    return this.parse(text);
  } // JSONValue parse(String)

  /**
   * parse JSON string to JSONValue
   * 
   * @param buffer
   * @return JSONValue
   * @pre buffer is not null
   * @post the JSON string/bufferedreader has been passed to a Java HasTable
   *       object
   * @throws Exception
   */
  public JSONValue parse(BufferedReader buffer)
    throws Exception
  {
    // mark each space in buffer before advancing one
    // advance through each char in buffer
    // ignore whitespace
    // if char signals the opener of a JSONValue, such as {, [, or "
    // // parse that object starting at the next char
    // if char is the first char in a JSONValue, such as t in true or 1 in 125
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
              throw new Exception("JSON ERROR: Invalid input character " + c);
          } // switch(c)
      } // while (!buffer_end)
    throw new Exception("JSON ERROR: no json values found in input");
  } // parse(BufferedReader)

  /**
   * Parse JSON string/bufferedReader into a JSONNumber
   * 
   * @pre buffer is an element of REAL NUMBERS
   * @post string/bufferedreader is parsed to java BigDecimal
   * @param buffer
   * @return JSONNumber
   * @throws Exception
   */
  public JSONNumber parseNum(BufferedReader buffer)
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
          } // switch (n)
      } // while (!num_end)
    String number = builder.toString();
    try
      {
        return new JSONNumber(number);
      } // try
    catch (NumberFormatException e)
      {
        throw new Exception("JSON NUMBER ERROR: invalid number" + number);
      } // catch
  } // parseNum(BufferedReader)

  /**
   * Parse JSON string to JSONNull
   * 
   * @param buffer
   * @pre buffer must be "null"
   * @return JSONNull
   * @throws Exception
   */
  public JSONNull parseNull(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'null', return null
    // otherwise throw exception
    if (buffer.read() == 'n' && buffer.read() == 'u' && buffer.read() == 'l'
        && buffer.read() == 'l')
      {
        return new JSONNull();
      } // if
    else
      {
        throw new Exception(
                            "JSON NULL ERROR: precondtion not met, input not \"null\"");
      }// else
  }// Object parseNull(BufferedReader buffer)

  /**
   * Parse JSON to JSONBoolean (true)
   * 
   * @param buffer
   * @pre buffer must be "true"
   * @throws Exception
   */
  public JSONBoolean parseTrue(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
    if (buffer.read() == 't' && buffer.read() == 'r' && buffer.read() == 'u'
        && buffer.read() == 'e')
      {
        return new JSONBoolean(true);
      } // if
    else
      {
        throw new Exception(
                            "JSON BOOLEAN ERROR: precondition not met, input not \"true\"");
      }// else
  }// parseTrue(BufferedReader buffer)

  /**
   * Parse JSON to JSONBoolean (false)
   * 
   * @param buffer
   * @pre buffer must be "false"
   * @throws Exception
   */
  public JSONBoolean parseFalse(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
    if (buffer.read() == 'f' && buffer.read() == 'a' && buffer.read() == 'l'
        && buffer.read() == 's' && buffer.read() == 'e')
      {
        return new JSONBoolean(false);
      } // if
    else
      {
        throw new Exception(
                            "JSON BOOLEAN ERROR: precondition not met, value not \"false\"");
      }// else
  }// parseFalse(BufferedReader buffer)

  /**
   * Parse JSON string to JSONString
   * 
   * @pre buffer is 'chain'(string) of characters and must start with either '"'
   *      or '\'
   * @param buffer
   * @return JSONString
   * @throws Exception
   */
  public JSONString parseString(BufferedReader buffer)
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
              if (c == '"' || c == '\\')
                {
                  builder.append((char) c);
                } // if
              else if (c == -1)
                {
                  throw new Exception(
                                      "JSON STRING ERROR: no closing \" before end of input");
                }
              else
                {
                  throw new Exception(
                                      "JSON STRING ERROR: invalid escape character \\"
                                          + c);
                } // else
              break;
            case '"':
              // string is done
              str_end = true;
              break;
            case -1:
              // reach end of json before ending string
              throw new Exception(
                                  "JSON STRING ERROR: no closing \" before end of input");
            default:
              // add all chars to string
              builder.append((char) c);
              break;
          }// switch(C)
      }// while
    return new JSONString(builder.toString());
  }// String parseString(BufferedReader buffer)

  /**
   * Parse Jason string to JSONArray
   * 
   * @param buffer
   * @pre buffer must start with '[' '[' can not be followed by ','
   * @return JSONArray
   * @throws Exception
   */
  public JSONArray parseArray(BufferedReader buffer)
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
    Vector<JSONValue> vec = new Vector<JSONValue>();
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
                }// if
              else
                {
                  throw new Exception(
                                      "JSON ARRAY ERROR: missplaced comma in array list");
                }
              break;
            case ']':
              array_end = true;
              break;
            case -1:
              // end of buffer before array end
              throw new Exception(
                                  "JSON ARRAY ERROR: no closing ] before end of input");
            default:
              // parse value
              buffer.reset();
              vec.add(parse(buffer));
              value_found = true;
              break;
          }// switch (c)
      }// while
    return new JSONArray(vec);
  }// parseArray(BufferedReader buffer)

  /**
   * Parse Jason string to JSONObject
   * 
   * @param buffer
   * @pre buffer must start with '{' '{' must be followed by type 'string' i.e a
   *      '"' and a ':'
   * @return JSONObject
   * @throws Exception
   */
  public JSONObject parseObject(BufferedReader buffer)
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
    HashMap<JSONString, JSONValue> hash = new HashMap<JSONString, JSONValue>();
    boolean key_found = false;
    boolean value_found = false;
    boolean hash_end = false;
    JSONString key = null;
    JSONValue value = null;
    while (!hash_end)
      {
        buffer.mark(1);
        int c = buffer.read();
        // if key is not found
        switch (c)
          {
            case ',':
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
                  key = (JSONString) parse(buffer);
                  key_found = true;
                } // if
              else
                {
                  throw new Exception("JSON OBJECT ERROR: invalid character "
                                      + c);
                } // else
              break;
            case ':':
              if (key_found && !value_found)
                {
                  value = parse(buffer);
                  value_found = true;
                } // if
              else
                {
                  throw new Exception(
                                      "JSON OBJECT ERROR: missplaced ':', should seperate key:value pairs");
                } // else
              break;
            case '}':
              if (key_found)
                {
                  throw new Exception(
                                      "JSON OBJECT ERROR: unresolved key value pair");
                } // if
              else
                {
                  hash_end = true;
                } // else
              break;
            case -1:
              throw new Exception(
                                  "JSON OBJECT ERROR: no closing } before end of input");
            default:
              throw new Exception("JSON OBJECT ERROR: invalid character " + c);
          } // switch(c)
        if (key_found && value_found)
          {
            hash.put(key, value);
            key_found = false;
            value_found = false;
          } // if
      } // while
    return new JSONObject(hash);
  } // parseObject(BufferedReader buffer)
}// JSONParserAlt
