<? 

class SourceCode { 
  var $code;
  var $sp;
  var $ep;
  
  function __construct( $code_str  ) {
    $this->code = "";
    $this->sp = 0;     /** note: unused */
    $this->ep = 0;     /** note: unused */
    $this->code = $code_str;
  }
}
class XMLNode { 
  var $code;
  var $sp;
  var $ep;
  var $vref;
  var $ns;
  var $value_type;
  var $string_value;
  var $children;
  var $attrs;
  var $parent;
  
  function __construct( $source , $start , $end  ) {
    $this->code;
    $this->sp = 0;
    $this->ep = 0;
    $this->vref = "";
    $this->ns = array();     /** note: unused */
    $this->value_type = 0;
    $this->string_value = "";
    $this->children = array();
    $this->attrs = array();
    $this->parent;
    $this->code = $source;
    $this->sp = $start;
    $this->ep = $end;
  }
  
  function getString() {
    return substr($this->code->code, $this->sp, $this->ep - $this->sp);
  }
}
class XMLParser { 
  var $code;
  var $buff;
  var $__len;
  var $i;
  var $parents;
  var $next;
  var $rootNode;
  var $last_parent_safe;
  var $curr_node;
  var $last_finished;
  var $tag_depth;
  
  function __construct( $code_module  ) {
    $this->code;
    $this->buff;
    $this->__len = 0;
    $this->i = 0;
    $this->parents = array();
    $this->next;     /** note: unused */
    $this->rootNode;
    $this->last_parent_safe;
    $this->curr_node;
    $this->last_finished;
    $this->tag_depth = 0;
    $this->buff = $code_module->code;
    $this->code = $code_module;
    $this->__len = strlen(($this->buff));
    $this->i = 0;
  }
  
  function parse_attributes() {
    $s = $this->buff;
    $last_i = 0;
    $do_break = false;
    /** unused:  $attr_name = ""   **/ ;
    $sp = $this->i;
    $ep = $this->i;
    $c = 0;
    $cc1 = 0;
    $cc2 = 0;
    $cc1 = ord($s[$this->i]);
    while ($this->i < $this->__len) {
      $last_i = $this->i;
      while (($this->i < $this->__len) && ((ord($s[$this->i])) <= 32)) {
        $this->i = 1 + $this->i;
      }
      $cc1 = ord($s[$this->i]);
      $cc2 = ord($s[($this->i + 1)]);
      if ( $this->i >= $this->__len ) {
        break;
      }
      if ( $cc1 == (62) ) {
        return $do_break;
      }
      if ( ($cc1 == (47)) && ($cc2 == (62)) ) {
        $this->i = 2 + $this->i;
        return true;
      }
      $sp = $this->i;
      $ep = $this->i;
      $c = ord($s[$this->i]);
      while (($this->i < $this->__len) && (((((($c >= 65) && ($c <= 90)) || (($c >= 97) && ($c <= 122))) || (($c >= 48) && ($c <= 57))) || ($c == (95))) || ($c == (45)))) {
        $this->i = 1 + $this->i;
        $c = ord($s[$this->i]);
      }
      $this->i = $this->i - 1;
      $an_sp = $sp;
      $an_ep = $this->i;
      $c = ord($s[$this->i]);
      while (($this->i < $this->__len) && ($c != (61))) {
        $this->i = 1 + $this->i;
        $c = ord($s[$this->i]);
      }
      if ( $c == (61) ) {
        $this->i = 1 + $this->i;
      }
      while (($this->i < $this->__len) && ((ord($s[$this->i])) <= 32)) {
        $this->i = 1 + $this->i;
      }
      if ( $this->i >= $this->__len ) {
        break;
      }
      $c = ord($s[$this->i]);
      if ( $c == 34 ) {
        $this->i = $this->i + 1;
        $sp = $this->i;
        $ep = $this->i;
        $c = ord($s[$this->i]);
        while (($this->i < $this->__len) && ($c != 34)) {
          $this->i = 1 + $this->i;
          $c = ord($s[$this->i]);
        }
        $ep = $this->i;
        if ( ($this->i < $this->__len) && ($ep > $sp) ) {
          $new_attr =  new XMLNode($this->code, $an_sp, $ep);
          $new_attr->value_type = 19;
          $new_attr->vref = substr($s, $an_sp, ($an_ep + 1) - $an_sp);
          $new_attr->string_value = substr($s, $sp, $ep - $sp);
          array_push($this->curr_node->attrs, $new_attr);
        }
        $this->i = 1 + $this->i;
      }
      if ( $last_i == $this->i ) {
        $this->i = 1 + $this->i;
      }
    }
    return $do_break;
  }
  
  function last() {
    return $this->last_finished;
  }
  
