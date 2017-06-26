Import "Streams.clj"

Enum XMLNodeType:int (
  NoType
  XMLNode
  XMLText
  XMLAttr
  XMLCDATA
)

class XMLDataReady {
  fn Data:void (last_node:XMLNode) {
  }
  fn Finished:void (last_node:XMLNode) {
  }
}

class XMLNode:void {
  def vref:string ""
  def ns:[string]
  def value_type:XMLNodeType XMLNodeType.NoType
  def string_value:string ""
  def children:[XMLNode]
  def attrs:[XMLNode]
  def parent@(weak):XMLNode
}
class XMLParser:void {

  def has_started:boolean false
  def has_data:boolean false
  def no_more_data:boolean false
  def inStream:ReadableStream
  def onReady@(weak):XMLDataReady

  def buff:StreamChunk
  def len:int 0
  def i:int 0
  def parents@(weak):[XMLNode]
  def next:XMLNode
  def rootNode:XMLNode
  def last_parent_safe:XMLNode
  def curr_node@(weak):XMLNode
  def last_finished@(weak):XMLNode
  def tag_depth:int 0

  Constructor (from:ReadableStream) {
    inStream = from
  }
 
  fn parse_attributes:boolean () {
    def s:StreamChunk (unwrap buff)
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
          def new_attr:XMLNode (new XMLNode ())
          new_attr.value_type = XMLNodeType.XMLAttr
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

  fn getMoreData:void () {

  }

  fn processData:void () {
    if no_more_data {
      if onReady {
        if last_finished {
          this.onReady.Finished( (unwrap last_finished) )
        }
      }
      return
    }
    if( has_data ) {
      if ( i >= (len - 1) ) {
        ask_more ( unwrap inStream )
      } {
        if( this.pull() ) {
          if last_finished {
            this.onReady.Data( (unwrap last_finished) )
          }
        } {
          ask_more ( unwrap inStream )
        }
      }
    }    
  }

  fn askMore:void (cb:XMLDataReady) {
    this.onReady = cb
    if (has_started == false) {
      has_started = true
      read (unwrap inStream) data:StreamChunk {
        has_data = true
        len = (length data)
        buff = data
        i = 0
        this.processData()
      } {
        no_more_data = true
        len = 0
        i = 0
        if onReady {
          this.onReady.Finished( (unwrap last_finished) )     
        }      
      }
      return
    } 
    this.processData()
  }

  fn pull:boolean () {
    def s:StreamChunk (unwrap buff)
    def c:char 0
    def next_c:char 0
    def fc:char 0
    def new_node:XMLNode
    def sp:int i
    def ep:int i
    def last_i:int 0
    def cc1:char 0
    def cc2:char 0
    if (i >= (len - 1)) {
      return false
    }
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
          def new_rnode:XMLNode (new XMLNode ())
          new_rnode.vref = new_tag
          new_rnode.value_type = XMLNodeType.XMLNode
          rootNode = new_rnode
          push parents new_rnode
          curr_node = new_rnode
        } {
          def new_node:XMLNode (new XMLNode ())
          new_node.vref = new_tag
          new_node.value_type = XMLNodeType.XMLNode
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
          def new_node:XMLNode (new XMLNode ())
          new_node.string_value = (substring s sp ep)
          new_node.value_type = XMLNodeType.XMLText
          push curr_node.children new_node
          last_finished = new_node
          return true
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

