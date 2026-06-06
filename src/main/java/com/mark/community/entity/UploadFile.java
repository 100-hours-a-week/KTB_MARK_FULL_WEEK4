package com.mark.community.entity;

public class UploadFile {
    private String fileId;
    private String filePath;
    private String fileType;


    public UploadFile(String fileId, String filePath, String fileType){
        this.fileId = fileId;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public UploadFile(){

    }

    public String getFileId() {
        return fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }
}
