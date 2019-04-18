package recourse;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import model.User;
import service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    UserService userService;

    @Inject
    public UserResource(UserService service) {
        this.userService = service;
    }

    @POST
    @Timed
    @Path("/postName")
    public String postName(String name) {
        System.out.println("Name given by : "+name);
        return "Ok";
    }

    @POST
    @Path("/")
    @UnitOfWork
    public Response store(@NotNull @Valid User user){
        userService.store(user);
        return Response.status(201).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/")
    public List<User> findAllEmp() {
//        System.out.println("All Emp  : "+userService.retrieveAll());
        return userService.retrieveAll();

    }
}