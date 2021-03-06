/* 
 *  Filename:    FontFactory 
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
package com.me.eng.core.reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.me.eng.core.application.ResourceLocator;
import java.awt.Color;

/**
 *
 * @author Matheus
 */
public class FontFactory
{
    private static BaseFont CALIBRI;
    private static BaseFont CALIBRI_BOLD;
    
    static
    {
        try
        {
            CALIBRI = BaseFont.createFont( "calibri.ttf", 
                                           BaseFont.WINANSI, true, true, 
                                           ResourceLocator.getResourceAsArray( "/fonts/calibri.ttf" ), null );
            
            CALIBRI_BOLD = BaseFont.createFont( "calibrib.ttf", 
                                                BaseFont.WINANSI, true, true, 
                                                ResourceLocator.getResourceAsArray( "/fonts/calibrib.ttf" ), null );
        }
        
        catch ( Exception ex )
        {
            throw new RuntimeException( ex );
        }
    }
    
    /**
     * getCalibriFont
     * 
     * @param size float
     * @return Font
     */
    public static Font getCalibriFont( float size )
    {
        return getCalibriFont( size, Color.black );
    }
    
    /**
     * getCalibriFont
     * 
     * @param size float
     * @param color Color
     * @return Font
     */
    public static Font getCalibriFont( float size, Color color )
    {
        Font font = new Font( CALIBRI );
        font.setColor( new BaseColor( color.getRGB() ) );
        font.setSize( size );
        
        return font;
    }
    
    /**
     * getCalibriBoldFont
     * 
     * @param size float
     * @return Font
     */
    public static Font getCalibriBoldFont( float size )
    {
        return getCalibriBoldFont( size, Color.black );
    }
    
    /**
     * getCalibriBoldFont
     * 
     * @param size float
     * @param color Color
     * @return Font
     */
    public static Font getCalibriBoldFont( float size, Color color )
    {
        Font font = new Font( CALIBRI_BOLD );
        font.setColor( new BaseColor( color.getRGB() ) );
        font.setSize( size );
        
        return font;
    }
}
