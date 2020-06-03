package com.some.es.controller;

import com.some.common.result.RespResult;
import com.some.es.utils.EsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-02 13:31
 */
@Api(value = "es相关操作", tags = "es相关操作")
@RestController
@RequestMapping("/es")
@Slf4j
public class EsController {
    @Autowired
    private EsUtils esUtils;

    @ApiOperation(value = "创建索引", notes = "创建索引", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RespResult<String> create(String index) {
        String contentType = "{\n" +
                "  \"properties\": {\n" +
                "    \"id\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"index\": true,\n" +
                "\t  \"filter\": \"lowercase\"\n" +
                "    },\n" +
                "\t\"name\": {\n" +
                "      \"type\": \"keyword\",\n" +
                "      \"index\": true,\n" +
                "\t  \"filter\": \"lowercase\"\n" +
                "    },\n" +
                "\t\"createTime\": {\n" +
                "      \"type\": \"date\",\n" +
                "      \"index\": true,\n" +
                "\t  \"format\": \"yyy/MM/dd HH:mm:ss||yyyy/MM/dd||epoch_millis\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        if (!esUtils.existIndex(index)) {
            esUtils.createIndex(index, contentType);
            return RespResult.create("创建成功");
        } else {
            return RespResult.create("该索引已经创建");
        }
    }

    @ApiOperation(value = "保存数据", notes = "保存数据", httpMethod = "POST")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespResult<Boolean> save(EsVo esVo) {
        return RespResult.create(esUtils.save(esVo.getIndex(),esVo.getIdKey(),esVo));
    }

    @ApiOperation(value = "search", notes = "list", httpMethod = "GET")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public RespResult<List<String>> search(String index,String content){
        String beginTime = "2000/01/01 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String endTime = sdf.format(new Date());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(
                QueryBuilders.boolQuery().must(
                        QueryBuilders.boolQuery()
                                //通配符匹配
                                .should(QueryBuilders.wildcardQuery("name.keyword", "*"+content+"*"))
                                //.should(QueryBuilders.termQuery("name", content))
                                //.should(QueryBuilders.matchQuery("name.keyword", content).boost((float) 2.0))
                )
                       // .filter(QueryBuilders.rangeQuery("createTime").from(beginTime).to(endTime))
        );

        List<String> rs = esUtils.search(index,sourceBuilder,0,10);
        return RespResult.create(rs);
    }
}
