package some.gateway.core.controller;

import com.some.common.result.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import some.gateway.core.service.RefreshRoutesService;

/**
 * @description: gateway
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-14 11:00
 */
@RestController
@RequestMapping("/gateway")
@Api(value = "gateway接口", tags = "gateway接口")
public class GatewayController {
    @Autowired
    private RefreshRoutesService refreshRoutesService;

    @ApiOperation(value = "刷新路由", notes = "刷新路由", httpMethod = "GET")
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public RespResult<String> refresh() {
        refreshRoutesService.refresh();
        return RespResult.create("刷新成功");
    }
}
