# EslSchool

## Description
You are to implement a School system where a new student can be registered and each registered student can register  for courses from a list of courses offered by the school. i.e School Encentral offers MATHS, ENGLISH, FINE ART, FURTHER MATHS, AGRIC, BIOLOGY, CHEMISTRY, and so on.
As a registered student I am permitted to register between 5 to 7 courses from the courses offered by the school.
Also there should be a list of teachers available in the school, upon the completion of registration for a student in the school as well as the registration of courses, a random teacher from the teacher in the school should be assigned to the student to serve as a school personal guide. 
A student should have only one personal guide (Teacher) but a teacher can serve as a school personal guide for many students.


## Required methods
* Register a student to the school
* Register courses for the student
* Get the list of every student in the school
* Get the list of courses registered for a student
* Get all the teachers in the school
* Get the teacher assigned to a student (Return this as a JSON string using Jackson)

- Write test cases for your implementations 
- For standardized and for validation implement this using Guava Library, use Jackson to convert to JSON string and write test cases using testContainers 


### NB:
1. Your project must be a maven project.
2. Write tests to cover all the methods to be written, before development begins. 
3. Make logical assumptions where necessary.
4. Submission is to be done with Github.
