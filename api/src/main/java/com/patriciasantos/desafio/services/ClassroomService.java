package com.patriciasantos.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.User;
import com.patriciasantos.desafio.models.to.ClassroomTO;
import com.patriciasantos.desafio.repositories.ClassroomRepository;
import com.patriciasantos.desafio.services.exceptions.BusinessException;
import com.patriciasantos.desafio.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final UserService userService;

    public ClassroomService(final ClassroomRepository classroomRepository, final UserService userService) {
        this.classroomRepository = classroomRepository;
        this.userService = userService;
    }

    public List<ClassroomTO> findAllByUser() {
       final Long userId = this.userService.getAuthenticatedUserId();
       final List<Classroom> classrooms = this.classroomRepository.findByUserId(userId);
       return classrooms.stream().map(classroom -> new ClassroomTO(classroom)).toList();
    }

    public Classroom findById(final Long id) {
        final Long userId = this.userService.getAuthenticatedUserId();
        final Classroom classroom = this.classroomRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Classe não encontrada!"));

        this.validateIfClassBelongsToTheUser(classroom, userId);
        this.validateIfClassWasDeleted(classroom);
        return classroom;
     }

     public ClassroomTO findTOById(final Long id) {
        return new ClassroomTO(this.findById(id));
     }

    @Transactional(rollbackOn = Exception.class)
    public Classroom create(final ClassroomTO classroomTO) {
        final User user = this.userService.getAuthenticatedUser();

        final Classroom classroom = new Classroom.ClassroomBuilder().create()
        .withName(classroomTO.getName())
        .withUser(user)
        .build();

        return this.classroomRepository.save(classroom);
    }

    @Transactional(rollbackOn = Exception.class)
    public void update(final ClassroomTO classroomTO, final Long id) {
        final Classroom classroom = this.findById(id);
        classroom.setName(classroomTO.getName());
        this.classroomRepository.save(classroom);
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(final Long id) {
        final Classroom classroom = this.findById(id);
        classroom.setStatus(false);
        this.classroomRepository.save(classroom);
    }

    private void validateIfClassBelongsToTheUser(final Classroom classroom, final Long userId) {
        if (!classroom.getIdUser().equals(userId)) {
            throw new BusinessException("A classe não pertence a este usuário!");
        }
    }

    private void validateIfClassWasDeleted(final Classroom classroom) {
        if (!classroom.isStatus()) {
            throw new ObjectNotFoundException("Classe excluida!");
        }
    }
}