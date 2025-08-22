package co.edu.uniquindio.mundoComputo.services.impl;

import co.edu.uniquindio.mundoComputo.dtos.cliente.ClienteInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.cliente.CreateClienteDTO;
import co.edu.uniquindio.mundoComputo.dtos.cliente.UpdateClienteDTO;
import co.edu.uniquindio.mundoComputo.model.Cliente;
import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;
import co.edu.uniquindio.mundoComputo.model.TemplateEmailType;
import co.edu.uniquindio.mundoComputo.repositories.ClienteRepository;
import co.edu.uniquindio.mundoComputo.services.ClienteService;
import co.edu.uniquindio.mundoComputo.services.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmailService emailService;

    @Override
    public void createCliente(CreateClienteDTO clienteDTO) throws Exception {
        if(clienteRepository.existsByEmail(clienteDTO.email())) throw new Exception(String.format("El cliente con email %s con existe", clienteDTO.email()));
        if(clienteRepository.existsByTelefono(clienteDTO.telefono())) throw new Exception(String.format("El cliente con telefono %s con existe", clienteDTO.telefono()));

        clienteRepository.save(
                Cliente.builder()
                        .nombre(clienteDTO.nombre())
                        .apellido(clienteDTO.apellido())
                        .email(clienteDTO.email())
                        .telefono(clienteDTO.telefono())
                        .estado(EstadoUsuario.ACTIVO)
                        .build()
        );
    }

    @Override
    public void updateCliente(UpdateClienteDTO clienteDTO) throws Exception {
        Cliente cliente = getClientById(clienteDTO.id());

        cliente.setNombre(clienteDTO.nombre());
        cliente.setApellido(clienteDTO.apellido());
        cliente.setTelefono(clienteDTO.telefono());

        clienteRepository.save(cliente);

        emailService.sendHtmlEmail(cliente.getEmail(), "Datos actualizados", cliente.getNombre(), TemplateEmailType.UPDATE_CLIENTE);
    }

    @Override
    public void deleteCliente(Long id) throws Exception {
        Cliente cliente = getClientById(id);
        cliente.setEstado(EstadoUsuario.ELIMINADO);
        
        clienteRepository.save(cliente);
        emailService.sendHtmlEmail(cliente.getEmail(), "Cuenta eliminada", cliente.getNombre(), TemplateEmailType.DELETE_CLIENTE);
    }

    @Override
    public ClienteInfoDTO getClienteInfoById(Long id) throws Exception {
        Cliente cliente = getClientById(id);

        return mapToInfoDTO(cliente);

    }

    @Override
    public List<ClienteInfoDTO> getAllClientes() throws Exception {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::mapToInfoDTO)
                .toList();
    }

    @Override
    public List<ClienteInfoDTO> getClientesByEstado(EstadoUsuario estado) throws Exception {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .filter(cliente -> cliente.getEstado().equals(estado))
                .map(this::mapToInfoDTO)
                .toList();
    }

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

    private Cliente getClientById(Long id) throws Exception {
        if(clienteRepository.findById(id).isEmpty()) throw new Exception(String.format("El cliente con id %s no existe", id));
        return clienteRepository.findById(id).get();
    }
}
