/* OpenCommercial.java */

import java.net.*;
import java.io.*;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class OpenCommercial {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();        /* Make sure the line is printed immediately. */
    inputLine = keyboard.readLine();

    /* Replace this comment with your solution.  */
    //URL u = new URL("http://www."+inputLine+".com");
    // Try using the full url instead
    URL u= new URL(inputLine);
    BufferedReader uBf=new BufferedReader(new InputStreamReader(u.openStream()));

    // use a String array to make it print reversely
    String urlLines[];
    int size=10;
    urlLines=new String[size];
    for(int i=0;i<size;i++)
    {
        urlLines[i]=uBf.readLine();
    }
    for(int i=size-1;i>=0;i--) 
    {
        System.out.println(urlLines[i]);
    }

  }
}
