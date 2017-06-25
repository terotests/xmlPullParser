package main
import (
)

type GoNullable struct { 
  value interface{}
  has_value bool
}

type SourceCode struct { 
  code string
  sp int64 /**  unused  **/ 
  ep int64 /**  unused  **/ 
}
type IFACE_SourceCode interface { 
  Get_code() string
  Set_code(value string) 
  Get_sp() int64
  Set_sp(value int64) 
  Get_ep() int64
  Set_ep(value int64) 
}

func CreateNew_SourceCode(code_str string) *SourceCode {
  me := new(SourceCode)
  me.code = ""
  me.sp = 0
  me.ep = 0
  me.code = code_str; 
  return me;
}
// getter for variable code
func (this *SourceCode) Get_code() string {
  return this.code
}
// setter for variable code
func (this *SourceCode) Set_code( value string)  {
  this.code = value 
}
// getter for variable sp
func (this *SourceCode) Get_sp() int64 {
  return this.sp
}
// setter for variable sp
func (this *SourceCode) Set_sp( value int64)  {
  this.sp = value 
}
// getter for variable ep
func (this *SourceCode) Get_ep() int64 {
  return this.ep
}
// setter for variable ep
func (this *SourceCode) Set_ep( value int64)  {
  this.ep = value 
}
type XMLNode struct { 
  code *GoNullable
  sp int64
  ep int64
  vref string
  ns []string /**  unused  **/ 
  value_type int64
  string_value string
  children []*XMLNode
  attrs []*XMLNode
  parent *GoNullable
}
type IFACE_XMLNode interface { 
  Get_code() *GoNullable
  Set_code(value *GoNullable) 
  Get_sp() int64
  Set_sp(value int64) 
  Get_ep() int64
  Set_ep(value int64) 
  Get_vref() string
  Set_vref(value string) 
  Get_ns() []string
  Set_ns(value []string) 
  Get_value_type() int64
  Set_value_type(value int64) 
  Get_string_value() string
  Set_string_value(value string) 
  Get_children() []*XMLNode
  Set_children(value []*XMLNode) 
  Get_attrs() []*XMLNode
  Set_attrs(value []*XMLNode) 
  Get_parent() *GoNullable
  Set_parent(value *GoNullable) 
  getString() string
}

func CreateNew_XMLNode(source *SourceCode, start int64, end int64) *XMLNode {
  me := new(XMLNode)
  me.sp = 0
  me.ep = 0
  me.vref = ""
  me.ns = make([]string,0)
  me.value_type = 0
  me.string_value = ""
  me.children = make([]*XMLNode,0)
  me.attrs = make([]*XMLNode,0)
  me.code = new(GoNullable);
  me.parent = new(GoNullable);
  me.code.value = source;
  me.code.has_value = true; /* detected as non-optional */
  me.sp = start; 
  me.ep = end; 
  return me;
}
func (this *XMLNode) getString () string {
  return this.code.value.(*SourceCode).code[this.sp:this.ep];
}
// getter for variable code
func (this *XMLNode) Get_code() *GoNullable {
  return this.code
}
// setter for variable code
func (this *XMLNode) Set_code( value *GoNullable)  {
  this.code = value 
}
// getter for variable sp
func (this *XMLNode) Get_sp() int64 {
  return this.sp
}
// setter for variable sp
func (this *XMLNode) Set_sp( value int64)  {
  this.sp = value 
}
// getter for variable ep
func (this *XMLNode) Get_ep() int64 {
  return this.ep
}
// setter for variable ep
func (this *XMLNode) Set_ep( value int64)  {
  this.ep = value 
}
// getter for variable vref
func (this *XMLNode) Get_vref() string {
  return this.vref
}
// setter for variable vref
func (this *XMLNode) Set_vref( value string)  {
  this.vref = value 
}
// getter for variable ns
func (this *XMLNode) Get_ns() []string {
  return this.ns
}
// setter for variable ns
func (this *XMLNode) Set_ns( value []string)  {
  this.ns = value 
}
// getter for variable value_type
func (this *XMLNode) Get_value_type() int64 {
  return this.value_type
}
// setter for variable value_type
func (this *XMLNode) Set_value_type( value int64)  {
  this.value_type = value 
}
// getter for variable string_value
func (this *XMLNode) Get_string_value() string {
  return this.string_value
}
// setter for variable string_value
func (this *XMLNode) Set_string_value( value string)  {
  this.string_value = value 
}
// getter for variable children
func (this *XMLNode) Get_children() []*XMLNode {
  return this.children
}
// setter for variable children
func (this *XMLNode) Set_children( value []*XMLNode)  {
  this.children = value 
}
// getter for variable attrs
func (this *XMLNode) Get_attrs() []*XMLNode {
  return this.attrs
}
// setter for variable attrs
func (this *XMLNode) Set_attrs( value []*XMLNode)  {
  this.attrs = value 
}
// getter for variable parent
func (this *XMLNode) Get_parent() *GoNullable {
  return this.parent
}
// setter for variable parent
func (this *XMLNode) Set_parent( value *GoNullable)  {
  this.parent = value 
}
type XMLParser struct { 
  code *GoNullable
  buff *GoNullable
  len int64
  i int64
  parents []*XMLNode
  next *GoNullable /**  unused  **/ 
  rootNode *GoNullable
  last_parent_safe *GoNullable
  curr_node *GoNullable
  last_finished *GoNullable
  tag_depth int64
}
type IFACE_XMLParser interface { 
  Get_code() *GoNullable
  Set_code(value *GoNullable) 
  Get_buff() *GoNullable
  Set_buff(value *GoNullable) 
  Get_len() int64
  Set_len(value int64) 
  Get_i() int64
  Set_i(value int64) 
  Get_parents() []*XMLNode
  Set_parents(value []*XMLNode) 
  Get_next() *GoNullable
  Set_next(value *GoNullable) 
  Get_rootNode() *GoNullable
  Set_rootNode(value *GoNullable) 
  Get_last_parent_safe() *GoNullable
  Set_last_parent_safe(value *GoNullable) 
  Get_curr_node() *GoNullable
  Set_curr_node(value *GoNullable) 
  Get_last_finished() *GoNullable
  Set_last_finished(value *GoNullable) 
  Get_tag_depth() int64
  Set_tag_depth(value int64) 
  parse_attributes() bool
  last() *XMLNode
  pull() bool
}

