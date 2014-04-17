package edu.grinnell.callaway;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import org.junit.Test;

public class JSONTester
{
  JSONParser parser1 = new JSONParser();
  ToString str = new ToString();

  @Test
  public void toStrTestVals()
    throws Exception
  {
    assertEquals("1. true", "true", str.toStr(true));
    assertEquals("2. false", "false", str.toStr(false));
    assertEquals("3. num", "2.34", str.toStr(2.34));
    assertEquals("4. enum", "2.3E35", str.toStr(23e34));
    assertEquals("5. null", "null", str.toStr(null));
    assertEquals("6. string", "\"a string\"", str.toStr("a string"));
  } // toStrTestVals

  @Test
  public void toStrTestObVec()
    throws Exception
  {
    HashMap<String, Object> hash = new HashMap<String, Object>(8);

    Vector<Serializable> vector = new Vector<Serializable>(3);
    vector.add(1);
    vector.add(2);

    assertEquals("1. vector alone", "[1,2]", str.toStr(vector));

    Vector<Serializable> vector2 = new Vector<Serializable>(3);
    vector2.add(vector);
    vector2.add(2);

    assertEquals("2. vector in vector", "[[1,2],2]", str.toStr(vector2));

    HashMap<String, Object> hmap = new HashMap<String, Object>(3);

    hmap.put("x", "it");
    hmap.put("y", 2.7e-4);

    assertEquals("3. hashMap", "{\"y\":2.7E-4,\"x\":\"it\"}", str.toStr(hmap));

    vector.add(hmap);

    assertEquals("4. hashMap in vector", "[1,2,{\"y\":2.7E-4,\"x\":\"it\"}]",
                 str.toStr(vector));

    hash.put("a", true);
    hash.put("b", 2.7e-4);
    hash.put("c", "The");
    hash.put("j", vector2);
    hash.put("d", vector);
    hash.put("e", null);
    hash.put("f", hmap);

    assertEquals("5. things within things",
                 "{\"f\":{\"y\":2.7E-4,\"x\":\"it\"},\"d\":[1,2,{\"y\":2.7E-4,\"x\":\"it\"}],\"e\":null,\"j\":[[1,2,{\"y\":2.7E-4,\"x\":\"it\"}],2],\"b\":2.7E-4,\"c\":\"The\",\"a\":true}",
                 str.toStr(hash));
  } // toStrTestObVec
} // class JSONTester
