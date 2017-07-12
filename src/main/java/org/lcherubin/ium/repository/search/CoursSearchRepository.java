package org.lcherubin.ium.repository.search;

import org.lcherubin.ium.domain.Cours;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Cours entity.
 */
public interface CoursSearchRepository extends ElasticsearchRepository<Cours, Long> {
}
