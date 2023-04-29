package com.mamt4real.repositories;

import com.mamt4real.interfaces.CrudOperations;
import com.mamt4real.models.Teacher;
import com.mamt4real.models.QTeacher;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TeacherRepository implements CrudOperations<Teacher> {
    private final EntityManager entityManager;
    private final JPAQuery<Teacher> query;
    private final QTeacher Q_COURSE = QTeacher.teacher;

    public TeacherRepository() {
        this(BaseRepository.getEntityManager());
    }

    public TeacherRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQuery<>(entityManager);
    }

    private void withTransaction(Runnable runnable){
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }

    @Override
    public long createOne(Teacher data) {
        withTransaction(()-> entityManager.persist(data));
        return data.getTeacherId();
    }

    @Override
    public List<Teacher> getAll() {
        return query.from(Q_COURSE).fetch();
    }

    @Override
    public Teacher getOne(long id) {
        return query.from(Q_COURSE).where(Q_COURSE.teacherId.eq(id)).fetchOne();
    }

    @Override
    public Teacher update(Teacher data) {
        final Teacher[] updated = new Teacher[1];
        withTransaction(()-> updated[0] = entityManager.merge(data));
        return updated[0];
    }

    @Override
    public void delete(Teacher data) {
        withTransaction(()->entityManager.remove(data));
    }

}
