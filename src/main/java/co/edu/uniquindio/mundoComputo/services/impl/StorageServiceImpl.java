package co.edu.uniquindio.mundoComputo.services.impl;

import co.edu.uniquindio.mundoComputo.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final WebClient supabaseWebClient;

    @Value("${SUPABASE_BUCKET}")
    private String bucket;

    @Value("${SUPABASE_URL}")
    private String supabaseUrl;

    public void uploadFile(byte[] fileBytes, String fileName, String contentType) {
        String path = bucket + "/" + fileName;

        supabaseWebClient.post()
                .uri("/object/" + path)
                .contentType(MediaType.parseMediaType(contentType))
                .bodyValue(fileBytes)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new Exception("Error en subida: " + error))))
                .toBodilessEntity()
                .block(); // bloqueamos para simplicidad (puede ser reactivo)
    }

    public String getPublicUrl(String filePath) {
        return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + filePath;
    }


}
