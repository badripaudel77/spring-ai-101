package com.ai.myspring.multimedia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/multimedia")
public class ImageController {

    @Value("classpath:/images/nepal_mountain.jpg")
    Resource resource;

    private final MultiMediaService service;

    public ImageController(MultiMediaService service) {
        this.service = service;
    }

    @GetMapping("/image-to-text")
    public String describeImage() {
        return service
                .imageToText(resource);
    }

    @PostMapping("/describe-image")
    public String describeImage(@RequestPart("image")MultipartFile image) {
        if(image.isEmpty()) {
            return "Please upload an image.";
        }
        return service
                .describeImage(image);
    }

}
