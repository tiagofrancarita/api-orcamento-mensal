package br.com.franca.api.orcamento.mensal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

    @RequestMapping
    public String ping() {

        try {
            Thread.sleep(1000);
            return "ping";
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o endpoint");

        }

    }
}
