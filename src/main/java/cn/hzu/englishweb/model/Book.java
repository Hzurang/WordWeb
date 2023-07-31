package cn.hzu.englishweb.model;

import lombok.Data;

@Data
public class Book {
    private Integer bookId;

    private String bookName;

    private String bookUser;

    private String description;

    private String content;
}
