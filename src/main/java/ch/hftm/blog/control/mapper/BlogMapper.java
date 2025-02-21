package ch.hftm.blog.control.mapper;

import ch.hftm.blog.control.dto.BlogDTO;
import ch.hftm.blog.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogMapper {
    BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);

    BlogDTO map(Blog blog);

    @Mapping(target = "blogid", ignore = true)
    Blog map(BlogDTO dto);
}
