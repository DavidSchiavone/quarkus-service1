package ch.hftm.blog.messaging;

import java.util.List;

public record AdditionalInformation(boolean valid, String summary, SEOInfo seoInfo, List<String> tags) {
}
