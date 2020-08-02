package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/file")
@Controller
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile,
                             Model model) throws IOException {
        String errorMessage = null;
        String fileName = multipartFile.getOriginalFilename();

        if (multipartFile.isEmpty()) {
            errorMessage = "Please choose file you want to upload.";
        }

        if (fileService.filenameExists(fileName)) {
            errorMessage = "File with filename " + fileName + " already exists.";
        }

        if (errorMessage == null) {
            try {
                File file = new File(
                        null,
                        fileName,
                        multipartFile.getContentType(),
                        Long.toString(multipartFile.getSize()),
                        userService.getUserId(authentication.getName()),
                        multipartFile.getBytes()
                );

                if (this.fileService.insertFile(file) < 1) {
                    errorMessage = "There was an error uploading the file. Please try again.";
                }
            } catch (IOException e) {
                errorMessage = "There was an error while processing your file.";
                e.printStackTrace();
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam(defaultValue = "fileId") Integer fileId, HttpServletResponse response) throws IOException {
        File fileData = this.fileService.getOne(fileId);

        byte[] output = fileData.getFileData();
        HttpHeaders responseHeaders = new HttpHeaders();

        ContentDisposition disposition = ContentDisposition
                .builder("inline")
                .filename(fileData.getFileName())
                .build();

        responseHeaders.setContentDisposition(disposition);
        responseHeaders.setContentType(MediaType.valueOf(fileData.getContentType()));
        responseHeaders.setContentLength(output.length);
        return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("delete")
    public String deleteFile(@RequestParam(value = "fileId") Integer fileId, Model model) {
        String errorMessage = null;

        if (this.fileService.deleteFile(fileId) < 1) {
            errorMessage = "There was an error deleting the file. Please try again.";
        }

        model.addAttribute("errorMessage", errorMessage);

        return "result";
    }
}
