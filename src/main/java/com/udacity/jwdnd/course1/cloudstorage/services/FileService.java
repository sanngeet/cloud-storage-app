package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    public File getOne(Integer fileId) {
        return fileMapper.getOne(fileId);
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getFiles(userId);
    }

    public Integer deleteFile(Integer userId) {
        return fileMapper.deleteFile(userId);
    }

    public Boolean filenameExists(String fileName) {
        return fileMapper.filenameExists(fileName) != null;
    }
}
