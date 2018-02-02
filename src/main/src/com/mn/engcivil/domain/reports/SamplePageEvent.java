package com.mn.engcivil.domain.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.mn.engcivil.application.ApplicationContext;
import com.mn.engcivil.application.ConfigurationManager;
import com.mn.engcivil.application.ResourceLocator;

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
