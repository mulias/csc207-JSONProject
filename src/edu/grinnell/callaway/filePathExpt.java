package edu.grinnell.callaway;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class filePathExpt
{
  // http://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java
  // http://stackoverflow.com/questions/14169661/read-complete-file-without-using-loop-in-java
  public static void main(String[] args) throws IOException
  {
    System.out.println("Working Directory = " + System.getProperty("user.dir"));
    String text = new String(Files.readAllBytes(Paths.get("JSONfile.json")), StandardCharsets.UTF_8);
    System.out.println(text);
  }

}
