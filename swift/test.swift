import Foundation
import CoreFoundation
class SourceCode { 
  var code : String = ""
  var sp : Int = 0     /** note: unused */
  var ep : Int = 0     /** note: unused */
  init(code_str : String ) {
    code = code_str;
  }
}
class XMLNode { 
  var code : SourceCode?
  var sp : Int = 0
  var ep : Int = 0
  var vref : String = ""
  var ns : [String] = [String]()     /** note: unused */
  var value_type : Int = 0
  var string_value : String = ""
  var children : [XMLNode] = [XMLNode]()
  var attrs : [XMLNode] = [XMLNode]()
  var parent : XMLNode?
  init(source : SourceCode, start : Int, end : Int ) {
    code = source;
    sp = start;
    ep = end;
  }
  func getString() -> String {
    return code!.code[code!.code.index(code!.code.startIndex, offsetBy:sp)..<code!.code.index(code!.code.startIndex, offsetBy:ep)];
  }
}
class XMLParser { 
  var code : SourceCode?
  var buff : [UInt8]?
  var len : Int = 0
  var i : Int = 0
  var parents : [XMLNode] = [XMLNode]()
  var next : XMLNode?     /** note: unused */
  var rootNode : XMLNode?
  var last_parent_safe : XMLNode?
  var curr_node : XMLNode?
  var last_finished : XMLNode?
  var tag_depth : Int = 0
  init(code_module : SourceCode ) {
    buff = Array(code_module.code.utf8);
    code = code_module;
    len = (buff!).count;
  }
  func parse_attributes() -> Bool {
    let s : [UInt8] = buff!
    var last_i : Int = 0
    let do_break : Bool = false
    /** unused:  let attr_name : String = ""   **/ 
    var sp : Int = i
    var ep : Int = i
    var c : UInt8 = 0
    var cc1 : UInt8 = 0
    var cc2 : UInt8 = 0
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
      let an_sp : Int = sp
      let an_ep : Int = i
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
          let new_attr : XMLNode = XMLNode(source : code!, start : an_sp, end : ep)
          new_attr.value_type = 19;
          new_attr.vref = String(data: Data(bytes:s[an_sp ..< (an_ep + 1)]), encoding: .utf8)!;
          new_attr.string_value = String(data: Data(bytes:s[sp ..< ep]), encoding: .utf8)!;
          curr_node!.attrs.append(new_attr)
        }
        i = 1 + i;
      }
      if ( last_i == i ) {
        i = 1 + i;
      }
    }
    return do_break;
  }
  func last() -> XMLNode {
    return last_finished!;
  }
  func pull() -> Bool {
    let s_4 : [UInt8] = buff!
    var c_4 : UInt8 = 0
    /** unused:  let next_c : UInt8 = 0   **/ 
    /** unused:  let fc : UInt8 = 0   **/ 
    /** unused:  let new_node : XMLNode?   **/ 
    var sp_4 : Int = i
    var ep_4 : Int = i
    var last_i_4 : Int = 0
    var cc1_4 : UInt8 = 0
    var cc2_4 : UInt8 = 0
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
        parents.removeLast();
        let p_cnt : Int = parents.count
        if ( 0 == p_cnt ) {
          return false;
        }
        let last_parent : XMLNode = parents[(p_cnt - 1)]
        last_parent_safe = last_parent;
        curr_node = last_parent;
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
        parents.removeLast();
        let p_cnt_8 : Int = parents.count
        if ( 0 == p_cnt_8 ) {
          return false;
        }
        let last_parent_8 : XMLNode = parents[(p_cnt_8 - 1)]
        last_finished = curr_node;
        last_parent_safe = last_parent_8;
        curr_node = last_parent_8;
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
        let new_tag : String = String(data: Data(bytes:s_4[sp_4 ..< ep_4]), encoding: .utf8)!
        if ( curr_node == nil ) {
          let new_rnode : XMLNode = XMLNode(source : code!, start : sp_4, end : ep_4)
          new_rnode.vref = new_tag;
          new_rnode.value_type = 17;
          rootNode = new_rnode;
          parents.append(new_rnode)
          curr_node = new_rnode;
        } else {
          let new_node_10 : XMLNode = XMLNode(source : code!, start : sp_4, end : ep_4)
          new_node_10.vref = new_tag;
          new_node_10.value_type = 17;
          curr_node!.children.append(new_node_10)
          parents.append(new_node_10)
          new_node_10.parent = curr_node;
          curr_node = new_node_10;
        }
        if ( c_4 == (47) ) {
          continue;
        }
        _ = self.parse_attributes()
        continue;
      }
      if ( curr_node != nil  ) {
        sp_4 = i;
        ep_4 = i;
        c_4 = s_4[i];
        while ((i < len) && (c_4 != (60))) {
          i = 1 + i;
          c_4 = s_4[i];
        }
        ep_4 = i;
        if ( ep_4 > sp_4 ) {
          let new_node_15 : XMLNode = XMLNode(source : code!, start : sp_4, end : ep_4)
          new_node_15.string_value = String(data: Data(bytes:s_4[sp_4 ..< ep_4]), encoding: .utf8)!;
          new_node_15.value_type = 18;
          curr_node!.children.append(new_node_15)
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
class tester { 
}
print("Testing XML parser")
let read_code : String = "<div>\r\n                hello World <span/>\r\n                <span/>\r\n                <ul style=\"color:green;\">\r\n                    <li>Ferrari <span>F50</span></li>\r\n                    <li>Ford</li>\r\n                </ul></div>"
let the_code : SourceCode = SourceCode(code_str : read_code)
let p : XMLParser = XMLParser(code_module : the_code)
do {
  let _start = CFAbsoluteTimeGetCurrent()
  while (p.pull()) {let last : XMLNode = p.last()
    print("-> pulled a new node " + last.vref)
    let last_11 : XMLNode = p.last_finished!
    for ( _ , ch ) in last_11.children.enumerated() {
      if ( ch.value_type == 18 ) {
        print("text : " + ch.string_value)
      } else {
        print("child : " + ch.vref)
      }
    }
    for ( _ , attr ) in last_11.attrs.enumerated() {
      print((attr.vref + " = ") + attr.string_value)
    }
  }
  let last_12 : XMLNode = p.last()
  print("The children of the last node are " + last_12.vref)
  for ( _ , ch_8 ) in last_12.children.enumerated() {
    if ( ch_8.value_type == 18 ) {
      print("text : " + ch_8.string_value)
    } else {
      print("child : " + ch_8.vref)
    }
  }
  print("Time for parsing the code:", CFAbsoluteTimeGetCurrent() - _start )
}
print("--- done --- ")