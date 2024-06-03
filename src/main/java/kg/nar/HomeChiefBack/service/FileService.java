package kg.nar.HomeChiefBack.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService {
    String uploadFile(MultipartFile file, UUID userId) throws IOException;
}
