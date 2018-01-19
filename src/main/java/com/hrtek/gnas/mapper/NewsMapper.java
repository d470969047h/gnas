package com.hrtek.gnas.mapper;

import com.hrtek.gnas.entity.NewsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**

/**
 * 新闻mapper
 * 470969043@qq.com
 * @author daihui
 * @since 2018-01-04 15:42
 */
@Mapper
public interface NewsMapper {

    /**
     * 查询所有类别下的新闻
     * @return 所有新闻
     */
    List<NewsEntity> list();

    /**
     * 根据新闻分类获取该类新闻
     * @param newsCategory
     * @return 某类新闻
     */
    List<NewsEntity> listByNewsCategory(String newsCategory);

    /**
     * 获取各地新闻条数
     * @return
     */
    List<NewsEntity> getAddress();

    /**
     * 根据新闻发生地获取新闻
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
