package com.some.aclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cloud-alibaba-service")
public interface TestService {
    @GetMapping("/service/getNacos")
    String getNacos();
}
