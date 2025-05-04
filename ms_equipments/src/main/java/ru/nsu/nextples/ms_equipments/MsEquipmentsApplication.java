package ru.nsu.nextples.ms_equipments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsEquipmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEquipmentsApplication.class, args);
    }

}
