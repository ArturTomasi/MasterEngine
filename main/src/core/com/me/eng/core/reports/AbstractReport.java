package com.me.eng.core.reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.application.ResourceLocator;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * @author artur
 */
public abstract  class AbstractReport 
{
    protected Color color      = new Color( 48, 67, 105 );
    protected BaseColor bcolor = new BaseColor( 48, 67, 105 );
    
    protected Font fontHead     = FontFactory.getCalibriBoldFont( 10 );
    protected Font fontRow      = FontFactory.getCalibriFont( 9 );
    protected Font fontTitle    = FontFactory.getCalibriBoldFont( 16, color );
    protected Font fontSubTitle = FontFactory.getCalibriBoldFont( 14, color);
     
    protected DateFormat df   = new SimpleDateFormat( "dd/MM/yyyy" );
    protected NumberFormat nf = NumberFormat.getCurrencyInstance();
    
    protected Document document;
    protected PdfWriter writer;
    
    /**
     * AbstractReport
     * 
     */
    public AbstractReport() 
    {
        this( false );
    }
    
    
    /**
     * AbstractReport
     * 
     * @param rotate boolean
     */
    public AbstractReport( boolean rotate )
    {
        if( rotate )
        {
            document = new Document( PageSize.A4.rotate(), 36f, 36f, 36f, 36f );
        }
        
        else
        {
            document = new Document( PageSize.A4, 36f, 36f, 36f, 36f );
        }
    }
    
    /**
     * setTitle
     * 
     * @param title String
     */
    protected void setTitle( String title )
    {
        setTitle( title, null );
    }
    
