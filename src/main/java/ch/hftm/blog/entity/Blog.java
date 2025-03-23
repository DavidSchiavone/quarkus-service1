package ch.hftm.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
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
    @Lob
    private String summary;
    private String metaTitle;
    @Lob
    private String metaDescription;
    private String tags;

}
