package cl.consumer.democonsumer.controller;

import cl.consumer.democonsumer.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars-consumer")
public class CarController {

    private final WebClient webClient;

    @GetMapping()
    public ResponseEntity<Car> getCarById() {
        Car car = webClient.get()
                .uri("/cars-producer")
                .retrieve()
                .bodyToMono(Car.class)
                .block();
        return new ResponseEntity<>(car, HttpStatus.OK);
    }
}
