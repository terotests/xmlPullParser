Import "xmlStreamParser.clj"

class myDataHandler {
  Extends(XMLDataReady)
  def parser:XMLParser

  Constructor(p:XMLParser) {
      parser = p
  }

  fn Data:void (last_node:XMLNode) {
    def remove_latest:boolean false
    switch last_node.value_type {
        case XMLNodeType.XMLNode {
            print "read a new node, removing it... " + last_node.vref
            remove_latest = true
        }
        case XMLNodeType.XMLText {
            print "text : " + last_node.string_value
        }
    }  
    detach {
        parser.askMore(  this )
    }
    return remove_latest
  }
  fn Finished:void (last_node:XMLNode) {
    print "all data was read, total bytes processed = " + parser.total_bytes + ", total nodes = " + parser.total_nodes
  }
}

class streamTester {

    fn read:void (fileName:string) {
        def inS:ReadableStream (create_read_stream (new File(fileName)))
        def parser:XMLParser (new XMLParser(inS) )
        def handler:myDataHandler (new myDataHandler( parser ))
        parser.askMore( handler )        
    }

    sfn m@(main):void () {
        def tester:streamTester (new streamTester())
        tester.read("testCode.xml")
    }
    
}