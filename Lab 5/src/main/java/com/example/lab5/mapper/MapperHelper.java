package com.example.lab5.mapper;

import com.example.lab5.model.TeacherClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MapperHelper {

    @PersistenceContext
    private EntityManager entityManager;

    @Named("mapGroupIdToGroup")
    public TeacherClass map(String groupId) {
        if (groupId == null || groupId.isEmpty()) {
            return null;
        }
        UUID uuid = UUID.fromString(groupId);
        return entityManager.getReference(TeacherClass.class, uuid);
    }
}
