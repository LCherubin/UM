package org.lcherubin.ium.repository.search;

import org.lcherubin.ium.domain.Etudiant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Etudiant entity.
 */
public interface EtudiantSearchRepository extends ElasticsearchRepository<Etudiant, Long> {
}
