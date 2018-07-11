/*
 *  Filename:    PostingEditorAttachmentPane
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
import com.me.eng.core.ui.parts.Dropupload;
import com.me.eng.finances.domain.Posting;
import org.zkoss.zul.Html;

/**
 *
 * @author Artur Tomasi
 */
public class PostingEditorAttachmentPane
    extends 
        SubEditorPanel<Posting>
{

    /**
     * PostingEditorAttachmentPane
     * 
     */
    public PostingEditorAttachmentPane() 
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
        source.addAttachment( dropUpload.getAttachments() );
    }

    /**
     * setInput
     * 
     * @param source Posting
     */
    @Override
    public void setInput( Posting source ) 
    {
        dropUpload.setSource( source );
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        appendChild( dropUpload );
    }

    private Dropupload dropUpload = new Dropupload();
}
