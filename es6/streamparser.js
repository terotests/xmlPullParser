
class File  {
  
  constructor(fName  ) {
    this.filename = "";
    this.filename = fName;
  }
}
class XMLDataReady  {
  
  constructor( ) {
  }
  
  Data(last_node ) {
  }
  
  Finished(last_node ) {
  }
}
class XMLNode  {
  
  constructor( ) {
    this.vref = "";
    this.ns = [];     /** note: unused */
    this.value_type = 0;
    this.string_value = "";
    this.children = [];
    this.attrs = [];
    this.parent;
  }
}
class XMLParser  {
  
  constructor(from  ) {
    this.has_started = false;
    this.has_data = false;
    this.no_more_data = false;
    this.inStream;
    this.onReady;
    this.buff;
    this.len = 0;
    this.i = 0;
    this.parents = [];
    this.next;     /** note: unused */
    this.rootNode;
    this.last_parent_safe;
    this.curr_node;
    this.last_finished;
    this.tag_depth = 0;
    this.inStream = from;
  }
  
  parse_attributes() {
    var s = this.buff
    var last_i = 0
    var do_break = false
    /** unused:  var attr_name = ""   **/ 
    var sp = this.i
    var ep = this.i
    var c = 0
    var cc1 = 0
    var cc2 = 0
    cc1 = s[this.i];
    while (this.i < this.len) {
      last_i = this.i;
      while ((this.i < this.len) && ((s[this.i]) <= 32)) {
        this.i = 1 + this.i;
      }
      cc1 = s[this.i];
      cc2 = s[(this.i + 1)];
      if ( this.i >= this.len ) {
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
      while ((this.i < this.len) && ((((((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122))) || ((c >= 48) && (c <= 57))) || (c == (95))) || (c == (45)))) {
        this.i = 1 + this.i;
        c = s[this.i];
      }
      this.i = this.i - 1;
      var an_sp = sp
      var an_ep = this.i
      c = s[this.i];
      while ((this.i < this.len) && (c != (61))) {
        this.i = 1 + this.i;
        c = s[this.i];
      }
      if ( c == (61) ) {
        this.i = 1 + this.i;
      }
      while ((this.i < this.len) && ((s[this.i]) <= 32)) {
        this.i = 1 + this.i;
      }
      if ( this.i >= this.len ) {
        break;
      }
      c = s[this.i];
      if ( c == 34 ) {
        this.i = this.i + 1;
        sp = this.i;
        ep = this.i;
        c = s[this.i];
        while ((this.i < this.len) && (c != 34)) {
          this.i = 1 + this.i;
          c = s[this.i];
        }
        ep = this.i;
        if ( (this.i < this.len) && (ep > sp) ) {
          var new_attr = new XMLNode()
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
  
  last() {
    return this.last_finished;
  }
  
  getMoreData() {
  }
  
  askMore(cb ) {
    this.onReady = cb;
    if ( this.has_started == false ) {
      this.has_started = true;
      this.inStream.on('data', (data) => {
        this.inStream.pause()
        this.has_data = true;
        this.len = data.length;
        this.buff = data;
        this.i = 0;
        if ( typeof(this.onReady) != "undefined" ) {
          this.askMore(this.onReady);
        }
      });
      this.inStream.on('end', () => {
        this.no_more_data = true;
        this.len = 0;
        this.i = 0;
        if ( typeof(this.onReady) != "undefined" ) {
          this.onReady.Finished(this.last_finished);
        }
      });
      return;
    }
    if ( this.no_more_data ) {
      if ( typeof(this.onReady) != "undefined" ) {
        if ( typeof(this.last_finished) != "undefined" ) {
          this.onReady.Finished(this.last_finished);
        }
      }
      return;
    }
    if ( this.has_data ) {
      if ( this.i >= (this.len - 1) ) {
        this.inStream.resume()
      } else {
        if ( this.pull() ) {
          if ( typeof(this.last_finished) != "undefined" ) {
            this.onReady.Data(this.last_finished);
          }
        } else {
          this.inStream.resume()
        }
      }
    }
  }
  
  pull() {
    var s_4 = this.buff
    var c_4 = 0
    /** unused:  var next_c = 0   **/ 
    /** unused:  var fc = 0   **/ 
    /** unused:  var new_node   **/ 
    var sp_4 = this.i
    var ep_4 = this.i
    var last_i_4 = 0
    var cc1_4 = 0
    var cc2_4 = 0
    if ( this.i >= (this.len - 1) ) {
      return false;
    }
    while (this.i < this.len) {
      this.last_finished = this.curr_node;
      last_i_4 = this.i;
      if ( this.i >= (this.len - 1) ) {
        return false;
      }
      cc1_4 = s_4[this.i];
      cc2_4 = s_4[(this.i + 1)];
      if ( cc1_4 == (62) ) {
        this.i = this.i + 1;
        cc1_4 = s_4[this.i];
        cc2_4 = s_4[(this.i + 1)];
        continue;
      }
      if ( ((47) == cc1_4) && (cc2_4 == (62)) ) {
        this.tag_depth = this.tag_depth - 1;
        this.i = this.i + 2;
        this.last_finished = this.curr_node;
        this.parents.pop();
        var p_cnt = this.parents.length
        if ( 0 == p_cnt ) {
          return false;
        }
        var last_parent = this.parents[(p_cnt - 1)]
        this.last_parent_safe = last_parent;
        this.curr_node = last_parent;
        return true;
      }
      if ( this.i >= this.len ) {
        return false;
      }
      if ( ((60) == cc1_4) && (cc2_4 == (47)) ) {
        this.tag_depth = this.tag_depth - 1;
        this.i = this.i + 2;
        sp_4 = this.i;
        ep_4 = this.i;
        c_4 = s_4[this.i];
        while (((this.i < this.len) && (c_4 > 32)) && (c_4 != (62))) {
          this.i = 1 + this.i;
          c_4 = s_4[this.i];
        }
        ep_4 = this.i;
        this.parents.pop();
        var p_cnt_8 = this.parents.length
        if ( 0 == p_cnt_8 ) {
          return false;
        }
        var last_parent_8 = this.parents[(p_cnt_8 - 1)]
        this.last_finished = this.curr_node;
        this.last_parent_safe = last_parent_8;
        this.curr_node = last_parent_8;
        return true;
      }
      if ( cc1_4 == (60) ) {
        this.i = this.i + 1;
        sp_4 = this.i;
        ep_4 = this.i;
        c_4 = s_4[this.i];
        while (((this.i < this.len) && (c_4 != (62))) && (((((((c_4 >= 65) && (c_4 <= 90)) || ((c_4 >= 97) && (c_4 <= 122))) || ((c_4 >= 48) && (c_4 <= 57))) || (c_4 == 95)) || (c_4 == 46)) || (c_4 == 64))) {
          this.i = 1 + this.i;
          c_4 = s_4[this.i];
        }
        ep_4 = this.i;
        var new_tag = s_4.slice( sp_4,  ep_4 ).toString()
        if ( typeof(this.curr_node) === "undefined" ) {
          var new_rnode = new XMLNode()
          new_rnode.vref = new_tag;
          new_rnode.value_type = 1;
          this.rootNode = new_rnode;
          this.parents.push(new_rnode);
          this.curr_node = new_rnode;
        } else {
          var new_node_10 = new XMLNode()
          new_node_10.vref = new_tag;
          new_node_10.value_type = 1;
          this.curr_node.children.push(new_node_10);
          this.parents.push(new_node_10);
          new_node_10.parent = this.curr_node;
          this.curr_node = new_node_10;
        }
        if ( c_4 == (47) ) {
          continue;
        }
        this.parse_attributes();
        continue;
      }
      if ( typeof(this.curr_node) !== "undefined" ) {
        sp_4 = this.i;
        ep_4 = this.i;
        c_4 = s_4[this.i];
        while ((this.i < this.len) && (c_4 != (60))) {
          this.i = 1 + this.i;
          c_4 = s_4[this.i];
        }
        ep_4 = this.i;
        if ( ep_4 > sp_4 ) {
          var new_node_15 = new XMLNode()
          new_node_15.string_value = s_4.slice( sp_4,  ep_4 ).toString();
          new_node_15.value_type = 2;
          this.curr_node.children.push(new_node_15);
          this.last_finished = new_node_15;
          return true;
        }
      }
      if ( last_i_4 == this.i ) {
        this.i = 1 + this.i;
      }
      if ( this.i >= (this.len - 1) ) {
        return false;
      }
    }
    this.last_finished = this.curr_node;
    return true;
  }
}
