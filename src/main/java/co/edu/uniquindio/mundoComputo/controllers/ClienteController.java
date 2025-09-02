package co.edu.uniquindio.mundoComputo.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uniquindio.mundoComputo.dtos.clientes.*;
import co.edu.uniquindio.mundoComputo.dtos.responses.MessageDTO;
import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;
import co.edu.uniquindio.mundoComputo.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "clientes")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente en la plataforma")
    @PostMapping
    public ResponseEntity<MessageDTO<String>> createCliente(@Valid @RequestBody CreateClienteDTO createClienteDTO) throws Exception {
        clienteService.createCliente(createClienteDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Cliente creado satisfactoriamente"));
    }

    @PutMapping
    @Operation(summary = "Actualizar cliente", description = "Actualiza la informacion de un cliente existente")
    public ResponseEntity<MessageDTO<String>> updateCliente(@Valid @RequestBody UpdateClienteDTO updateClienteDTO) throws Exception {
        clienteService.updateCliente(updateClienteDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Cliente actualizado satisfactoriamente"));
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente de la plataforma")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO<String>> deleteCliente(@PathVariable("id") long id) throws Exception {
        clienteService.deleteCliente(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Cliente eliminado satisfactoriamente"));
    }

    @Operation(summary = "Obtener informacion del cliente", description = "Obtiene la informacion de un cliente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO<ClienteInfoDTO>> getClienteInfoById(@PathVariable("id") Long id) throws Exception {
        ClienteInfoDTO clienteInfo = clienteService.getClienteInfoById(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, clienteInfo));
    }

    @GetMapping()
    @Operation(summary = "Obtener todos los clientes", description = "Obtiene una lista de todos los clientes en la plataforma")
    public ResponseEntity<MessageDTO<List<ClienteInfoDTO>>> getAllClientes() throws Exception {
        List<ClienteInfoDTO> clientes = clienteService.getAllClientes();
        return ResponseEntity.status(200).body(new MessageDTO<>(false, clientes));
    }
    
    @GetMapping("/byEstado")
    @Operation(summary = "Obtener clientes por estado", description = "Obtiene una lista de clientes filtrados por su estado")
    public ResponseEntity<MessageDTO<List<ClienteInfoDTO>>> getClientesByEstado(@RequestParam("estado") EstadoUsuario estado) throws Exception {
        List<ClienteInfoDTO> clientes = clienteService.getClientesByEstado(estado);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, clientes));
    }

    @PutMapping("/desactivar/{id}")
    @Operation(summary = "Desactivar cliente", description = "Desactiva la cuenta de un cliente")
    public ResponseEntity<MessageDTO<String>> desactivarCliente(@PathVariable("id") Long id) throws Exception {
        clienteService.desactivarCliente(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Cliente desactivado satisfactoriamente"));
    }
    
    
}