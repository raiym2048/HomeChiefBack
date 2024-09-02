package kg.nar.HomeChiefBack.service.impl;

import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import com.google.api.client.util.Value;
import kg.nar.HomeChiefBack.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Value("${upload.dir}")
    private String uploadDir = "/home/raiym/IdeaProjects/HomeChiefBack";


    @Override
    public String uploadFile(MultipartFile file, UUID userId, int i) throws IOException {

        if (file.isEmpty() || file == null) {
            throw new IllegalArgumentException("File is empty");
        }
        System.out.println("the upload dir:"+uploadDir);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssmm");

        // Construct the new filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String newFilename = userId + "_" + now.format(formatter)+"_" + i + fileExtension;



        // Define the file path
        Path uploadPath = Paths.get(uploadDir);
        if (Files.notExists(uploadPath)) {
            Files.createDirectories(uploadPath);  // Create the directory if it doesn't exist
        }
        Path filePath = uploadPath.resolve(newFilename);

        // Save the file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the new file name or file path
        return newFilename;
    }

    private String getFileExtension(String filename) {
        return filename != null && filename.contains(".") ? filename.substring(filename.lastIndexOf(".")) : "";
    }

    @Override
    public List<String> listFiles(UUID userId)throws IOException {
        return null;
    }


    /*@Override
    public  void downloadFile(String objectName) throws IOException {
        String filePath = "/Users/bambook/Downloads/HomeChiefBack/src/main/resources/static/1.png";
        Storage storage = StorageOptions.newBuilder().setProjectId
                (projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        Blob blob = storage.get(blobId);

        blob.downloadTo(Paths.get(filePath));
        System.out.println("File downloaded to bucket");

    }*/
    @Override
    public Resource downloadFile(String objectName) throws IOException {
        return null;
    }

}
