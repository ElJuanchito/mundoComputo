package co.edu.uniquindio.mundoComputo.services.impl;

import co.edu.uniquindio.mundoComputo.dtos.clientes.ClienteInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.clientes.CreateClienteDTO;
import co.edu.uniquindio.mundoComputo.dtos.clientes.UpdateClienteDTO;
import co.edu.uniquindio.mundoComputo.model.Cliente;
import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;
import co.edu.uniquindio.mundoComputo.model.TemplateEmailType;
import co.edu.uniquindio.mundoComputo.repositories.ClienteRepository;
import co.edu.uniquindio.mundoComputo.services.ClienteService;
import co.edu.uniquindio.mundoComputo.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de gestión de clientes.
 * Proporciona la lógica para crear, actualizar, eliminar y consultar clientes,
 * así como el envío de notificaciones por correo electrónico.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmailService emailService;

    /**
     * {@inheritDoc}
     * Valida que el email y teléfono no existan antes de crear el cliente.
     */
    @Override
    public void createCliente(CreateClienteDTO clienteDTO) throws Exception {
        if(clienteRepository.existsByEmail(clienteDTO.email())) throw new Exception(String.format("El cliente con email %s existe", clienteDTO.email()));
        if(clienteRepository.existsByTelefono(clienteDTO.telefono())) throw new Exception(String.format("El cliente con telefono %s existe", clienteDTO.telefono()));

        clienteRepository.save(
                Cliente.builder()
                        .nombre(clienteDTO.nombre())
                        .apellido(clienteDTO.apellido())
                        .email(clienteDTO.email())
                        .telefono(clienteDTO.telefono())
                        .estado(EstadoUsuario.ACTIVO)
                        .build()
        );

        emailService.sendHtmlEmail(clienteDTO.email(), "Cuenta creada", clienteDTO.nombre(), TemplateEmailType.CREATE_CLIENTE);
    }

    /**
     * {@inheritDoc}
     * Actualiza los datos del cliente y envía una notificación por correo.
     */
    @Override
    public void updateCliente(UpdateClienteDTO clienteDTO) throws Exception {
        Cliente cliente = getClientById(clienteDTO.id());

        cliente.setNombre(clienteDTO.nombre());
        cliente.setApellido(clienteDTO.apellido());
        cliente.setTelefono(clienteDTO.telefono());

        clienteRepository.save(cliente);

        emailService.sendHtmlEmail(cliente.getEmail(), "Datos actualizados", cliente.getNombre(), TemplateEmailType.UPDATE_CLIENTE);
    }

    /**
     * {@inheritDoc}
     * Marca el cliente como eliminado y envía una notificación por correo.
     */
    @Override
    public void deleteCliente(Long id) throws Exception {
        Cliente cliente = getClientById(id);
        cliente.setEstado(EstadoUsuario.ELIMINADO);
        
        clienteRepository.save(cliente);
        emailService.sendHtmlEmail(cliente.getEmail(), "Cuenta eliminada", cliente.getNombre(), TemplateEmailType.DELETE_CLIENTE);
    }

    /**
     * {@inheritDoc}
     * Obtiene la información de un cliente por su ID.
     */
    @Override
    public ClienteInfoDTO getClienteInfoById(Long id) throws Exception {
        Cliente cliente = getClientById(id);

        return mapToInfoDTO(cliente);

    }

    /**
     * {@inheritDoc}
     * Obtiene la lista de todos los clientes registrados y los mapea para entregar una lista de DTOs.
     */
    @Override
    public List<ClienteInfoDTO> getAllClientes() throws Exception {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::mapToInfoDTO)
                .toList();
    }

    @Override
    public void desactivarCliente(Long id) throws Exception {
        Cliente cliente = getClientById(id);
        cliente.setEstado(EstadoUsuario.INACTIVO);

        clienteRepository.save(cliente);
        emailService.sendHtmlEmail(cliente.getEmail(), "Cuenta desactivada", cliente.getNombre(), TemplateEmailType.DEACTIVATE_CLIENTE);
    }

    /**
     * {@inheritDoc}
     * Obtiene la lista de clientes filtrados por estado.
     */
    @Override
    public List<ClienteInfoDTO> getClientesByEstado(EstadoUsuario estado) throws Exception {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .filter(cliente -> cliente.getEstado().equals(estado))
                .map(this::mapToInfoDTO)
                .toList();
    }

    /**
     * Convierte un objeto Cliente en un ClienteInfoDTO.
     * @param cliente Entidad Cliente.
     * @return DTO con la información del cliente.
     */
    private ClienteInfoDTO mapToInfoDTO(Cliente cliente) {
        return new ClienteInfoDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getEstado()
        );
    } 

    /**
     * Obtiene un cliente por su ID, lanzando excepción si no existe.
     * @param id Identificador único del cliente.
     * @return Entidad Cliente.
     * @throws Exception si el cliente no existe.
     */
    private Cliente getClientById(Long id) throws Exception {
        if(clienteRepository.findById(id).isEmpty()) throw new Exception(String.format("El cliente con id %s no existe", id));
        return clienteRepository.findById(id).get();
    }
}
