/*
 *  Filename:    PostingEditorInformationPane
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
package com.me.eng.finances.ui.editors.tabs;

import com.me.eng.core.ui.editors.tabs.SubEditorPanel;
import com.me.eng.finances.domain.Posting;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Artur Tomasi
 */
public class PostingEditorInformationPane 
    extends 
        SubEditorPanel<Posting>
{
    /**
     * PostingEditorInformationPane
     * 
     */
    public PostingEditorInformationPane()
    {
        initComponents();
    }

    /**
     * getInput
     * 
     * @param source Posting
     */
    @Override
    public void getInput( Posting source ) 
    {
        source.setInfo( infoField.getText() );
    }

    /**
     * setInput
     * 
     * @param source Posting
     */
    @Override
    public void setInput( Posting source )
    {
        infoField.setText( source.getInfo() );
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        infoField.setWidth( "100%" );
        infoField.setRows( 15 );
        
        appendChild( infoField );
    }
    
    private Textbox infoField = new Textbox();
}
