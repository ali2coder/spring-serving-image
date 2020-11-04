package com.servingimage.controller;

import org.springframework.core.io.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@Controller
public class ImageController {


    // Directly writing to HttpServletResponse
    @RequestMapping(value = "/apple", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage1(HttpServletResponse response) throws IOException {
        Resource image = new ClassPathResource("images/fruits/APPLE.jpg");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(image.getInputStream(), response.getOutputStream());
    }

    // Using ResponseEntity
    @RequestMapping(value = "/peach", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage2() throws IOException{
        var image = new FileUrlResource("/home/ali/Pictures/PEACH.jpg");

//        These are also valid
//        var image = new FileSystemResource("/home/ali/Pictures/PEACH.jpg");
//        var image = new UrlResource("file:////home/ali/Pictures/PEACH.jpg");

        System.out.println(image.exists());
        byte[] bytes = StreamUtils.copyToByteArray(image.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);

    }
    // Using StreamInputResource
    @RequestMapping(value = "/pomegranate", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage3() throws IOException {

        var image = new ClassPathResource("images/fruits/POMEGRANATE.jpg");

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(image.getInputStream()));
    }
}
