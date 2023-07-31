package cn.hzu.englishweb.model;

import lombok.Data;

@Data
public class Sentence {
    /**
     * 句子Id
     */
    private Integer sentenceId;
    /**
     * 句子语音路径
     */
    private String sentencePath;
    /**
     * 句子英文
     */
    private String sentenceName;
    /**
     * 句子中文
     */
    private String sentenceNote;
    /**
     * 日期
     */
    private String sentenceDate;
}