    /**
     * setTitle
     * 
     * @param title String
     * @param icon String
     */
    protected void setTitle( String title, String icon )
    {
        try
        {
            Paragraph paragraph = new Paragraph();
            paragraph.add( new Chunk( title, fontTitle ) );
            paragraph.setAlignment( Paragraph.ALIGN_CENTER );
            paragraph.setSpacingAfter( 2f );

            if( icon != null )
            {
                PdfPCell titleCell = new PdfPCell();
                titleCell.addElement( paragraph );
                titleCell.setPadding( 0 );
                titleCell.setHorizontalAlignment( PdfPCell.ALIGN_RIGHT );
                titleCell.setBorder( PdfPCell.NO_BORDER );

                PdfPCell iconCell = new PdfPCell();
                iconCell.setPadding( 0 );
                iconCell.setHorizontalAlignment( PdfPCell.ALIGN_RIGHT );
                iconCell.setBorder( PdfPCell.NO_BORDER );
                Image img = Image.getInstance( ResourceLocator.getImageResource( icon ) );
                img.scaleAbsolute( 40, 40 );
                img.setAlignment( Image.ALIGN_RIGHT );
                iconCell.addElement( img );

                PdfPTable titleTable = new PdfPTable( new float[]{ 97f, 3f } );
                titleTable.setSpacingAfter( 2f );
                titleTable.setWidthPercentage( 100 );
                titleTable.addCell( titleCell );
                titleTable.addCell( iconCell );

                document.add( titleTable );
            }

            else
            {
                document.add( paragraph );
            }

            newLine();
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e ); 
        }
    }

    /**
     * setSubTitle
     * 
     * @param subTitle String
     * @throws Exception
     */
    protected void setSubTitle( String subTitle ) throws Exception
    {
        newLine();
        Paragraph paragraph = new Paragraph();
        paragraph.add( new Chunk( subTitle, fontSubTitle ) );
        paragraph.setAlignment( Paragraph.ALIGN_CENTER );
        paragraph.setSpacingAfter( 2f );
        
        document.add( paragraph );
    }
    
    /**
     * separator
     * 
     * @throws Exception
     */
    protected void separator() throws Exception
    {
        PdfPTable separator = new PdfPTable( 1 );
        separator.setWidthPercentage( 100 );
        
        PdfPCell cell = new PdfPCell();
        cell.setPadding( 0 );
        cell.setBorder( PdfPCell.BOTTOM );
        cell.setBorderWidth( 2f );
        cell.setBorderColor( bcolor );
        cell.setFixedHeight( 10f );
        
        separator.addCell( cell );
        
        document.add( separator );
    }

    
    /**
     * newLine
     * 
     * @throws Exception
     */
    protected void newLine() throws Exception
    {
        document.add( new Phrase( "\n" ) );
    }
    
    
    /**
     * newPage
     * 
     */
    protected void newPage()
    {
        document.newPage();
    }
    
    
    /**
     * addHTML
     * 
     * @param html String
     * @throws Exception
     */
    protected void addHTML( String html ) throws Exception
    {
        XMLWorkerHelper.parseToElementList( html.replaceAll( "<br>", "<br></br>"), null ).forEach( element -> 
        {
            try
            {
                if( element instanceof Paragraph )
                {
                    Paragraph paragraph = (Paragraph) element;
                    paragraph.setAlignment( Paragraph.ALIGN_CENTER );
                    paragraph.setSpacingAfter( 2f );
                    paragraph.setFont( fontSubTitle );

                    document.add( paragraph );
                }
                
                else if ( element instanceof List )
                {
                    List list = (List) element;

                    document.add( list );
                    
                }
            }
            
            catch( DocumentException e )
            {
                ApplicationContext.getInstance().logException( e );
            }
        } );
    }

    
    /**
     * printFooter
     * 
     * @param writer PdfWriter
     */
    private void printFooter( PdfWriter writer )
    {
        String user = ApplicationContext.getInstance().getActiveUser().getName();
        
        String date = df.format( new Date( System.currentTimeMillis() ) );
        
        FooterEvent event = new FooterEvent( user, date );
        
        writer.setPageEvent( event );
    }
    
    /**
     * generate
     * 
     * @return File
     * @throws Exception
     */
    public File generate() throws Exception
    {
        File file = File.createTempFile( "Master Engine ", ".pdf" );
        
        writer = PdfWriter.getInstance( document, new FileOutputStream( file ) );

        printFooter( writer );

        document.open();

        document.addAuthor( "Over Line Ltda" );
        document.addCreator( "Master Engine Application" );
        document.addTitle( "Report Master Engine" );

        generateDocument( document );

        document.close();
        
        return file;
    }

   
    /**
     * void
     * 
     * @param document Document
     * @return abstract
     * @throws Exception
     * @ignored generateDocument
     */
    protected abstract void generateDocument( Document document ) throws Exception;
    
    
    /**
     * Table
     * 
     * @author Artur Tomasi
     */
    protected class Table
        extends 
            PdfPTable
    {
        /**
         * Table
         * 
         * @param widths float...
         * @throws Exception
         */
        public Table( float... widths ) throws Exception
        {
            super( widths );
            setWidthPercentage( 100f );
        }
        
        /**
         * setHeader
         * 
         * @param header String...
         */
        public void setHeader( String... header )
        {
            for( String head : header )
            {
                PdfPCell cell = new PdfPCell();
//                cell.setBorderColor( background );
//                cell.setBorderWidth( 1.5f );
//                cell.setBackgroundColor( background );

                Paragraph paragraph = new Paragraph();
                paragraph.add( new Chunk( head, fontHead ) );
                paragraph.setAlignment( Paragraph.ALIGN_CENTER );
                paragraph.setSpacingAfter( 5f );

                cell.addElement( paragraph );
                
                addCell( cell );
            }
        }
        
        /**
         * addRow
         * 
         * @param row Object...
         */
        public void addRow( Object... row )
        {
            for( Object r : row )
            {
                PdfPCell cell = new PdfPCell();
                cell.setBorderColor( bcolor );
                cell.setBorderWidth( 1 );
                cell.setPaddingLeft( 3f );
                
                if ( r instanceof Image )
                {
                    Image img = (Image) r;
                    img.scaleAbsolute( 10, 10 );
                    img.setSpacingBefore( 4f );
                    img.setAlignment( Paragraph.ALIGN_MIDDLE );
                    cell.addElement( img );
                }
                
                else if ( r instanceof PdfPCell )
                {
                    cell = (PdfPCell) r;
                }

                else 
                {
                    String value = r != null ? r.toString() : "n/d";
                    
                    Paragraph paragraph = new Paragraph( value, fontRow );
                    paragraph.setSpacingAfter( 5f );
                    cell.addElement(paragraph);
                }


                addCell( cell );
            }
        }
    }
    

    
    /**
     * DetailsTable
     * 
     * @author Artur Tomasi
     */
    protected class DetailsTable
        extends 
            PdfPTable
    {
        private BaseColor background2 = new BaseColor( 207, 216, 220 ); //#CFD8DC
        private BaseColor background  = new BaseColor( 144, 164, 174 ); //90A4AE    
        private BaseColor fontColor2  = new BaseColor( 55, 71, 79 ); //263238
        
        private boolean formatCelll = true;
        
        /**
         * DetailsTable
         * 
         * @param widths float...
         * @throws Exception
         */
        public DetailsTable( float... widths ) throws Exception
        {
            super( ( widths.length * 2 ) -1 );
            
            float heads [] = new float[ ( widths.length * 2 ) -1 ];
            
            int j = 0;
            
            for ( int i = 0; i < heads.length; i++ )
            {
                if( i % 2 == 0 )
                {
                    heads[i] = widths[j]; j++;
                }
                
                else
                {
                    heads[i] = 1f;
                }
            }
            
            setWidths( heads );
            
            setWidthPercentage( 100f );
            
            addSeparatorRow();
        }
        
        /**
         * setHeader
         * 
         * @param header String...
         */
        public void setHeader( String... header )
        {
            for( int i = 0; i < header.length; i++ )
            {
                String head = header[i];
                
                PdfPCell cell = new PdfPCell();
                cell.setBorderColor( background );
                cell.setBorderWidth( 1.5f );
                cell.setBackgroundColor( background );

                Paragraph paragraph = new Paragraph();
                paragraph.add( new Chunk( head, new Font( Font.FontFamily.HELVETICA  , 12, Font.BOLD, BaseColor.WHITE ) ) );
                paragraph.setAlignment( Paragraph.ALIGN_CENTER );
                paragraph.setSpacingAfter( 5f );

                cell.addElement( paragraph );
                
                addCell( cell );
            
                if( i != header.length -1 )
                {
                    addSeparatorColumn();
                }
            }
            
            addSeparatorRow();
        }
        
        /**
         * addRow
         * 
         * @param row Object...
         */
        public void addRow( Object... row )
        {
            for ( int i = 0; i < row.length; i++ )
            {
                Object column = row[i];
                
                PdfPCell cell = new PdfPCell();
                cell.setBackgroundColor( BaseColor.WHITE );            
                cell.setBorderWidth( 1.5f );

                if( i == 0 && formatCelll )
                {
                    cell.setBackgroundColor( background );
                    cell.setBorderColor( background );
                    
                    if( column instanceof String )
                    {
                        Paragraph paragraph = new Paragraph();
                        paragraph.setAlignment( Paragraph.ALIGN_MIDDLE );
                        paragraph.setIndentationLeft( 5f );
                        paragraph.setSpacingAfter( 5f );
                        paragraph.add( new Chunk( column + ":", new Font( Font.FontFamily.HELVETICA  , 12, Font.BOLD, BaseColor.WHITE ) ) );
                        cell.addElement( paragraph );
                    }

                    else if( column instanceof Image )
                    {
                        Image img = (Image) column;
                        img.scaleAbsolute( 10, 10 );
                        img.setSpacingBefore( 4f );
                        img.setAlignment( Paragraph.ALIGN_MIDDLE );
                        cell.addElement( img );
                    }
                }

                else
                {
                     cell.setBackgroundColor( background2 );
                     cell.setBorderColor( background2 );
                     
                    if( column instanceof String )
                    {
                        Font font = new Font( Font.FontFamily.HELVETICA  , 10, Font.BOLD, fontColor2 );
                        Paragraph paragraph = new Paragraph();
                        paragraph.setAlignment( Paragraph.ALIGN_MIDDLE );
                        paragraph.setSpacingAfter( 5f );
                        paragraph.setIndentationLeft( 5f );
                        paragraph.add( new Chunk( (String)column, font ) );
                        cell.addElement( paragraph );
                    }

                    else if( column instanceof Image )
                    {
                        Image img = (Image) column;
                        img.scaleAbsolute( 10, 10 );
                        img.setSpacingBefore( 4f );
                        img.setAlignment( Paragraph.ALIGN_MIDDLE );
                        cell.addElement( img );
                    }
                }

                addCell( cell );
                
                if( i != row.length -1 )
                {
                    addSeparatorColumn();
                }
            }
            
            addSeparatorRow();
        }

        /**
         * addSeparatorColumn
         * 
         */
        private void addSeparatorColumn()
        {
            PdfPCell cell = new PdfPCell();
            cell.setBorder( PdfPCell.NO_BORDER );

            addCell( cell ); 
        }

        /**
         * addSeparatorRow
         * 
         */
        private void addSeparatorRow()
        {
            for( int i =0; i < getAbsoluteWidths().length; i++ )
            {
                PdfPCell cell = new PdfPCell();
                cell.setBorder( PdfPCell.NO_BORDER );
                cell.setFixedHeight( 5f );
                addCell( cell );
            }
        }

        /**
         * getBackground
         * 
         * @return BaseColor
         */
        public BaseColor getBackground() 
        {
            return background;
        }

        /**
         * setBackground
         * 
         * @param background BaseColor
         */
        public void setBackground( BaseColor background )
        {
            this.background = background;
        }

        /**
         * isFormatCelll
         * 
         * @return boolean
         */
        public boolean isFormatCelll() 
        {
            return formatCelll;
        }

        /**
         * setFormatCelll
         * 
         * @param formatCelll boolean
         */
        public void setFormatCelll( boolean formatCelll )
        {
            this.formatCelll = formatCelll;
        }
    }
}
