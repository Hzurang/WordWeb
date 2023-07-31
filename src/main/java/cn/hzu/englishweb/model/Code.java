package cn.hzu.englishweb.model;

import lombok.Data;

/**
 * @className Code
 * @description 验证码实体类
 * @author Hzurang
 * @createDate 2021/11/30
 */
@Data
public class Code {
    /**
     * 验证码
     */
    private String code;

    /**
     * 当前时间
     */
    private Long currentTime;
}
