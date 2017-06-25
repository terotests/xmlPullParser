import java.util.Optional;
import java.util.*;

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
}
