package ch.hftm.blog.messaging;

public record AdditionalInformationsRequest(long blogId, String title, String content) {
}
