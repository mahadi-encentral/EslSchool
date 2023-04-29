package com.mamt4real.repositories;

import com.mamt4real.interfaces.CrudOperations;
import com.mamt4real.models.Course;
import com.mamt4real.models.QStudent;
import com.mamt4real.models.Student;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;

public class StudentRepository implements CrudOperations<Student> {

    private final EntityManager entityManager;
    private final JPAQuery<Student> query;

    private final QStudent Q_STUDENT = QStudent.student;

    public StudentRepository() {
        this(BaseRepository.getEntityManager());
    }

    public StudentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQuery<>(entityManager);
    }

    private void withTransaction(Runnable runnable) {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }

    @Override
    public long createOne(Student data) {
        entityManager.getTransaction().begin();
        entityManager.persist(data);
        entityManager.getTransaction().commit();
        return data.getStudentId();
    }

    @Override
    public List<Student> getAll() {
        return query.from(Q_STUDENT).fetch();
    }

    @Override
    public Student getOne(long id) {
        return query.from(Q_STUDENT).where(Q_STUDENT.studentId.eq(id)).fetchOne();
    }

    @Override
    public Student update(Student data) {
        entityManager.getTransaction().begin();
        Student updated = entityManager.merge(data);
        entityManager.getTransaction().commit();
        return updated;
    }

    @Override
    public void delete(Student data) {
        withTransaction(() -> entityManager.remove(data));
    }

    public void registerCourses(Student student, List<Course> courses) {

    }
}
