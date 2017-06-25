#include  <string>
#include  <vector>
#include  <iostream>
#include  <ctime>

// define classes here to avoid compiler errors
class SourceCode;
class XMLNode;
class XMLParser;
class tester;

// header definitions
class SourceCode { 
  public :
    std::string code;
    int sp     /** note: unused */;
    int ep     /** note: unused */;
    /* class constructor */ 
    SourceCode( std::string code_str  );
};
class XMLNode { 
  public :
    std::shared_ptr<SourceCode> code;
    int sp;
    int ep;
    std::string vref;
    std::vector<std::string> ns     /** note: unused */;
    int value_type;
    std::string string_value;
    std::vector<std::shared_ptr<XMLNode>> children;
    std::vector<std::shared_ptr<XMLNode>> attrs;
    std::shared_ptr<XMLNode> parent;
    /* class constructor */ 
    XMLNode( std::shared_ptr<SourceCode> source , int start , int end  );
    /* instance methods */ 
    std::string getString();
};
class XMLParser { 
  public :
    std::shared_ptr<SourceCode> code;
    const char* buff;
    int len;
    int i;
    std::vector<std::shared_ptr<XMLNode>> parents;
    std::shared_ptr<XMLNode> next     /** note: unused */;
    std::shared_ptr<XMLNode> rootNode;
    std::shared_ptr<XMLNode> last_parent_safe;
    std::shared_ptr<XMLNode> curr_node;
    std::shared_ptr<XMLNode> last_finished;
    int tag_depth;
    /* class constructor */ 
    XMLParser( std::shared_ptr<SourceCode> code_module  );
    /* instance methods */ 
    bool parse_attributes();
    std::shared_ptr<XMLNode> last();
    bool pull();
};
class tester { 
  public :
    /* class constructor */ 
    tester( );
    /* static methods */ 
    static void m();
};

SourceCode::SourceCode( std::string code_str  ) {
  code = code_str;
}

XMLNode::XMLNode( std::shared_ptr<SourceCode> source , int start , int end  ) {
  code  = source;
  sp = start;
  ep = end;
}

std::string  XMLNode::getString() {
  return code->code.substr(sp, ep - sp);
}

XMLParser::XMLParser( std::shared_ptr<SourceCode> code_module  ) {
  buff  = code_module->code.c_str();
  code  = code_module;
  len = strlen( (buff) );
}

bool  XMLParser::parse_attributes() {
  const char* s = buff;
  int last_i = 0;
  bool do_break = false;
  /** unused:  std::string attr_name = ""   **/ ;
  int sp = i;
  int ep = i;
  char c = 0;
  char cc1 = 0;
  char cc2 = 0;
  cc1 = s[ i];
  while (i < len) {
    last_i = i;
    while ((i < len) && ((s[ i]) <= 32)) {
      i = 1 + i;
    }
    cc1 = s[ i];
    cc2 = s[ (i + 1)];
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
    c = s[ i];
    while ((i < len) && ((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == (95))) || (c == (45)))) {
      i = 1 + i;
      c = s[ i];
    }
    i = i - 1;
    int an_sp = sp;
    int an_ep = i;
    c = s[ i];
    while ((i < len) && (c != (61))) {
      i = 1 + i;
      c = s[ i];
    }
    if ( c == (61) ) {
      i = 1 + i;
    }
    while ((i < len) && ((s[ i]) <= 32)) {
      i = 1 + i;
    }
    if ( i >= len ) {
      break;
    }
    c = s[ i];
    if ( c == 34 ) {
      i = i + 1;
      sp = i;
      ep = i;
      c = s[ i];
      while ((i < len) && (c != 34)) {
        i = 1 + i;
        c = s[ i];
      }
      ep = i;
      if ( (i < len) && (ep > sp) ) {
        std::shared_ptr<XMLNode> new_attr =  std::make_shared<XMLNode>(code, an_sp, ep);
        new_attr->value_type = 19;
        new_attr->vref = std::string( s + an_sp, (an_ep + 1) - an_sp );
        new_attr->string_value = std::string( s + sp, ep - sp );
        curr_node->attrs.push_back( new_attr  );
      }
      i = 1 + i;
    }
    if ( last_i == i ) {
      i = 1 + i;
    }
  }
  return do_break;
}

std::shared_ptr<XMLNode>  XMLParser::last() {
  return last_finished;
}

