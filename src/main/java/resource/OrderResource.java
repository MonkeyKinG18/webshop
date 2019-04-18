package resource;

import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import model.Orders;
import model.User;
import service.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    private OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService){
        this.orderService = orderService;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Orders retrieve(@PathParam("id") int id){
        return orderService.retrieve(id);
    }

    @GET
    @Path("/")
    @UnitOfWork
    @RolesAllowed({ "ADMIN"})
    public List<Orders> retrieveAll(){
        return orderService.retrieveAll();
    }

    @POST
    @Path("/")
    @RolesAllowed({"GUEST", "ADMIN"})
    @UnitOfWork
    public Response store(@NotNull @Valid Orders order, @Auth User user){
        order.setUser(user);
        orderService.store(order);
        return Response.status(201).build();
    }

    @PUT
    @Path("/")
    @UnitOfWork
    public Response update(@NotNull @Valid Orders order) {
        orderService.update(order);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @RolesAllowed({"GUEST", "ADMIN"})
    public Response delete(@PathParam("id") int id){
        orderService.delete(id);
        return Response.status(200).build();
    }


}
