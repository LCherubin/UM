package org.lcherubin.ium.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(org.lcherubin.ium.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Contribution.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.AnneeUniversitaire.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Etudiant.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Enseignant.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Parent.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Filiere.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Cours.class.getName(), jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Etudiant.class.getName() + ".annees", jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Etudiant.class.getName() + ".contributions", jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Enseignant.class.getName() + ".annees", jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Parent.class.getName() + ".parents", jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Cours.class.getName() + ".cours", jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Enseignant.class.getName() + ".cours", jcacheConfiguration);
            cm.createCache(org.lcherubin.ium.domain.Filiere.class.getName() + ".cours", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
