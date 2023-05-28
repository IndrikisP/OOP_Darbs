package com.lidosta.Deikstras_Lidosta.controller;

import com.lidosta.Deikstras_Lidosta.processor.calculator.Calculation;
import com.lidosta.Deikstras_Lidosta.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class InformationUploadController {
    private InformationService informationService;

    @Autowired
    public InformationUploadController(InformationService informationService) {
        this.informationService = informationService;
    }

    @PostMapping(value = "/add/document")
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("Invalid file name");
        }

        File file = new File(originalFilename);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new IOException("Failed to save file: " + originalFilename, e);
        }
        informationService.uploadInformation(file);
    }
}
