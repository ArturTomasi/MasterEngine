
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