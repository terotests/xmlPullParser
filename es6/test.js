
class SourceCode  {
  constructor(code_str) {
    this.code = "";
    this.sp = 0;     /** note: unused */
    this.ep = 0;     /** note: unused */
    this.code = code_str;
  }
}
class XMLNode  {
  constructor(source, start, end) {
    this.sp = 0;
    this.ep = 0;
    this.vref = "";
    this.ns = [];     /** note: unused */
    this.value_type = 0;
    this.string_value = "";
    this.children = [];
    this.attrs = [];
    this.code = source;
    this.sp = start;
    this.ep = end;
  }
  getString () {
    return this.code.code.substring(this.sp, this.ep );
  }
}
class XMLParser  {
  constructor(code_module) {
    this.__len = 0;
    this.i = 0;
    this.parents = [];
    this.tag_depth = 0;
    this.buff = code_module.code;
    this.code = code_module;
    this.__len = (this.buff).length;
    this.i = 0;
  }
  parse_attributes () {
    const s = this.buff;
    let last_i = 0;
    const do_break = false;
    /** unused:  const attr_name = ""   **/ 
    let sp = this.i;
    let ep = this.i;
    let c = 0;
    let cc1 = 0;
    let cc2 = 0;
    cc1 = s.charCodeAt(this.i );
    while (this.i < this.__len) {
      last_i = this.i;
      while ((this.i < this.__len) && ((s.charCodeAt(this.i )) <= 32)) {
        this.i = 1 + this.i;
      }
      cc1 = s.charCodeAt(this.i );
      cc2 = s.charCodeAt((this.i + 1) );
      if ( this.i >= this.__len ) {
        break;
      }
      if ( cc1 == (62) ) {
        return do_break;
      }
      if ( (cc1 == (47)) && (cc2 == (62)) ) {
        this.i = 2 + this.i;
        return true;
      }
      sp = this.i;
      ep = this.i;
      c = s.charCodeAt(this.i );
      while ((this.i < this.__len) && ((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == (95))) || (c == (45)))) {
        this.i = 1 + this.i;
        c = s.charCodeAt(this.i );
      }
      this.i = this.i - 1;
      const an_sp = sp;
      const an_ep = this.i;
      c = s.charCodeAt(this.i );
      while ((this.i < this.__len) && (c != (61))) {
        this.i = 1 + this.i;
        c = s.charCodeAt(this.i );
      }
      if ( c == (61) ) {
        this.i = 1 + this.i;
      }
      while ((this.i < this.__len) && ((s.charCodeAt(this.i )) <= 32)) {
        this.i = 1 + this.i;
      }
      if ( this.i >= this.__len ) {
        break;
      }
      c = s.charCodeAt(this.i );
      if ( c == 34 ) {
        this.i = this.i + 1;
        sp = this.i;
        ep = this.i;
        c = s.charCodeAt(this.i );
        while ((this.i < this.__len) && (c != 34)) {
          this.i = 1 + this.i;
          c = s.charCodeAt(this.i );
        }
        ep = this.i;
        if ( (this.i < this.__len) && (ep > sp) ) {
          const new_attr = new XMLNode(this.code, an_sp, ep);
          new_attr.value_type = 19;
          new_attr.vref = s.substring(an_sp, (an_ep + 1) );
          new_attr.string_value = s.substring(sp, ep );
          this.curr_node.attrs.push(new_attr);
        }
        this.i = 1 + this.i;
      }
      if ( last_i == this.i ) {
        this.i = 1 + this.i;
      }
    }
    return do_break;
  }
  last () {
    return this.last_finished;
  }
  pull () {
    const s = this.buff;
    let c = 0;
    /** unused:  const next_c = 0   **/ 
    /** unused:  const fc = 0   **/ 
    /** unused:  let new_node   **/ 
    let sp = this.i;
    let ep = this.i;
    let last_i = 0;
    let cc1 = 0;
    let cc2 = 0;
    while (this.i < this.__len) {
      this.last_finished = this.curr_node;
      last_i = this.i;
      if ( this.i >= (this.__len - 1) ) {
        return false;
      }
      cc1 = s.charCodeAt(this.i );
      cc2 = s.charCodeAt((this.i + 1) );
      if ( cc1 == (62) ) {
        this.i = this.i + 1;
        cc1 = s.charCodeAt(this.i );
        cc2 = s.charCodeAt((this.i + 1) );
        continue;
      }
      if ( ((47) == cc1) && (cc2 == (62)) ) {
        this.tag_depth = this.tag_depth - 1;
        this.i = this.i + 2;
        this.last_finished = this.curr_node;
        this.parents.pop();
        const p_cnt = this.parents.length;
        if ( 0 == p_cnt ) {
          return false;
        }
        const last_parent = this.parents[(p_cnt - 1)];
        this.last_parent_safe = last_parent;
        this.curr_node = last_parent;
        return true;
      }
      if ( this.i >= this.__len ) {
        return false;
      }
      if ( ((60) == cc1) && (cc2 == (47)) ) {
        this.tag_depth = this.tag_depth - 1;
        this.i = this.i + 2;
        sp = this.i;
        ep = this.i;
        c = s.charCodeAt(this.i );
        while (((this.i < this.__len) && (c > 32)) && (c != (62))) {
          this.i = 1 + this.i;
          c = s.charCodeAt(this.i );
        }
        ep = this.i;
        this.parents.pop();
        const p_cnt_1 = this.parents.length;
        if ( 0 == p_cnt_1 ) {
          return false;
        }
        const last_parent_1 = this.parents[(p_cnt_1 - 1)];
        this.last_finished = this.curr_node;
        this.last_parent_safe = last_parent_1;
        this.curr_node = last_parent_1;
        return true;
      }
      if ( cc1 == (60) ) {
        this.i = this.i + 1;
        sp = this.i;
        ep = this.i;
        c = s.charCodeAt(this.i );
        while (((this.i < this.__len) && (c != (62))) && (((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == 95)) || (c == 46)) || (c == 64))) {
          this.i = 1 + this.i;
          c = s.charCodeAt(this.i );
        }
        ep = this.i;
        const new_tag = s.substring(sp, ep );
        if ( typeof(this.curr_node) === "undefined" ) {
          const new_rnode = new XMLNode(this.code, sp, ep);
          new_rnode.vref = new_tag;
          new_rnode.value_type = 17;
          this.rootNode = new_rnode;
          this.parents.push(new_rnode);
          this.curr_node = new_rnode;
        } else {
          const new_node_2 = new XMLNode(this.code, sp, ep);
          new_node_2.vref = new_tag;
          new_node_2.value_type = 17;
          this.curr_node.children.push(new_node_2);
          this.parents.push(new_node_2);
          new_node_2.parent = this.curr_node;
          this.curr_node = new_node_2;
        }
        if ( c == (47) ) {
          continue;
        }
        this.parse_attributes();
        continue;
      }
      if ( typeof(this.curr_node) !== "undefined" ) {
        sp = this.i;
        ep = this.i;
        c = s.charCodeAt(this.i );
        while ((this.i < this.__len) && (c != (60))) {
          this.i = 1 + this.i;
          c = s.charCodeAt(this.i );
        }
        ep = this.i;
        if ( ep > sp ) {
          const new_node_3 = new XMLNode(this.code, sp, ep);
          new_node_3.string_value = s.substring(sp, ep );
          new_node_3.value_type = 18;
          this.curr_node.children.push(new_node_3);
        }
      }
      if ( last_i == this.i ) {
        this.i = 1 + this.i;
      }
      if ( this.i >= (this.__len - 1) ) {
        return false;
      }
    }
    this.last_finished = this.curr_node;
    return true;
  }
}
class tester  {
  constructor() {
  }
}
/* static JavaSript main routine */
function __js_main() {
  console.log("Testing XML parser")
  const read_code = "<View padding=\"2px\" margin=\"3px\" background-color=\"#fef6f2\" >\r\n    <View width=\"100%\" padding=\"10px\" id=\"stats1\" >\r\n        <View padding=\"20px\" width=\"dss\" >\r\n        Some text here...\r\n        </View>\r\n        <View padding=\"20px\" width=\"dss\" >\r\n        Some text here...\r\n        </View>\r\n    </View>\r\n</View>";
  const the_code = new SourceCode(read_code);
  const p = new XMLParser(the_code);
  console.time("Time for parsing the code:");
  while (p.pull()) {
    const last = p.last();
    console.log("-> pulled a new node " + last.vref)
    const last_1 = p.last_finished;
    for ( let i = 0; i < last_1.children.length; i++) {
      var ch = last_1.children[i];
      if ( ch.value_type == 18 ) {
        console.log("text : " + ch.string_value)
      } else {
        console.log("child : " + ch.vref)
      }
    }
    for ( let i_1 = 0; i_1 < last_1.attrs.length; i_1++) {
      var attr = last_1.attrs[i_1];
      console.log((attr.vref + " = ") + attr.string_value)
    }
  }
  const last_2 = p.last();
  console.log("The children of the last node are " + last_2.vref)
  for ( let i_2 = 0; i_2 < last_2.children.length; i_2++) {
    var ch_1 = last_2.children[i_2];
    if ( ch_1.value_type == 18 ) {
      console.log("text : " + ch_1.string_value)
    } else {
      console.log("child : " + ch_1.vref)
    }
  }
  console.timeEnd("Time for parsing the code:");
  console.log("--- done --- ")
}
__js_main();
