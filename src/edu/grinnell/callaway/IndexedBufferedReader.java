package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class IndexedBufferedReader
    extends
      BufferedReader
{

  /**
   * the current character location on a line
   */
  int index;

  /**
   * current line of the input
   */
  int line;

  /**
   * marked character to return to with reset
   */
  int markedIndex;

  /**
   * marked line to return to with reset
   */
  int markedLine;

  /**
   * was the last char a newline?
   */
  boolean newline;

  /**
   * current line, stored as a string
   */
  String currentLine;

  public IndexedBufferedReader(Reader stream) throws IOException
  {
    super(stream);
    this.index = -1;
    this.line = 1;
    this.markedIndex = -1;
    this.markedLine = 1;
    this.newline = false;
    this.currentLine = this.lineToString();
  }

  public String lineToString()
    throws IOException
  {
    super.mark(2048);
    String str = readLine();
    super.reset();
    return str;
  }

  @Override
  public int read()
    throws IOException
  {
    // get the next char in the stream
    int read_val = super.read();
    // if we're at the start of a new line, but not the end of the stream
    // reset index/line variables and save the line to a string
    if (this.newline && read_val != -1)
      {
        this.line++;
        this.index = -1;
        this.newline = false;
      }
    // if this is the end of a line, set flag that next char starts a new line
    if (read_val == '\n')
      {
        this.index++;
        this.newline = true;
        this.currentLine = this.lineToString();
      }
    // if we're in the middle of a line, advance
    else if (read_val != -1)
      {
        this.index++;
      }
    return read_val;
  }

  @Override
  public void mark(int readAheadLimit)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public void reset()
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public long skip(long n)
    throws IOException
  {
    int i = 0;
    while (this.read() != -1)
      {
        i++;
      }
    return i;
  }

  @Override
  public int read(char[] cbuf, int off, int len)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public String readLine()
    throws IOException
  {
    StringBuilder line = new StringBuilder();
    int pos = 0;
    int c = super.read();
    while (c != -1 && c != '\n' && pos < 2047)
      {
        line.append((char) c);
        pos++;
        c = super.read();
      }
    return line.toString();
  }

  public int peek()
    throws IOException
  {
    super.mark(1);
    int c = super.read();
    super.reset();
    return c;
  }

  public int index()
  {
    return this.index;
  }

  public int line()
  {
    return this.line;
  }

  public String currentLine()
  {
    return this.currentLine;
  }
}
