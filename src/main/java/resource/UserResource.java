package resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import model.User;
import service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Omid Wiar
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    UserService userService;

    @Inject
    public UserResource(UserService service) {
        this.userService = service;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/")
    @RolesAllowed({"ADMIN"})
    public List<User> retrieveAll() {
        return userService.retrieveAll();
    }

//    @GET
//    @Timed
//    @UnitOfWork
//    @Path("/{emailAddress}")
//    @RolesAllowed({"GUEST", "ADMIN"})
//    public User retrieve(@PathParam("emailAddress") String emailAddress) {
//        return userService.retrieve(emailAddress);
//    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/{id}")
    @RolesAllowed({"GUEST", "ADMIN"})
    public User retrieveByID(@PathParam("id") Integer id) {
        return userService.retrieveById(id);
    }

    @POST
    @Path("/")
    @UnitOfWork
    public Response create(@NotNull @Valid User user){
        userService.store(user);
        return Response.status(201).build();
    }

    @PUT
    @Path("/")
    @UnitOfWork
    public Response update(@NotNull @Valid User user){
        userService.update(user);
        return Response.status(200).build();
    }


    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") int id){
        userService.delete(id);
        return Response.status(200).build();
    }

//    @GET
//    @Path("/newId")
//    @UnitOfWork
//    public int generateNewId() { return userService.generateNewId(); }
}