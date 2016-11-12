	import java.sql.*; // import the Java SQL library
	/**
	 * @author Erick Posada-Roque
	 * Program: The program extracts student enrollment information from an oracle database.
	 * The information includes the sections,time,place,and the instructor who teaches the course.
	 * In addition, the program calculates the tuition for students who are enrolled.
	 */
	class program// create a new class to encapsulate the program
	{
		public static void SQLError (Exception e) // our function for handling SQL errors
		{
			System.out.println("ORACLE error detected:");
			e.printStackTrace();
		}
		public static void main (String args[])// the main function
		{
			try// watch for errors
			{
				String driverName = "oracle.jdbc.driver.OracleDriver";
				Class.forName(driverName);
				System.out.println("Connecting to Oracle...");
				String url = "jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g";
				Connection conn = DriverManager.getConnection(url,"username","password");
				System.out.println("Connected!");
				Statement stmt = conn.createStatement(); //create a new statement
				Statement stmt2 = conn.createStatement(); //create a new statement
				// Now execute the query and store the results in the myresults object:
				ResultSet studentTable = stmt.executeQuery("SELECT * FROM STUDENT");
				while (studentTable.next()) // Print each row in a loop
				{
					//print the student
					System.out.println("-------------------------------------------------");
					System.out.println("Date mailed: 06/19/2014");
					System.out.println("Semester: Summer 2014");
					System.out.println("Student Name: " + studentTable.getString("SNAME"));
					System.out.println("Student identification number: " + studentTable.getString("SUID"));
					System.out.println("Student major: " + studentTable.getString("MAJOR"));
					System.out.println("Student status: " + studentTable.getString("STATUS"));
					System.out.println();
					//print the courses the student is enrolled
					ResultSet studentSemesterTable = stmt2.executeQuery("SELECT E.DCODE, E.CNO, E.SNO,C.TITLE,C.CREDIT,F.FNAME,M.BLDNUMBER,M.MEETINGDAY,M.BEGINTIME,M.ENDTIME FROM ENROLL E, STUDENT S1, FACULTY F, COURSE C, SECTION S, MEETING M WHERE S1.SUID = "+studentTable.getString("SUID")+ " AND E.SUID = S1.SUID AND C.DCODE = E.DCODE AND C.CNO = E.CNO AND S.DCODE = E.DCODE AND S.CNO = E.CNO AND S.SNO = E.SNO AND S.INSTRUCTORID = F.FUID AND S.DCODE = M.DCODE AND S.CNO = M.CNO AND S.SNO = M.SNO");
					if(!studentSemesterTable.next())//ResultSet is empty
					{
						System.out.println("Courses the student("+studentTable.getString("SUID").replaceAll("\\s+","") +") is enrolled: NONE\n"); //heading
					}
					else 
					{
						//print enrollment information
						System.out.println("Courses the student("+studentTable.getString("SUID").replaceAll("\\s+","") +") is enrolled:"); //heading
						do{
							System.out.println("Course identification: "+ studentSemesterTable.getString("DCODE") + studentSemesterTable.getString("CNO"));
							System.out.println("Course Credits: "+ studentSemesterTable.getString("CREDIT"));
							System.out.println("Course title: "+ studentSemesterTable.getString("TITLE"));
							System.out.println("Section: " + studentSemesterTable.getString("SNO"));
							System.out.println("Instructor Name: "+ studentSemesterTable.getString("FNAME"));
							System.out.println("Meeting: "+ studentSemesterTable.getString("MEETINGDAY")+" in building: "+studentSemesterTable.getString("BLDNUMBER") + " from: " + studentSemesterTable.getString("BEGINTIME")+ " to: "+studentSemesterTable.getString("ENDTIME"));
							System.out.println();
						}while(studentSemesterTable.next());
						//compute credits and tuition
						ResultSet totalCreditsTable = stmt2.executeQuery("SELECT S.SUID, SUM(CREDIT) AS CREDIT_TOTAL FROM ENROLL E, STUDENT S, COURSE C WHERE S.SUID ="+ studentTable.getString("SUID")+"AND E.SUID=S.SUID AND E.DCODE = C.DCODE AND E.CNO = C.CNO GROUP BY S.SUID");
						double tuition = 0;
						totalCreditsTable.next(); //point to the first row
						int credits = totalCreditsTable.getInt("CREDIT_TOTAL");
						String status = studentTable.getString("Status");
						System.out.println("Total Credits: "+credits);
						if(credits <= 11 && (status.equals("Undergraduate")||status.equals("non-degree")));
							tuition = credits * 377.75;
						if(credits >= 12 && status.equals("Undergraduate"))
							tuition = 4533.0;
						if(credits <= 8 && status.equals("Graduate"))
							tuition = credits*561.00;
						if(credits >= 9 && status.equals("Graduate"))
							tuition = 5049.00;
						System.out.println("Tuition: $"+tuition);	
						
					}
				}
				System.out.println("-------------------------------------------------");
				conn.close(); // Close the connection.
			}catch (Exception e) {SQLError(e);} // if error occurred in the try block, call SQLError
		} // End main
	} // End myprog
