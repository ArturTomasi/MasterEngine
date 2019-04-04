<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
      <meta charset="utf-8" />
      <script src="https://www.gstatic.com/firebasejs/5.5.4/firebase.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.17.0/codemirror.js"></script>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.17.0/codemirror.css" />
      <link rel="stylesheet" href="https://cdn.firebase.com/libs/firepad/1.4.0/firepad.css" />
      <script src="https://cdn.firebase.com/libs/firepad/1.4.0/firepad.min.js"></script>
      <script src="inc/js/jquery-3.3.1.min.js"></script>
      <style>
        html { height: 100%; }
        body { margin: 0; height: 100%; position: relative; }
        #firepad-container 
        {
          width: 100%;
          height: 100%;
        }
      </style>
    </head>
    <body onload="init()">
        <div id="firepad-container"></div>
    </body>
    
    <script>
        function init() 
        {
            $.ajax( 'rs/me-pad/config' ).done( function( config )
            {
                firebase.initializeApp(config);
            
                var firepadRef = getExampleRef();
                var codeMirror = CodeMirror(document.getElementById('firepad-container'), { lineWrapping: true });
                var firepad = Firepad.fromCodeMirror(firepadRef, codeMirror, { richTextToolbar: true, richTextShortcuts: true });

                firepad.on('ready', function() 
                {
                    if (firepad.isHistoryEmpty()) {
                      firepad.setHtml( '<p>Write something</p>' );
                    }
                });
            } );
        }
        
        function getExampleRef() {
          var ref = firebase.database().ref();
          var hash = window.location.hash.replace(/#/g, '');
          if (hash) {
            ref = ref.child(hash);
          } else {
            ref = ref.push(); // generate unique location.
            window.location = window.location + '#' + ref.key; // add it as a hash to the URL.
          }
          if (typeof console !== 'undefined') {
            console.log('Firebase data: ', ref.toString());
          }
          return ref;
        }
  </script>
</html>