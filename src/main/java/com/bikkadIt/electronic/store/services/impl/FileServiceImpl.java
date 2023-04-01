package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.exception.BadApiRequest;
import com.bikkadIt.electronic.store.services.FileService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uplodImage(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        log.info("Filename :{}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        log.info("full image path:{}", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            //file save
            log.info("file extenxion is {}", extension);
            File folder = new File(path);
            if (!folder.exists()) {
                //create the folder
                folder.mkdir();
            }

            //uplod File

          Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;
        } else {
            throw new BadApiRequest("File with this" + extension + "not allowed!!");



    }
    }
    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
