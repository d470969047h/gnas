package com.hrtek.gnas.service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Date: Created in 16:39 2019-03-28
 * @Author: daihui
 * @Modified By:
 */
@Component
public class SolrService {

    private static final String DEFAULT_COLLECTION = "jcg";

    @Autowired
    private SolrClient client;

    public String getById(String collection, String id) throws IOException, SolrServerException {
        SolrDocument document = client.getById(collection,id);
        return document.toString();
    }

    public String getById(String id) throws IOException, SolrServerException {
        SolrDocument document = client.getById(DEFAULT_COLLECTION,id);
        return document.toString();
    }


    public Object search() throws IOException, SolrServerException {

        SolrQuery params = new SolrQuery();

        //查询条件, 这里的 q 对应 下面图片标红的地方
        params.set("q", "title:china");

        //过滤条件
//        params.set("fq", "product_price:[100 TO 100000]");

        //排序
//        params.addSort("product_price", SolrQuery.ORDER.asc);

        //分页
        params.setStart(0);
        params.setRows(20);

        //默认域
//        params.set("df", "product_title");

        //只查询指定域
//        params.set("fl", "id,product_title,product_price");

        //高亮
        //打开开关
        params.setHighlight(true);
        //指定高亮域
        params.addHighlightField("title");
        //设置前缀
        params.setHighlightSimplePre("<span style='color:red'>");
        //设置后缀
        params.setHighlightSimplePost("</span>");

        QueryResponse queryResponse = client.query(DEFAULT_COLLECTION,params);
        SolrDocumentList results = queryResponse.getResults();
        long numFound = results.getNumFound();
        System.out.println("总条数："+numFound);

        //获取高亮显示的结果, 高亮显示的结果和查询结果是分开放的
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
            System.out.println(result.get("refernceUrl"));
            System.out.println(result.get("url"));
            System.out.println(result.get("title"));
            System.out.println(result.get("content"));

            Map<String, List<String>> map = highlighting.get(result.get("id"));
            List<String> list = map.get("title");
            System.out.println(list.get(0));

            System.out.println("------------------");
            System.out.println();
        }

        return results;
    }
}
