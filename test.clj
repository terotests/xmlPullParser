
Import "xmlPullParser.clj"

class tester {
    sfn m@(main):void () {
        print "Testing XML parser"

        def read_code:string "<View padding=\"2px\" margin=\"3px\" background-color=\"#fef6f2\" >
    <View width=\"100%\" padding=\"10px\" id=\"stats1\" >
        <View padding=\"20px\" width=\"dss\" >
        Some text here...
        </View>
        <View padding=\"20px\" width=\"dss\" >
        Some text here...
        </View>
    </View>
</View>"
        def the_code:SourceCode (new SourceCode ( read_code ))
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