package br.com.compass.mscatalog.resources;

import br.com.compass.mscatalog.domain.model.Media;
import br.com.compass.mscatalog.domain.model.Product;
import br.com.compass.mscatalog.domain.model.Sku;
import br.com.compass.mscatalog.repository.MediaRepository;
import br.com.compass.mscatalog.repository.ProductRepository;
import br.com.compass.mscatalog.repository.SkuRepository;
import br.com.compass.mscatalog.resources.dto.SkuDto;
import br.com.compass.mscatalog.resources.dto.SkuFormDto;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/skus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class SkuResource {

    private SkuRepository skuRepository;
    private ProductRepository productRepository;
    private MediaRepository mediaRepository;

    @Inject
    public SkuResource(SkuRepository skuRepository, ProductRepository productRepository, MediaRepository mediaRepository){
        this.skuRepository = skuRepository;
        this.productRepository = productRepository;
        this.mediaRepository = mediaRepository;
    }

    @POST
    public Response create(@Valid SkuFormDto skuFormDto){
        Product product = productRepository.findById(skuFormDto.getProductId());
        if (product == null){
            return Response.status(404).entity("Product id: " + skuFormDto.getProductId() + " not found.").build();
        }
        Sku sku = new Sku(skuFormDto, product);
        for(String imagemUrl : skuFormDto.getImages()) {
            Media media = new Media(imagemUrl, sku);
            sku.addImages(media);
            mediaRepository.persist(media);
        }
        skuRepository.persist(sku);
        return Response.status(201).entity(new SkuDto(sku)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@PathParam("id") Long id, @Valid SkuFormDto skuFormDto){
        Sku sku = skuRepository.findById(id);
        if (sku == null){
            return Response.status(404).entity("Sku id: " + id + " not found.").build();
        }
        Product product = productRepository.findById(skuFormDto.getProductId());
        if (product == null){
            return Response.status(404).entity("Product id: " + skuFormDto.getProductId() + " not found.").build();
        }
        sku.setProduct(product);
        sku.setColor(skuFormDto.getColor());
        sku.setPrice(skuFormDto.getPrice());
        sku.setQuantity(skuFormDto.getQuantity());
        sku.setSize(skuFormDto.getSize());
        sku.setHeight(skuFormDto.getHeight());
        sku.setWidth(skuFormDto.getWidth());

        for(String imagemUrl : skuFormDto.getImages()) {
            mediaRepository.persist(new Media(imagemUrl, sku));
        }
        return Response.ok(new SkuDto(sku)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id){
        if (skuRepository.findById(id) == null){
            return Response.status(404).entity("Sku id: " + id + " not found.").build();
        }
        skuRepository.deleteById(id);
        return Response.noContent().build();
    }
}
