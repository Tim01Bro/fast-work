package io.project.fastwork.services.api;

import io.project.fastwork.domains.TypeWork;
import io.project.fastwork.services.exception.TypeWorkAlreadyExistsException;
import io.project.fastwork.services.exception.TypeWorkInvalidParameterException;
import io.project.fastwork.services.exception.TypeWorkNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TypeWorkServiceApi {
    TypeWork saveTypeWork(TypeWork savedTypeWork) throws TypeWorkInvalidParameterException, TypeWorkAlreadyExistsException;
    TypeWork updateTypeWork(TypeWork updatedTypeWork) throws TypeWorkAlreadyExistsException, TypeWorkInvalidParameterException;
    TypeWork deleteTypeWork(TypeWork deletedTypeWork) throws TypeWorkNotFound;
    TypeWork getTypeWorkById(Long id) throws TypeWorkNotFound;
    List<TypeWork>findAll();
}
