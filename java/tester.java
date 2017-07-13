import java.io.*;
import java.util.Optional;

class tester { 
  
  public static void main(String [] args ) {
    System.out.println(String.valueOf( "Testing XML parser" ) );
    final String read_code = "<View padding=\"2px\" margin=\"3px\" background-color=\"#fef6f2\" >\r\n    <View width=\"100%\" padding=\"10px\" id=\"stats1\" >\r\n        <View padding=\"20px\" width=\"dss\" >\r\n        Some text here...\r\n        </View>\r\n        <View padding=\"20px\" width=\"dss\" >\r\n        Some text here...\r\n        </View>\r\n    </View>\r\n</View>";
    final SourceCode the_code = new SourceCode(read_code);
    final XMLParser p = new XMLParser(the_code);
    long startTime = System.nanoTime();
    while (p.pull()) {
      final XMLNode last = p.last();
      System.out.println(String.valueOf( "-> pulled a new node " + last.vref ) );
      final XMLNode last_1 = p.last_finished.get();
      for ( int i = 0; i < last_1.children.size(); i++) {
        XMLNode ch = last_1.children.get(i);
        if ( ch.value_type == 18 ) {
          System.out.println(String.valueOf( "text : " + ch.string_value ) );
        } else {
          System.out.println(String.valueOf( "child : " + ch.vref ) );
        }
      }
      for ( int i_1 = 0; i_1 < last_1.attrs.size(); i_1++) {
        XMLNode attr = last_1.attrs.get(i_1);
        System.out.println(String.valueOf( (attr.vref + " = ") + attr.string_value ) );
      }
    }
    final XMLNode last_2 = p.last();
    System.out.println(String.valueOf( "The children of the last node are " + last_2.vref ) );
    for ( int i_2 = 0; i_2 < last_2.children.size(); i_2++) {
      XMLNode ch_1 = last_2.children.get(i_2);
      if ( ch_1.value_type == 18 ) {
        System.out.println(String.valueOf( "text : " + ch_1.string_value ) );
      } else {
        System.out.println(String.valueOf( "child : " + ch_1.vref ) );
      }
    }
    long elapsedTime = System.nanoTime() - startTime;
    System.out.println( "Time for parsing the code:"+ String.valueOf((double)elapsedTime / 1000000000.0));
    System.out.println(String.valueOf( "--- done --- " ) );
  }
}
