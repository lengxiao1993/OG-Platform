/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web;

import java.net.URI;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.joda.beans.impl.flexi.FlexiBean;

/**
 * RESTful resource for the about page.
 */
@Path("/about")
public class WebAboutResource extends AbstractWebResource {

  /**
   * Creates the resource.
   */
  public WebAboutResource() {
  }

  //-------------------------------------------------------------------------
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String get(@Context ServletContext servletContext, @Context UriInfo uriInfo) {
    FreemarkerOutputter freemarker = new FreemarkerOutputter(servletContext);
    FlexiBean out = freemarker.createRootData();
    out = createRootData(out, uriInfo, servletContext);
    return freemarker.build("about.ftl", out);
  }

  //-------------------------------------------------------------------------
  /**
   * Creates the output root data.
   * 
   * @param out  the root data to populate, not null
   * @param uriInfo  the URI information, not null
   * @param servletContext  the servlet context, not null
   * @return the output root data, not null
   */
  protected FlexiBean createRootData(FlexiBean out, UriInfo uriInfo, ServletContext servletContext) {
    out.put("uris", new WebHomeUris(uriInfo));
    
    out.put("baseUri", uriInfo.getBaseUri().toString());
    out.put("about", new WebAbout(servletContext));
    
    return out;
  }

  //-------------------------------------------------------------------------
  /**
   * Builds a URI for this page.
   * @param uriInfo  the uriInfo, not null
   * @return the URI, not null
   */
  public static URI uri(UriInfo uriInfo) {
    return uriInfo.getBaseUriBuilder().path(WebAboutResource.class).build();
  }

}
