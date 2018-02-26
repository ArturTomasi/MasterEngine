/* 
 *  Filename:    SamplePane 
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
package com.me.eng.ui.panes;

import com.me.eng.domain.repositories.UserRepository;
import javax.inject.Inject;
import org.zkoss.zul.Div;

/**
 *
 * @author Matheus
 */
public class SamplePane
    extends 
        Div
{
    @Inject UserRepository userRepository;
    
    public SamplePane()
    {
        System.out.println( userRepository );
    }
}

