package br.com.compass.mscatalog.resources;

import br.com.compass.mscatalog.domain.model.Category;
import br.com.compass.mscatalog.repository.CategoryRepository;
import br.com.compass.mscatalog.resources.dto.CategoryDto;
import br.com.compass.mscatalog.resources.dto.CategoryFormDto;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private CategoryRepository categoryRepository;

    @Inject
    public CategoryResource(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @POST
    @Transactional
    public Response create(@Valid CategoryFormDto categoryFormDto){

        if(categoryFormDto.getParentId() == null){
            Category category = new Category(categoryFormDto);
            categoryRepository.persist(category);
            return Response.status(201).entity(new CategoryDto(category)).build();
        }
        Category parentCategory = categoryRepository.findById(categoryFormDto.getParentId());
        if(parentCategory == null){
            return Response.status(404).entity("ParentCategory id: " + categoryFormDto.getParentId() + " not found.").build();
        } else {
            Category saveCategory = new Category(categoryFormDto, parentCategory);
            categoryRepository.persist(saveCategory);
            parentCategory.addChildren(saveCategory);
            return Response.status(201).entity(new CategoryDto(saveCategory)).build();
        }
    }

    @GET
    public Response findAll(){
        List<Category> categoryList = categoryRepository.findAll().list();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(CategoryDto::new).collect(Collectors.toList());
        return Response.ok(categoryDtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        Category category = categoryRepository.findById(id);
        if(category == null){
            return Response.status(404).entity("Category id " + id + " not found.").build();
        }
        return Response.ok(new CategoryDto(category)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long id){
        Category category = categoryRepository.findById(id);
        if(category == null){
            return Response.status(404).entity("Category id " + id + " not found.").build();
        }
        categoryRepository.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateById(@PathParam("id") Long id, @Valid CategoryFormDto categoryFormDto){
        Category category = categoryRepository.findById(id);
        if(category == null){
            return Response.status(404).entity("Category id " + id + " not found.").build();
        }
        category.setName(categoryFormDto.getName());
        category.setActive(categoryFormDto.getActive());
        return Response.ok(new CategoryDto(category)).build();
    }
}