func CreateNew_XMLParser(code_module *SourceCode) *XMLParser {
  me := new(XMLParser)
  me.len = 0
  me.i = 0
  me.parents = make([]*XMLNode,0)
  me.tag_depth = 0
  me.code = new(GoNullable);
  me.buff = new(GoNullable);
  me.next = new(GoNullable);
  me.rootNode = new(GoNullable);
  me.last_parent_safe = new(GoNullable);
  me.curr_node = new(GoNullable);
  me.last_finished = new(GoNullable);
  me.buff.value = []byte(code_module.code);
  me.buff.has_value = true; /* detected as non-optional */
  me.code.value = code_module;
  me.code.has_value = true; /* detected as non-optional */
  me.len = int64(len((me.buff.value.([]byte)))); 
  return me;
}
func (this *XMLParser) parse_attributes () bool {
  var s []byte = this.buff.value.([]byte);
  var last_i int64 = 0;
  var do_break bool = false;
  /** unused:  attr_name*/
  var sp int64 = this.i;
  var ep int64 = this.i;
  var c byte = 0;
  var cc1 byte = 0;
  var cc2 byte = 0;
  cc1 = s[this.i]; 
  for this.i < this.len {
    last_i = this.i; 
    for (this.i < this.len) && ((s[this.i]) <= 32) {
      this.i = 1 + this.i; 
    }
    cc1 = s[this.i]; 
    cc2 = s[(this.i + 1)]; 
    if  this.i >= this.len {
      break;
    }
    if  cc1 == (62) {
      return do_break;
    }
    if  (cc1 == (47)) && (cc2 == (62)) {
      this.i = 2 + this.i; 
      return true;
    }
    sp = this.i; 
    ep = this.i; 
    c = s[this.i]; 
    for (this.i < this.len) && ((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == (95))) || (c == (45))) {
      this.i = 1 + this.i; 
      c = s[this.i]; 
    }
    this.i = this.i - 1; 
    var an_sp int64 = sp;
    var an_ep int64 = this.i;
    c = s[this.i]; 
    for (this.i < this.len) && (c != (61)) {
      this.i = 1 + this.i; 
      c = s[this.i]; 
    }
    if  c == (61) {
      this.i = 1 + this.i; 
    }
    for (this.i < this.len) && ((s[this.i]) <= 32) {
      this.i = 1 + this.i; 
    }
    if  this.i >= this.len {
      break;
    }
    c = s[this.i]; 
    if  c == 34 {
      this.i = this.i + 1; 
      sp = this.i; 
      ep = this.i; 
      c = s[this.i]; 
      for (this.i < this.len) && (c != 34) {
        this.i = 1 + this.i; 
        c = s[this.i]; 
      }
      ep = this.i; 
      if  (this.i < this.len) && (ep > sp) {
        var new_attr *XMLNode = CreateNew_XMLNode(this.code.value.(*SourceCode), an_sp, ep);
        new_attr.value_type = 19; 
        new_attr.vref = fmt.Sprintf("%s", s[an_sp:(an_ep + 1)]); 
        new_attr.string_value = fmt.Sprintf("%s", s[sp:ep]); 
        this.curr_node.value.(*XMLNode).attrs = append(this.curr_node.value.(*XMLNode).attrs,new_attr); 
      }
      this.i = 1 + this.i; 
    }
    if  last_i == this.i {
      this.i = 1 + this.i; 
    }
  }
  return do_break;
}
func (this *XMLParser) last () *XMLNode {
  return this.last_finished.value.(*XMLNode);
}
func (this *XMLParser) pull () bool {
  var s_4 []byte = this.buff.value.([]byte);
  var c_4 byte = 0;
  /** unused:  next_c*/
  /** unused:  fc*/
  /** unused:  new_node*/
  var sp_4 int64 = this.i;
  var ep_4 int64 = this.i;
  var last_i_4 int64 = 0;
  var cc1_4 byte = 0;
  var cc2_4 byte = 0;
  for this.i < this.len {
    this.last_finished.value = this.curr_node.value;
    this.last_finished.has_value = this.curr_node.has_value; 
    last_i_4 = this.i; 
    if  this.i >= (this.len - 1) {
      return false;
    }
    cc1_4 = s_4[this.i]; 
    cc2_4 = s_4[(this.i + 1)]; 
    if  cc1_4 == (62) {
      this.i = this.i + 1; 
      cc1_4 = s_4[this.i]; 
      cc2_4 = s_4[(this.i + 1)]; 
      continue;
    }
    if  ((47) == cc1_4) && (cc2_4 == (62)) {
      this.tag_depth = this.tag_depth - 1; 
      this.i = this.i + 2; 
      this.last_finished.value = this.curr_node.value;
      this.last_finished.has_value = this.curr_node.has_value; 
      this.parents = this.parents[:len(this.parents)-1]; 
      var p_cnt int64 = int64(len(this.parents));
      if  0 == p_cnt {
        return false;
      }
      var last_parent *XMLNode = this.parents[(p_cnt - 1)];
      this.last_parent_safe.value = last_parent;
      this.last_parent_safe.has_value = true; /* detected as non-optional */
      this.curr_node.value = last_parent;
      this.curr_node.has_value = true; /* detected as non-optional */
      return true;
    }
    if  this.i >= this.len {
      return false;
    }
    if  ((60) == cc1_4) && (cc2_4 == (47)) {
      this.tag_depth = this.tag_depth - 1; 
      this.i = this.i + 2; 
      sp_4 = this.i; 
      ep_4 = this.i; 
      c_4 = s_4[this.i]; 
      for ((this.i < this.len) && (c_4 > 32)) && (c_4 != (62)) {
        this.i = 1 + this.i; 
        c_4 = s_4[this.i]; 
      }
      ep_4 = this.i; 
      this.parents = this.parents[:len(this.parents)-1]; 
      var p_cnt_8 int64 = int64(len(this.parents));
      if  0 == p_cnt_8 {
        return false;
      }
      var last_parent_8 *XMLNode = this.parents[(p_cnt_8 - 1)];
      this.last_finished.value = this.curr_node.value;
      this.last_finished.has_value = this.curr_node.has_value; 
      this.last_parent_safe.value = last_parent_8;
      this.last_parent_safe.has_value = true; /* detected as non-optional */
      this.curr_node.value = last_parent_8;
      this.curr_node.has_value = true; /* detected as non-optional */
      return true;
    }
    if  cc1_4 == (60) {
      this.i = this.i + 1; 
      sp_4 = this.i; 
      ep_4 = this.i; 
      c_4 = s_4[this.i]; 
      for ((this.i < this.len) && (c_4 != (62))) && (((((((c_4 >= 65) && (c_4 <= 90)) || ((c_4 >= 97) && (c_4 <= 122))) || ((c_4 >= 48) && (c_4 <= 57))) || (c_4 == 95)) || (c_4 == 46)) || (c_4 == 64)) {
        this.i = 1 + this.i; 
        c_4 = s_4[this.i]; 
      }
      ep_4 = this.i; 
      var new_tag string = fmt.Sprintf("%s", s_4[sp_4:ep_4]);
      if  !this.curr_node.has_value  {
        var new_rnode *XMLNode = CreateNew_XMLNode(this.code.value.(*SourceCode), sp_4, ep_4);
        new_rnode.vref = new_tag; 
        new_rnode.value_type = 17; 
        this.rootNode.value = new_rnode;
        this.rootNode.has_value = true; /* detected as non-optional */
        this.parents = append(this.parents,new_rnode); 
        this.curr_node.value = new_rnode;
        this.curr_node.has_value = true; /* detected as non-optional */
      } else {
        var new_node_10 *XMLNode = CreateNew_XMLNode(this.code.value.(*SourceCode), sp_4, ep_4);
        new_node_10.vref = new_tag; 
        new_node_10.value_type = 17; 
        this.curr_node.value.(*XMLNode).children = append(this.curr_node.value.(*XMLNode).children,new_node_10); 
        this.parents = append(this.parents,new_node_10); 
        new_node_10.parent.value = this.curr_node.value;
        new_node_10.parent.has_value = this.curr_node.has_value; 
        this.curr_node.value = new_node_10;
        this.curr_node.has_value = true; /* detected as non-optional */
      }
      if  c_4 == (47) {
        continue;
      }
      this.parse_attributes();
      continue;
    }
    if  this.curr_node.has_value {
      sp_4 = this.i; 
      ep_4 = this.i; 
      c_4 = s_4[this.i]; 
      for (this.i < this.len) && (c_4 != (60)) {
        this.i = 1 + this.i; 
        c_4 = s_4[this.i]; 
      }
      ep_4 = this.i; 
      if  ep_4 > sp_4 {
        var new_node_15 *XMLNode = CreateNew_XMLNode(this.code.value.(*SourceCode), sp_4, ep_4);
        new_node_15.string_value = fmt.Sprintf("%s", s_4[sp_4:ep_4]); 
        new_node_15.value_type = 18; 
        this.curr_node.value.(*XMLNode).children = append(this.curr_node.value.(*XMLNode).children,new_node_15); 
      }
    }
    if  last_i_4 == this.i {
      this.i = 1 + this.i; 
    }
    if  this.i >= (this.len - 1) {
      return false;
    }
  }
  this.last_finished.value = this.curr_node.value;
  this.last_finished.has_value = this.curr_node.has_value; 
  return true;
}
// getter for variable code
func (this *XMLParser) Get_code() *GoNullable {
  return this.code
}
// setter for variable code
func (this *XMLParser) Set_code( value *GoNullable)  {
  this.code = value 
}
// getter for variable buff
func (this *XMLParser) Get_buff() *GoNullable {
  return this.buff
}
// setter for variable buff
func (this *XMLParser) Set_buff( value *GoNullable)  {
  this.buff = value 
}
// getter for variable len
func (this *XMLParser) Get_len() int64 {
  return this.len
}
// setter for variable len
func (this *XMLParser) Set_len( value int64)  {
  this.len = value 
}
// getter for variable i
func (this *XMLParser) Get_i() int64 {
  return this.i
}
// setter for variable i
func (this *XMLParser) Set_i( value int64)  {
  this.i = value 
}
// getter for variable parents
func (this *XMLParser) Get_parents() []*XMLNode {
  return this.parents
}
// setter for variable parents
func (this *XMLParser) Set_parents( value []*XMLNode)  {
  this.parents = value 
}
// getter for variable next
func (this *XMLParser) Get_next() *GoNullable {
  return this.next
}
// setter for variable next
func (this *XMLParser) Set_next( value *GoNullable)  {
  this.next = value 
}
// getter for variable rootNode
func (this *XMLParser) Get_rootNode() *GoNullable {
  return this.rootNode
}
// setter for variable rootNode
func (this *XMLParser) Set_rootNode( value *GoNullable)  {
  this.rootNode = value 
}
// getter for variable last_parent_safe
func (this *XMLParser) Get_last_parent_safe() *GoNullable {
  return this.last_parent_safe
}
// setter for variable last_parent_safe
func (this *XMLParser) Set_last_parent_safe( value *GoNullable)  {
  this.last_parent_safe = value 
}
// getter for variable curr_node
func (this *XMLParser) Get_curr_node() *GoNullable {
  return this.curr_node
}
// setter for variable curr_node
func (this *XMLParser) Set_curr_node( value *GoNullable)  {
  this.curr_node = value 
}
// getter for variable last_finished
func (this *XMLParser) Get_last_finished() *GoNullable {
  return this.last_finished
}
// setter for variable last_finished
func (this *XMLParser) Set_last_finished( value *GoNullable)  {
  this.last_finished = value 
}
// getter for variable tag_depth
func (this *XMLParser) Get_tag_depth() int64 {
  return this.tag_depth
}
// setter for variable tag_depth
func (this *XMLParser) Set_tag_depth( value int64)  {
  this.tag_depth = value 
}
