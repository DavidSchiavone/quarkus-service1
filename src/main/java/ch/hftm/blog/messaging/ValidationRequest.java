package ch.hftm.blog.messaging;

public record ValidationRequest(long blogId, String title, String content) {
}
