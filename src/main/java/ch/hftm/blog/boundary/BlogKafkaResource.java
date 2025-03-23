package ch.hftm.blog.boundary;

import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.dto.BlogDTO;
import ch.hftm.blog.control.mapper.BlogMapper;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.messaging.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class BlogKafkaResource {

    @Inject
    @Channel("additional-information-request")
    Emitter<AdditionalInformationsRequest> additionalInformationsRequestEmitter;


    @Inject
    @Channel("generate-blog-request")
    Emitter<GenerateBlogRequest> generateBlogEmitter;

    @Inject
    BlogService blogService;

    public void sendRequest(AdditionalInformationsRequest request) {
        additionalInformationsRequestEmitter.send(request);
    }

    public void sendGenerateBlogRequest(GenerateBlogRequest generateBlogRequest) {
        generateBlogEmitter.send(generateBlogRequest);
    }

    @Incoming("additional-information-response")
    @Transactional
    public void processValidation(AdditionalInformationResponse response) {
        Blog blog = blogService.getBlog(response.blogId());
        AdditionalInformation additionalInformation = response.additionalInformation();

        if (blog != null) {
            BlogDTO blogDTO = BlogMapper.INSTANCE.map(blog);
            blogDTO.setValid(additionalInformation.valid());
            if (additionalInformation.valid()) {
                blogDTO.setSummary(additionalInformation.summary());
                blogDTO.setMetaTitle(additionalInformation.seoInfo().title());
                blogDTO.setMetaDescription(additionalInformation.seoInfo().metaDescription());
                blogDTO.setTags(String.join(", ", additionalInformation.tags()));
            }

            blogService.updateBlog(response.blogId(), blogDTO);
        }
    }

    @Incoming("generate-blog-response")
    @Transactional
    public void handleBlogGeneration(GenerateBlogResponse response) {
        ch.hftm.blog.messaging.Blog blogResp = response.blog();
        AdditionalInformation additionalInformation = response.additionalInformation();
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setTitle(blogResp.title());
        blogDTO.setContent(blogResp.content());
        blogDTO.setSummary(additionalInformation.summary());
        blogDTO.setMetaTitle(additionalInformation.seoInfo().title());
        blogDTO.setMetaDescription(additionalInformation.seoInfo().metaDescription());
        blogDTO.setTags(String.join(", ", additionalInformation.tags()));
        blogDTO.setValid(additionalInformation.valid());

        blogService.addBlog(blogDTO);
    }
}
