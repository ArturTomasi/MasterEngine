/* 
 *  Filename:    ConfigurationNode 
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
package com.me.eng.core.domain;

import java.util.List;
import java.util.Properties;

/**
 *
 * @author Matheus
 */
public interface ConfigurationNode
{
    /**
     * getName
     * 
     * @return String
     */
    public String getName();
    
    /**
     * add
     * 
     * @param node ConfigurationNode
     */
    public void add( ConfigurationNode node );
    
    /**
     * children
     * 
     * @return List&lt;ConfigurationNode&gt;
     */
    public List<ConfigurationNode> children();
    
    /**
     * store
     * 
     * @param properties Properties
     */
    public void store( Properties properties );
}
