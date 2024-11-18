package com.api_controle_acesso.services;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ValidadorDataUtilService {
    
     private static final Set<LocalDate> FERIADOS = new HashSet<>();

     static {
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 1, 1));   // Ano Novo
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 4, 21));  // Tiradentes
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 5, 1));   // Dia do Trabalho
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 8, 30));  // Feriado Municipal
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 9, 7));   // Independência do Brasil
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 10, 12)); // Nossa Senhora Aparecida
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 11, 2));  // Finados
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 11, 15)); // Proclamação da República
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 11, 20)); // Consciência Negra
        FERIADOS.add(LocalDate.of(LocalDate.now().getYear(), 12, 25)); // Natal

    }

    
    public static LocalDate calcularPascoa(int ano) {
        int a = ano % 19;
        int b = ano / 100;
        int c = ano % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int mes = (h + l - 7 * m + 114) / 31;
        int dia = ((h + l - 7 * m + 114) % 31) + 1;
        
        return LocalDate.of(ano, mes, dia);
    }
    
    public static LocalDate calcularSextaFeiraSanta(int ano) {
        LocalDate pascoa = calcularPascoa(ano);
        return pascoa.minusDays(2);
    }
    
    public boolean isDiaUtil(LocalDate data) {

        DayOfWeek diaDaSemana = data.getDayOfWeek();
        if (diaDaSemana == DayOfWeek.SATURDAY || diaDaSemana == DayOfWeek.SUNDAY) {
            return false;
        }

        if (FERIADOS.contains(data)) {
            return false;
        }

        if (data == calcularPascoa(data.getYear())) {
            return false;
        }

        if (data == calcularSextaFeiraSanta(data.getYear())) {
            return false;
        }

        return true;
    }
    
}
