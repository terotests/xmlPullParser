import java.util.Optional;
import java.util.*;
import java.io.*;

class XMLNode { 
  public Optional<SourceCode> code = Optional.empty();
  public int sp = 0;
  public int ep = 0;
  public String vref = "";
  public ArrayList<String> ns = new ArrayList<String>()     /** note: unused */;
  public int value_type = 0;
  public String string_value = "";
  public ArrayList<XMLNode> children = new ArrayList<XMLNode>();
  public ArrayList<XMLNode> attrs = new ArrayList<XMLNode>();
  public Optional<XMLNode> parent = Optional.empty();
  
  XMLNode( SourceCode source , int start , int end  ) {
    code = Optional.of(source);
    sp = start;
    ep = end;
  }
  
  public String getString() {
    return code.get().code.substring(sp, ep );
  }
  
  public void walk() {
    System.out.println(String.valueOf( vref ) );
    for ( int idx = 0; idx < attrs.size(); idx++) {
      XMLNode attr = attrs.get(idx);
      System.out.println(String.valueOf( (((("attr[" + idx) + "] ") + attr.vref) + " = ") + attr.string_value ) );
    }
    for ( int i = 0; i < children.size(); i++) {
      XMLNode item = children.get(i);
      item.walk();
    }
  }
}
