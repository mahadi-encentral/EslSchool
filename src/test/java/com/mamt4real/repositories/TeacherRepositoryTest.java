package com.mamt4real.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.mamt4real.models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import java.util.List;

public class TeacherRepositoryTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private TeacherRepository teacherRepository;

    @BeforeAll
    public static void setUpBeforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("esl_school_test");
    }

    @AfterAll
    public static void tearDownAfterAll() {
        entityManagerFactory.close();
    }

    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        teacherRepository = new TeacherRepository(entityManager);
    }

    @AfterEach
    public void tearDown() {
        entityManager.close();
    }

    @Test
    public void testCreateOne() {
        Teacher teacher = new Teacher("John Smith");
        long id = teacherRepository.createOne(teacher);
        assertTrue(id > 0);
    }

    @Test
    public void testGetAll() {
        List<Teacher> teachers = teacherRepository.getAll();
        assertNotNull(teachers);
    }

    @Test
    public void testGetOne() {
        Teacher teacher = new Teacher("Mary Brown");
        long id = teacherRepository.createOne(teacher);
        Teacher foundTeacher = teacherRepository.getOne(id);
        assertNotNull(foundTeacher);
        assertEquals(id, foundTeacher.getTeacherId());
    }

    @Test
    public void testUpdate() {
        Teacher teacher = new Teacher("Bob Wilson");
        long id = teacherRepository.createOne(teacher);
        teacher.setName("Bobby Wilson");
        Teacher updatedTeacher = teacherRepository.update(teacher);
        assertEquals("Bobby Wilson", updatedTeacher.getName());
    }

    @Test
    public void testDelete() {
        Teacher teacher = new Teacher("Sara Lee");
        long id = teacherRepository.createOne(teacher);
        Teacher foundTeacher = teacherRepository.getOne(id);
        assertNotNull(foundTeacher);
        teacherRepository.delete(foundTeacher);
        foundTeacher = teacherRepository.getOne(id);
        assertNull(foundTeacher);
    }

    @Test
    public void testFindRandom() {
        List<Teacher> teachers = teacherRepository.getAll();
        if (teachers.size() > 0) {
            Teacher randomTeacher = teacherRepository.findRandom();
            assertNotNull(randomTeacher);
        }
    }
}
