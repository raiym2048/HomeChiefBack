package kg.nar.HomeChiefBack.config;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GoogleCloud {
    private static String projectId = "regal-thought-424914-c3";
    private static String bucketName = "homechief";
    private static String objectName = "www_1717258905432.png";

    public static void main(String[] args) throws IOException {
        //uploadFile();

        //listFiles();
        //deleteFile();
        //readFile();
        downloadFile();
    }

    private static void listFiles()throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId
                (projectId).build().getService();

        for (Blob blob : storage.list(bucketName, Storage.BlobListOption.currentDirectory(), Storage.BlobListOption.prefix("")).iterateAll()){
            System.out.println(blob.getName());
        }
    }

    private static void deleteFile() throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId
                (projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        Blob blob = storage.get(blobId);
        blob.delete();
        System.out.println("File deleted from bucket with name: " + objectName);
    }

    private static void readFile() throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId
                (projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        Blob blob = storage.get(blobId);

        String content = new String(blob.getContent());
        System.out.println("File content: ");
        System.out.println(content);

    }

    public static void downloadFile() throws IOException {
        String filePath = "/Users/bambook/Downloads/HomeChiefBack/src/main/resources/static/1.png";
        Storage storage = StorageOptions.newBuilder().setProjectId
                (projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        Blob blob = storage.get(blobId);

        blob.downloadTo(Paths.get(filePath));
        System.out.println("File downloaded to bucket");

    }


    /*Credentials credentials = GoogleCredentials
            .fromStream(new FileInputStream("path/to/file"));
    Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
            .setProjectId("regal-thought-424914-c3").build().getService();

    public GoogleCloud() throws IOException {
    }*/
}
/**/
