package co.edu.uniquindio.mundoComputo.controllers;

import co.edu.uniquindio.mundoComputo.dtos.responses.MessageDTO;
import co.edu.uniquindio.mundoComputo.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/upload")
    public MessageDTO<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String filePath = "uploads/" + file.getOriginalFilename();
        storageService.uploadFile(file.getBytes(), filePath, file.getContentType());

        String publicUrl = storageService.getPublicUrl(filePath);
        return new MessageDTO<>(false, publicUrl);
    }
}
