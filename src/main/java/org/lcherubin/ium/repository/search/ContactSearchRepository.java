package org.lcherubin.ium.repository.search;

import org.lcherubin.ium.domain.Contact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Contact entity.
 */
public interface ContactSearchRepository extends ElasticsearchRepository<Contact, Long> {
}
