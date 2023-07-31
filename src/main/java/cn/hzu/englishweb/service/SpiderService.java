package cn.hzu.englishweb.service;

import cn.hzu.englishweb.model.Listen;
import cn.hzu.englishweb.model.Word;

import java.util.List;

public interface SpiderService {
    /**
     * 获取四级单词
     * @return 完整信息的单词列表
     * @throws Exception
     */
    List<Word> getDataCET4() throws Exception;

    /**
     * 获取单词
     * @return  只有单词的列表
     * @throws Exception
     */
    List<Word> getWord() throws Exception;

    /**
     * 获取六级单词
     * @return 完整信息的单词列表
     * @throws Exception
     */
    List<Word> getDataCET6() throws Exception;

    /**
     * 获取托福单词
     * @return 完整信息的单词列表
     * @throws Exception
     */
    List<Word> getDataTF() throws Exception;

    /**
     * 获取雅思单词
     * @return 完整信息的单词列表
     * @throws Exception
     */
    List<Word> getDataYS() throws Exception;

    /**
     * 获取听力信息
     * @return 完整信息的听力列表
     * @throws Exception
     */
    List<Listen> spider(String url) throws Exception;
}