package com.niukedemo.mapper.elasticsearch;

import com.niukedemo.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 刘欢
 * @date 2022年11月04日 16:40
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {
}
