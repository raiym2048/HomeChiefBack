package kg.nar.HomeChiefBack.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import kg.nar.HomeChiefBack.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<String> listFiles(UUID userId)throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId
                (projectId).build().getService();
        List<String> files = new ArrayList<>();

        for (Blob blob : storage.list(bucketName+"/"+userId, Storage.BlobListOption.currentDirectory(), Storage.BlobListOption.prefix("")).iterateAll()){
            files.add(blob.getName());
        }
        return files;
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
        Storage storage = StorageOptions.newBuilder().setProjectId
                (projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        Blob blob = storage.get(blobId);

        byte[] content = blob.getContent();
        return new ByteArrayResource(content);    }

}
