/* 
 *  Filename:    drop-upload
 *
 *  Author:      Artur Tomasi
 *  EMail:       tomasi.artur@gmail.com
 *  Internet:    www.masterengine.com.br
 *
 *  Copyright © 2018 by Over Line Ltda.
 *  95900-038, LAJEADO, RS
 *  BRAZIL
 *
 *  The copyright to the computer program(s) herein
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */


com.me.eng.core.ui.parts.DropUpload = zk.$extends( zk.Widget, 
{
    init : function ()
    {
        
    },
    
    redraw : function ( out ) 
    {
        out.push
        (
             '<div', this.domAttrs_(),'>', 
                '<form action="/file-upload" class="dropzone" id="my-awesome-dropzone">',
                    '<input type="file" name="file" />',
                '</form>', 
            '</div>'
        );
    }
} );