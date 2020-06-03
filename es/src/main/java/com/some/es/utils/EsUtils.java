package com.some.es.utils;

import com.some.es.config.EsConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-03 10:18
 */
@Component
@Slf4j
public class EsUtils {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private EsConfig esConfig;

    public boolean existIndex(String index) {
        try {
            GetIndexRequest request = new GetIndexRequest(index);
            boolean ret = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
            return ret;
        }catch (Exception e){
            log.error("判断索引失败:{} " , e.getMessage(),e);
            return false;
        }
    }

    /**
     * 创建索引 可以理解为创建database
     * @param index
     * @param contentType 创建字段属性等内容
     */
    public void createIndex(String index, String contentType) {
        try
        {
            //create index, set index name
          CreateIndexRequest  request = new CreateIndexRequest(index);
            //set shard and replicas cnt
            request.settings(Settings.builder()
                    .put("index.number_of_shards",esConfig.getNumberOfShards())
                    .put("index.number_of_replicas",esConfig.getNumberOfReplicas())
            );
            //set index mapping
            request.source(contentType, XContentType.JSON);
            //request.alias(new Alias("test"));
            //request.setTimeout(TimeValue.timeValueMinutes(requestTimeout));
            //request.setMasterTimeout(TimeValue.timeValueMinutes(requestMasterTimeout));
            request.waitForActiveShards(ActiveShardCount.DEFAULT);

            CreateIndexResponse response = restHighLevelClient.indices().create(request,RequestOptions.DEFAULT);
            boolean acknowledged = response.isShardsAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            log.info("acknowledged:{},shardsAcknowledged:{}",acknowledged,shardsAcknowledged);
            //client.close();
        }catch (Exception e){
            log.error("创建索引失败:{} " , e.getMessage(),e);
        }
    }

    /**
     * 保存数据
     * @param index 相当于database type在版本7已经取消
     * @param idKey
     * @return
     */
    public boolean save(String index ,String idKey,Object bean) {
        try {


            BeanMap beanMap = BeanMap.create(bean);
            XContentBuilder builder =  XContentFactory.jsonBuilder().startObject();
            String idKeyValue = (String)beanMap.get("idKey");
            String id = (String)beanMap.get(idKeyValue);
            for(Object key:beanMap.keySet()){
                builder.field((String) key, beanMap.get(key));

            }
            builder.endObject();

            UpdateRequest request = new UpdateRequest(index, id)
                    .doc(builder)
                    .upsert(builder);//upsert--id不存在时就插入

            UpdateResponse response = restHighLevelClient.update(request,RequestOptions.DEFAULT);
            response.forcedRefresh();
            if (response.status().equals(RestStatus.OK)
                    || response.status().equals(RestStatus.CREATED)){
                log.info("index:{},id:{},保存成功",index,id);
                return true;
            }else{
                log.error("index:{},id:{},保存失败,status:{},msg:{}",index,id,response.status(),response.toString());
                return false;
            }
        }catch (Exception e){
            log.error("es更新失败:{}",e.getMessage(),e);
            return false;
        }
    }

    public  List<String> search(String index, SearchSourceBuilder sourceBuilder, int from,
                                int size) {
       return search(index,sourceBuilder,from,size,null);
    }

    /**
     *
     * @param index
     * @param sourceBuilder
     * @param from
     * @param size
     * @param highlightFields 高亮暂时不处理
     * @return
     */
    public  List<String> search(String index, SearchSourceBuilder sourceBuilder, int from,
                                    int size, String[] highlightFields) {


        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);

        sourceBuilder.from(from); //设置from选项，确定要开始搜索的结果索引。 默认为0。
        sourceBuilder.size(size); //设置大小选项，确定要返回的搜索匹配数。 默认为10。
        if (esConfig.getSearchTimeout() != null && esConfig.getSearchTimeout() > 0){
            sourceBuilder.timeout(new TimeValue(esConfig.getSearchTimeout()));
        }

//        if (highlightFields != null){
//            HighlightBuilder highlightBuilder = new HighlightBuilder();
//            for (String highlightField : highlightFields){
//                HighlightBuilder.Field field = new HighlightBuilder.Field(highlightField);
//                highlightBuilder.field(field);
//            }
//            sourceBuilder.highlighter(highlightBuilder);
//        }

        searchRequest.source(sourceBuilder);

        try {
            SearchResponse response = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            log.info("status:{}",response.status());

            SearchHits searchHits = response.getHits();
            List<String> rs = new ArrayList<>();
            Iterator<SearchHit> iterator = searchHits.iterator();
            while (iterator.hasNext()){
                SearchHit searchHit = iterator.next();
                rs.add(searchHit.getSourceAsString());
                //高亮暂时不处理
//                if (highlightFields != null){
//                    Map<String, HighlightField> hlFieldsMap = searchHit.getHighlightFields();
//
//                    for (String highlightField : highlightFields){
//                        if (hlFieldsMap.containsKey(highlightField)){
//                            HighlightField highlight = hlFieldsMap.get(highlightField);
//                            Text[] fragments = highlight.fragments();
//                            if (fragments.length > 0){
//                                StringBuffer sb = new StringBuffer();;
//                                for (Text fragment : fragments){
//                                    sb.append(fragment.string());
//                                }
//
//                            }
//                        }
//                    }
//                }
            }
            return rs;
        } catch (Exception e) {
            log.error("搜索失败:{}",e.getMessage(),e);
            return null;
        }
    }

    public boolean delete(String index, String id) {

        DeleteRequest request = new DeleteRequest()
                .index(index)
                .id(id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(request,RequestOptions.DEFAULT);
            if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
                log.info("未找到要删除的文档[{}]",id);
                return true;
            }
            if (deleteResponse.status().equals(RestStatus.OK)){
                log.info("文档[{}]删除成功！",id);
                return true;
            }else{
                log.info("删除状态:{},msg:{}",deleteResponse.status() ,deleteResponse.toString());
                return false;
            }

        } catch (Exception e) {
            log.error("删除ES文档失败:{} " ,e.getMessage(),e);
            return false;
        }
    }


}
