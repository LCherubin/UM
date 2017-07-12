package org.lcherubin.ium.repository.search;

import org.lcherubin.ium.domain.AnneeUniversitaire;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AnneeUniversitaire entity.
 */
public interface AnneeUniversitaireSearchRepository extends ElasticsearchRepository<AnneeUniversitaire, Long> {
}
