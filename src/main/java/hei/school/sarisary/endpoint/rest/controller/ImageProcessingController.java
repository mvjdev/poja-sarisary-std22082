package hei.school.sarisary.endpoint.rest.controller;

import hei.school.sarisary.service.ImageProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ImageProcessingController {

    private final ImageProcessingService imageProcessingService;

    @PutMapping("/black-and-white/{id}")
    private final void uploadImage(@PathVariable String id, byte[] image) {
        imageProcessingService.convertToBlackAndWhite(image, id);
    }
}