import java.util.Optional;
import java.util.*;

class XMLParser { 
  public Optional<SourceCode> code = Optional.empty();
  public Optional<byte[]> buff = Optional.empty();
  public int len = 0;
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
    len = (buff.get()).length;
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
    while (i < len) {
      last_i = i;
      while ((i < len) && ((s[i]) <= 32)) {
        i = 1 + i;
      }
      cc1 = s[i];
      cc2 = s[(i + 1)];
      if ( i >= len ) {
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
      while ((i < len) && ((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == (95))) || (c == (45)))) {
        i = 1 + i;
        c = s[i];
      }
      i = i - 1;
      final int an_sp = sp;
      final int an_ep = i;
      c = s[i];
      while ((i < len) && (c != (61))) {
        i = 1 + i;
        c = s[i];
      }
      if ( c == (61) ) {
        i = 1 + i;
      }
      while ((i < len) && ((s[i]) <= 32)) {
        i = 1 + i;
      }
      if ( i >= len ) {
        break;
      }
      c = s[i];
      if ( c == 34 ) {
        i = i + 1;
        sp = i;
        ep = i;
        c = s[i];
        while ((i < len) && (c != 34)) {
          i = 1 + i;
          c = s[i];
        }
        ep = i;
        if ( (i < len) && (ep > sp) ) {
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
    final byte[] s_4 = buff.get();
    byte c_4 = 0;
    /** unused:  final byte next_c = 0   **/ ;
    /** unused:  final byte fc = 0   **/ ;
    /** unused:  final Optional<XMLNode> new_node = Optional.empty()   **/ ;
    int sp_4 = i;
    int ep_4 = i;
    int last_i_4 = 0;
    byte cc1_4 = 0;
    byte cc2_4 = 0;
    while (i < len) {
      last_finished = curr_node;
      last_i_4 = i;
      if ( i >= (len - 1) ) {
        return false;
      }
      cc1_4 = s_4[i];
      cc2_4 = s_4[(i + 1)];
      if ( cc1_4 == (62) ) {
        i = i + 1;
        cc1_4 = s_4[i];
        cc2_4 = s_4[(i + 1)];
        continue;
      }
      if ( ((47) == cc1_4) && (cc2_4 == (62)) ) {
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
      if ( i >= len ) {
        return false;
      }
      if ( ((60) == cc1_4) && (cc2_4 == (47)) ) {
        tag_depth = tag_depth - 1;
        i = i + 2;
        sp_4 = i;
        ep_4 = i;
        c_4 = s_4[i];
        while (((i < len) && (c_4 > 32)) && (c_4 != (62))) {
          i = 1 + i;
          c_4 = s_4[i];
        }
        ep_4 = i;
        parents.remove(parents.size() - 1);
        final int p_cnt_8 = parents.size();
        if ( 0 == p_cnt_8 ) {
          return false;
        }
        final XMLNode last_parent_8 = parents.get((p_cnt_8 - 1));
        last_finished = curr_node;
        last_parent_safe = Optional.of(last_parent_8);
        curr_node = Optional.of(last_parent_8);
        return true;
      }
      if ( cc1_4 == (60) ) {
        i = i + 1;
        sp_4 = i;
        ep_4 = i;
        c_4 = s_4[i];
        while (((i < len) && (c_4 != (62))) && (((((((c_4 >= 65) && (c_4 <= 90)) || ((c_4 >= 97) && (c_4 <= 122))) || ((c_4 >= 48) && (c_4 <= 57))) || (c_4 == 95)) || (c_4 == 46)) || (c_4 == 64))) {
          i = 1 + i;
          c_4 = s_4[i];
        }
        ep_4 = i;
        final String new_tag = new String(s_4,sp_4, ep_4 - sp_4 );
        if ( !curr_node.isPresent() ) {
          final XMLNode new_rnode = new XMLNode(code.get(), sp_4, ep_4);
          new_rnode.vref = new_tag;
          new_rnode.value_type = 17;
          rootNode = Optional.of(new_rnode);
          parents.add(new_rnode);
          curr_node = Optional.of(new_rnode);
        } else {
          final XMLNode new_node_10 = new XMLNode(code.get(), sp_4, ep_4);
          new_node_10.vref = new_tag;
          new_node_10.value_type = 17;
          curr_node.get().children.add(new_node_10);
          parents.add(new_node_10);
          new_node_10.parent = curr_node;
          curr_node = Optional.of(new_node_10);
        }
        if ( c_4 == (47) ) {
          continue;
        }
        this.parse_attributes();
        continue;
      }
      if ( curr_node.isPresent() ) {
        sp_4 = i;
        ep_4 = i;
        c_4 = s_4[i];
        while ((i < len) && (c_4 != (60))) {
          i = 1 + i;
          c_4 = s_4[i];
        }
        ep_4 = i;
        if ( ep_4 > sp_4 ) {
          final XMLNode new_node_15 = new XMLNode(code.get(), sp_4, ep_4);
          new_node_15.string_value = new String(s_4,sp_4, ep_4 - sp_4 );
          new_node_15.value_type = 18;
          curr_node.get().children.add(new_node_15);
        }
      }
      if ( last_i_4 == i ) {
        i = 1 + i;
      }
      if ( i >= (len - 1) ) {
        return false;
      }
    }
    last_finished = curr_node;
    return true;
  }
}
