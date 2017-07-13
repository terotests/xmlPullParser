
class InputFile  {
  constructor(fName) {
    this.filename = "";
    this.filename = fName;
  }
}
class XMLDataReady  {
  constructor() {
  }
  Data (last_node) {
    return false;
  }
  Finished (last_node) {
  }
}
class XMLNode  {
  constructor() {
    this.vref = "";
    this.ns = [];     /** note: unused */
    this.value_type = 0;
    this.string_value = "";
    this.children = [];
    this.attrs = [];
  }
}
class XMLParser  {
  constructor(from) {
    this.has_started = false;
    this.has_data = false;
    this.no_more_data = false;
    this.total_bytes = 0;
    this.total_nodes = 0;
    this.__len = 0;
    this.i = 0;
    this.parents = [];
    this.tag_depth = 0;
    this.inStream = from;
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
    cc1 = s[this.i];
    while (this.i < this.__len) {
      last_i = this.i;
      while ((this.i < this.__len) && ((s[this.i]) <= 32)) {
        this.i = 1 + this.i;
      }
      cc1 = s[this.i];
      cc2 = s[(this.i + 1)];
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
      c = s[this.i];
      while ((this.i < this.__len) && ((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == (95))) || (c == (45)))) {
        this.i = 1 + this.i;
        c = s[this.i];
      }
      this.i = this.i - 1;
      const an_sp = sp;
      const an_ep = this.i;
      c = s[this.i];
      while ((this.i < this.__len) && (c != (61))) {
        this.i = 1 + this.i;
        c = s[this.i];
      }
      if ( c == (61) ) {
        this.i = 1 + this.i;
      }
      while ((this.i < this.__len) && ((s[this.i]) <= 32)) {
        this.i = 1 + this.i;
      }
      if ( this.i >= this.__len ) {
        break;
      }
      c = s[this.i];
      if ( c == 34 ) {
        this.i = this.i + 1;
        sp = this.i;
        ep = this.i;
        c = s[this.i];
        while ((this.i < this.__len) && (c != 34)) {
          this.i = 1 + this.i;
          c = s[this.i];
        }
        ep = this.i;
        if ( (this.i < this.__len) && (ep > sp) ) {
          const new_attr = new XMLNode();
          new_attr.value_type = 3;
          new_attr.vref = s.slice( an_sp,  (an_ep + 1) ).toString();
          new_attr.string_value = s.slice( sp,  ep ).toString();
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
  getMoreData () {
  }
  processData () {
    if ( this.no_more_data ) {
      if ( typeof(this.onReady) != "undefined" ) {
        if ( typeof(this.last_finished) != "undefined" ) {
          this.onReady.Finished(this.last_finished);
        }
      }
      return;
    }
    if ( this.has_data ) {
      if ( this.i >= (this.__len - 1) ) {
        this.inStream.resume()
      } else {
        if ( this.pull() ) {
          if ( typeof(this.last_finished) != "undefined" ) {
            this.total_nodes = this.total_nodes + 1;
            if ( this.onReady.Data((this.last_finished)) ) {
              if ( typeof(this.last_finished.parent) != "undefined" ) {
                const last = this.last_finished;
                const p = last.parent;
                const idx = p.children.indexOf(last);
                const removed = p.children.splice(idx, 1).pop();
                delete removed.parent
              }
            }
          }
        } else {
          this.inStream.resume()
        }
      }
    }
  }
  askMore (cb) {
    this.onReady = cb;
    if ( this.has_started == false ) {
      this.has_started = true;
      this.inStream.on('data', (data) => {
        this.inStream.pause()
        this.has_data = true;
        this.__len = data.length;
        this.total_bytes = this.total_bytes + this.__len;
        this.buff = data;
        this.i = 0;
        this.processData();
      });
      this.inStream.on('end', () => {
        this.no_more_data = true;
        this.__len = 0;
        this.i = 0;
        if ( typeof(this.onReady) != "undefined" ) {
          this.onReady.Finished(this.last_finished);
        }
      });
      return;
    }
    this.processData();
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
    if ( this.i >= (this.__len - 1) ) {
      return false;
    }
    while (this.i < this.__len) {
      this.last_finished = this.curr_node;
      last_i = this.i;
      if ( this.i >= (this.__len - 1) ) {
        return false;
      }
      cc1 = s[this.i];
      cc2 = s[(this.i + 1)];
      if ( cc1 == (62) ) {
        this.i = this.i + 1;
        cc1 = s[this.i];
        cc2 = s[(this.i + 1)];
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
        c = s[this.i];
        while (((this.i < this.__len) && (c > 32)) && (c != (62))) {
          this.i = 1 + this.i;
          c = s[this.i];
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
        c = s[this.i];
        while (((this.i < this.__len) && (c != (62))) && (((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == 95)) || (c == 46)) || (c == 64))) {
          this.i = 1 + this.i;
          c = s[this.i];
        }
        ep = this.i;
        const new_tag = s.slice( sp,  ep ).toString();
        if ( typeof(this.curr_node) === "undefined" ) {
          const new_rnode = new XMLNode();
          new_rnode.vref = new_tag;
          new_rnode.value_type = 1;
          this.rootNode = new_rnode;
          this.parents.push(new_rnode);
          this.curr_node = new_rnode;
        } else {
          const new_node_2 = new XMLNode();
          new_node_2.vref = new_tag;
          new_node_2.value_type = 1;
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
        c = s[this.i];
        while ((this.i < this.__len) && (c != (60))) {
          this.i = 1 + this.i;
          c = s[this.i];
        }
        ep = this.i;
        if ( ep > sp ) {
          const new_node_3 = new XMLNode();
          new_node_3.string_value = s.slice( sp,  ep ).toString();
          new_node_3.value_type = 2;
          this.curr_node.children.push(new_node_3);
          this.last_finished = new_node_3;
          return true;
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
class myDataHandler  extends XMLDataReady {
  constructor(p) {
    super()
    this.parser = p;
  }
  Data (last_node) {
    let remove_latest = false;
    switch (last_node.value_type ) { 
      case 1 : 
        console.log("read a new node, removing it... " + last_node.vref)
        remove_latest = true;
        break;
      case 2 : 
        console.log("text : " + last_node.string_value)
        break;
    }
    process.nextTick( () => {
      this.parser.askMore(this)
    })
    return remove_latest;
  }
  Finished (last_node) {
    console.log((("all data was read, total bytes processed = " + this.parser.total_bytes) + ", total nodes = ") + this.parser.total_nodes)
  }
}
class streamTester  {
  constructor() {
  }
  read (fileName) {
    const inS = require('fs').createReadStream(new InputFile(fileName).filename);
    const parser = new XMLParser(inS);
    const handler = new myDataHandler(parser);
    parser.askMore(handler);
  }
}
/* static JavaSript main routine */
function __js_main() {
  const tester = new streamTester();
  tester.read("testCode.xml");
}
__js_main();
