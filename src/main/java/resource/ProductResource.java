package resource;

import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import model.Orders;
import model.Product;
import service.ProductService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    private ProductService productService;

    @Inject
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Path("/")
    @UnitOfWork
    public List<Product> retrieveAll(){
        return productService.retrieveAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Product retrieve(@PathParam("id") int id){
        return productService.retrieveProduct(id);
    }

    @POST
    @Path("/")
    @UnitOfWork
    @RolesAllowed({"ADMIN"})
    public Response create(@NotNull @Valid Product product){
        productService.store(product);
        return Response.status(201).build();
    }

    @PUT
    @Path("/")
    @UnitOfWork
    @RolesAllowed({"ADMIN"})
    public Response update(@NotNull @Valid Product product){
        productService.update(product);
        return Response.status(201).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @RolesAllowed({"ADMIN"})
    public Response delete(@PathParam("id") int id){
        productService.delete(id);
        return Response.status(201).build();

    }

}