bool  XMLParser::pull() {
  const char* s_4 = buff;
  char c_4 = 0;
  /** unused:  char next_c = 0   **/ ;
  /** unused:  char fc = 0   **/ ;
  /** unused:  std::shared_ptr<XMLNode> new_node   **/ ;
  int sp_4 = i;
  int ep_4 = i;
  int last_i_4 = 0;
  char cc1_4 = 0;
  char cc2_4 = 0;
  while (i < len) {
    last_finished  = curr_node;
    last_i_4 = i;
    if ( i >= (len - 1) ) {
      return false;
    }
    cc1_4 = s_4[ i];
    cc2_4 = s_4[ (i + 1)];
    if ( cc1_4 == (62) ) {
      i = i + 1;
      cc1_4 = s_4[ i];
      cc2_4 = s_4[ (i + 1)];
      continue;
    }
    if ( ((47) == cc1_4) && (cc2_4 == (62)) ) {
      tag_depth = tag_depth - 1;
      i = i + 2;
      last_finished  = curr_node;
      parents.pop_back();
      int p_cnt = parents.size();
      if ( 0 == p_cnt ) {
        return false;
      }
      std::shared_ptr<XMLNode> last_parent = parents.at( (p_cnt - 1));
      last_parent_safe  = last_parent;
      curr_node  = last_parent;
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
      c_4 = s_4[ i];
      while (((i < len) && (c_4 > 32)) && (c_4 != (62))) {
        i = 1 + i;
        c_4 = s_4[ i];
      }
      ep_4 = i;
      parents.pop_back();
      int p_cnt_8 = parents.size();
      if ( 0 == p_cnt_8 ) {
        return false;
      }
      std::shared_ptr<XMLNode> last_parent_8 = parents.at( (p_cnt_8 - 1));
      last_finished  = curr_node;
      last_parent_safe  = last_parent_8;
      curr_node  = last_parent_8;
      return true;
    }
    if ( cc1_4 == (60) ) {
      i = i + 1;
      sp_4 = i;
      ep_4 = i;
      c_4 = s_4[ i];
      while (((i < len) && (c_4 != (62))) && (((((((c_4 >= 65) && (c_4 <= 90)) || ((c_4 >= 97) && (c_4 <= 122))) || ((c_4 >= 48) && (c_4 <= 57))) || (c_4 == 95)) || (c_4 == 46)) || (c_4 == 64))) {
        i = 1 + i;
        c_4 = s_4[ i];
      }
      ep_4 = i;
      std::string new_tag = std::string( s_4 + sp_4, ep_4 - sp_4 );
      if ( curr_node == NULL ) {
        std::shared_ptr<XMLNode> new_rnode =  std::make_shared<XMLNode>(code, sp_4, ep_4);
        new_rnode->vref = new_tag;
        new_rnode->value_type = 17;
        rootNode  = new_rnode;
        parents.push_back( new_rnode  );
        curr_node  = new_rnode;
      } else {
        std::shared_ptr<XMLNode> new_node_10 =  std::make_shared<XMLNode>(code, sp_4, ep_4);
        new_node_10->vref = new_tag;
        new_node_10->value_type = 17;
        curr_node->children.push_back( new_node_10  );
        parents.push_back( new_node_10  );
        new_node_10->parent  = curr_node;
        curr_node  = new_node_10;
      }
      if ( c_4 == (47) ) {
        continue;
      }
      this->parse_attributes();
      continue;
    }
    if ( curr_node != NULL  ) {
      sp_4 = i;
      ep_4 = i;
      c_4 = s_4[ i];
      while ((i < len) && (c_4 != (60))) {
        i = 1 + i;
        c_4 = s_4[ i];
      }
      ep_4 = i;
      if ( ep_4 > sp_4 ) {
        std::shared_ptr<XMLNode> new_node_15 =  std::make_shared<XMLNode>(code, sp_4, ep_4);
        new_node_15->string_value = std::string( s_4 + sp_4, ep_4 - sp_4 );
        new_node_15->value_type = 18;
        curr_node->children.push_back( new_node_15  );
      }
    }
    if ( last_i_4 == i ) {
      i = 1 + i;
    }
    if ( i >= (len - 1) ) {
      return false;
    }
  }
  last_finished  = curr_node;
  return true;
}

tester::tester( ) {
}

int main(int argc, char* argv[]) {
  std::cout << "Testing XML parser" << std::endl;
  std::string read_code = "<div>\r\n                hello World <span/>\r\n                <span/>\r\n                <ul style=\"color:green;\">\r\n                    <li>Ferrari <span>F50</span></li>\r\n                    <li>Ford</li>\r\n                </ul></div>";
  std::shared_ptr<SourceCode> the_code =  std::make_shared<SourceCode>(read_code);
  std::shared_ptr<XMLParser> p =  std::make_shared<XMLParser>(the_code);
  std::clock_t __begin = std::clock();
  while (p->pull()) {std::shared_ptr<XMLNode> last = p->last();
    std::cout << "-> pulled a new node " + last->vref << std::endl;
    std::shared_ptr<XMLNode> last_11 = p->last_finished;
    for ( std::vector< std::shared_ptr<XMLNode>>::size_type i = 0; i != last_11->children.size(); i++) {
      std::shared_ptr<XMLNode> ch = last_11->children.at(i);
      if ( ch->value_type == 18 ) {
        std::cout << "text : " + ch->string_value << std::endl;
      } else {
        std::cout << "child : " + ch->vref << std::endl;
      }
    }
    for ( std::vector< std::shared_ptr<XMLNode>>::size_type i_10 = 0; i_10 != last_11->attrs.size(); i_10++) {
      std::shared_ptr<XMLNode> attr = last_11->attrs.at(i_10);
      std::cout << (attr->vref + " = ") + attr->string_value << std::endl;
    }
  }
  std::shared_ptr<XMLNode> last_12 = p->last();
  std::cout << "The children of the last node are " + last_12->vref << std::endl;
  for ( std::vector< std::shared_ptr<XMLNode>>::size_type i_12 = 0; i_12 != last_12->children.size(); i_12++) {
    std::shared_ptr<XMLNode> ch_8 = last_12->children.at(i_12);
    if ( ch_8->value_type == 18 ) {
      std::cout << "text : " + ch_8->string_value << std::endl;
    } else {
      std::cout << "child : " + ch_8->vref << std::endl;
    }
  }
  std::clock_t __end = std::clock();
  std::cout << "Time for parsing the code:" << ( double(__end - __begin) / CLOCKS_PER_SEC ) << std::endl;
  std::cout << "--- done --- " << std::endl;
  return 0;
}
