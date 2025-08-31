package co.edu.uniquindio.mundoComputo.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TemplateEmailType {

    UPDATE_CLIENTE("updateCliente"),
    CREATE_CLIENTE("createCliente"),
    DELETE_CLIENTE("deleteCliente"),
    PRODUCTO_UPDATE("productoUpdate"),
    PRODUCTO_DELETE("productoDelete"),
    PRODUCTO_OUTPUT("productoOutput"),
    PRODUCTO_INPUT("productoInput"),
    NOTIFICATION("notification");


    final String value;

    public String getValue() {
        return value;
    }
}
