package com.servingimage.controller;

import org.springframework.core.io.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller

public class ImageController {


    // Directly writing to HttpServletResponse
    @RequestMapping(value = "/image/{name}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response,
                          @PathVariable(value = "name") String imageName) throws IOException {

        // First step is to bring an image using the path variable imageName

        Resource image = new ClassPathResource("images/fruits/apple.jpg");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(image.getInputStream(), response.getOutputStream());
    }

    // Using ResponseEntity
    @RequestMapping(value = "/images/{name}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String name) throws IOException{
        String basePath = "/home/some_user/storage/";
        var image = new FileUrlResource(basePath+name+".jpg");

//        These are also valid
//        var image = new FileSystemResource("/home/some_user/storage/apple.jpg");
//        var image = new UrlResource("file:////home/some_user/storage/peach.jpg");

        System.out.println(image.exists());
        byte[] bytes = StreamUtils.copyToByteArray(image.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);

    }
    // Using StreamInputResource
    @RequestMapping(value = "/images/{name}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage1(@PathVariable String name) throws IOException {

        var image = new ClassPathResource("images/fruits/" + name + ".jpg");
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(image.getInputStream()));
    }
}
