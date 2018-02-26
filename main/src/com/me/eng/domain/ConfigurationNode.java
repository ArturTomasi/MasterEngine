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
package com.me.eng.domain;

import java.util.List;
import java.util.Properties;

/**
 *
 * @author Matheus
 */
public interface ConfigurationNode
{
    public String getName();
    public void add( ConfigurationNode node );
    public List<ConfigurationNode> children();
    public void store( Properties properties );
}
