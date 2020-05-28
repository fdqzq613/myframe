package com.some.zipkin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cloud-alibaba-client")
public interface TestService {
    @GetMapping("/test/getNacos")
    String getNacos();
}
