package dev.cee.dreamshops.service.image;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import dev.cee.dreamshops.dtos.ImageDto;
import dev.cee.dreamshops.model.Image;

public interface ImageServiceI {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long prodId);
    void updateImage(MultipartFile file, Long imageId);
}
