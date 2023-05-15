package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	static Connection conn = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "jdbc:postgresql://localhost:5432/d1?useSSL=false";
		String username = "postgres";
		String password = "12345678";
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("ci siamo");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		insertStudent(2, "Matteo", "Vacca", "M", 3.4, 1.1, 5.2);
//		insertStudent(3, "Gigio", "Gigietti", "M", 9.0, 8.0, 10.0);
//		insertStudent(4, "Maria", "Rossi", "F", 7.0, 6.0, 8.0);
//		insertStudent(5, "Bella", "Viola", "F", 6.0, 5.0, 7.0);
//		updateStudent(1, "");
		deleteStudent();

		getBest();

		getVoteRange(3.1, 7.2);
	}

	public static void insertStudent(int id, String name, String surname, String gender, double avg, double min_vote,
			double max_vote) {
		String sqlInsert = "INSERT INTO school_students (id, name, surname, gender, birthdate, avg, min_vote, mav_vote) VALUES (?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlInsert);
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setString(3, surname);
			stmt.setString(4, gender);
			stmt.setDate(5, null);
			stmt.setDouble(6, avg);
			stmt.setDouble(7, min_vote);
			stmt.setDouble(8, max_vote);
			stmt.execute();
			System.out.println("Studente inserito");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateStudent(int id, String nuovoNome) {
		String sqlUpdateOne = "UPDATE school_students SET name = ? WHERE id = ?";
		try {
			PreparedStatement stmtUpdateOne = conn.prepareStatement(sqlUpdateOne);
			stmtUpdateOne.setString(1, "Francesca");
			stmtUpdateOne.setInt(2, 4);
			stmtUpdateOne.execute();
			System.out.println("Studente modificato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteStudent() {
		String sqlDelete = "DELETE FROM school_students WHERE id = ?";
		try {
			PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete);
			stmtDelete.setInt(1, 1);
			stmtDelete.execute();
			System.out.println("Studente eliminato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void getBest() {
		String sqlSelect = "SELECT * FROM school_students ORDER BY avg DESC LIMIT 1";
		try {
			Statement stmt = conn.createStatement();
			ResultSet students = stmt.executeQuery(sqlSelect);
			while (students.next()) {
				System.out.println(students.getString("name") + " " + students.getString("surname") + " "
						+ "ha la media migliore" + " " + students.getDouble("avg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void getVoteRange(double min, double max) {
		String sqlRange = "SELECT * FROM school_students WHERE min_vote >= ? AND mav_vote <= ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlRange);
			stmt.setDouble(1, min);
			stmt.setDouble(2, max);
			ResultSet students = stmt.executeQuery();
			while (students.next()) {
				System.out.println("Lo studente con questo range di voti è:" + " " + students.getString("name") + " "
						+ students.getString("surname"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
