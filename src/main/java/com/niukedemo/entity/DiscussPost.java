package com.niukedemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author 刘欢
 * @date 2022年10月01日 20:22
 */
@Data
@Document(indexName = "discusspost",shards = 6,replicas = 3)
public class DiscussPost {
    @Id
    private int id;
    @Field(type = FieldType.Integer)
    private int userId;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer ="ik_smart")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer ="ik_smart")
    private String content;

    private int type;

    private int status;

    private Date createTime;

    private int commentCount;

    private double score;
}
