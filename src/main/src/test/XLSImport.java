//package test;
//
//import java.io.File;
//import java.util.Iterator;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
///**
// *
// * @author artur
// */
//public class XLSImport 
//{
//    private static final int COL_DIM_X      = 2;
//    private static final int COL_DIM_Y      = 3;
//    private static final int COL_FC         = 5;
//    private static final int COL_RUPTURA    = 6;
//    private static final int COL_AGE        = 7;
//    private static final int COL_DT_MOLD    = 8;
//    private static final int COL_DT_NF      = 10;
//    private static final int COL_CP         = 11;
//    private static final int COL_DT_RUPTURA = 12;
//    private static final int DT_RUPTURA     = 12;
//    private static final int DT_CLIENTE     = 13;
//            
//            
//    private static final String XLS_PATH =  "/home/artur/Downloads/26-12-2017.xlsx";
//    
//    public static void main( String[] args ) 
//    {
//        try
//        {
//            XLSImport xls = new XLSImport();
//            xls.open();
//        }
//        
//        catch ( Exception e )
//        {
//            e.printStackTrace( System.err );
//        }
//    }
//    
//    private void open(  ) throws Exception
//    {
//        File f = new File( XLS_PATH ) ;
//        
//        if ( f.exists() )
//        {
//            Workbook book = new XSSFWorkbook( XLS_PATH );
//
//            Sheet sheet = book.getSheetAt( 0 );
//
//            for ( Iterator<Row> iterator = sheet.rowIterator(); iterator.hasNext(); )
//            {
//                Row row = iterator.next();
//
//                System.out.println( row.getCell( COL_CP ).getStringCellValue() );
//            }
//        }
//    }
//}
