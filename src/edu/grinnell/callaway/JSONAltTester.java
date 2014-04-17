package edu.grinnell.callaway;

import static org.junit.Assert.*;

import org.junit.Test;

public class JSONAltTester
{
  JSONParserAlt parserAlt = new JSONParserAlt();

  /**
   * Test the alternative parser
   * @throws Exception
   */
  @Test
  public void ParserAltTest() throws Exception
  {
    assertEquals("1. JSON string",
                 "{\"Pro\":{ \"FName\":\"Sam\",\"LName\":\"Rebelsky\" },\"Number\":207,\"Department\":\"CSC\" }",
                 (parserAlt.parse("{\"Department\":\"CSC\", \"Number\":207, \"Pro\":{\"LName\":\"Rebelsky\",\"FName\":\"Sam\"}}")).toJSON());
    assertEquals("2. array", "[1, 2, 3]", (parserAlt.parse("[1,2,3]").toJSON()));
    assertEquals("3. number", "34", (parserAlt.parse("34")).toJSON());
    assertEquals("4. decimal", "34.6", (parserAlt.parse("34.6")).toJSON());
    assertEquals("5. enum", "3E+32", (parserAlt.parse("3e32")).toJSON());
    assertEquals("6. null", "null", (parserAlt.parse("null")).toJSON());
    assertEquals("7. true", "true", (parserAlt.parse("true")).toJSON());
    assertEquals("8. false", "false", (parserAlt.parse("false")).toJSON());
  }

}
