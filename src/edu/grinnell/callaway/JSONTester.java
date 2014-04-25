package edu.grinnell.callaway;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Vector;

import org.junit.Test;

public class JSONTester
{
  JSONParser parser = new JSONParser();

  /**
   * Test the individual values with toJSONString()
   * 
   * @throws Exception
   */
  @Test
  public void toStringTestVals()
    throws Exception
  {
    assertEquals("1. true", "true", parser.toJSONString(true));
    assertEquals("2. false", "false", parser.toJSONString(false));
    assertEquals("3. num", "2.34", parser.toJSONString(2.34));
    assertEquals("4. enum", "2.3E35", parser.toJSONString(23e34));
    assertEquals("5. small enum", "0.0184", parser.toJSONString(18.4E-3));
    assertEquals("6. +enum", "2.6E30", parser.toJSONString(2.6e+30));
    assertEquals("7. -enum", "2.3E-35", parser.toJSONString(2.3E-35));
    assertEquals("8. null", "null", parser.toJSONString(null));
    assertEquals("9. string", "\"a string\"", parser.toJSONString("a string"));
    assertEquals("10. object", "\"{}\"", parser.toJSONString("{}"));
    assertEquals("11. array", "\"[]\"", parser.toJSONString("[]"));
  } // toStrTestVals

  /**
   * Test Objects and Vectors with toJSONString
   * 
   * @throws Exception
   */
  @Test
  public void toStringTestObVec()
    throws Exception
  {
    HashMap<String, Object> hash = new HashMap<String, Object>(8);

    Vector<Serializable> vector = new Vector<Serializable>(3);
    vector.add(1);
    vector.add(2);

    assertEquals("1. vector alone", "[1,2]", parser.toJSONString(vector));

    Vector<Serializable> vector2 = new Vector<Serializable>(3);
    vector2.add(vector);
    vector2.add(2);

    assertEquals("2. vector in vector", "[[1,2],2]",
                 parser.toJSONString(vector2));

    HashMap<String, Object> hmap = new HashMap<String, Object>(3);

    hmap.put("x", "it");
    hmap.put("y", 2.7e-4);

    assertEquals("3. hashMap", "{\"y\":2.7E-4,\"x\":\"it\"}",
                 parser.toJSONString(hmap));

    vector.add(hmap);

    assertEquals("4. hashMap in vector", "[1,2,{\"y\":2.7E-4,\"x\":\"it\"}]",
                 parser.toJSONString(vector));

    hash.put("a", true);
    hash.put("b", 2.7e-4);
    hash.put("c", "The");
    hash.put("j", vector2);
    hash.put("d", vector);
    hash.put("e", null);
    hash.put("f", hmap);

    assertEquals("5. things within things",
                 "{\"f\":{\"y\":2.7E-4," + "\"x\":\"it\"},"
                     + "\"d\":[1,2,{\"y\":2.7E-4,\"x\":\"it\"}],"
                     + "\"e\":null,"
                     + "\"j\":[[1,2,{\"y\":2.7E-4,\"x\":\"it\"}],2],"
                     + "\"b\":2.7E-4," + "\"c\":\"The\"," + "\"a\":true}",
                 parser.toJSONString(hash));
  } // toStrTestObVec

  /**
   * Check the parser for errors.
   * @throws Exception
   */
  @Test
  public void parseTest()
    throws Exception
  {
    assertEquals("1. JSON string",
                 "{\"Pro\":{\"FName\":\"Sam\",\"LName\":\"Rebelsky\"},"
                     + "\"Number\":207,\"Department\":\"CSC\"}",
                 parser.toJSONString(parser.parseFromSource("{\"Department\":\"CSC\","
                                                            + " \"Number\":207, "
                                                            + "\"Pro\":{\"LName\":\"Rebelsky\","
                                                            + "\"FName\":\"Sam\"}}")));
    assertEquals("2. array", "[1,2,3]",
                 parser.toJSONString(parser.parseFromSource("[1,2,3]")));
    assertEquals("3. number", "34",
                 parser.toJSONString(parser.parseFromSource("34")));
    assertEquals("4. decimal", "34.6",
                 parser.toJSONString(parser.parseFromSource("34.6")));
    assertEquals("5. enum", "3E+32",
                 parser.toJSONString(parser.parseFromSource("3e32")));
    assertEquals("6. null", "null",
                 parser.toJSONString(parser.parseFromSource("null")));
    assertEquals("7. true", "true",
                 parser.toJSONString(parser.parseFromSource("true")));
    assertEquals("8. false", "false",
                 parser.toJSONString(parser.parseFromSource("false")));
    assertEquals("9. negative", "-3.4",
                 parser.toJSONString(parser.parseFromSource("-3.4")));

    assertEquals("10. things within things",
                 "{\"f\":{\"y\":2.9E+57,\"x\":\"it\"},"
                     + "\"e\":null,\"c\":\"The\",\"j\":[1,2]}",
                 parser.toJSONString(parser.parseFromSource("{\"f\":{\"y\":29e56,\"x\":\"it\"},"
                                                            + "\"e\":null,\"j\":[1,2],\"c\":\"The\"}")));
    assertEquals("11. unicode", "\"\u2300\"",
                 parser.toJSONString(parser.parseFromSource("\"\\u2300\"")));

  } // parseTest()

  /**
   * Check that the parser can take and successfully process input from a file.
   * @throws Exception
   */
  @Test
  public void pathTest()
    throws Exception
  {
    // JSON string from-- http://en.wikipedia.org/wiki/JSON
    // http://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java
    // http://stackoverflow.com/questions/14169661/read-complete-file-without-using-loop-in-java

    String text =
        new String(Files.readAllBytes(Paths.get("JSONfileExpected.json")),
                   StandardCharsets.UTF_8);
    assertEquals("path",
                 text,
                 parser.toJSONString(parser.parseFromSource(System.getProperty("user.dir")
                                                            + "/JSONfile.json")));
  } // pathTest()

  /**
   * Check to make sure that the parser can take and process links to JSON files on a website.
   * @throws Exception
   */
  @Test
  public void linkTest()
    throws Exception
  {
    String text =
        new String(Files.readAllBytes(Paths.get("JSONlinkExpected.json")),
                   StandardCharsets.UTF_8);
    assertEquals("link",
                 text,
                 parser.toJSONString(parser.parseFromSource("http://grinnellappdev.com/tutorials/appdev_directory.json")));
  } // linkTest()
} // class JSONTester
