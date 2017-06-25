

Enum RangerNodeType:int (
  NoType
  InvalidType
  Double
  Integer
  String
  Boolean
  Array
  Hash
  Object
  VRef
  Comment
  Enum
  Char
  CharBuffer
  Expression
  ExpressionType
  Lambda
  XMLNode
  XMLText
  XMLAttr
  XMLCDATA
  Dictionary
  Any
  Class
  GenericClass
  ClassRef
  Method
  ClassVar
  Function
  Literal
  Quasiliteral
  Null
)
class SourceCode {
  def code:string ""
  def sp:int 0
  def ep:int 0
  Constructor(code_str:string) {
    code = code_str
  }
}
class XMLNode:void {
  def code:SourceCode
  def sp:int 0
  def ep:int 0
  def vref:string ""
  def ns:[string]
  def value_type:RangerNodeType RangerNodeType.NoType
  def string_value:string ""
  def children:[XMLNode]
  def attrs:[XMLNode]
  def parent@(weak):XMLNode
  Constructor(source:SourceCode start:int end:int) {
    code = source
    sp = start
    ep = end
  }
  fn getString:string () {
    return (substring code.code sp ep)
  }
}
class XMLParser:void {
  def code:SourceCode
  def buff:charbuffer
  def len:int 0
  def i:int 0
  def parents@(weak):[XMLNode]
  def next:XMLNode
  def rootNode:XMLNode
  def last_parent_safe:XMLNode
  def curr_node@(weak):XMLNode
  def last_finished@(weak):XMLNode
  def tag_depth:int 0

  Constructor (code_module:SourceCode ) {
    buff = (to_charbuffer code_module.code)
    code = code_module
    len = (length (unwrap buff))
  }
 
  fn parse_attributes:boolean () {
    def s:charbuffer (unwrap buff)
    def last_i:int 0
    def do_break:boolean false
    def attr_name:string ""
    def sp:int i
    def ep:int i
    def c:char 0
    def cc1:char 0
    def cc2:char 0
    cc1 = (charAt s i)
    while (i < len) {
      last_i = i
      while ((i < len) && ((charAt s i) <= 32)) {
        i = (1 + i)
      }
      cc1 = (charAt s i)
      cc2 = (charAt s (i + 1))
      if (i >= len) {
        break _
      }
      if (cc1 == (ccode ">")) {
        return do_break
      }
      if ((cc1 == (ccode "/")) && (cc2 == (ccode ">"))) {
        i = (2 + i)
        return true
      }
      sp = i
      ep = i
      c = (charAt s i)
      while ((i < len) && (((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122)) || ((c >= 48) && (c <= 57)) || (c == (ccode "_")) || (c == (ccode "-")))) {
        i = (1 + i)
        c = (charAt s i)
      }
      i = (i - 1)
      def an_sp:int sp
      def an_ep:int i
      c = (charAt s i)
      while ((i < len) && (c != (ccode "="))) {
        i = (1 + i)
        c = (charAt s i)
      }
      if (c == (ccode "=")) {
        i = (1 + i)
      }
      while ((i < len) && ((charAt s i) <= 32)) {
        i = (1 + i)
      }
      if (i >= len) {
        break _
      }
      c = (charAt s i)
      if (c == 34) {
        i = (i + 1)
        sp = i
        ep = i
        c = (charAt s i)
        while ((i < len) && (c != 34)) {
          i = (1 + i)
          c = (charAt s i)
        }
        ep = i
        if ((i < len) && (ep > sp)) {
          def new_attr:XMLNode (new XMLNode ((unwrap code) an_sp ep))
          new_attr.value_type = RangerNodeType.XMLAttr
          new_attr.vref = (substring s an_sp (an_ep + 1))
          new_attr.string_value = (substring s sp ep)
          push curr_node.attrs new_attr
        }
        i = (1 + i)
      }
      if (last_i == i) {
        i = (1 + i)
      }
    }
    return do_break
  }

  fn last:XMLNode () {
    return (unwrap last_finished)
  }

  fn pull:boolean () {
    def s:charbuffer (unwrap buff)
    def c:char 0
    def next_c:char 0
    def fc:char 0
    def new_node:XMLNode
    def sp:int i
    def ep:int i
    def last_i:int 0
    def cc1:char 0
    def cc2:char 0
    while (i < len) {
      last_finished = curr_node
      last_i = i
      if (i >= (len - 1)) {
        return false
      }
      cc1 = (charAt s i)
      cc2 = (charAt s (i + 1))
      if (cc1 == (ccode ">")) {
        i = (i + 1)
        cc1 = (charAt s i)
        cc2 = (charAt s (i + 1))
        continue 
      }
      if (((ccode "/") == cc1) && (cc2 == (ccode ">"))) {
        tag_depth = (tag_depth - 1)
        i = (i + 2)
        last_finished = curr_node
        removeLast parents
        def p_cnt:int (array_length parents)
        if (0 == p_cnt) {
          return false
        }
        def last_parent:XMLNode (itemAt parents (p_cnt - 1))
        last_parent_safe = last_parent
        curr_node = last_parent
        return true        
      }
      if (i >= len) {
        return false
      }
      if (((ccode "<") == cc1) && (cc2 == (ccode "/"))) {
        tag_depth = (tag_depth - 1)
        i = (i + 2)
        sp = i
        ep = i
        c = (charAt s i)
        while ((i < len) && (c > 32) && (c != (ccode ">"))) {
          i = (1 + i)
          c = (charAt s i)
        }
        ep = i
        removeLast parents
        def p_cnt:int (array_length parents)
        if (0 == p_cnt) {
          return false
        }
        def last_parent:XMLNode (itemAt parents (p_cnt - 1))
        last_finished = curr_node
        last_parent_safe = last_parent
        curr_node = last_parent
        return true
      }
      if (cc1 == (ccode "<")) {
        i = (i + 1)
        sp = i
        ep = i
        c = (charAt s i)
        while ((i < len) && (c != (ccode ">")) && (((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122)) || ((c >= 48) && (c <= 57)) || (c == 95) || (c == 46) || (c == 64))) {
          i = (1 + i)
          c = (charAt s i)
        }
        ep = i
        def new_tag:string (substring s sp ep)
        if (null? curr_node) {
          def new_rnode:XMLNode (new XMLNode ((unwrap code) sp ep))
          new_rnode.vref = new_tag
          new_rnode.value_type = RangerNodeType.XMLNode
          rootNode = new_rnode
          push parents new_rnode
          curr_node = new_rnode
        } {
          def new_node:XMLNode (new XMLNode ((unwrap code) sp ep))
          new_node.vref = new_tag
          new_node.value_type = RangerNodeType.XMLNode
          push curr_node.children new_node
          push parents new_node
          new_node.parent = curr_node
          curr_node = new_node
        }
        if ( c == (ccode "/") ) {
          continue
        }        
        this.parse_attributes()) 
        continue
      }
      if (!null? curr_node) {
        sp = i
        ep = i
        c = (charAt s i)
        while ((i < len) && (c != (ccode "<"))) {
          i = (1 + i)
          c = (charAt s i)
        }
        ep = i
        if (ep > sp) {
          def new_node:XMLNode (new XMLNode ((unwrap code) sp ep))
          new_node.string_value = (substring s sp ep)
          new_node.value_type = RangerNodeType.XMLText
          push curr_node.children new_node
        }
      }
      if (last_i == i) {
        i = (1 + i)
      }
      if (i >= (len - 1)) {
        return false
      }
    }
    last_finished = curr_node
    return true
  }
}

