import java.io.*;
import java.util.Optional;
import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class tester { 
  
  static String readFile(String path, Charset encoding) 
  {
    try {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
    } catch(IOException e) { 
      return "";
    }
  }    
      
  
  public static void main(String [] args ) {
    System.out.println(String.valueOf( "Testing XML parser" ) );
    final String read_code = (Optional.of(readFile("." + "/" + "testCode.xml" , StandardCharsets.UTF_8 ))).get();
    final SourceCode the_code = new SourceCode(read_code);
    final XMLParser p = new XMLParser(the_code);
    long startTime = System.nanoTime();
    int node_cnt = 0;
    int text_cnt = 0;
    while (p.pull()) {
      /** unused:  final XMLNode last = p.last()   **/ ;
      final XMLNode last_1 = p.last_finished.get();
      for ( int i = 0; i < last_1.children.size(); i++) {
        XMLNode ch = last_1.children.get(i);
        if ( ch.value_type == 18 ) {
          node_cnt = node_cnt + 1;
        } else {
          text_cnt = text_cnt + 1;
        }
      }
    }
    final XMLNode last_2 = p.last();
    System.out.println(String.valueOf( "Last node was" + last_2.vref ) );
    System.out.println(String.valueOf( ((("Collected " + node_cnt) + " nodes and ") + text_cnt) + " text nodes" ) );
    long elapsedTime = System.nanoTime() - startTime;
    System.out.println( "Time for parsing the code:"+ String.valueOf((double)elapsedTime / 1000000000.0));
    System.out.println(String.valueOf( "--- done --- " ) );
  }
}
