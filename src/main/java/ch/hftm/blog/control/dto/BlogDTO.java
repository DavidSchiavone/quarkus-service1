package ch.hftm.blog.control.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    @NotNull
    @Size(min = 3, max = 30, message = "Der Titel muss zwischen 3 und 30 Zeichen haben.")
    private String title;
    @NotBlank(message = "Der Inhalt muss gesetzt werden.")
    private String content;

    private boolean valid;
}
