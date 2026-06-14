package com.biblioteca.model.enums;

public enum RolUsuario {
    CLIENTE(1),
    EMPLEADO(2),
    ADMIN(3);

    private final int value;

    RolUsuario(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RolUsuario fromValue(int value) {
        for (RolUsuario rol : RolUsuario.values()) {
            if (rol.value == value) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Rol invalido: " + value);
    }
}
