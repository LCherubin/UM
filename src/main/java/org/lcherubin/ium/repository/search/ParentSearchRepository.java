package org.lcherubin.ium.repository.search;

import org.lcherubin.ium.domain.Parent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Parent entity.
 */
public interface ParentSearchRepository extends ElasticsearchRepository<Parent, Long> {
}
