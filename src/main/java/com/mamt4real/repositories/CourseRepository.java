package com.mamt4real.repositories;

import com.mamt4real.interfaces.CrudOperations;
import com.mamt4real.models.Course;
import com.mamt4real.models.QCourse;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CourseRepository implements CrudOperations<Course> {

    private final EntityManager entityManager;
    private final JPAQuery<Course> query;
    private final QCourse Q_COURSE = QCourse.course;

    public CourseRepository() {
        this(BaseRepository.getEntityManager());
    }

    public CourseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQuery<>(entityManager);
    }

    private void withTransaction(Runnable runnable){
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }

    @Override
    public long createOne(Course data) {
        withTransaction(()-> entityManager.persist(data));
        return data.getCourseId();
    }

    @Override
    public List<Course> getAll() {
        return query.from(Q_COURSE).fetch();
    }

    @Override
    public Course getOne(long id) {
        return query.from(Q_COURSE).where(Q_COURSE.courseId.eq(id)).fetchOne();
    }

    @Override
    public Course update(Course data) {
        final Course[] updated = new Course[1];
        withTransaction(()-> updated[0] = entityManager.merge(data));
        return updated[0];
    }

    @Override
    public void delete(Course data) {
        withTransaction(()->entityManager.remove(data));
    }

    public List<Course> getCoursesByIds(List<Long> ids){
        return query.from(Q_COURSE).where(Q_COURSE.courseId.in(ids)).fetch();
    }
}
