package org.lcherubin.ium.repository.search;

import org.lcherubin.ium.domain.Contribution;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Contribution entity.
 */
public interface ContributionSearchRepository extends ElasticsearchRepository<Contribution, Long> {
}
