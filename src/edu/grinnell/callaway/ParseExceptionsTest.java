package edu.grinnell.callaway;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParseExceptionsTest
{

  JSONParser parser = new JSONParser();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void noJasonTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR: no json values found in input");
    parser.parseFromSource("");
  }

  @Test
  public void noJasonTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR: no json values found in input");
    parser.parseFromSource(" \t \n \r \b \f ");
  }

  @Test
  public void invalidCharTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR(L1:C0): Invalid input character a\na\n^");
    parser.parseFromSource("a");
  }

  @Test
  public void invalidCharTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR(L1:C0): Invalid input character @\n@\n^");
    parser.parseFromSource("@");
  }

  @Test
  public void invalidCharTest3()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR(L1:C0): Invalid input character }\n}\n^");
    parser.parseFromSource("}");
  }

  @Test
  public void numberTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NUMBER ERROR(L1:C0): invalid number 15-\n15-\n^");
    parser.parseFromSource("15-");
  }

  @Test
  public void numberTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NUMBER ERROR(L1:C0): invalid number 13+\n13+\n^");
    parser.parseFromSource("13+");
  }

  @Test
  public void booleanTrueTest()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON BOOLEAN ERROR(L1:C4): expected input \"true\"\ntruqqqqqq\n    ^");
    parser.parseFromSource("truqqqqqq");
  }

  @Test
  public void booleanFalseTest()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON BOOLEAN ERROR(L1:C3): expected input \"false\"\nfake\n   ^");
    parser.parseFromSource("fake");
  }

  @Test
  public void nullTest()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NULL ERROR(L1:C3): expected input \"null\"\nnunya\n   ^");
    parser.parseFromSource("nunya");
  }
  
  @Test
  public void stringTest1()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON STRING ERROR(L1:C8): no closing \" before end of input\n\"string\\\n        ^");
    parser.parseFromSource("\"string\\");
  }
}
