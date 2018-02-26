/* 
 *  Filename:    PageNumberEvent 
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

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.me.eng.application.ApplicationContext;

/**
 *
 * @author artur
 */
public class PageNumberEvent 
    extends 
        PdfPageEventHelper 
{
    private Font font;
    private PdfTemplate template;
    private Image total;

    public void setFont( Font font ) 
    {
        this.font = font;
    }
    
    
    
    @Override
    public void onOpenDocument( PdfWriter writer, Document document )
    {
        template = writer.getDirectContent().createTemplate(30, 16);
    
        try 
        {
            total = Image.getInstance( template );
             
            total.setRole( PdfName.ARTIFACT );
            
            if ( font == null )
            {
                 font = new Font( FontFactory.getCalibriBoldFont( 10 ) );
            }
        } 
        
        catch ( Exception e ) 
        {
            ApplicationContext.getInstance().handleException( e );
        }
    }
 
    @Override
    public void onEndPage( PdfWriter writer, Document document )
    {
        PdfPTable table = new PdfPTable( 2 );
        
        try 
        {
            table.setWidths(new int[]{24, 1});
            table.setTotalWidth( document.getPageSize().getWidth() - 
                                 document.leftMargin() - 
                                 document.rightMargin() );
            
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER );
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Phrase( String.format( "%d /", writer.getPageNumber()), font ) );
            
            PdfPCell cell = new PdfPCell(total);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment( Element.ALIGN_RIGHT);
            table.addCell(cell);
            

            
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            table.writeSelectedRows( 0, -1, document.leftMargin(), 
                                     document.top() + 30,
                                     canvas );
            
            canvas.endMarkedContentSequence();
        } 
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().handleException( e );
        }
    }

    @Override
    public void onCloseDocument( PdfWriter writer, Document document ) 
    {
        ColumnText.showTextAligned( template, 
                                    Element.ALIGN_RIGHT, 
                                    new Phrase( String.valueOf( writer.getPageNumber() ), font ),
                                    5, 4, 0 );
    }
}
