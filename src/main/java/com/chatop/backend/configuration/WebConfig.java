package com.chatop.backend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// toutes les requêtes d'accès aux ressources statiques sous le chemin "/images/" seront gérées par ce gestionnaire.
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    ImageProperties imageProperties;

    //définit le pattern d'URL à gérer
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(imageProperties.getFullImageDir());   //  addResourceLocations qui indique l'emplacement physique des ressources
    }
}
