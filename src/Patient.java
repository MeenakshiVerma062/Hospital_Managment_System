import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


    public class Patient {

        private Connection connection;
        private Scanner scanner;
        public Patient(Connection connection, Scanner scanner){
            this.connection = connection;
            this.scanner = scanner;
        }


        public void addPatient() {
            // Read the patient name
            System.out.println("Enter Patient Name: ");
            String name = scanner.next(); // Use nextLine() to capture full name, including spaces

            // Read the patient's age
            int age = 0;
            boolean validAge = false;
            while (!validAge) {
                System.out.println("Enter Patient Age: ");
                String ageInput = scanner.next(); // Read age as a string
                try {
                    age = Integer.parseInt(ageInput);  // Try parsing it as an integer
                    if (age > 0) {
                        validAge = true;  // Age is valid if it's a positive number
                    } else {
                        System.out.println("Please enter a valid age (positive integer).");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer for age.");
                }
            }

            // Read the patient's gender
            System.out.println("Enter Patient Gender M/F and Other: ");
            String gender = scanner.next(); // Use nextLine() for gender input

            try {
                String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, gender);

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Patient Added Successfully!!");
                } else {
                    System.out.println("Patient not Added Successfully!!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




        public void viewPatients() {
            String query = "SELECT * FROM patients";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("Patients");
                System.out.println("+----------------+--------------------+-------------------+--------------------+");
                System.out.println("| Patient Id     | Name               | Age               | Gender             |");
                System.out.println("+----------------+--------------------+-------------------+--------------------+");

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String gender = resultSet.getString("gender");
                    // Print patient details
                    System.out.printf("| %-15d | %-18s | %-17d | %-18s |\n",id, name, age, gender);
                }
                System.out.println("+----------------+--------------------+-------------------+--------------------+");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public boolean getPatientById(int id){
            String query = "SELECT * FROM patients WHERE id = ?";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    return true;
                }else{
                    return false;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }

    }

