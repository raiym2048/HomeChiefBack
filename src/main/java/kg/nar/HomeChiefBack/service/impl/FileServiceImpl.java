package kg.nar.HomeChiefBack.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {


    private  String projectId = "regal-thought-424914-c3";
    private  String bucketName = "homechief";
    @Override
    public String uploadFile(MultipartFile file, UUID userId) throws IOException {

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        // Generate a new filename
        String newFileName = generateNewFileName(Objects.requireNonNull(file.getOriginalFilename()));



        BlobId blob = BlobId.of(bucketName, userId+"/"+ newFileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blob).build();


        // Use the bytes from the uploaded file
        byte[] fileBytes = file.getBytes();

        storage.create(blobInfo, fileBytes);

        System.out.println("File uploaded to bucket with new filename");
        return newFileName;
    }

    private String generateNewFileName(String originalFileName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        // Extracts file extension
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // Constructs new filename with timestamp
        return originalFileName.replace(extension, "") + "_" + timestamp + extension;
    }
}
