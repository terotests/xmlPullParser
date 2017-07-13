import java.util.Optional;
import java.util.*;

class XMLParser { 
  public Optional<SourceCode> code = Optional.empty();
  public Optional<byte[]> buff = Optional.empty();
  public int __len = 0;
  public int i = 0;
  public ArrayList<XMLNode> parents = new ArrayList<XMLNode>();
  public Optional<XMLNode> next = Optional.empty()     /** note: unused */;
  public Optional<XMLNode> rootNode = Optional.empty();
  public Optional<XMLNode> last_parent_safe = Optional.empty();
  public Optional<XMLNode> curr_node = Optional.empty();
  public Optional<XMLNode> last_finished = Optional.empty();
  public int tag_depth = 0;
  
  XMLParser( SourceCode code_module  ) {
    buff = Optional.of(code_module.code.getBytes());
    code = Optional.of(code_module);
    __len = (buff.get()).length;
    i = 0;
  }
  
  public boolean parse_attributes() {
    final byte[] s = buff.get();
    int last_i = 0;
    final boolean do_break = false;
    /** unused:  final String attr_name = ""   **/ ;
    int sp = i;
    int ep = i;
    byte c = 0;
    byte cc1 = 0;
    byte cc2 = 0;
    cc1 = s[i];
    while (i < __len) {
      last_i = i;
      while ((i < __len) && ((s[i]) <= 32)) {
        i = 1 + i;
      }
      cc1 = s[i];
      cc2 = s[(i + 1)];
      if ( i >= __len ) {
        break;
      }
      if ( cc1 == (62) ) {
        return do_break;
      }
      if ( (cc1 == (47)) && (cc2 == (62)) ) {
        i = 2 + i;
        return true;
      }
      sp = i;
      ep = i;
      c = s[i];
      while ((i < __len) && ((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == (95))) || (c == (45)))) {
        i = 1 + i;
        c = s[i];
      }
      i = i - 1;
      final int an_sp = sp;
      final int an_ep = i;
      c = s[i];
      while ((i < __len) && (c != (61))) {
        i = 1 + i;
        c = s[i];
      }
      if ( c == (61) ) {
        i = 1 + i;
      }
      while ((i < __len) && ((s[i]) <= 32)) {
        i = 1 + i;
      }
      if ( i >= __len ) {
        break;
      }
      c = s[i];
      if ( c == 34 ) {
        i = i + 1;
        sp = i;
        ep = i;
        c = s[i];
        while ((i < __len) && (c != 34)) {
          i = 1 + i;
          c = s[i];
        }
        ep = i;
        if ( (i < __len) && (ep > sp) ) {
          final XMLNode new_attr = new XMLNode(code.get(), an_sp, ep);
          new_attr.value_type = 19;
          new_attr.vref = new String(s,an_sp, (an_ep + 1) - an_sp );
          new_attr.string_value = new String(s,sp, ep - sp );
          curr_node.get().attrs.add(new_attr);
        }
        i = 1 + i;
      }
      if ( last_i == i ) {
        i = 1 + i;
      }
    }
    return do_break;
  }
  
  public XMLNode last() {
    return last_finished.get();
  }
  
  public boolean pull() {
    final byte[] s = buff.get();
    byte c = 0;
    /** unused:  final byte next_c = 0   **/ ;
    /** unused:  final byte fc = 0   **/ ;
    /** unused:  final Optional<XMLNode> new_node = Optional.empty()   **/ ;
    int sp = i;
    int ep = i;
    int last_i = 0;
    byte cc1 = 0;
    byte cc2 = 0;
    while (i < __len) {
      last_finished = curr_node;
      last_i = i;
      if ( i >= (__len - 1) ) {
        return false;
      }
      cc1 = s[i];
      cc2 = s[(i + 1)];
      if ( cc1 == (62) ) {
        i = i + 1;
        cc1 = s[i];
        cc2 = s[(i + 1)];
        continue;
      }
      if ( ((47) == cc1) && (cc2 == (62)) ) {
        tag_depth = tag_depth - 1;
        i = i + 2;
        last_finished = curr_node;
        parents.remove(parents.size() - 1);
        final int p_cnt = parents.size();
        if ( 0 == p_cnt ) {
          return false;
        }
        final XMLNode last_parent = parents.get((p_cnt - 1));
        last_parent_safe = Optional.of(last_parent);
        curr_node = Optional.of(last_parent);
        return true;
      }
      if ( i >= __len ) {
        return false;
      }
      if ( ((60) == cc1) && (cc2 == (47)) ) {
        tag_depth = tag_depth - 1;
        i = i + 2;
        sp = i;
        ep = i;
        c = s[i];
        while (((i < __len) && (c > 32)) && (c != (62))) {
          i = 1 + i;
          c = s[i];
        }
        ep = i;
        parents.remove(parents.size() - 1);
        final int p_cnt_1 = parents.size();
        if ( 0 == p_cnt_1 ) {
          return false;
        }
        final XMLNode last_parent_1 = parents.get((p_cnt_1 - 1));
        last_finished = curr_node;
        last_parent_safe = Optional.of(last_parent_1);
        curr_node = Optional.of(last_parent_1);
        return true;
      }
      if ( cc1 == (60) ) {
        i = i + 1;
        sp = i;
        ep = i;
        c = s[i];
        while (((i < __len) && (c != (62))) && (((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == 95)) || (c == 46)) || (c == 64))) {
          i = 1 + i;
          c = s[i];
        }
        ep = i;
        final String new_tag = new String(s,sp, ep - sp );
        if ( !curr_node.isPresent() ) {
          final XMLNode new_rnode = new XMLNode(code.get(), sp, ep);
          new_rnode.vref = new_tag;
          new_rnode.value_type = 17;
          rootNode = Optional.of(new_rnode);
          parents.add(new_rnode);
          curr_node = Optional.of(new_rnode);
        } else {
          final XMLNode new_node_2 = new XMLNode(code.get(), sp, ep);
          new_node_2.vref = new_tag;
          new_node_2.value_type = 17;
          curr_node.get().children.add(new_node_2);
          parents.add(new_node_2);
          new_node_2.parent = curr_node;
          curr_node = Optional.of(new_node_2);
        }
        if ( c == (47) ) {
          continue;
        }
        this.parse_attributes();
        continue;
      }
      if ( curr_node.isPresent() ) {
        sp = i;
        ep = i;
        c = s[i];
        while ((i < __len) && (c != (60))) {
          i = 1 + i;
          c = s[i];
        }
        ep = i;
        if ( ep > sp ) {
          final XMLNode new_node_3 = new XMLNode(code.get(), sp, ep);
          new_node_3.string_value = new String(s,sp, ep - sp );
          new_node_3.value_type = 18;
          curr_node.get().children.add(new_node_3);
        }
      }
      if ( last_i == i ) {
        i = 1 + i;
      }
      if ( i >= (__len - 1) ) {
        return false;
      }
    }
    last_finished = curr_node;
    return true;
  }
}
