package ch.hftm.blog.messaging;

public record ValidationResponse(long blogId, boolean valid) {
}
