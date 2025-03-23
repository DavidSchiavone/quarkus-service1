package ch.hftm.blog.control;

import ch.hftm.blog.entity.Blog;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
@RegisterAiService
public interface AiService {

    @SystemMessage("Du lieferst Antworten zu Fragen zu Blog-Einträgen")
    @UserMessage("""
            Beantworte die Frage "{question}" zu folgendem Blog:
            Title: {title}, Content: {content}
            """)
    String answerQuestion(@V("title") String title, @V("content") String content, @V("question") String question);

    @SystemMessage("Du suchst nach ähnlichen Blog-Einträgen aus einer Liste")
    @UserMessage("""
            Gib mir eine Liste von blog-IDs zurück mit ähnlichen Inhalten wie der Eintrag mit der ID {id}.
            Suche in folgender Liste: {blogs}
            Gib mir nur die die IDs zurück.
            """)
    List<String> findSimilarBlogs(@V("id") long id, @V("blogs") List<Blog> blogs);
}
