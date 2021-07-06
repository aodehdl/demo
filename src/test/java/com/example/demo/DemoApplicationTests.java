package com.example.demo;

import com.example.demo.dto.Response;
import com.example.demo.dto.ValidTestDto;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Test
    void contextLoads() {
        RestTemplate rest = new RestTemplate();
        ValidTestDto.Req req = ValidTestDto.Req.builder()
                .email("aodehdlwkdnaver.com")
                .build();
        Response<Object> res = rest.postForObject("http://localhost:8080/3", req, Response.class);
        Assertions.assertEquals(res.getStateCode(), 405);
    }

}
