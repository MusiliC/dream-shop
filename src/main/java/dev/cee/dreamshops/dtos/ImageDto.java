package dev.cee.dreamshops.dtos;


import lombok.Data;

@Data
public class ImageDto {
    private  Long imageId;
    private String fileName;
    private String downloadUrl;
}
