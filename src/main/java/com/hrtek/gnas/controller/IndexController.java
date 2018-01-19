package com.hrtek.gnas.controller;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hrtek.gnas.entity.NewsEntity;
import com.hrtek.gnas.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 球的Index页面所有
 * 470969043@qq.com
 *
 * @author daihui
 * @since 2018-01-04 14:08
 */
@Controller
public class IndexController {


    @Resource
    private NewsService newsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<NewsEntity> allNews = newsService.list();
        model.addAttribute("allNews", allNews);
        return "index";
    }


    @ResponseBody
    @RequestMapping(value = "/getTitleById", method = RequestMethod.GET)
    public NewsEntity getTitleById(@RequestParam("newsId") String newsId) {
        NewsEntity news = new NewsEntity();
        news.setNewsId(Integer.parseInt(newsId));
        news.setNewsTitle("测试题目测试题目测试题目测试题目测试题目测试题目测试题目测试题目测试题目测试题目");
        news.setNewsContent("测试内容。。");
        return news;
    }

    /**
     * 词频统计
     *
     * @param content 要统计内容
     * @return String
     */
    @RequestMapping(value = "/chart")
    @ResponseBody
    public String showChart(String content) {
        //词频统计
        HanLP.Config.ShowTermNature = false;
        List<Term> segments = SpeedTokenizer.segment(content);
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Term term : segments) {
            //过滤标点符号等
            if (term.length() >= 2) {
                //统计出现的次数
                if (!map.containsKey(term.toString())) {
                    map.put(term.toString(), 1);
                } else {
                    map.put(term.toString(), map.get(term.toString()) + 1);
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> list = sortMap(map);
        // 取前10条记录
        if (list.size() > 10) {
            return list.subList(0, 10).toString();
        } else {
            return list.toString();
        }
    }

    /**
     * 关键字提取
     *
     * @param content 要提取的内容
     * @return List<String>
     */
    @RequestMapping(value = "/chart2")
    @ResponseBody
    public List<String> showChart2(String content) {
        return HanLP.extractKeyword(content, 15);
    }

    /**
     * 命名实体识别
     * @param content  要识别的内容
     * @return List<String>
     */
    @RequestMapping(value = "/chart3")
    @ResponseBody
    public List<Map<String,Object>> showChart3(String content){
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        List<Term> terms = segment.seg(content);
        //识别人名perponNameList
        List<String> perponNameList = new ArrayList<String>();
        //识别地名placeNameList
        List<String> placeNameList = new ArrayList<String>();
        //识别机构名originazeNameList
        List<String> originazeNameList = new ArrayList<String>();
        //关键字keywordList
        List<String> keywordList = HanLP.extractKeyword(content, 5);
        for(Term term : terms){
            if((term.nature.toString().contains("nr") || term.nature.toString().contains("nrf")) && !perponNameList.contains(term.word) ){//人名
                perponNameList.add(term.word);
            }
            if(term.nature.toString().contains("ns") && !placeNameList.contains(term.word)){//地名
                placeNameList.add(term.word);
            }
            if(term.nature.toString().contains("nt") && !originazeNameList.contains(term.word)){//机构名
                originazeNameList.add(term.word);
            }
        }
        //处理文本、人名、地名、机构名、关键字之间的关系
        //格式{name:...,id:...,pid:...}
        //第一层：{name:"文本",id:0,pid:-1}
        //第二层：{name:"人名",id:1,pid:0 || name:"地名",id:2,pid:0 || name:"机构名",id:3,pid:0 || name:"关键字",id:4,pid:0}
        //第三层：{name:...,id:...,pid:...}
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        int count = 0;
        //第一层
        list = dealData(list,"文本",count,-1);
        //第二层
        //人名perponNameList
        list = dealData(list,"人名",++count,0);
        int personid = count;
        for(String str : perponNameList){
            list = dealData(list,str,++count,personid);
        }
        //地名placeNameList
        list = dealData(list,"地名",++count,0);
        int placeid = count;
        for(String str : placeNameList){
            list = dealData(list,str,++count,placeid);
        }
        //机构名originazeNameList
        list = dealData(list,"机构名",++count,0);
        int originazeid = count;
        for(String str : originazeNameList){
            list = dealData(list,str,++count,originazeid);
        }
        //关键字keywordList
        list = dealData(list,"关键字",++count,0);
        int keywordid = count;
        for(String str : keywordList){
            list = dealData(list,str,++count,keywordid);
        }

        return list;
    }


    /**
     * 内容摘要
     * @param content  要摘要的内容
     * @return List<String>
     */
    @RequestMapping(value = "/chart5")
    @ResponseBody
    public List<String> showChart5(String content){
        return HanLP.extractSummary(content, 3);
    }

    /**
     * 把map装入list中
     *
     * @param list 要返回的结果
     * @param name
     * @param pid
     * @return List<Map<String,Object>>
     */
    public static List<Map<String, Object>> dealData(List<Map<String, Object>> list, String name, int id, int pid) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("id", id);
        map.put("pid", pid);
        list.add(map);
        return list;
    }

    /**
     * 对map中value值进行排序
     *
     * @param map
     * @return ArrayList<Map.Entry<String, Integer>>
     */
    public static ArrayList<Map.Entry<String, Integer>> sortMap(Map map) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, (obj1, obj2) -> obj2.getValue() - obj1.getValue());
        return (ArrayList<Map.Entry<String, Integer>>) entries;
    }

    /**
     * 获取所有新闻发生地
     * @return List<NewsEntity>
     * daihui
     */
    @ResponseBody
    @RequestMapping(value = "/getAddress")
    public List<NewsEntity> getAddress(){
        return newsService.getAddress();
    }

    /**
     * 获取某地新闻标题
     * @param newsAddressName 地名
     * @return List<String>
     * daihui
     */
    @ResponseBody
    @RequestMapping(value = "/getTitles")
    public List<String> getTitles(@RequestParam("newsAddressName") String newsAddressName) {
        if (!StringUtils.isEmpty(newsAddressName)){
            return newsService.getTitlesByNewsAddressName(newsAddressName);
        }
        return null;
    }

    /**
     * 根据新闻标题获取新闻所有信息
     * @param newsTitle 新闻标题
     * @return NewsEntity
     */
    @ResponseBody
    @RequestMapping(value = "/getNews")
    public NewsEntity getNews(@RequestParam("newsTitle") String newsTitle) {
        if (!StringUtils.isEmpty(newsTitle)){
            return newsService.getNewsByTitle(newsTitle);
        }
        return null;
    }
}














































