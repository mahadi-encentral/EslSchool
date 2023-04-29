package com.mamt4real.repositories;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;


import com.mamt4real.models.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourseRepositoryTest {

    private EntityManager entityManager;
    private CourseRepository courseRepository;

    @Before
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("esl_school_test");
        entityManager = entityManagerFactory.createEntityManager();
        courseRepository = new CourseRepository(entityManager);
    }

    @After
    public void tearDown() {
        entityManager.close();
    }

    @Test
    public void testCreateOne() {
        Course course = new Course("Mathematics");
        long courseId = courseRepository.createOne(course);
        assertNotEquals(0, courseId);

        Course persistedCourse = entityManager.find(Course.class, courseId);
        assertNotNull(persistedCourse);
        assertEquals("Mathematics", persistedCourse.getTitle());

    }

    @Test
    public void testGetAll() {
        Course course1 = new Course("Mathematics");
        Course course2 = new Course("Computer Science");

        entityManager.getTransaction().begin();
        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.getTransaction().commit();

        List<Course> courses = courseRepository.getAll();
        assertEquals(2, courses.size());
    }

    @Test
    public void testGetOne() {
        Course course1 = new Course("Mathematics");
        Course course2 = new Course("Computer Science");

        entityManager.getTransaction().begin();
        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.getTransaction().commit();

        Course foundCourse = courseRepository.getOne(course2.getCourseId());
        assertNotNull(foundCourse);
        assertEquals(course2.getTitle(), foundCourse.getTitle());

    }

    @Test
    public void testUpdate() {
        Course course = new Course("Mathematics");

        entityManager.getTransaction().begin();
        entityManager.persist(course);
        entityManager.getTransaction().commit();

        course.setTitle("Advanced Mathematics");
        Course updatedCourse = courseRepository.update(course);
        assertNotNull(updatedCourse);
        assertEquals("Advanced Mathematics", updatedCourse.getTitle());

        Course persistedCourse = entityManager.find(Course.class, course.getCourseId());
        assertNotNull(persistedCourse);
        assertEquals("Advanced Mathematics", persistedCourse.getTitle());

    }

    @Test
    public void testDelete() {
        Course course = new Course("Mathematics");

        entityManager.getTransaction().begin();
        entityManager.persist(course);
        entityManager.getTransaction().commit();

        courseRepository.delete(course);

        Course deletedCourse = entityManager.find(Course.class, course.getCourseId());
        assertNull(deletedCourse);
    }

    @Test
    public void testGetCoursesByIds() {
        Course course1 = new Course("Mathematics");
        Course course2 = new Course("Computer Science");
        Course course3 = new Course("Chemistry Science");

        entityManager.getTransaction().begin();
        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.persist(course3);
        entityManager.getTransaction().commit();

        var courses = courseRepository.getCoursesByIds(List.of(course1.getCourseId(),course2.getCourseId(),course3.getCourseId()));
        assertEquals(3, courses.size());
        assertTrue(courses.containsAll(List.of(course1,course2,course3)));

    }
}

