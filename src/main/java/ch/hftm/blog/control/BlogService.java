package ch.hftm.blog.control;

import ch.hftm.blog.control.dto.BlogDTO;
import ch.hftm.blog.control.mapper.BlogMapper;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.repository.BlogRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;

    public List<Blog> getBlogs(Optional<String> searchStr) {
        PanacheQuery<Blog> blogQuery = searchStr
                .map(str -> blogRepository.find("title like ?1 or content like ?1", "%" + str + "%"))
                .orElse(blogRepository.findAll());
        List<Blog> blogs = blogQuery.list();
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public Blog getBlog(long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public Blog addBlog(BlogDTO blogDto) {
        Log.info("Adding blog " + blogDto.getTitle());
        Blog blog = BlogMapper.INSTANCE.map(blogDto);
        blogRepository.persist(blog);

        return blog;
    }

    @Transactional
    public boolean updateBlog(long id, BlogDTO blog) {
        return Optional.ofNullable(getBlog(id))
                .map(blogToUpdate -> {
                    blogToUpdate.setTitle(blog.getTitle());
                    blogToUpdate.setContent(blog.getContent());
                    blogToUpdate.setValid(blog.isValid());

                    blogRepository.persist(blogToUpdate);

                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public boolean deleteBlog(long id) {
        return blogRepository.deleteById(id);
    }

    public List<Blog> findByTitle(String title) {
        return blogRepository.find("title", title)
                .stream()
                .toList();
    }
}
