package edu.grinnell.callaway.jsonvalues;

import java.math.BigDecimal;

public class JSONNumber
    implements
      JSONValue
{
  BigDecimal val;

  public JSONNumber(String numStr) throws Exception
  {
    this.val = new BigDecimal(numStr);
  }

  public String toJSON()
  {
    return this.val.toString();
  }
}
