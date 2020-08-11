package org.acme.rest.coffee

import org.acme.core.cofffe.BaristaNotFoundException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class MyApplicationExceptionHandler : ExceptionMapper<BaristaNotFoundException> {
    override fun toResponse(exception: BaristaNotFoundException): Response {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.message).build()
    }
}