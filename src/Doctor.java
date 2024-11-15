import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {

    private Connection connection;
    public Doctor(Connection connection){
        this.connection = connection;
    }
    public void viewDoctor() {
        String query = "SELECT * FROM doctor";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Doctors: ");
            System.out.println("+------------------+-------------------+-------------------+");
            System.out.println("| Doctor Id        | Name              | Specialization    |");
            System.out.println("+------------------+-------------------+-------------------+");

            // Iterate through the result set and print each row
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-16d | %-17s | %-17s |\n", id, name, specialization);
            }
            System.out.println("+------------------+-------------------+-------------------+");

        } catch (SQLException e) {
            System.out.println("Error querying the doctor table.");
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
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
