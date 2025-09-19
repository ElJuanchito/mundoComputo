package co.edu.uniquindio.mundoComputo.services;

public interface StorageService {

    void uploadFile(byte[] fileBytes, String fileName, String contentType);
    String getPublicUrl(String filePath);
}
