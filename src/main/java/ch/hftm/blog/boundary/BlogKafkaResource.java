package ch.hftm.blog.boundary;

import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.dto.BlogDTO;
import ch.hftm.blog.control.mapper.BlogMapper;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.messaging.ValidationRequest;
import ch.hftm.blog.messaging.ValidationResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class BlogKafkaResource {

    @Inject
    @Channel("validate-blog-request")
    Emitter<ValidationRequest> requestEmitter;

    @Inject
    BlogService blogService;

    public void sendRequest(ValidationRequest request) {
        requestEmitter.send(request);
    }

    @Incoming("validate-blog-response")
    @Transactional
    public void processValidation(ValidationResponse response) {
        Blog blog = blogService.getBlog(response.blogId());

        if (blog != null) {
            BlogDTO blogDTO = BlogMapper.INSTANCE.map(blog);
            blogDTO.setValid(response.valid());

            blogService.updateBlog(response.blogId(), blogDTO);
        }
    }
}
