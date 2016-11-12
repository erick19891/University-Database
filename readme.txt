This project is divided into two tasks

Task 1: Create a database (Oracle SQL 10g) of university students who are (or not) enrolled in courses.

A student is described by the following attributes:

Student name
Student identification number
Student major
Student status

Courses are described the the following attributes:

Semester
Course identification
Course title
Number of credits
Section number
Instructor name
Meetings

The second task uses Java to connect to the database and compute the students tuition for the semester.

The amount due is computed as follows. 

For undergraduate students: If enrolled in 11
credits or less, tuition is per credit: $377.75; if enrolled in 12 credits or more, tuition is
fixed: $4,533.00. For graduate students: If enrolled in 8 credits or less, tuition is per credit:
$561.00; if enrolled in 9 credits or more, tuition is fixed: $5,049.00.
Reports are generated for students that are not enrolled as well (with an appropriate remark to
the recipient).
