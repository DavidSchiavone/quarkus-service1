package ch.hftm.blog.boundary;

import ch.hftm.blog.control.AiService;
import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.dto.BlogDTO;
import ch.hftm.blog.control.dto.QandADTO;
import ch.hftm.blog.control.dto.QandAResponseDTO;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.messaging.GenerateBlogRequest;
import ch.hftm.blog.messaging.AdditionalInformationsRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Produces("application/json")
@Consumes("application/json")
@Path("blogs")
public class BlogRessource {
    @Inject
    BlogService blogService;

    @Inject
    BlogKafkaResource kafkaResource;

    @Inject
    AiService aiService;

    @GET
    public List<Blog> getBlogs(@QueryParam("searchStr") Optional<String> searchStr) {
        return blogService.getBlogs(searchStr);
    }

    @Path("{id}")
    @GET
    public Response getBlog(@PathParam("id") long id) {
        return Optional.ofNullable(blogService.getBlog(id))
                .map(blog -> Response.ok(blog)
                        .build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .build());
    }


    @POST
    public void addBlog(@Valid BlogDTO blog) {
        Blog blog1 = blogService.addBlog(blog);

        kafkaResource.sendRequest(new AdditionalInformationsRequest(blog1.getBlogid(), blog.getTitle(), blog1.getContent()));
    }

    @Path("generate")
    @POST
    public void generateBlog(@Valid GenerateBlogRequest generateBlogRequest) {
        kafkaResource.sendGenerateBlogRequest(generateBlogRequest);
    }


    @Path("{id}")
    @PUT
    public Response updateBlog(@PathParam("id") long id, @Valid BlogDTO blog) {
        if (blogService.updateBlog(id, blog)) {
            return Response.ok()
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @Path("{id}")
    @DELETE
    public Response deleteBlog(@PathParam("id") long id) {
        if (blogService.deleteBlog(id)) {
            return Response.ok()
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @Path("{id}/qa")
    @GET
    public Response qa(@PathParam("id") long id, QandADTO qa) {
        Blog blog = blogService.getBlog(id);

        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        String answer = aiService.answerQuestion(blog.getTitle(), blog.getContent(), qa.getQuestion());

        return Response.ok(new QandAResponseDTO(answer))
                .build();
    }

    @Path("{id}/similars")
    @GET
    // TODO: this should be optimized with a vector database
    public Response getSimilars(@PathParam("id") long id) {
        List<Blog> blogs = blogService.getBlogs(Optional.empty());

        if (blogs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        if ( blogs.stream()
                .noneMatch(blog -> blog.getBlogid() == id)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        List<Long> similarBlogIds = aiService.findSimilarBlogs(id, blogs)
                .stream()
                .map(String::trim)
                .map(Long::parseLong)
                .toList();

        List<Blog> similarBlogs = blogs.stream()
                .filter(blog -> similarBlogIds.contains(blog.getBlogid()))
                .toList();

        return Response.ok(similarBlogs)
                .build();
    }
}
