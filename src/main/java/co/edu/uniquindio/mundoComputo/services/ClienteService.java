package co.edu.uniquindio.mundoComputo.services;

import co.edu.uniquindio.mundoComputo.dtos.clientes.ClienteInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.clientes.CreateClienteDTO;
import co.edu.uniquindio.mundoComputo.dtos.clientes.UpdateClienteDTO;
import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;

import java.util.List;

/**
 * Servicio para la gestión de clientes en el sistema.
 * Proporciona métodos para crear, actualizar, eliminar y consultar información de clientes.
 */
public interface ClienteService {

    /**
     * Crea un nuevo cliente en el sistema.
     * @param clienteDTO DTO con la información del cliente a crear.
     * @throws Exception si ocurre un error durante la creación.
     */
    void createCliente(CreateClienteDTO clienteDTO) throws Exception;

    /**
     * Actualiza la información de un cliente existente.
     * @param clienteDTO DTO con los datos actualizados del cliente.
     * @throws Exception si ocurre un error durante la actualización.
     */
    void updateCliente(UpdateClienteDTO clienteDTO) throws Exception;

    /**
     * Elimina un cliente por su identificador.
     * @param id Identificador único del cliente.
     * @throws Exception si ocurre un error durante la eliminación.
     */
    void deleteCliente(Long id) throws Exception;

    /**
     * Obtiene la información de un cliente por su identificador.
     * @param id Identificador único del cliente.
     * @return DTO con la información del cliente.
     * @throws Exception si el cliente no existe o ocurre un error.
     */
    ClienteInfoDTO getClienteInfoById(Long id) throws Exception;

    /**
     * Obtiene la lista de todos los clientes registrados.
     * @return Lista de DTOs con la información de los clientes.
     * @throws Exception si ocurre un error durante la consulta.
     */
    List<ClienteInfoDTO> getAllClientes() throws Exception;

    /**
     * Obtiene un cliente por su identificador y lo desactiva.
     * @param id Identificador único del cliente.
     * @throws Exception si el cliente no existe o ocurre un error.
     */
    void desactivarCliente(Long id) throws Exception;

    /**
     * Obtiene la lista de clientes filtrados por estado.
     * @param estado Estado por el cual filtrar los clientes.
     * @return Lista de DTOs con la información de los clientes filtrados.
     * @throws Exception si ocurre un error durante la consulta.
     */
    List<ClienteInfoDTO> getClientesByEstado(EstadoUsuario estado) throws Exception;
}
