package co.edu.uniquindio.mundoComputo.services;

import co.edu.uniquindio.mundoComputo.dtos.cliente.ClienteInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.cliente.CreateClienteDTO;
import co.edu.uniquindio.mundoComputo.dtos.cliente.UpdateClienteDTO;
import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;

import java.util.List;

public interface ClienteService {

    void createCliente(CreateClienteDTO clienteDTO) throws Exception;
    void updateCliente(UpdateClienteDTO clienteDTO) throws Exception;
    void deleteCliente(Long id) throws Exception;
    ClienteInfoDTO getClienteInfoById(Long id) throws Exception;
    List<ClienteInfoDTO> getAllClientes() throws Exception;
    List<ClienteInfoDTO> getClientesByEstado(EstadoUsuario estado) throws Exception;
}
