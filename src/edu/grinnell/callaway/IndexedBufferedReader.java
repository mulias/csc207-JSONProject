package edu.grinnell.callaway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 
 * @author Shaun, Mataire
 * @author Mulhall, Elias
 * @author Callaway, Erin M
 */
public class IndexedBufferedReader
    extends BufferedReader
{

  /**
   * The current character location of the buffer on a line
   */
  int index;

  /**
   * The current line location of the buffer
   */
  int line;

  /**
   * was the last char a newline?
   */
  boolean newline;

  /**
   * current line, stored as a string
   */
  String currentLine;

  /**
   * Create a buffering character-input stream that tracks the 
   * current position in the stream, and saves the current line 
   * of text to a String
   * 
   * @param stream - a reader
   * @throws IOException
   */
  public IndexedBufferedReader(Reader stream) throws IOException
  {
    super(stream);
    this.index = -1;
    this.line = 1;
    this.newline = false;
    this.currentLine = this.lineToString();
  } // IndexedBufferedReader(Reader)

  /**
   * Reads through the buffer and saves each char to a string, 
   * until the end of stream or a new line is found. Returns 
   * to the original position in the stream.
   * 
   * @return - a line of text from the buffer
   * @throws IOException
   */
  public String lineToString()
    throws IOException
  {
    super.mark(2048);
    String str = readLine();
    super.reset();
    return str;
  } // lineToString()

  /**
   * Advance to the next char in the stream. Monitors line and index position
   * 
   * @return - The character read, as an integer in the range 0 to 65535 
   * (0x00-0xffff), or -1 if the end of the stream has been reached
   */
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
      } // if (newline)
    // if this is the end of a line, set flag that next char starts a new line
    if (read_val == '\n')
      {
        this.index++;
        this.newline = true;
        this.currentLine = this.lineToString();
      } // else if (n)
    // if we're in the middle of a line, advance
    else if (read_val != -1)
      {
        this.index++;
      } // else if (!=-1)
    return read_val;
  } // read()

  // mark and reset are not supported because saving each line requires marking, 
  // so a mark called through read can interfere with external marks
  @Override
  public void mark(int readAheadLimit)
  {
    throw new UnsupportedOperationException();
  } // mark(int)

  // mark and reset are not supported because saving each line requires marking, 
  // so a mark called through read can interfere with external marks
  @Override
  public void reset()
  {
    throw new UnsupportedOperationException();
  } // reset()

  /**
   * Skip a number of characters in the stream, while keeping track of position
   * 
   * @param n - number of spaces to skip
   * @return - the number of spaces actually skipped
   */
  @Override
  public long skip(long n)
    throws IOException
  {
    int i = 0;
    while (this.read() != -1)
      i++;
    return i;
  } // skip(long)

  @Override
  public int read(char[] cbuf, int off, int len)
  {
    throw new UnsupportedOperationException();
  } // read(char[], int, int)

  /**
   * read characters until a newline or end of file is found, then return a string of all chars read
   * @return - a string of chars on the line
   */
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
      } // while
    return line.toString();
  } // readLine()

  /**
   * Get the next value in the stream, without advancing in the stream
   * 
   * @return - The character read, as an integer in the range 0 to 65535 
   * (0x00-0xffff), or -1 if the end of the stream has been reached
   * @throws IOException
   */
  public int peek()
    throws IOException
  {
    super.mark(1);
    int c = super.read();
    super.reset();
    return c;
  } // peek()

  /**
   * The current character number on a line in the stream
   * @return - current position on line
   */
  public int index()
  {
    return this.index;
  } // index()

  /**
   * The current line number in the stream
   * @return - current line position
   */
  public int line()
  {
    return this.line;
  } // line()

  /**
   * A String or the entire line the buffer is currently on in the stream 
   * @return - contents of current line
   */
  public String currentLine()
  {
    return this.currentLine;
  } // currentLine()
} // class IndexedBufferedReader
