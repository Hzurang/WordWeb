package cn.hzu.englishweb.model.vo;

import lombok.Data;

@Data
public class WordInfo {
    /**
     * 单词ID
     */
    private Integer wordId;
    /**
     * 单词英文
     */
    private String wordEng;
    /**
     * 单词中文
     */
    private String wordChi;
    /**
     * 英式读音
     */
    private String wordEN;
    /**
     * 单词类别
     */
    private String wordStyle;
}
