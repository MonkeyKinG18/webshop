package app.auth;


import com.google.inject.Inject;
import io.dropwizard.auth.UnauthorizedHandler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DefaultUnauthorizedHandler implements UnauthorizedHandler
{
    @Inject
    public DefaultUnauthorizedHandler() {
    }

    @Override
    public Response buildResponse(String prefix, String realm) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity("Credentials are required to access this resource.")
                .build();
    }
}
