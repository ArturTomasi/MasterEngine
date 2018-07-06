
/**
 * onLoginError
 * 
 * @return function
 */
function onLoginError()
{
    $( 'input[type!=submit]' ).val( "" ).blur();
    
    $( 'input:first' ).focus();

    $( '.error-login' ).click( function()
    {
        $( this ).hide();
    } );

    $( '.login-center-container' ).effect( "shake", function()
    {
        $( '.error-login' ).show( 'fade', setTimeout( function()
        {
            $( '.error-login' ).hide( 'fade' );
            
        }, 3000 ) );
    } );
}


function shake( parent )
{
    var self = $( parent.$n() );
    
    self.effect( 'shake', function() {
        setTimeout( function() { 
            self.fadeOut( "slow", function(){ self.remove() } );            
        }, 3000 );
    } ); 
};


function alert( parent )
{
    var self = $( parent.$n() );
    
    var root = zk.Widget.$( '.z-page' );
    
    zAu.cmd0.showBusy( root.uuid, "alert" );
    
    self.find( '.close' ).click( function()
    {
        self.remove();
        
        zAu.cmd0.clearBusy( root.uuid );
    } );
}


function copyClipboard( text )
{
    var elem = document.createElement( 'textarea' );
    
    elem.style = "display: none;";
    elem.value = text;
    
    document.body.appendChild( elem );
    
    elem.select();

    document.execCommand( "copy" );
    
    elem.remove();
}