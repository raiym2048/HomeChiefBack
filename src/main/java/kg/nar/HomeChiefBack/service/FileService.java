package kg.nar.HomeChiefBack.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface FileService {
    String uploadFile(MultipartFile file, UUID userId) throws IOException;

    List<String> listFiles(UUID userId)throws IOException;

    Resource downloadFile(String name) throws IOException;
}
