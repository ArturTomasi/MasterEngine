/* 
 *  Filename:    ScriptApplicationViewUI 
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
 *  is the property of Over Line Ltda., Brazil.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda.
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.ui.views;

import bsh.Interpreter;
import com.me.eng.application.ApplicationContext;
import com.me.eng.ui.apps.Action;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Artur Tomasi
 */
public class ScriptApplicationViewUI 
    extends 
        ApplicationViewUI
{
    private Interpreter interpreter = new Interpreter();
    
    /**
     * ScriptApplicationViewUI
     * 
     */
    public ScriptApplicationViewUI() 
    {
        setLabel( "Script" );
        setIcon( "sb_script.png" );
        addAction( "Script", runAction );
    }

    /**
     * run
     * 
     */
    public void run()
    {
        try 
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PrintStream outStream = new PrintStream( out );

            interpreter.setOut( outStream );
            
            interpreter.eval( inputBox.getValue() );
            
            outputBox.setValue( out.toString( "UTF-8" ) );
        }
        
        catch ( Exception e ) 
        {
            outputBox.setValue( e.toString() );
        }
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents()
    {
        setVflex( "true" );
        
        inputBox.setMultiline( true );
        inputBox.setHeight( "80%" );
        inputBox.setWidth( "100%" );
        inputBox.setPlaceholder( "//Escreva seu código aqui" );

        outputBox.setMultiline( true );
        outputBox.setStyle( "width: 100%; background-color: lightyellow; color: #000;" );
        outputBox.setVflex( "true" );
        
        appendChild( inputBox );
        appendChild( outputBox );
    }
    
    private Textbox inputBox = new Textbox();
    private Textbox outputBox = new Textbox();
    
    private Action runAction = new Action( "", "Executar", "Executa o texto escrito no painel" )
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            run();
        }
    };
}