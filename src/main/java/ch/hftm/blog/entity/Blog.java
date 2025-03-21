package ch.hftm.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogid;

    private String title;
    @Lob
    private String content;
    private boolean valid;
    private String summary;
    private String metaTitle;
    private String metaDescription;
    private String tags;

}
