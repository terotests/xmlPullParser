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
class SourceCode : public std::enable_shared_from_this<SourceCode>  { 
  public :
    std::string code;
    int sp     /** note: unused */;
    int ep     /** note: unused */;
    /* class constructor */ 
    SourceCode( std::string code_str  );
};
class XMLNode : public std::enable_shared_from_this<XMLNode>  { 
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
class XMLParser : public std::enable_shared_from_this<XMLParser>  { 
  public :
    std::shared_ptr<SourceCode> code;
    const char* buff;
    int __len;
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
class tester : public std::enable_shared_from_this<tester>  { 
  public :
    /* class constructor */ 
    tester( );
    /* static methods */ 
    static void m();
};


SourceCode::SourceCode( std::string code_str  ) {
  this->code = std::string("");
  this->sp = 0;
  this->ep = 0;
  code = code_str;
}

XMLNode::XMLNode( std::shared_ptr<SourceCode> source , int start , int end  ) {
  this->sp = 0;
  this->ep = 0;
  this->vref = std::string("");
  this->value_type = 0;
  this->string_value = std::string("");
  code  = source;
  sp = start;
  ep = end;
}

std::string  XMLNode::getString() {
  return code->code.substr(sp, ep - sp);
}

XMLParser::XMLParser( std::shared_ptr<SourceCode> code_module  ) {
  this->__len = 0;
  this->i = 0;
  this->tag_depth = 0;
  buff  = code_module->code.c_str();
  code  = code_module;
  __len = strlen( (buff) );
  i = 0;
}

bool  XMLParser::parse_attributes() {
  const char* s = buff;
  int last_i = 0;
  bool do_break = false;
  /** unused:  std::string attr_name = std::string("")   **/ ;
  int sp = i;
  int ep = i;
  char c = 0;
  char cc1 = 0;
  char cc2 = 0;
  cc1 = s[ i];
  while (i < __len) {
    last_i = i;
    while ((i < __len) && ((s[ i]) <= 32)) {
      i = 1 + i;
    }
    cc1 = s[ i];
    cc2 = s[ (i + 1)];
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
    c = s[ i];
    while ((i < __len) && ((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == (95))) || (c == (45)))) {
      i = 1 + i;
      c = s[ i];
    }
    i = i - 1;
    int an_sp = sp;
    int an_ep = i;
    c = s[ i];
    while ((i < __len) && (c != (61))) {
      i = 1 + i;
      c = s[ i];
    }
    if ( c == (61) ) {
      i = 1 + i;
    }
    while ((i < __len) && ((s[ i]) <= 32)) {
      i = 1 + i;
    }
    if ( i >= __len ) {
      break;
    }
    c = s[ i];
    if ( c == 34 ) {
      i = i + 1;
      sp = i;
      ep = i;
      c = s[ i];
      while ((i < __len) && (c != 34)) {
        i = 1 + i;
        c = s[ i];
      }
      ep = i;
      if ( (i < __len) && (ep > sp) ) {
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
  const char* s = buff;
  char c = 0;
  /** unused:  char next_c = 0   **/ ;
  /** unused:  char fc = 0   **/ ;
  /** unused:  std::shared_ptr<XMLNode> new_node   **/ ;
  int sp = i;
  int ep = i;
  int last_i = 0;
  char cc1 = 0;
  char cc2 = 0;
  while (i < __len) {
    last_finished  = curr_node;
    last_i = i;
    if ( i >= (__len - 1) ) {
      return false;
    }
    cc1 = s[ i];
    cc2 = s[ (i + 1)];
    if ( cc1 == (62) ) {
      i = i + 1;
      cc1 = s[ i];
      cc2 = s[ (i + 1)];
      continue;
    }
    if ( ((47) == cc1) && (cc2 == (62)) ) {
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
    if ( i >= __len ) {
      return false;
    }
    if ( ((60) == cc1) && (cc2 == (47)) ) {
      tag_depth = tag_depth - 1;
      i = i + 2;
      sp = i;
      ep = i;
      c = s[ i];
      while (((i < __len) && (c > 32)) && (c != (62))) {
        i = 1 + i;
        c = s[ i];
      }
      ep = i;
      parents.pop_back();
      int p_cnt_1 = parents.size();
      if ( 0 == p_cnt_1 ) {
        return false;
      }
      std::shared_ptr<XMLNode> last_parent_1 = parents.at( (p_cnt_1 - 1));
      last_finished  = curr_node;
      last_parent_safe  = last_parent_1;
      curr_node  = last_parent_1;
      return true;
    }
    if ( cc1 == (60) ) {
      i = i + 1;
      sp = i;
      ep = i;
      c = s[ i];
      while (((i < __len) && (c != (62))) && (((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == 95)) || (c == 46)) || (c == 64))) {
        i = 1 + i;
        c = s[ i];
      }
      ep = i;
      std::string new_tag = std::string( s + sp, ep - sp );
      if ( curr_node == NULL ) {
        std::shared_ptr<XMLNode> new_rnode =  std::make_shared<XMLNode>(code, sp, ep);
        new_rnode->vref = new_tag;
        new_rnode->value_type = 17;
        rootNode  = new_rnode;
        parents.push_back( new_rnode  );
        curr_node  = new_rnode;
      } else {
        std::shared_ptr<XMLNode> new_node_2 =  std::make_shared<XMLNode>(code, sp, ep);
        new_node_2->vref = new_tag;
        new_node_2->value_type = 17;
        curr_node->children.push_back( new_node_2  );
        parents.push_back( new_node_2  );
        new_node_2->parent  = curr_node;
        curr_node  = new_node_2;
      }
      if ( c == (47) ) {
        continue;
      }
      this->parse_attributes();
      continue;
    }
    if ( curr_node != NULL  ) {
      sp = i;
      ep = i;
      c = s[ i];
      while ((i < __len) && (c != (60))) {
        i = 1 + i;
        c = s[ i];
      }
      ep = i;
      if ( ep > sp ) {
        std::shared_ptr<XMLNode> new_node_3 =  std::make_shared<XMLNode>(code, sp, ep);
        new_node_3->string_value = std::string( s + sp, ep - sp );
        new_node_3->value_type = 18;
        curr_node->children.push_back( new_node_3  );
      }
    }
    if ( last_i == i ) {
      i = 1 + i;
    }
    if ( i >= (__len - 1) ) {
      return false;
    }
  }
  last_finished  = curr_node;
  return true;
}

tester::tester( ) {
}

int main(int argc, char* argv[]) {
  std::cout << std::string("Testing XML parser") << std::endl;
  std::string read_code = std::string("<View padding=\"2px\" margin=\"3px\" background-color=\"#fef6f2\" >\r\n    <View width=\"100%\" padding=\"10px\" id=\"stats1\" >\r\n        <View padding=\"20px\" width=\"dss\" >\r\n        Some text here...\r\n        </View>\r\n        <View padding=\"20px\" width=\"dss\" >\r\n        Some text here...\r\n        </View>\r\n    </View>\r\n</View>");
  std::shared_ptr<SourceCode> the_code =  std::make_shared<SourceCode>(read_code);
  std::shared_ptr<XMLParser> p =  std::make_shared<XMLParser>(the_code);
  std::clock_t __begin = std::clock();
  while (p->pull()) {
    std::shared_ptr<XMLNode> last = p->last();
    std::cout << std::string("-> pulled a new node ") + last->vref << std::endl;
    std::shared_ptr<XMLNode> last_1 = p->last_finished;
    for ( std::vector< std::shared_ptr<XMLNode>>::size_type i = 0; i != last_1->children.size(); i++) {
      std::shared_ptr<XMLNode> ch = last_1->children.at(i);
      if ( ch->value_type == 18 ) {
        std::cout << std::string("text : ") + ch->string_value << std::endl;
      } else {
        std::cout << std::string("child : ") + ch->vref << std::endl;
      }
    }
    for ( std::vector< std::shared_ptr<XMLNode>>::size_type i_1 = 0; i_1 != last_1->attrs.size(); i_1++) {
      std::shared_ptr<XMLNode> attr = last_1->attrs.at(i_1);
      std::cout << (attr->vref + std::string(" = ")) + attr->string_value << std::endl;
    }
  }
  std::shared_ptr<XMLNode> last_2 = p->last();
  std::cout << std::string("The children of the last node are ") + last_2->vref << std::endl;
  for ( std::vector< std::shared_ptr<XMLNode>>::size_type i_2 = 0; i_2 != last_2->children.size(); i_2++) {
    std::shared_ptr<XMLNode> ch_1 = last_2->children.at(i_2);
    if ( ch_1->value_type == 18 ) {
      std::cout << std::string("text : ") + ch_1->string_value << std::endl;
    } else {
      std::cout << std::string("child : ") + ch_1->vref << std::endl;
    }
  }
  std::clock_t __end = std::clock();
  std::cout << std::string("Time for parsing the code:") << ( double(__end - __begin) / CLOCKS_PER_SEC ) << std::endl;
  std::cout << std::string("--- done --- ") << std::endl;
  return 0;
}
