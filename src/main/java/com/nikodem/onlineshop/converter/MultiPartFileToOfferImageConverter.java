package com.nikodem.onlineshop.converter;

import com.nikodem.onlineshop.domain.OfferImage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class MultiPartFileToOfferImageConverter implements Converter<MultipartFile, OfferImage> {
    @Override
    public OfferImage convert(MultipartFile file) {
        Path absolutePath = Paths.get(".").toAbsolutePath();
        Path path = Paths.get(absolutePath + "/src/main/resources/static/uploads/" + file.getOriginalFilename());
        try {
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new OfferImage(file.getOriginalFilename(), path.toString());
    }
}
