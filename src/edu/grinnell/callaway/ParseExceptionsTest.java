package edu.grinnell.callaway;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 
 *  * @author Shaun, Mataire
 * @author Mulhall, Elias
 * @author Callaway, Erin M
 *
 */
public class ParseExceptionsTest
{

  JSONParser parser = new JSONParser();

  // testing exceptions with junit: http://alexruiz.developerblogs.com/?p=1530
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void noJasonTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR: no json values found in input");
    parser.parseFromString("");
  } // noJasonTest1()

  @Test
  public void noJasonTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR: no json values found in input");
    parser.parseFromString(" \t \n \r \b \f ");
  } // noJasonTest2()

  @Test
  public void invalidCharTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR(L1:C0): Invalid input character a\na\n^");
    parser.parseFromString("a");
  } // invalidCharTest1()

  @Test
  public void invalidCharTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR(L1:C0): Invalid input character @\n@\n^");
    parser.parseFromString("@");
  } // invalidCharTest2()

  @Test
  public void invalidCharTest3()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR(L1:C0): Invalid input character }\n}\n^");
    parser.parseFromString("}");
  } // invalidCharTest3()

  @Test
  public void numberTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NUMBER ERROR(L1:C0): invalid number 15-\n15-\n^");
    parser.parseFromString("15-");
  } //numberTest1()

  @Test
  public void numberTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NUMBER ERROR(L1:C0): invalid number 13+\n13+\n^");
    parser.parseFromString("13+");
  } // numberTest2()

  @Test
  public void numberTest3()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NUMBER ERROR(L1:C0): invalid number 13E.5\n13E.5\n^");
    parser.parseFromString("13E.5");
  } // numberTest3()

  @Test
  public void booleanTrueTest()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON BOOLEAN ERROR(L1:C3): expected input \"true\"\ntruqqqqqq\n   ^");
    parser.parseFromString("truqqqqqq");
  } // booleanTrueTest()

  @Test
  public void booleanFalseTest()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON BOOLEAN ERROR(L1:C2): expected input \"false\"\nfake\n  ^");
    parser.parseFromString("fake");
  } // booleanFalseTest()

  @Test
  public void nullTest()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NULL ERROR(L1:C2): expected input \"null\"\nnunya\n  ^");
    parser.parseFromString("nunya");
  } // nullTest()

  @Test
  public void stringTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON STRING ERROR(L1:C6): no closing \" before end of input\n\"string\n      ^");
    parser.parseFromString("\"string");
  } // stringTest1()

  @Test
  public void stringTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON STRING ERROR(L1:C8): invalid escape character \\q\n\"string \\q\"\n        ^");
    parser.parseFromString("\"string \\q\"");
  } // stringTest2()

  @Test
  public void stringTest3()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON STRING ERROR(L1:C1): invalid escape character \\x\n\"\\x\"\n ^");
    parser.parseFromString("\"\\x\"");
  } // stringTest3()

  @Test
  public void stringTest4()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON STRING ERROR(L1:C8): unfinished escape character, no closing \" before end of input\n\"string \\\n        ^");
    parser.parseFromString("\"string \\");
  } // stringTest4()

  @Test
  public void arrayTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ARRAY ERROR(L1:C19): no closing ] before end of input\n[ true, null, false \n                   ^");
    parser.parseFromString("[ true, null, false ");
  } // arrayTest1()

  @Test
  public void objectTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON Object ERROR(L1:C12): no closing } before end of input\n{ \"one\":true \n            ^");
    parser.parseFromString("{ \"one\":true ");
  } // objectTest1()

  @Test
  public void objectTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON OBJECT ERROR(L1:C1): invalid character 'f', expected start of key string\n{ false:true }\n ^");
    parser.parseFromString("{ false:true }");
  } // objectTest2()

  @Test
  public void objectTest3()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON OBJECT ERROR(L1:C8): invalid character '@', expected value for key:value pair\n{ \"test\"@true }\n        ^");
    parser.parseFromString("{ \"test\"@true }");
  } // objectTest3()

} // class ParseExceptionTest
