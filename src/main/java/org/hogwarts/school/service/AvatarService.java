package org.hogwarts.school.service;

import org.hogwarts.school.model.Avatar;
import org.hogwarts.school.model.Student;
import org.hogwarts.school.repository.AvatarRepository;
import org.hogwarts.school.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {
    Logger logger = LoggerFactory.getLogger(AvatarService.class);
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final String avatarsDir;

    public AvatarService(
            AvatarRepository avatarRepository,
            StudentRepository studentRepository,
            @Value("${path.to.avatars.folder}") String avatarsDir
    ) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarsDir = avatarsDir;
    }

    public ResponseEntity<String> uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Был вызван метод uploadAvatar");
        Student student = studentRepository.findById(studentId).get();
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        return ResponseEntity.ok().build();
    }

   private String getExtensions(String fileName) {
       logger.info("Был вызван метод getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

    public void downloadAvatar(Long id, HttpServletResponse response) throws IOException {
        logger.info("Был вызван метод downloadAvatar");
        Avatar avatar = avatarRepository.findById(id).get();
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    public ResponseEntity<byte[]> downloadFromDb(Long id) {
        Avatar avatar = avatarRepository.findById(id).get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }
    public Page<Avatar> getWithPageAvatar(Integer page, Integer count) {
        return avatarRepository.findAll(PageRequest.of(page-1, count));
    }
}