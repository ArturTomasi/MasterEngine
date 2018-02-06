package com.me.eng.domain.reports;

import com.google.common.base.Strings;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.me.eng.application.ResourceLocator;
import com.me.eng.domain.City;
import com.me.eng.domain.Cnpj;
import com.me.eng.domain.Contact;
import com.me.eng.domain.Equipment;
import com.me.eng.domain.Formula;
import com.me.eng.domain.Sample;
import com.me.eng.domain.SampleFormmater;
import com.me.eng.domain.Serial;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class ItextReportGenerator
    implements 
        ReportGenerator
{
    private float fontSize = 11;
    
    private Formula newton;
    private Formula rc;
    
    private Document document;
    private PdfWriter writer;
    
    private SampleFormmater formmater;

    public void setNewton( Formula newton )
    {
        this.newton = newton;
    }

    public void setRc( Formula rc )
    {
        this.rc = rc;
    }
    
    @Override
    public void generateReport( SampleReport report, File out ) throws Exception
    {
        formmater = SampleFormmater.newInstance();
        formmater.setPatternDate( "dd 'de' MMMM 'de' YYYY" );
        
        document = new Document( PageSize.A4 );
        document.setMargins( px( 2.5f ), px( 2.5f ), px( 3f ), px( 2f ) );
        
        writer = PdfWriter.getInstance( document, new FileOutputStream( out ) );
        
        SamplePageEvent pageEvent = new SamplePageEvent();
        pageEvent.setLetterhead( ! report.isLetterhead() );
        
        writer.setPageEvent( pageEvent );
        
        document.open();
        
        Sample root = report.getRoot();
        
        List<Sample> samples = report.getItems();
        
        Serial serial = report.getRoot().getSerial();
        
        Paragraph title = new Paragraph();
        title.add( new Chunk( "DOCUMENTO:", FontFactory.getCalibriFont( fontSize, Color.decode( "0xa6a6a6" ) ) ) );
        title.add( new Chunk( " RELATÓRIO DE ENSAIO.", FontFactory.getCalibriBoldFont( fontSize ) ) );
        title.add( "                         " );
        title.add( new Chunk( "NÚMERO: ", FontFactory.getCalibriFont( fontSize, Color.decode( "0xa6a6a6" ) ) ) );
        title.add( new Chunk( "LCPT " + serial.getMajorNumber(), FontFactory.getCalibriFont( fontSize ) ) );
        title.add( new Chunk( "/" + serial.getMinorNumber(), FontFactory.getCalibriBoldFont( fontSize ) ) );

        addBreak();
        
        document.add( title );
        
        addBreak();
        
        Paragraph subtitle = new Paragraph( new Chunk( "Os resultados contidos neste documento "
                + "têm significação restrita e "
                + "aplicam-se exclusivamente ao "
                + "item ou itens ensaiados ou calibrados.", FontFactory.getCalibriFont( 10, Color.decode( "0xa6a6a6" ) ) ) );
        
        subtitle.setAlignment( Element.ALIGN_CENTER );
        
        document.add( subtitle );
        
        Paragraph subtitle2 = new Paragraph( new Chunk( "Este documento somente poderá"
                + " ser publicado na integra.", FontFactory.getCalibriFont( 10, Color.decode( "0xa6a6a6" ) ) ) );
        
        subtitle2.setAlignment( Element.ALIGN_CENTER );
        
        document.add( subtitle2 );
        
        addBreak();
        
        Paragraph subtitle3 = new Paragraph( new Chunk( "ENSAIO DE COMPRESSÃO DE "
                + "CORPOS DE PROVA CILÍNDRICOS", FontFactory.getCalibriBoldFont( fontSize ) ) );
        
        subtitle3.setAlignment( Element.ALIGN_CENTER );
        
        document.add( subtitle3 );
        
        addBreak();
        
        createClientSection( report );

        addBreak();
        
        Contact c = root.getContact();
        
        if ( c != null )
        {
            Paragraph contact = new Paragraph();
            contact.add( new Chunk( "Contato:        ", FontFactory.getCalibriBoldFont( fontSize ) ) );
            contact.add( new Chunk( c.getName(), FontFactory.getCalibriFont( 13 ) ) );

            document.add( contact );

            addBreak();
        }
        
        createJobSection( report );
        
        String value = "Corpo de prova cilíndrico"; 
        
        if ( samples.size() == 2 )
        {
            value = "Dois  corpos  de  prova  cilíndricos";
        }
        
        else if ( samples.size() > 2 )
        {
            value = "Corpos de provas cilindrícos";
        }
        
        Paragraph material = new Paragraph();
        material.add( new Chunk( "Material ensaiado: ", FontFactory.getCalibriBoldFont( fontSize ) ) );
        material.add( new Chunk( value + " de  concreto,  "
                + "com  dimensões nominais de (" + root.getDimW() + "x" + 
                                                   root.getDimH() + ")mm.", FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( material );
        
        addBreak();
        
        Paragraph dtAccept = new Paragraph();
        dtAccept.add( new Chunk( "Data do recebimento do material: ", FontFactory.getCalibriBoldFont( fontSize ) ) );
        dtAccept.add( new Chunk( formmater.formatDate( root.getDateCreated() ), FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( dtAccept );
        
        addBreak();
        
        Paragraph dtExecuted = new Paragraph();
        dtExecuted.add( new Chunk( "Data da realização do ensaio: ", FontFactory.getCalibriBoldFont( fontSize ) ) );
        dtExecuted.add( new Chunk( formmater.formatDate( root.getDateRupture() ), FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( dtExecuted );
        
        addBreak();
        
        Paragraph method = new Paragraph();
        method.add( new Chunk( "Método utilizado para realização do ensaio:", FontFactory.getCalibriBoldFont( fontSize ) ) );
        
        Paragraph method2 = new Paragraph();
        method2.add( new Chunk( root.getRule().toString(), FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( method );
        document.add( method2 );
        
        addBreak();
        
        Paragraph capeamento = new Paragraph();
        capeamento.add( new Chunk( "Tipo de capeamento empregado: ", FontFactory.getCalibriBoldFont( fontSize ) ) );
        capeamento.add( new Chunk( formmater.formatCapstones( root ), FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( capeamento );
        
        addBreak();
        
        if ( ! root.getEquipaments().isEmpty() )
        {
            Paragraph equipament = new Paragraph();
            equipament.add( new Chunk( "Equipamentos utilizados para a realização do ensaio:", FontFactory.getCalibriBoldFont( fontSize ) ) );

            document.add( equipament );
            
            for ( Equipment e : root.getEquipaments() )
            {
                document.add( new Phrase( e.toString(), FontFactory.getCalibriFont( fontSize ) ) );
                document.add( Chunk.NEWLINE );
            }
        }
        
        document.newPage();
        
        Paragraph rupture = new Paragraph();
        rupture.add( new Chunk( "Tipo de ruptura de corpos de prova:", FontFactory.getCalibriBoldFont( fontSize ) ) );
        
        Image ruptureImage = Image.getInstance( ResourceLocator.getImageURL( "/report/rupture_type.png" ) );
        ruptureImage.scaleAbsoluteWidth( 363.2f );
        ruptureImage.scaleAbsoluteHeight( 365.6f );
        
        document.add( rupture );
        document.add( ruptureImage );
        
        addBreak();
        
        Paragraph result = new Paragraph();
        result.add( new Chunk( "Resultados:", FontFactory.getCalibriBoldFont( fontSize ) ) );
        
        document.add( result );

        PdfPTable table = new PdfPTable( 8 );
        table.setSpacingBefore( 10 );
        table.setWidthPercentage( 100 );
        table.setWidths( new float[]{ 8, 10, 8, 10, 10, 15, 10, 29 } );
        
        float fontSizeTable = 9;
        
        PdfPCell cell;
        
        cell = new PdfPCell( new Phrase( "Resistência à compressão", FontFactory.getCalibriFont( fontSizeTable ) ) );
        cell.setColspan( 5 );
        cell.setRowspan( 2 );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Informações do Cliente", FontFactory.getCalibriFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        cell.setColspan( 3 );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Data de moldagem", FontFactory.getCalibriFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        cell.setRowspan( 2 );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Identificação", FontFactory.getCalibriFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        cell.setColspan( 2 );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "C.P. (Nº)", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Fc\n(N)", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Rc (MPa)", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Tipo de ruptura", FontFactory.getCalibriBoldFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Idade", FontFactory.getCalibriFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Nota Fiscal", FontFactory.getCalibriFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Corpo de prova", FontFactory.getCalibriFont( fontSizeTable ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
        table.addCell( cell );

        for ( Sample sample : samples )
        {
            addSample( table, sample );
        }
       
        document.add( table );
        
        addBreak();
        addBreak();
        
        PdfPCell cellDate = new PdfPCell( new Phrase( "Estrela, " + formmater.formatDate( new Date( System.currentTimeMillis() ) ), FontFactory.getCalibriFont( fontSize ) ) );
        cellDate.setBorder( Rectangle.NO_BORDER );
        cellDate.setFixedHeight( 25 );
        cellDate.setHorizontalAlignment( Rectangle.ALIGN_CENTER );
         
        PdfPTable assTable = new PdfPTable( 2 );
        assTable.setKeepTogether( true );
        
        if ( ! report.isLetterhead() )
        {
            Image assFernando = Image.getInstance( ResourceLocator.getImageURL( "report/ass_fernando.jpg" ) );
            assFernando.scaleAbsoluteWidth( 100f );
            assFernando.scaleAbsoluteHeight( 27f );

            Image assDouglas = Image.getInstance( ResourceLocator.getImageURL( "report/ass_douglas.jpg" ) );
            assDouglas.scaleAbsoluteWidth( 100f );
            assDouglas.scaleAbsoluteHeight( 27f );

            cell = new PdfPCell( assFernando, false );
            cell.setBorder( Rectangle.NO_BORDER );
            cell.setHorizontalAlignment( Rectangle.ALIGN_CENTER );
            assTable.addCell( cell );

            cell = new PdfPCell( assDouglas, false );
            cell.setBorder( Rectangle.NO_BORDER );
            cell.setHorizontalAlignment( Rectangle.ALIGN_CENTER );
            assTable.addCell( cell );
        }
        
        else
        {
            cell = new PdfPCell();
            cell.setFixedHeight( 50f );
            cell.setBorder( Rectangle.NO_BORDER );
            
            assTable.addCell( cell );
            assTable.addCell( cell );
        }
        
        cell = new PdfPCell( new Phrase( "Fernando Henrique Scheneider", FontFactory.getCalibriFont( 9 ) ) );
        cell.setBorder( Rectangle.NO_BORDER );
        cell.setHorizontalAlignment( Rectangle.ALIGN_CENTER );
        assTable.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Douglas Giongo Ludwig", FontFactory.getCalibriFont( 9 ) ) );
        cell.setBorder( Rectangle.NO_BORDER );
        cell.setHorizontalAlignment( Rectangle.ALIGN_CENTER );
        assTable.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Responsável Técnico - CREA-RS nº198500", FontFactory.getCalibriFont( 8 ) ) );
        cell.setBorder( Rectangle.NO_BORDER );
        cell.setHorizontalAlignment( Rectangle.ALIGN_CENTER );
        assTable.addCell( cell );
        
        cell = new PdfPCell( new Phrase( "Eng. Civil - CREA-RS nº205618", FontFactory.getCalibriFont( 8 ) ) );
        cell.setBorder( Rectangle.NO_BORDER );
        cell.setHorizontalAlignment( Rectangle.ALIGN_CENTER );
        assTable.addCell( cell );
        
        PdfPCell cellAss = new PdfPCell( assTable );
        cellAss.setBorder( Rectangle.NO_BORDER );
        cellAss.setHorizontalAlignment( Rectangle.ALIGN_CENTER );
        
        
        PdfPTable endBox = new PdfPTable( 1 );
        endBox.setKeepTogether( true );
        endBox.setWidthPercentage( 100 );
        endBox.setTotalWidth( PageSize.A4.getWidth() - document.leftMargin() - document.rightMargin() );
        
        endBox.addCell( cellDate );
        endBox.addCell( cellAss );
        
        float spaceAss = document.bottom() + endBox.getTotalHeight();
        
        if (  writer.getVerticalPosition( false ) < spaceAss )
        {
            document.newPage();
        }
        
        endBox.writeSelectedRows( 0, -1, document.left(), spaceAss, writer.getDirectContent() );
        
        document.close();
    }
    
    private void addSample( PdfPTable table, Sample sample )
    {
        table.addCell( createCell( formmater.formatId( sample ) ) );
        table.addCell( createCell( formmater.formatFC( newton.evaluate( sample.getResistence() ) ) ) );
        table.addCell( createCell( formmater.formatRC( rc.evaluate( sample.getResistence() ) ) ) );
        table.addCell( createCell( sample.getRuptureType() ) );
        table.addCell( createCell( formmater.formatAge( sample ) ) );
        table.addCell( createCell( formmater.formatDate( sample.getDateExecuted(), "dd/MM/YYYY" ) ) );
        table.addCell( createCell( sample.getNf() ) );
        table.addCell( createCell( sample.getName() ) );
    }
    
    private PdfPCell createCell( Object value )
    {
        PdfPCell cell = new PdfPCell( new Phrase( value != null ? value.toString() : "", FontFactory.getCalibriFont( 9 ) ) );
        cell.setVerticalAlignment( Element.ALIGN_MIDDLE );
        cell.setHorizontalAlignment( Element.ALIGN_CENTER );
 
        return cell;
    }
    
    private void addBreak() throws Exception
    {
        document.add( Chunk.NEWLINE );
    }
    
    private void createClientSection( SampleReport report ) throws Exception
    {
        Sample root = report.getRoot();
        
        Paragraph client = new Paragraph();
        client.add( new Chunk( "Cliente:           ", FontFactory.getCalibriBoldFont( fontSize ) ) );
        client.add( new Chunk( root.getClient().getName(), FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( client );
        
        String address = root.getClient().getAddress();
        
        Paragraph paragraph = new Paragraph();
        paragraph.add( new Chunk( "                         ", FontFactory.getCalibriBoldFont( fontSize ) ) );
        paragraph.add( new Chunk( address, FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( paragraph );
        
        City city = root.getClient().getCity();
        
        paragraph = new Paragraph();
        paragraph.add( new Chunk( "                         ", FontFactory.getCalibriBoldFont( fontSize ) ) );
        paragraph.add( new Chunk( city + "-" + city.getState().getUF().toUpperCase(), FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( paragraph );
        
        Cnpj cnpj = root.getClient().getCnpj();
        
        paragraph = new Paragraph();
        paragraph.add( new Chunk( "                         ", FontFactory.getCalibriBoldFont( fontSize ) ) );
        paragraph.add( new Chunk( "CNPJ " + cnpj, FontFactory.getCalibriFont( fontSize ) ) );
        
        document.add( paragraph );
    }
    
    private void createJobSection( SampleReport report ) throws Exception
    {
        Sample root = report.getRoot();
        
        if ( root.getJob() != null )
        {
            Paragraph job = new Paragraph();
            job.add( new Chunk( "Obra:              ", FontFactory.getCalibriBoldFont( fontSize ) ) );
            job.add( new Chunk( root.getJob().getName(), FontFactory.getCalibriFont( fontSize ) ) );

            document.add( job );

            String address = root.getJob().getAddress();
        
            Paragraph paragraph;
                
            if ( ! Strings.isNullOrEmpty( address ) )
            {
                paragraph= new Paragraph();
                paragraph.add( new Chunk( "                         ", FontFactory.getCalibriBoldFont( fontSize ) ) );
                paragraph.add( new Chunk( address, FontFactory.getCalibriFont( fontSize ) ) );

                document.add( paragraph );
            }

            City city = root.getJob().getCity();

            if ( city != null )
            {
                paragraph = new Paragraph();
                paragraph.add( new Chunk( "                         ", FontFactory.getCalibriBoldFont( fontSize ) ) );
                paragraph.add( new Chunk( city + "-" + city.getState().getUF().toUpperCase(), FontFactory.getCalibriFont( fontSize ) ) );
            
                document.add( paragraph );
            }

            String cei = root.getJob().getCEI();

            if ( ! Strings.isNullOrEmpty( cei ) )
            {
                paragraph = new Paragraph();
                paragraph.add( new Chunk( "                         ", FontFactory.getCalibriBoldFont( fontSize ) ) );
                paragraph.add( new Chunk( "CEI " + cei, FontFactory.getCalibriFont( fontSize ) ) );

                document.add( paragraph );
            }
            
            addBreak();
        }
    }
    
    private float px( float cm ) 
    {
        return cm * 36;
    }
}
