package cl.consumer.democonsumer.contracts;

import cl.consumer.democonsumer.controller.CarController;
import cl.consumer.democonsumer.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "cl.spring.contract-example:demo:+:stubs:8090")
public class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testContractToCarByIdProducer() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

        Car car = WebClient.builder().baseUrl("http://localhost:8090")
                .build()
                .get()
                .uri("/cars-consumer")
                .retrieve()
                .bodyToMono(Car.class)
                .block();
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/cars-consumer")
//                        .contentType(APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andExpect(content().json(ow.writeValueAsString(car)));

        System.out.println(car.getId());
        System.out.println(car.getColor());
        System.out.println(car.getModel());
        System.out.println(car.getBrand());
    }

}
