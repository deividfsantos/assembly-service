package com.deividsantos.assembly.util;

import org.apache.commons.lang3.StringUtils;

public class DocumentUtil {
    private static final int[] CPF_PESO = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int CPF_SIZE = 11;

    public static boolean isValidCpf(String cpf) {
        if (!isCpfSizeCorrect(cpf)) {
            return false;
        }
        Integer digito1 = calculateDigit(cpf.substring(0, 9));
        Integer digito2 = calculateDigit(cpf.substring(0, 9) + digito1);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private static boolean isCpfSizeCorrect(String cpf) {
        return StringUtils.isNotBlank(cpf) && (cpf.length() == CPF_SIZE);
    }

    private static int calculateDigit(String str) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * DocumentUtil.CPF_PESO[DocumentUtil.CPF_PESO.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }
}