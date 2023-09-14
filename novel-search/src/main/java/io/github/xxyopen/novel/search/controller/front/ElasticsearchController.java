package io.github.xxyopen.novel.search.controller.front;

import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;

import io.github.xxyopen.novel.search.config.EsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ElasticsearchController {

    @Autowired
    private EsConfig elasticSearchClientConfig;

    /**
     * 新建my_index索引
     * @return
     * @throws IOException
     */
    @GetMapping("/createIndex")
    public Boolean createIndex() throws IOException {

        CreateIndexResponse createIndexResponse = elasticSearchClientConfig.restClient().getElasticsearchClient().indices().create(c -> c.index("my_index"));
        // 打印结果
        System.out.println(createIndexResponse.acknowledged());
        // 关闭连接

//        elasticSearchClientConfig.restClient().getElasticsearchTransport().close();
//        elasticSearchClientConfig.restClient().getRestClient().close();
        return createIndexResponse.acknowledged();
    }

    /**
     * 查询索引
     * @throws IOException
     */
    @GetMapping("/selectIndex")
    public void selectIndex() throws IOException {

        GetIndexResponse getIndexResponse = elasticSearchClientConfig.restClient().getElasticsearchClient().indices().get(e -> e.index("jing_index"));

        // 打印结果
        System.out.println("getIndexResponse.result() = " + getIndexResponse.result());
        System.out.println("getIndexResponse.result().keySet() = " + getIndexResponse.result().keySet());

        // 关闭连接
        elasticSearchClientConfig.restClient().getElasticsearchTransport().close();
        elasticSearchClientConfig.restClient().getRestClient().close();
    }

    /**
     * 删除索引
     * @return
     * @throws IOException
     */
    @GetMapping("/deleteIndex")
    public Boolean deleteIndex() throws IOException {
        // 删除索引
        DeleteIndexResponse deleteIndexResponse = elasticSearchClientConfig.restClient().getElasticsearchClient().indices().delete(e -> e.index("jing_index"));
        System.out.println("删除操作 = " + deleteIndexResponse.acknowledged());
        // 关闭连接
        elasticSearchClientConfig.restClient().getElasticsearchTransport().close();
        elasticSearchClientConfig.restClient().getRestClient().close();
        return deleteIndexResponse.acknowledged();
    }

}