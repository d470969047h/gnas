package com.hrtek.gnas.service;


import com.hrtek.gnas.entity.NewsEntity;

import java.util.List;

/**
 * 新闻sevice
 * 470969043@qq.com
 *
 * @author daihui
 * @since 2018-01-04 16:15
 */
public interface NewsService {

    /**
     * 查询有新闻
     */
    List<NewsEntity> list();

    /**
     * 获取所有各地新闻条数
     * @return
     */
    List<NewsEntity> getAddress();


    /**
     * 根据新闻发生地获取新闻
     * @param newsAddressName
     * @return
     */
    List<String> getTitlesByNewsAddressName(String newsAddressName);

    /**
     * 根据新闻标题地获取新闻
     * @param newsTitle
     * @return
     */
    NewsEntity getNewsByTitle(String newsTitle);
}
