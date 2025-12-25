package com.ai.myspring.multimedia;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class MultiMediaService {
    @Qualifier("googleGenAiChatClient")
    private final ChatClient chatClient;


    public MultiMediaService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Cacheable(value = "image-to-text", key = "#resource")
    public String imageToText(Resource resource) {
        return chatClient.prompt()
                .user(u -> {
                    u.text("Describe the given image");
                    // u.media(MimeTypeUtils.IMAGE_PNG, resource);
                    // Dynamically detect the MimeType so it works for .jpg, .png etc.
                    u.media(MimeType.valueOf(MediaTypeFactory.getMediaType(resource)
                            .orElse(MediaType.IMAGE_PNG).toString()), resource);
                })
                .call()
                .content();
    }

    // When Multipart file is passed.
    @Cacheable(value = "describe-image-", key = "#multipartFile")
    public String describeImage(MultipartFile multipartFile) {
        return chatClient.prompt()
                .user(u -> {
                    u.text("Describe the given image");
                    u.media(MimeTypeUtils.parseMimeType(multipartFile.getContentType()), multipartFile.getResource());
                })
                .call()
                .content();
    }

}
