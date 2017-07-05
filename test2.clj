
Import "xmlPullParser.clj"

class tester {
    sfn m@(main):void () {
        print "Testing XML parser"

        def read_code:string (unwrap (read_file "." "testCode.xml"))
        def the_code:SourceCode (new SourceCode ( read_code ))
        def p:XMLParser (new XMLParser (the_code))

        timer "Time for parsing the code:" {
            def node_cnt:int 0
            def text_cnt:int 0 
            while( p.pull() ) {
                def last:XMLNode (p.last())
                def last:XMLNode (unwrap p.last_finished)
                for last.children ch:XMLNode i {
                    if (ch.value_type == RangerNodeType.XMLText) {
                        ; print "text : " + ch.string_value
                        node_cnt = node_cnt + 1
                    } {
                        text_cnt = text_cnt + 1
                    }
                }
            }
            def last:XMLNode (p.last())
            print "Last node was" + (last.vref)
            print "Collected " + node_cnt + " nodes and " + text_cnt + " text nodes"

        }
        print "--- done --- "        
    }
}