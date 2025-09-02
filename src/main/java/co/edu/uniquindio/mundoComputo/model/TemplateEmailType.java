package co.edu.uniquindio.mundoComputo.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TemplateEmailType {

    UPDATE_CLIENTE("updateCliente"),
    CREATE_CLIENTE("createCliente"),
    DELETE_CLIENTE("deleteCliente"),
    DEACTIVATE_CLIENTE("desactivateCliente"),

    NOTIFICATION("notificationInventario"),
    VERIFICATION_CODE("codigoVerificacion");


    final String value;

    public String getValue() {
        return value;
    }
}
