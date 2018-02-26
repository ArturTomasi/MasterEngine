/* 
 *  Filename:    SamplePageEvent 
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
package com.me.eng.domain.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.me.eng.application.ApplicationContext;
import com.me.eng.application.ConfigurationManager;
import com.me.eng.application.ResourceLocator;

/**
 *
 * @author artur
 */
public class SamplePageEvent 
    extends 
        PageNumberEvent
{
    private boolean isLetterhead = false;
    
    public void setLetterhead( boolean isLetterhead )
    {
        this.isLetterhead = isLetterhead;
    }

    @Override
    public void onEndPage( PdfWriter writer, Document document )
    {
        if ( isLetterhead )
        {
            try
            {
                PdfContentByte canvas = writer.getDirectContentUnder();

                String img = ConfigurationManager.getInstance().getProperty( "ReportGenerator.background", "report/background.jpg" );

                Image image = Image.getInstance( ResourceLocator.getImageURL( img ) );
                image.scaleAbsolute( PageSize.A4.getWidth(), PageSize.A4.getHeight() );
                image.setAbsolutePosition( 0, 0 );

                canvas.addImage( image );
            }

            catch ( Exception ex )
            {
                ApplicationContext.getInstance().logException( ex );
            }
        }
        
        super.onEndPage( writer, document );
    }
    
}
