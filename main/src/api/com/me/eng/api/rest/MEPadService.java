/*
 * Filename:    Service 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.me.eng.api.rest;

import com.me.eng.core.application.ConfigurationManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author Artur Tomasi
 */
@Path( "/me-pad" )
public class MEPadService 
{
    @GET
    @Path( "config" )
    @Produces( MediaType.APPLICATION_JSON ) 
    public Response config()
    {
        JSONObject json = new JSONObject();
        json.put( "apiKey", ConfigurationManager.getInstance().getProperty( "me.pad.api.key" ) );
        json.put( "authDomain", ConfigurationManager.getInstance().getProperty( "me.pad.auth.domain" ) );
        json.put( "databaseURL", ConfigurationManager.getInstance().getProperty( "me.pad.database.url" ) );
        
        return Response
                .status( Response.Status.OK )
                .entity( json.toString() )
                .type( MediaType.APPLICATION_JSON )
                .build();    }
}
