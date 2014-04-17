package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

/**
 * Parse JSON string to java object(HashTable)
 * @author Shaun, Mataire
 * @author Mulhall, Elias
 * @author Callaway, Erin M
 *
 */
public class JSONParser1
{

  public JSONParser1()
  {

  }

  /**
   * parse JSON string to java Object
   * @param buffer
   * @return
   * Hashtable object
   * @pre
   * buffer is not null
   * @post
   * the JSON string/bufferedreader has been passed to a Java HasTable object
   * @throws Exception
   */
  public Object parse(String str)
    throws Exception
  {
    BufferedReader text = new BufferedReader(new StringReader(str));
    return this.parse(text);
  }//Object parse(String str)

  /**
   * parse JSON string to java Object
   * @param buffer
   * @return
   * Hashtable object
   * @pre
   * buffer is not null
   * @post
   * the JSON string/bufferedreader has been passed to a Java HasTable object
   * @throws Exception
   */
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
          }//switch(c)
      }//while
    throw new Exception("2");
  }// Object parse(BufferedReader buffer)

  /**
   * Parse JSON string/bufferedReader into a java BigDecimal 
   * @pre
   * buffer is an element of REAL NUMBERS
   * @post
   * string/bufferedreader is parsed to java BigDecimal
   * @param buffer
   * @throws Exception
   */
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
          }//switch (n)
      }//while(!num_end)
    try
      {
        return new BigDecimal(builder.toString());
      }//try
    catch (NumberFormatException e)
      {
        throw new Exception(e);
      }//catch
  }//BigDecimal parseNum(BufferedReader buffer)
  
  
  /**
   * Parse JSON string to java null 
   * @param buffer
   * @pre buffer must be "null"
   * @return
   * @throws Exception
   */

  public Object parseNull(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'null', return null
    // otherwise throw exception
    if (buffer.read() == 'n' && buffer.read() == 'u' && buffer.read() == 'l'
        && buffer.read() == 'l')
      {
        return null;
      }//if
    else
      {
        throw new Exception("Precondtion not met: input string not \"null\"");
      }//else
  }//Object parseNull(BufferedReader buffer)
  
  
  /**
   * Parse JSON to java boolean (true)
   * @param buffer
   * @pre buffer must be "true"
   * @throws Exception
   */

  public boolean parseTrue(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
    if (buffer.read() == 't' && buffer.read() == 'r' && buffer.read() == 'u'
        && buffer.read() == 'e')
      {
        return true;
      }//if
    else
      {
        throw new Exception("Precondition preconditon not met: buffer not \"true\"");
      }//else
  }//parseTrue(BufferedReader buffer)

  
  /**
   * Parse JSON to java boolean (false)
   * @param buffer
   * @pre buffer must be "false"
   * @throws Exception
   */
  public boolean parseFalse(BufferedReader buffer)
    throws Exception
  {
    // if the next chars spell out 'true', return true
    // otherwise throw exception
    if (buffer.read() == 'f' && buffer.read() == 'a' && buffer.read() == 'l'
        && buffer.read() == 's' && buffer.read() == 'e')
      {
        return false;
      }//if
    else
      {
        throw new Exception("Precondition preconditon not met: buffer not \"false\"");
      }//else
  }//parseFalse(BufferedReader buffer)

  
  /**
   * Parse JSON string
   * @pre buffer is 'chain'(string) of characters and must start with either '"' or '\'
   * @param buffer
   * @return Java string
   * @throws Exception
   */
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
                    throw new Exception(c+" not \" or \\ ");
                }//switch(c)
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
          }//switch(C)
      }//while
    return builder.toString();
  }//String parseString(BufferedReader buffer)

  
  /**
   * Parse Jason Array
   * @param buffer
   * @pre 
   * buffer must start with '['
   * '[' can not be followed by ','
   * @return
   * Vector
   * @throws Exception
   */
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
                }//if
              else
                {
                  throw new Exception(" ',' not found");
                }//else
              break;
            case ']':
              if (value_found)
                {
                  array_end = true;
                }//if
              else
                {
                  // last char was ',' bad syntax
                  throw new Exception("bad syntax: last character is '");
                }//else
              break;
            case -1:
              // end of buffer before array end
              throw new Exception("Array not closed");
            default:
              // parse value
              buffer.reset();
              vec.add(parse(buffer));
              value_found = true;
              break;
          }//switch (c)
      }//while
    return vec;
  }//parseArray(BufferedReader buffer)

  /**
   * Parse Jason Array
   * @param buffer
   * @pre 
   * buffer must start with '{'
   * '{' must be followed by type 'string' i.e a '"' and a ':'
   * @return
   * HashMap
   * @throws Exception
   */
  
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
                }//if
              else
                {
                  throw new Exception("Precondition not met: '{' not followed by type string i.e. '\"' ");
                }//else
              break;
            case ':':
              if (key_found && !value_found)
                {
                  value = parse(buffer);
                  value_found = true;
                }//if
              else
                {
                  throw new Exception("Precondition not met: type string not followed by a ':'");
                }//else
              break;
            case '}':
              if (key_found)
                {
                  throw new Exception("unresolved key value pair");
                }//if
              else
                {
                  hash_end = true;
                }//else
              break;
            case -1:
              throw new Exception("Object not closed, expected '}'");
            default:
              throw new Exception("");
          }//switch(c)
        if (key_found && value_found)
          {
            hash.put(key, value);
            key_found = false;
            value_found = false;
          }//if
      }//while
    return hash;
  }//parseObject(BufferedReader buffer)

  
  /**
   * Experiment
   * @param args
   * @throws Exception
   */
  public static void main(String[] args)
    throws Exception
  {
    JSONParser1 parser = new JSONParser1();
    ToString str = new ToString();
    Object val =
        parser.parse("{ \"test\":false \"One\":2 \"obJ\":{ \"1\":1e34 } \"arr\":[ 1, 2, 3, true, null ] }");
    System.out.println(str.toStr(val));
  }//main(string[]
}//JSONParse1
