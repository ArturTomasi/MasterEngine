/*
 *  Filename:    ListSamplesReport
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
package com.me.eng.domain.reports;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.me.eng.domain.Sample;
import com.me.eng.domain.SampleFormmater;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 *
 * @author Artur Tomasi
 */
public class SampleRelationReport 
{
    private Document document;
    
    private SampleFormmater formmater;
    
    private List<Sample> samples;

    /**
     * setSamples
     * 
     * @param samples List&lt;Sample&gt;
     */
    public void setSamples( List<Sample> samples )
    {
        this.samples = samples;
    }
    
    /**
     * generateReport
     * 
     * @param out File
     * @throws Exception
     */
    public void generateReport( File out ) throws Exception 
    {
        formmater = SampleFormmater.newInstance();
        formmater.setPatternDate( "dd 'de' MMMM 'de' YYYY" );
        
        document = new Document( PageSize.A4 );
        document.setMargins( px( 1.5f ), px( 1.5f ), px( 1.5f ), px( 1.5f ) );
        
        PdfWriter.getInstance( document, new FileOutputStream( out ) );
        
        document.open();
        
        document.add( new Paragraph( new Chunk( "RELAÇÃO DE AMOSTRAS.", FontFactory.getCalibriBoldFont( 11 ) ) ) );
        
        PdfPTable table = new PdfPTable( 7 );
        table.setSpacingBefore( 10 );
        table.setWidthPercentage( 100 );
        table.setWidths( new float[]{ 10, 10, 10, 40, 10, 10, 10 } );
        
        PdfPCell cell;
        float fontSizeTable = 9;
         
        cell = new PdfPCell( new Phrase( "C.P. (Nº)", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Tonelada", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
  
        cell = new PdfPCell( new Phrase( "Tipo", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Corpo de Prova", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Idade", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
     
        cell = new PdfPCell( new Phrase( "Nota Fiscal", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );

        cell = new PdfPCell( new Phrase( "Ruptura", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        samples.forEach( (sample) -> 
        {
            addSample( table, sample );
        });
       
        document.add( table );
        
        document.close();
    }
    
    /**
     * addSample
     * 
     * @param table PdfPTable
     * @param sample Sample
     */
    private void addSample( PdfPTable table, Sample sample )
    {
        table.addCell( createCell( formmater.formatId( sample ) ) );
        table.addCell( createCell( null ) );
        table.addCell( createCell( null ) );
        
        PdfPCell cell =  createCell( formmater.formatName( sample ) );
        cell.setHorizontalAlignment( Element.ALIGN_LEFT );
        cell.setPaddingLeft( 3 );
        
        table.addCell( cell );
        table.addCell( createCell( formmater.formatAge( sample ) ) );
        table.addCell( createCell( sample.getNf() ) );
        
        table.addCell( createCell( formmater.formatRupture( sample ) ) );
    }
    
    /**
     * createCell
     * 
     * @param value Object
     * @return PdfPCell
     */
    private PdfPCell createCell( Object value )
    {
        PdfPCell cell = new PdfPCell( new Phrase( value != null ? value.toString() : "", FontFactory.getCalibriFont( 9 ) ) );
        cell.setPaddingBottom( 3 );
        cell.setPaddingTop( 3 );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
 
        return cell;
    }
    
    /**
     * px
     * 
     * @param cm float
     * @return float
     */
    private float px( float cm ) 
    {
        return cm * 36;
    }
    
}
