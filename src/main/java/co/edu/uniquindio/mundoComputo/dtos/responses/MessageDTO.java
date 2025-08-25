package co.edu.uniquindio.mundoComputo.dtos.responses;

public record MessageDTO<T>(
    boolean error,
    T message
) {
    
}
