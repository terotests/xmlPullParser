<? 

class SourceCode { 
  var $code;
  var $sp;
  var $ep;
  
  function __construct( $code_str  ) {
    $this->code = '';
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
    $this->vref = '';
    $this->ns = array();     /** note: unused */
    $this->value_type = 0;
    $this->string_value = '';
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
  var $len;
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
    $this->len = 0;
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
    $this->len = strlen(($this->buff));
    $this->i = 0;
  }
  
  function parse_attributes() {
    $s = $this->buff;
    $last_i = 0;
    $do_break = false;
    /** unused:  $attr_name = ''   **/ ;
    $sp = $this->i;
    $ep = $this->i;
    $c = 0;
    $cc1 = 0;
    $cc2 = 0;
    $cc1 = ord($s[$this->i]);
    while ($this->i < $this->len) {
      $last_i = $this->i;
      while (($this->i < $this->len) && ((ord($s[$this->i])) <= 32)) {
        $this->i = 1 + $this->i;
      }
      $cc1 = ord($s[$this->i]);
      $cc2 = ord($s[($this->i + 1)]);
      if ( $this->i >= $this->len ) {
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
      while (($this->i < $this->len) && (((((($c >= 65) && ($c <= 90)) || (($c >= 97) && ($c <= 122))) || (($c >= 48) && ($c <= 57))) || ($c == (95))) || ($c == (45)))) {
        $this->i = 1 + $this->i;
        $c = ord($s[$this->i]);
      }
      $this->i = $this->i - 1;
      $an_sp = $sp;
      $an_ep = $this->i;
      $c = ord($s[$this->i]);
      while (($this->i < $this->len) && ($c != (61))) {
        $this->i = 1 + $this->i;
        $c = ord($s[$this->i]);
      }
      if ( $c == (61) ) {
        $this->i = 1 + $this->i;
      }
      while (($this->i < $this->len) && ((ord($s[$this->i])) <= 32)) {
        $this->i = 1 + $this->i;
      }
      if ( $this->i >= $this->len ) {
        break;
      }
      $c = ord($s[$this->i]);
      if ( $c == 34 ) {
        $this->i = $this->i + 1;
        $sp = $this->i;
        $ep = $this->i;
        $c = ord($s[$this->i]);
        while (($this->i < $this->len) && ($c != 34)) {
          $this->i = 1 + $this->i;
          $c = ord($s[$this->i]);
        }
        $ep = $this->i;
        if ( ($this->i < $this->len) && ($ep > $sp) ) {
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
    $s_4 = $this->buff;
    $c_4 = 0;
    /** unused:  $next_c = 0   **/ ;
    /** unused:  $fc = 0   **/ ;
    /** unused:  $new_node   **/ ;
    $sp_4 = $this->i;
    $ep_4 = $this->i;
    $last_i_4 = 0;
    $cc1_4 = 0;
    $cc2_4 = 0;
    while ($this->i < $this->len) {
      $this->last_finished = $this->curr_node;
      $last_i_4 = $this->i;
      if ( $this->i >= ($this->len - 1) ) {
        return false;
      }
      $cc1_4 = ord($s_4[$this->i]);
      $cc2_4 = ord($s_4[($this->i + 1)]);
      if ( $cc1_4 == (62) ) {
        $this->i = $this->i + 1;
        $cc1_4 = ord($s_4[$this->i]);
        $cc2_4 = ord($s_4[($this->i + 1)]);
        continue;
      }
      if ( ((47) == $cc1_4) && ($cc2_4 == (62)) ) {
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
      if ( $this->i >= $this->len ) {
        return false;
      }
      if ( ((60) == $cc1_4) && ($cc2_4 == (47)) ) {
        $this->tag_depth = $this->tag_depth - 1;
        $this->i = $this->i + 2;
        $sp_4 = $this->i;
        $ep_4 = $this->i;
        $c_4 = ord($s_4[$this->i]);
        while ((($this->i < $this->len) && ($c_4 > 32)) && ($c_4 != (62))) {
          $this->i = 1 + $this->i;
          $c_4 = ord($s_4[$this->i]);
        }
        $ep_4 = $this->i;
        array_pop($this->parents );
        $p_cnt_8 = count($this->parents);
        if ( 0 == $p_cnt_8 ) {
          return false;
        }
        $last_parent_8 = $this->parents[($p_cnt_8 - 1)];
        $this->last_finished = $this->curr_node;
        $this->last_parent_safe = $last_parent_8;
        $this->curr_node = $last_parent_8;
        return true;
      }
      if ( $cc1_4 == (60) ) {
        $this->i = $this->i + 1;
        $sp_4 = $this->i;
        $ep_4 = $this->i;
        $c_4 = ord($s_4[$this->i]);
        while ((($this->i < $this->len) && ($c_4 != (62))) && ((((((($c_4 >= 65) && ($c_4 <= 90)) || (($c_4 >= 97) && ($c_4 <= 122))) || (($c_4 >= 48) && ($c_4 <= 57))) || ($c_4 == 95)) || ($c_4 == 46)) || ($c_4 == 64))) {
          $this->i = 1 + $this->i;
          $c_4 = ord($s_4[$this->i]);
        }
        $ep_4 = $this->i;
        $new_tag = substr($s_4, $sp_4, $ep_4 - $sp_4);
        if ( (!isset($this->curr_node)) ) {
          $new_rnode =  new XMLNode($this->code, $sp_4, $ep_4);
          $new_rnode->vref = $new_tag;
          $new_rnode->value_type = 17;
          $this->rootNode = $new_rnode;
          array_push($this->parents, $new_rnode);
          $this->curr_node = $new_rnode;
        } else {
          $new_node_10 =  new XMLNode($this->code, $sp_4, $ep_4);
          $new_node_10->vref = $new_tag;
          $new_node_10->value_type = 17;
          array_push($this->curr_node->children, $new_node_10);
          array_push($this->parents, $new_node_10);
          $new_node_10->parent = $this->curr_node;
          $this->curr_node = $new_node_10;
        }
        if ( $c_4 == (47) ) {
          continue;
        }
        $this->parse_attributes();
        continue;
      }
      if ( (isset($this->curr_node)) ) {
        $sp_4 = $this->i;
        $ep_4 = $this->i;
        $c_4 = ord($s_4[$this->i]);
        while (($this->i < $this->len) && ($c_4 != (60))) {
          $this->i = 1 + $this->i;
          $c_4 = ord($s_4[$this->i]);
        }
        $ep_4 = $this->i;
        if ( $ep_4 > $sp_4 ) {
          $new_node_15 =  new XMLNode($this->code, $sp_4, $ep_4);
          $new_node_15->string_value = substr($s_4, $sp_4, $ep_4 - $sp_4);
          $new_node_15->value_type = 18;
          array_push($this->curr_node->children, $new_node_15);
        }
      }
      if ( $last_i_4 == $this->i ) {
        $this->i = 1 + $this->i;
      }
      if ( $this->i >= ($this->len - 1) ) {
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
echo( 'Testing XML parser' . "\n");
$read_code = (file_get_contents('.' . "/" . 'testCode.xml') );
$the_code =  new SourceCode($read_code);
$p =  new XMLParser($the_code);
$time_start = microtime(true);
while ($p->pull()) {
  $last = $p->last();
  echo( '-> pulled a new node ' . $last->vref . "\n");
  $last_11 = $p->last_finished;
  for ( $i = 0; $i < count($last_11->children); $i++) {
    $ch = $last_11->children[$i];
    if ( $ch->value_type == 18 ) {
      echo( 'text : ' . $ch->string_value . "\n");
    } else {
      echo( 'child : ' . $ch->vref . "\n");
    }
  }
  for ( $i_10 = 0; $i_10 < count($last_11->attrs); $i_10++) {
    $attr = $last_11->attrs[$i_10];
    echo( ($attr->vref . ' = ') . $attr->string_value . "\n");
  }
}
$last_12 = $p->last();
echo( 'The children of the last node are ' . $last_12->vref . "\n");
for ( $i_12 = 0; $i_12 < count($last_12->children); $i_12++) {
  $ch_8 = $last_12->children[$i_12];
  if ( $ch_8->value_type == 18 ) {
    echo( 'text : ' . $ch_8->string_value . "\n");
  } else {
    echo( 'child : ' . $ch_8->vref . "\n");
  }
}
$time_end = microtime(true);
echo('Time for parsing the code:'.($time_end - $time_start)."\n");
echo( '--- done --- ' . "\n");
