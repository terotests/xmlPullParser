
; --------------------------------------------------------------------------------------
; Support classes

class File {
    def filename:string ""
	Constructor (fName:string) {
        filename = fName
	}
}

; --------------------------------------------------------------------------------------
; system classes

systemclass StreamChunk {
    es6         Chunk
}

systemclass  FilePointer {
    es6          AnyObject      ; no type checks in JavaScrpt
}

systemclass  ReadableStream {
    es6          AnyObject      ; no type checks in JavaScrpt
}

systemclass  WritableStream {
    es6          AnyObject
}

; --------------------------------------------------------------------------------------
; operators

operators {

    detach process:void ( code:block ) {
        templates {
            es6 ( "process.nextTick( () => {" nl I  (e 1) i nl "})" )
        }                
    }

    substring stream:string (chunk:StreamChunk start:int end:int) {
        templates {
            es6 ( (e 1) ".slice( " (e 2) ",  " (e 3) " ).toString()" )
        }        
    }

    length stream:int ( chunk:StreamChunk ) {
        templates {
            es6 ( (e 1) ".length" )
        }
    }

    to_charbuffer stream:charbuffer ( chunk:StreamChunk ) {
        templates {
            es6 ( (e 1) )
        }        
    }

    create_read_stream http:ReadableStream ( input:File ) {
        templates {
            es6 ("require('fs').createReadStream(" (e 1) ".filename)")
        }
    }

    charAt cmdCharAt:char ( buff:StreamChunk index:int ) {
       templates {
            es6 (  (e 1) "[" (e 2) "]")
        }        
    }

    ask_more stream:void ( stream:ReadableStream ) {
       templates {
            es6 (  (e 1) ".resume()")
        }                
    }

    read http:void ( stream:ReadableStream chunk@(define):StreamChunk read_code:block end_code:block) {
        templates {
            es6 ( (e 1)".on('data', (" (e 2) ") => {" nl 
                    I
                    (e 1) ".pause()" nl 
                    (block 3)
                    nl i "});" nl
                (e 1)".on('end', () => {" nl 
                    I (block 4)
                    nl i "});" nl
            )
        }
    } 
    
}
