package br.com.compass.mscatalog.resources;

import br.com.compass.mscatalog.domain.model.Category;
import br.com.compass.mscatalog.domain.model.Product;
import br.com.compass.mscatalog.repository.CategoryRepository;
import br.com.compass.mscatalog.repository.ProductRepository;
import br.com.compass.mscatalog.resources.dto.ProductDto;
import br.com.compass.mscatalog.resources.dto.ProductFormDto;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Inject
    public ProductResource(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GET
    public Response findAll(){
        List<ProductDto> productDtoList = productRepository.findAll().list().stream().map(ProductDto::new).collect(Collectors.toList());
        return Response.ok(productDtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        Product product = productRepository.findById(id);
        if (product == null){
            return Response.status(404).entity("Product id " + id + " not found.").build();
        }
        return Response.ok(new ProductDto(product)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid ProductFormDto productFormDto){
        Category category = categoryRepository.findById(productFormDto.getCategoryId());
        if (category == null) {
            return Response.status(404).entity("Category id " + productFormDto.getCategoryId() + " not found.").build();
        }
        if(verifyCategory(category)) {
            Product product = new Product(productFormDto, category);
            productRepository.persist(product);
            return Response.status(201).entity(new ProductDto(product)).build();
        } else {
            return Response.status(400).entity("It is not possible to add a product to this category.").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateById(@PathParam("id") Long id, @Valid ProductFormDto productFormDto){
        Category category = categoryRepository.findById(productFormDto.getCategoryId());
        if (category == null) {
            return Response.status(404).entity("Category id " + productFormDto.getCategoryId() + " not found.").build();
        }
        Product product = productRepository.findById(id);
        if (product == null){
            return Response.status(404).entity("Product id " + id + " not found.").build();
        }
        if(!verifyCategory(category)) {
            return Response.status(400).entity("It is not possible to add a product to this category.").build();
        } else {
            product.setName(productFormDto.getName());
            product.setDescription(productFormDto.getDescription());
            product.setActive(productFormDto.getActive());
            product.setBrand(productFormDto.getBrand());
            product.setMaterial(productFormDto.getMaterial());
            product.setCategory(category);
            return Response.status(201).entity(new ProductDto(product)).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long id){
        Product product = productRepository.findById(id);
        if (product == null){
            return Response.status(404).entity("Product id " + id + " not found.").build();
        }
        productRepository.deleteById(id);
        return Response.noContent().build();
    }

    private Boolean verifyCategory(Category category) {
        if (!category.getChildren().isEmpty() || !category.getActive()) {
            return false;
        }
        while(category.getParent() != null) {
            if (!category.getParent().getActive()) {
                return false;
            }
            category = category.getParent();
        }
        return true;
    }
}
