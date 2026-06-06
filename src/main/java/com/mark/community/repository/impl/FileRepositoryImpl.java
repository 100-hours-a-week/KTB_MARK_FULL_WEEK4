package com.mark.community.repository.impl;

import com.mark.community.entity.UploadFile;
import com.mark.community.messages.ErrorMessage;
import com.mark.community.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Repository
@Slf4j
public class FileRepositoryImpl implements FileRepository {
    private final ObjectMapper objectMapper;
    private final String FILE = "files.json";
    private final String UPLOAD_DIR = "uploads/";
    private final HashMap<String, UploadFile> files = new HashMap<>();
    private int nextFileId = 1;

    public FileRepositoryImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        File jsonFile = new File(FILE);
        if (jsonFile.exists() && jsonFile.length() > 0) {
            UploadFile[] fileData = objectMapper.readValue(jsonFile, UploadFile[].class);
            for (UploadFile uploadFile : fileData) {
                files.put(uploadFile.getFileId(), uploadFile);
                int num = Integer.parseInt(uploadFile.getFileId().substring(1));
                if (num >= nextFileId) nextFileId = num + 1;
            }
        }
    }

    private void saveJson() {
        objectMapper.writeValue(new File(FILE), files.values());
    }

    @Override
    public UploadFile save(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.indexOf('.'));

        String fileId = "F" + nextFileId++;
        String filePath = UPLOAD_DIR + fileId + extension;
        String fileType = file.getContentType();

        UploadFile uploadFile = new UploadFile(fileId, filePath, fileType);
        try{
            file.transferTo(new File(filePath).getAbsoluteFile());
        } catch (IOException e){
            log.error(ErrorMessage.FAIL_UPLOAD.getMessage());
            return null;
        }

        files.put(fileId, uploadFile);
        saveJson();
        return uploadFile;
    }

    public Optional<UploadFile> findById(String fileId) {
        return Optional.ofNullable(files.get(fileId));
    }

}
