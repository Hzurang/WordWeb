package cn.hzu.englishweb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Listen {
    private Integer listenId;

    private String listenName;

    private String listenPath;

    private String content;

    private String description;
    /**
     * 听力创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd",iso=DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    private String gradeLis;

    private String pageUrl;

}
