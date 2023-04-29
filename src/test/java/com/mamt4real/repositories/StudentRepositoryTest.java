package com.mamt4real.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.google.common.collect.Lists;
import com.mamt4real.exceptions.RegisteredCoursesExceedLimitException;
import com.mamt4real.exceptions.StudentNotFoundException;
import com.mamt4real.models.Course;
import com.mamt4real.models.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import com.google.common.collect.ImmutableList;


public class StudentRepositoryTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private StudentRepository studentRepository;

    @BeforeAll
    public static void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("esl_school_test");
    }

    @AfterAll
    public static void cleanup() {
        entityManagerFactory.close();
    }

    @BeforeEach
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Student").executeUpdate();
        entityManager.createQuery("DELETE FROM Course").executeUpdate();
        entityManager.getTransaction().commit();
        studentRepository = new StudentRepository(entityManager);
    }

    @Test
    public void testCreateOne() {
        Student student = new Student("John Doe", "male", 1L);
        long id = studentRepository.createOne(student);
        assertNotNull(studentRepository.getOne(id));
    }

    @Test
    public void testGetAll() {
        Student student1 = new Student("John Doe", "male", 1L);
        Student student2 = new Student("Jane Doe", "female", 1L);
        studentRepository.createOne(student1);
        studentRepository.createOne(student2);
        List<Student> students = studentRepository.getAll();
        assertEquals(ImmutableList.of(student1, student2), students);
    }

    @Test
    public void testGetOne() {
        Student student = new Student("John Doe", "male", 1L);
        long id = studentRepository.createOne(student);
        assertEquals(student, studentRepository.getOne(id));
    }

    @Test
    public void testUpdate() {
        Student student = new Student("John Doe", "male", 1L);
        long id = studentRepository.createOne(student);
        student.setName("Jane Doe");
        student.setGender("female");
        studentRepository.update(student);
        assertEquals("Jane Doe", studentRepository.getOne(id).getName());
        assertEquals("female", studentRepository.getOne(id).getGender());
    }

    @Test
    public void testDelete() {
        Student student = new Student("John Doe", "male", 1L);
        long id = studentRepository.createOne(student);
        studentRepository.delete(student);
        assertNull(studentRepository.getOne(id));
    }

    @Test
    public void testRegisterCourses() throws RegisteredCoursesExceedLimitException, StudentNotFoundException {
        Course course1 = new Course("Math");
        Course course2 = new Course("English");
        Course course3 = new Course("History");
        Course course4 = new Course("Art");
        Course course5 = new Course("Geography");
        Course course6 = new Course("Literature");
        Course course7 = new Course("Physics");
        Course course8 = new Course("Chemistry");
        Student student = new Student("John Doe", "male", 1L);

        entityManager.getTransaction().begin();
        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.persist(course3);
        entityManager.persist(course4);
        entityManager.persist(course5);
        entityManager.persist(course6);
        entityManager.persist(course7);
        entityManager.persist(course8);
        entityManager.getTransaction().commit();

        final List<Course> courses = Lists.newArrayList(course2, course1, course6, course4, course7, course8, course5, course3);

        long id = studentRepository.createOne(student);
        Assertions.assertThrows(StudentNotFoundException.class, () -> studentRepository.registerCourses(4555, courses));

        Assertions.assertThrows(RegisteredCoursesExceedLimitException.class, () -> studentRepository.registerCourses(id, courses));

        courses.remove(course1);
        student.setRegisteredCourses(courses);
        studentRepository.registerCourses(student.getStudentId(), courses);

        Assertions.assertEquals(courses.size(), student.getRegisteredCourses().size());
        Assertions.assertTrue(student.getRegisteredCourses().containsAll(courses));
    }
}

