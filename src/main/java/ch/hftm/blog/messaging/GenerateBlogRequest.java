package ch.hftm.blog.messaging;

import java.util.List;

public record GenerateBlogRequest(String topic, List<String> keywords) {
}
