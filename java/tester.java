import java.io.*;
import java.util.Optional;

class tester { 
  
  public static void main(String [] args ) {
    System.out.println(String.valueOf( "Testing XML parser" ) );
    final String read_code = "<div>\r\n                hello World <span/>\r\n                <span/>\r\n                <ul style=\"color:green;\">\r\n                    <li>Ferrari <span>F50</span></li>\r\n                    <li>Ford</li>\r\n                </ul></div>";
    final SourceCode the_code = new SourceCode(read_code);
    final XMLParser p = new XMLParser(the_code);
    long startTime = System.nanoTime();
    while (p.pull()) {final XMLNode last = p.last();
      System.out.println(String.valueOf( "-> pulled a new node " + last.vref ) );
      final XMLNode last_11 = p.last_finished.get();
      for ( int i_2 = 0; i_2 < last_11.children.size(); i_2++) {
        XMLNode ch = last_11.children.get(i_2);
        if ( ch.value_type == 18 ) {
          System.out.println(String.valueOf( "text : " + ch.string_value ) );
        } else {
          System.out.println(String.valueOf( "child : " + ch.vref ) );
        }
      }
      for ( int i_11 = 0; i_11 < last_11.attrs.size(); i_11++) {
        XMLNode attr_2 = last_11.attrs.get(i_11);
        System.out.println(String.valueOf( (attr_2.vref + " = ") + attr_2.string_value ) );
      }
    }
    final XMLNode last_12 = p.last();
    System.out.println(String.valueOf( "The children of the last node are " + last_12.vref ) );
    for ( int i_13 = 0; i_13 < last_12.children.size(); i_13++) {
      XMLNode ch_8 = last_12.children.get(i_13);
      if ( ch_8.value_type == 18 ) {
        System.out.println(String.valueOf( "text : " + ch_8.string_value ) );
      } else {
        System.out.println(String.valueOf( "child : " + ch_8.vref ) );
      }
    }
    long elapsedTime = System.nanoTime() - startTime;
    System.out.println( "Time for parsing the code:"+ String.valueOf((double)elapsedTime / 1000000000.0));
    System.out.println(String.valueOf( "--- done --- " ) );
  }
}
