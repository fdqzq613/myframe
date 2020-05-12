package com.some.web.controller;//package com.yoya.movie.ext.controller;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.netflix.discovery.DiscoveryManager;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
///**
// * 
// * @version V1.0
// * @author qzq
// * @date   2020年4月29日
// */
//@RestController
//@RequestMapping("/eureka")
//@Api(value = "Eureka相关操作", tags = "Eureka相关操作")
//public class EurekaController {
//	@ApiOperation(value = "下线", notes = "下线", httpMethod = "GET")
//	@RequestMapping(value = "/offline", method = {RequestMethod.GET,RequestMethod.POST})
//    public void offLine(){
//    	DiscoveryManager.getInstance().shutdownComponent();
//    }   
//
//}
