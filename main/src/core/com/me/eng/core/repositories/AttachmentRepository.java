/*
 *  Filename:    AttachmentRepository
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
package com.me.eng.core.repositories;

import com.me.eng.core.domain.Attachment;
import com.me.eng.core.domain.AttachmentSource;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Artur Tomasi
 */
@Named
public interface AttachmentRepository 
    extends 
        EntityRepository<Attachment>
{
    public abstract List<Attachment> find( AttachmentSource source ) throws Exception;
    
    public abstract void delete( AttachmentSource source ) throws Exception;
}
