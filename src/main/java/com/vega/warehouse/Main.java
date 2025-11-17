package com.vega.warehouse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Vega Warehouse.
 * <p>
 * Esta aplicação gerencia um sistema de armazém para ingredientes,
 * permitindo o controle de compartimentos, ingredientes e movimentações
 * (entradas e saídas) do estoque.
 * </p>
 *
 * @author LemosFTW
 * @version 1.0
 */
@SpringBootApplication
public class Main {
    /**
     * Método principal que inicia a aplicação Spring Boot.
     *
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}