
Import "xmlPullParser.clj"

class tester {
    sfn m@(main):void () {
        print "Testing XML parser"
        ; def read_code:string (unwrap (read_file "." "testCode.xml"))
        def read_code:string "<div>
                hello World <span/>
                <span/>
                <ul style=\"color:green;\">
                    <li>Ferrari <span>F50</span></li>
                    <li>Ford</li>
                </ul></div>"
        def the_code:SourceCode (new SourceCode (read_code))
        def p:XMLParser (new XMLParser (the_code))
        timer "Time for parsing the code:" {
            while( p.pull() ) {
                def last:XMLNode (p.last())
                print "-> pulled a new node " + (last.vref)
                def last:XMLNode (unwrap p.last_finished)
                for last.children ch:XMLNode i {
                    if (ch.value_type == RangerNodeType.XMLText) {
                        print "text : " + ch.string_value
                    } {
                        print "child : " + ch.vref
                    }
                }
                for last.attrs attr:XMLNode i {
                    print attr.vref + " = " + attr.string_value
                }
            }
            def last:XMLNode (p.last())
            print "The children of the last node are " + (last.vref)
            for last.children ch:XMLNode i {
                if (ch.value_type == RangerNodeType.XMLText) {
                    print "text : " + ch.string_value
                } {
                    print "child : " + ch.vref
                }
            }

        }
        print "--- done --- "        
    }
}