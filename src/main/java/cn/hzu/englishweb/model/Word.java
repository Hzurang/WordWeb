package cn.hzu.englishweb.model;

import lombok.Data;

@Data
public class Word {
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
     * 英式读音路径
     */
    private String wordEnPath;
    /**
     * 美式读音路径
     */
    private String wordUkPath;
    /**
     * 图片路径
     */
    private String wordPhotoPath;
    /**
     * 单词例句
     */
    private String wordSentence;
    /**
     * 单词类别
     */
    private Integer wordStyle;
}
