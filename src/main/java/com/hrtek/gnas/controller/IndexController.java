package com.hrtek.gnas.controller;

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



    /**
     * 词频统计
     *
     * @param content 要统计内容
     * @return String
     */
//    @RequestMapping(value = "/chart")
//    @ResponseBody
//    public String showChart(String content) {
//        //词频统计
////        HanLP.Config.ShowTermNature = false;
////        List<Term> segments = SpeedTokenizer.segment(content);
//        Map<String, Integer> map = new HashMap<String, Integer>();
//        for (Term term : segments) {
//            //过滤标点符号等
//            if (term.length() >= 2) {
//                //统计出现的次数
//                if (!map.containsKey(term.toString())) {
//                    map.put(term.toString(), 1);
//                } else {
//                    map.put(term.toString(), map.get(term.toString()) + 1);
//                }
//            }
//        }
//        ArrayList<Map.Entry<String, Integer>> list = sortMap(map);
//        // 取前10条记录
//        if (list.size() > 10) {
//            return list.subList(0, 10).toString();
//        } else {
//            return list.toString();
//        }
//    }








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














































