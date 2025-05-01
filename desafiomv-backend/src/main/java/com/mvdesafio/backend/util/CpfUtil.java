package com.mvdesafio.backend.util;

public class CpfUtil {
    public static boolean isValid(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
}
