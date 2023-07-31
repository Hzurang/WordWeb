package cn.hzu.englishweb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Progress {
    private Integer id;

    private Integer tableId;

    private Integer wordId;

    private Integer userId;

    /**
     * 公告创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd",iso=DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date wordTime;
}