package com.example.spring.controller.Es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.example.spring.pojo.UserList;
import com.example.spring.service.esService.EsService;
import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/es")
public class EsController {

    @Resource
    EsService esService;

    @RequestMapping(value = "makeEs")
    public Result makeEs() throws IOException {
        // Create the low-level client
        RestClient httpClient = RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();

        // Create the Java API Client with the same low level client
        ElasticsearchTransport transport = new RestClientTransport(
                httpClient,
                new JacksonJsonpMapper()
        );
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        System.out.println(esClient);
        return Result.success(esClient);
    }

    @RequestMapping(value = "esIndexSelect")
    public Result esIndexSelect() throws IOException {
        GetIndexResponse getIndexResponse = esService.makeEs().indices().get(e->e.index("user"));
        System.out.println(getIndexResponse);
//        return JSONObject.toJSONString(getIndexResponse);
        return Result.success(getIndexResponse);
    }

    @RequestMapping(value = "esSelect")
    public Result esSelect() throws IOException {
        GetResponse<UserList> response = esService.makeEs().get(e -> e.index("user").id("123456"), UserList.class);
        assert response.source() != null;
        System.out.println(response);
        return Result.success();
    }

    @RequestMapping(value = "esSelectAll")
    public Result esSelectAll() throws IOException{
        SearchResponse<UserList> searchResponse = esService.makeEs().search(e -> e.index("user").query(q -> q.matchAll(m -> m)), UserList.class);
        HitsMetadata<UserList> hits = searchResponse.hits();
        for (Hit<UserList> hit : hits.hits()){
            assert hit.source() != null;
            System.out.println(hit);
        }
        assert searchResponse.hits().total() != null;
        System.out.println(searchResponse.hits().total().value());
        return Result.success();
    }


    @RequestMapping(value = "esCreate")
    public Result esCreate() throws IOException {
        UserList userList = new UserList();
        userList.setUsername("esMaker");
        userList.setStatus(1);
        userList.setSex(1);
        userList.setPassword("password");
        userList.setPhone("1594466");
        CreateResponse createResponse = esService.makeEs().create(e -> e.index("user").id("100003").document(userList));
        return Result.success(createResponse);
    }

    @RequestMapping(value = "createAll")
    public Result esCreateAll() throws IOException {
        List<BulkOperation> list = new ArrayList<>();
        UserList userList = new UserList();
        userList.setStatus(1);
        userList.setPassword("123456");
        userList.setPhone("1984631");

        return Result.success();
    }



    @RequestMapping(value = "getHtml")
    public Result getHtml() throws IOException {
        File f = new File("D:\\java_project\\spring\\src\\main\\resources\\templates\\input.html");
        /* 第三个参数用于处理相对路径 */
        Document doc = Jsoup.parse(f, "UTF-8");
        Elements str = doc.getElementsByClass("m-ticket");
        HashMap<String, String> results = new HashMap<>();
        for (Element tags : str){
            if (StringUtils.equals(tags.select(".desc").text(),"未使用")){
                results.put("status", tags.select(".desc").text());
                results.put("couponNo", tags.select("ticket-id").text());
//                results.put("date", tags.select("text-normal").ta);
            }
        }
        return Result.success(results);
    }

    @RequestMapping(value = "makePage")
    public Result makePage(){
        return Result.success();
    }




















}