  function pull() {
    $s = $this->buff;
    $c = 0;
    /** unused:  $next_c = 0   **/ ;
    /** unused:  $fc = 0   **/ ;
    /** unused:  $new_node   **/ ;
    $sp = $this->i;
    $ep = $this->i;
    $last_i = 0;
    $cc1 = 0;
    $cc2 = 0;
    while ($this->i < $this->__len) {
      $this->last_finished = $this->curr_node;
      $last_i = $this->i;
      if ( $this->i >= ($this->__len - 1) ) {
        return false;
      }
      $cc1 = ord($s[$this->i]);
      $cc2 = ord($s[($this->i + 1)]);
      if ( $cc1 == (62) ) {
        $this->i = $this->i + 1;
        $cc1 = ord($s[$this->i]);
        $cc2 = ord($s[($this->i + 1)]);
        continue;
      }
      if ( ((47) == $cc1) && ($cc2 == (62)) ) {
        $this->tag_depth = $this->tag_depth - 1;
        $this->i = $this->i + 2;
        $this->last_finished = $this->curr_node;
        array_pop($this->parents );
        $p_cnt = count($this->parents);
        if ( 0 == $p_cnt ) {
          return false;
        }
        $last_parent = $this->parents[($p_cnt - 1)];
        $this->last_parent_safe = $last_parent;
        $this->curr_node = $last_parent;
        return true;
      }
      if ( $this->i >= $this->__len ) {
        return false;
      }
      if ( ((60) == $cc1) && ($cc2 == (47)) ) {
        $this->tag_depth = $this->tag_depth - 1;
        $this->i = $this->i + 2;
        $sp = $this->i;
        $ep = $this->i;
        $c = ord($s[$this->i]);
        while ((($this->i < $this->__len) && ($c > 32)) && ($c != (62))) {
          $this->i = 1 + $this->i;
          $c = ord($s[$this->i]);
        }
        $ep = $this->i;
        array_pop($this->parents );
        $p_cnt_1 = count($this->parents);
        if ( 0 == $p_cnt_1 ) {
          return false;
        }
        $last_parent_1 = $this->parents[($p_cnt_1 - 1)];
        $this->last_finished = $this->curr_node;
        $this->last_parent_safe = $last_parent_1;
        $this->curr_node = $last_parent_1;
        return true;
      }
      if ( $cc1 == (60) ) {
        $this->i = $this->i + 1;
        $sp = $this->i;
        $ep = $this->i;
        $c = ord($s[$this->i]);
        while ((($this->i < $this->__len) && ($c != (62))) && ((((((($c >= 65) && ($c <= 90)) || (($c >= 97) && ($c <= 122))) || (($c >= 48) && ($c <= 57))) || ($c == 95)) || ($c == 46)) || ($c == 64))) {
          $this->i = 1 + $this->i;
          $c = ord($s[$this->i]);
        }
        $ep = $this->i;
        $new_tag = substr($s, $sp, $ep - $sp);
        if ( (!isset($this->curr_node)) ) {
          $new_rnode =  new XMLNode($this->code, $sp, $ep);
          $new_rnode->vref = $new_tag;
          $new_rnode->value_type = 17;
          $this->rootNode = $new_rnode;
          array_push($this->parents, $new_rnode);
          $this->curr_node = $new_rnode;
        } else {
          $new_node_2 =  new XMLNode($this->code, $sp, $ep);
          $new_node_2->vref = $new_tag;
          $new_node_2->value_type = 17;
          array_push($this->curr_node->children, $new_node_2);
          array_push($this->parents, $new_node_2);
          $new_node_2->parent = $this->curr_node;
          $this->curr_node = $new_node_2;
        }
        if ( $c == (47) ) {
          continue;
        }
        $this->parse_attributes();
        continue;
      }
      if ( (isset($this->curr_node)) ) {
        $sp = $this->i;
        $ep = $this->i;
        $c = ord($s[$this->i]);
        while (($this->i < $this->__len) && ($c != (60))) {
          $this->i = 1 + $this->i;
          $c = ord($s[$this->i]);
        }
        $ep = $this->i;
        if ( $ep > $sp ) {
          $new_node_3 =  new XMLNode($this->code, $sp, $ep);
          $new_node_3->string_value = substr($s, $sp, $ep - $sp);
          $new_node_3->value_type = 18;
          array_push($this->curr_node->children, $new_node_3);
        }
      }
      if ( $last_i == $this->i ) {
        $this->i = 1 + $this->i;
      }
      if ( $this->i >= ($this->__len - 1) ) {
        return false;
      }
    }
    $this->last_finished = $this->curr_node;
    return true;
  }
}
class tester { 
  
  function __construct( ) {
  }
  
}

/* static PHP main routine */
echo( "Testing XML parser" . "\n");
$read_code = (file_get_contents("." . "/" . "testCode.xml") );
$the_code =  new SourceCode($read_code);
$p =  new XMLParser($the_code);
$time_start = microtime(true);
$node_cnt = 0;
$text_cnt = 0;
while ($p->pull()) {
  /** unused:  $last = $p->last()   **/ ;
  $last_1 = $p->last_finished;
  for ( $i = 0; $i < count($last_1->children); $i++) {
    $ch = $last_1->children[$i];
    if ( $ch->value_type == 18 ) {
      $node_cnt = $node_cnt + 1;
    } else {
      $text_cnt = $text_cnt + 1;
    }
  }
}
$last_2 = $p->last();
echo( "Last node was" . $last_2->vref . "\n");
echo( ((("Collected " . $node_cnt) . " nodes and ") . $text_cnt) . " text nodes" . "\n");
$time_end = microtime(true);
echo("Time for parsing the code:".($time_end - $time_start)."\n");
echo( "--- done --- " . "\n");
