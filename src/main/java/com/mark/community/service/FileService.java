package com.mark.community.service;

import com.mark.community.entity.UploadFile;
import com.mark.community.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public String upload(MultipartFile multipartFile) {
        UploadFile uploadFile = null;
        uploadFile = fileRepository.save(multipartFile);
        return uploadFile.getFileId();
    }
}