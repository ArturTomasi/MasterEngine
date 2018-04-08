/* global zk, zAu */

/**
 * KeyEventControl
 * 
 * @return function
 */
 function KeyEventControl(){};

/**
 * =
 * 
 * @return KeyEventControl.prototype._target
 * @ignored undefined
 */
KeyEventControl.prototype._target = undefined;

/**
 * =
 * 
 * @return KeyEventControl.prototype.keys
 * @ignored new
 * @ignored Array
 */
KeyEventControl.prototype.keys = new Array();

KeyEventControl.prototype.target = function( target )
{
    this._target = target;
};

/**
 * =
 * 
 * @return KeyEventControl.prototype.enable
 * @ignored function
 */
KeyEventControl.prototype.enable = function()
{
    var self = this;
    
    $( document ).on( 'keydown', function( event)
    {
      self._processEvent( self, event );  
    } );
};

/**
 * =
 * 
 * @return KeyEventControl.prototype.disable
 * @ignored function
 */
KeyEventControl.prototype.disable = function()
{
    $( document ).off( 'keydown', this._processEvent );
};

/**
 * =
 * 
 * @param  event
 * @param  self
 * @return KeyEventControl.prototype._processEvent
 * @ignored function
 */
KeyEventControl.prototype._processEvent = function( self, event )
{
    var code  = ( event.keyCode ? event.keyCode : event.which ),
        ctrl  = event.ctrlKey,
        shift = event.shiftKey,
        alt   = event.altKey;

    var avaliables = self.keys.filter( function(e)
    {
        return e.code === code && e.ctrl === ctrl && e.shift === shift && e.alt === alt;
    } );

    if ( avaliables[0] )
    {
        this.sendEvent( avaliables[0] );
        
        event.preventDefault(); return;
    }
};

/**
 * =
 * 
 * @param  key
 * @return KeyEventControl.prototype.addKey
 * @ignored function
 */
KeyEventControl.prototype.key = function( key ) 
{
    this.keys.push( key );
};

/**
 * =
 * 
 * @param  key
 * @return KeyEventControl.prototype.sendEvent
 * @ignored function
 */
KeyEventControl.prototype.sendEvent = function( key )
{
    if ( this._target && key ) 
    { 
        zAu.send( new zk.Event( zk.Widget.$( $('[key-event-control=' + this._target+ ']' ) ), "onCustomKeyDown", key ) );
    }
};