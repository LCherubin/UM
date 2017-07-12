package org.lcherubin.ium.repository.search;

import org.lcherubin.ium.domain.Filiere;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Filiere entity.
 */
public interface FiliereSearchRepository extends ElasticsearchRepository<Filiere, Long> {
}
