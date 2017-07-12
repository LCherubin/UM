package org.lcherubin.ium.repository.search;

import org.lcherubin.ium.domain.Enseignant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Enseignant entity.
 */
public interface EnseignantSearchRepository extends ElasticsearchRepository<Enseignant, Long> {
}
