package com.lidosta.Deikstras_Lidosta.controller;

import com.lidosta.Deikstras_Lidosta.processor.calculator.Calculation;
import com.lidosta.Deikstras_Lidosta.service.InformationUploadService;
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
    private InformationUploadService informationUploadService;

    @Autowired
    public InformationUploadController(InformationUploadService informationUploadService) {
        this.informationUploadService = informationUploadService;
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
        informationUploadService.uploadInformation(file);
    }

    //method for testing cache
    @PostMapping(value = "/add/number")
    public void add(@RequestParam("num") String num) throws IOException {
        informationUploadService.getListByInputParameters(num);
    }

    @PostMapping(value = "/add/show")
    public void showGraph() {
        Calculation.getInstance().printGraph();
    }
}
