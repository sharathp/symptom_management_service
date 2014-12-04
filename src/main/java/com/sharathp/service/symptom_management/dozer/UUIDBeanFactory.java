package com.sharathp.service.symptom_management.dozer;

import org.dozer.BeanFactory;

import java.util.UUID;

public class UUIDBeanFactory implements BeanFactory {
    public Object createBean(Object sourceBean, Class<?> destinationType, String mapId) {
        if (sourceBean == null) {
            return null;
        }
        UUID source = (UUID) sourceBean;
        UUID destination = new UUID(source.getMostSignificantBits(), source.getLeastSignificantBits());
        return destination;
    }
}