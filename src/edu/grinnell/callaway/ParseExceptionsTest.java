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
  public void parserTest()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON ERROR: no json values found in input");
    parser.parseFromSource("");
  }
  
  /*
  @Test
  public void numberTest2()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NUMBER ERROR(L1:C0): invalid number 15-\n15-\n^");
    parser.parseFromSource("15-");
  }


  @Test
  public void runNumberTest3()
    throws Exception
  {
    thrown.expect(Exception.class);
    thrown.expectMessage("\nJSON NUMBER ERROR(L1:C0): invalid number 13+\n13+\n^");
    parser.parseFromSource("13+");
  }
  */
}
