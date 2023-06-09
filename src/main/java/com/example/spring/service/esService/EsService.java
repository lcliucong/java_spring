package com.example.spring.service.esService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Service
public class EsService {

    @RequestMapping(value = "makeEs")
    public ElasticsearchClient makeEs() throws IOException {
        // Create the low-level client
        RestClient httpClient = RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();

        // Create the Java API Client with the same low level client
        ElasticsearchTransport transport = new RestClientTransport(
                httpClient,
                new JacksonJsonpMapper()
        );
        return new ElasticsearchClient(transport);

    }
}
