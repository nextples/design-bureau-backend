package ru.nsu.nextples.ms_contracts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsContractsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsContractsApplication.class, args);
    }

}
