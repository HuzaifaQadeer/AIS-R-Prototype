package com.ais.tcpserver.util;

import com.ais.model.AdminModel;
import com.ais.model.DashboardDTO;
import com.ais.model.ManagementModel;
import com.ais.model.RecruitModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {

    private static final String url = "jdbc:mysql://localhost:3306/ais-r-db";
    private static final String user = "root";
    private static final String password = "pass";

    private Connection connection;

    public DBManager() throws SQLException {
        connect();
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to the MySQL database!");
        createManagementTableIfNotExists();
        createRecruitsTableIfNotExists();
        createAdminTableIfNotExists();
    }

    public Connection getConnection() {
        return connection;
    }

    public void insertAdmin(AdminModel admin) throws SQLException {
        String sql = "INSERT INTO admin (fullName, address, phoneNo, email, userName, password, level, branch) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, admin.getFullName());
            pstmt.setString(2, admin.getAddress());
            pstmt.setString(3, admin.getPhoneNo());
            pstmt.setString(4, admin.getEmail());
            pstmt.setString(5, admin.getUserName());
            pstmt.setString(6, admin.getPassword());
            pstmt.setString(7, admin.getLevel());
            pstmt.setString(8, admin.getBranch());

            pstmt.executeUpdate();
            System.out.println("Admin inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateAdmin(AdminModel admin) throws SQLException {
        String sql = "UPDATE admin SET fullName = ?, address = ?, phoneNo = ?, email = ?, password = ?, level = ?, branch = ?, userName = ? WHERE id = ?";
        boolean isUpdated = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, admin.getFullName());
            pstmt.setString(2, admin.getAddress());
            pstmt.setString(3, admin.getPhoneNo());
            pstmt.setString(4, admin.getEmail());
            pstmt.setString(5, admin.getPassword());
            pstmt.setString(6, admin.getLevel());
            pstmt.setString(7, admin.getBranch());
            pstmt.setString(8, admin.getUserName());
            pstmt.setInt(9, admin.getId());

            int rowsAffected = pstmt.executeUpdate();
            isUpdated = (rowsAffected > 0);

            System.out.println("Update query executed: " + pstmt);
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }

    public List<AdminModel> getAdmin() {
        List<AdminModel> admins = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM admin";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNo = rs.getString("phoneNo");
                String email = rs.getString("email");
                String userName = rs.getString("userName");
                String pwd = rs.getString("password");
                String level = rs.getString("level");
                String branch = rs.getString("branch");
                admins.add(new AdminModel(id, fullName, address, phoneNo, email, userName, pwd, level, branch));
            }
            return admins;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RecruitModel> getRecruits() {
        List<RecruitModel> recruits = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM recruit";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNo = rs.getString("phoneNo");
                String email = rs.getString("email");
                String userName = rs.getString("userName");
                String pwd = rs.getString("password");
                String interviewDate = rs.getString("interviewDate");
                String qualificationLevel = rs.getString("qualificationLevel");
                String department = rs.getString("department");
                recruits.add(new RecruitModel(id, fullName, address, phoneNo, email,
                        userName, pwd, interviewDate, qualificationLevel, department));
            }
            return recruits;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DashboardDTO> getManagementDashboard() {
        List<DashboardDTO> dashbords = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT department,count(id) as no_of_recruits FROM recruit WHERE department is not null GROUP BY department";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String department = rs.getString("department");
                int noOfRecruits = rs.getInt("no_of_recruits");
                dashbords.add(new DashboardDTO(department, noOfRecruits));
            }
            return dashbords;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RecruitModel> getRecruitsOrderWithFullName() {
        List<RecruitModel> recruits = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM recruit ORDER BY fullName,address";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNo = rs.getString("phoneNo");
                String email = rs.getString("email");
                String userName = rs.getString("userName");
                String pwd = rs.getString("password");
                String interviewDate = rs.getString("interviewDate");
                String qualificationLevel = rs.getString("qualificationLevel");
                String department = rs.getString("department");
                recruits.add(new RecruitModel(id, fullName, address, phoneNo, email,
                        userName, pwd, interviewDate, qualificationLevel, department));
            }
            return recruits;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RecruitModel> getRecruitsOrderWithQualification() {
        List<RecruitModel> recruits = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM recruit ORDER BY qualificationLevel";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNo = rs.getString("phoneNo");
                String email = rs.getString("email");
                String userName = rs.getString("userName");
                String pwd = rs.getString("password");
                String interviewDate = rs.getString("interviewDate");
                String qualificationLevel = rs.getString("qualificationLevel");
                String department = rs.getString("department");
                recruits.add(new RecruitModel(id, fullName, address, phoneNo, email,
                        userName, pwd, interviewDate, qualificationLevel, department));
            }
            return recruits;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AdminModel getAdminByUserNameAndPassword(String paramUserName, String password) {
        AdminModel admin = null;
        String sql = "SELECT * FROM admin WHERE userName = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paramUserName);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNo = rs.getString("phoneNo");
                String email = rs.getString("email");
                String userName = rs.getString("userName");
                String pwd = rs.getString("password");
                String level = rs.getString("level");
                String branch = rs.getString("branch");
                admin = new AdminModel(id, fullName, address, phoneNo, email, userName, pwd, level, branch);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public List<ManagementModel> getManagements() {
        try (Statement stmt = connection.createStatement()) {
            List<ManagementModel> managments = new ArrayList<>();
            String sql = "SELECT * FROM management";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNo = rs.getString("phoneNo");
                String email = rs.getString("email");
                String userName = rs.getString("userName");
                String pwd = rs.getString("password");
                String position = rs.getString("position");
                managments.add(new ManagementModel(id, fullName, address, phoneNo, email, userName, pwd, position));
            }
            return managments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ManagementModel getManagementByUserNameAndPassword(String paramUserName, String password) {
        ManagementModel management = null;
        String sql = "SELECT * FROM management WHERE userName = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paramUserName);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String fullName = rs.getString("fullName");
                    String address = rs.getString("address");
                    String phoneNo = rs.getString("phoneNo");
                    String email = rs.getString("email");
                    String userName = rs.getString("userName");
                    String pwd = rs.getString("password");
                    String position = rs.getString("position");
                    management = new ManagementModel(id, fullName, address, phoneNo, email, userName, pwd, position);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return management;
    }

    public RecruitModel getRecruitByUserNameAndPassword(String paramUserName, String password) {
        RecruitModel recruit = null;
        String sql = "SELECT * FROM recruit WHERE userName = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paramUserName);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String fullName = rs.getString("fullName");
                    String address = rs.getString("address");
                    String phoneNo = rs.getString("phoneNo");
                    String email = rs.getString("email");
                    String userName = rs.getString("userName");
                    String pwd = rs.getString("password");
                    String interviewDate = rs.getString("interviewDate");
                    String qualificationLevel = rs.getString("qualificationLevel");
                    String department = rs.getString("department");
                    recruit = new RecruitModel(id, fullName, address, phoneNo, email,
                            userName, pwd, interviewDate, qualificationLevel, department);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recruit;
    }

    public RecruitModel getRecruitByUserName(String paramUserName) {
        RecruitModel recruit = null;
        String sql = "SELECT * FROM recruit WHERE userName = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paramUserName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                String phoneNo = rs.getString("phoneNo");
                String email = rs.getString("email");
                String userName = rs.getString("userName");
                String pwd = rs.getString("password");
                String interviewDate = rs.getString("interviewDate");
                String qualificationLevel = rs.getString("qualificationLevel");
                String department = rs.getString("department");
                recruit = new RecruitModel(id, fullName, address, phoneNo, email,
                        userName, pwd, interviewDate, qualificationLevel, department);
                return recruit;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertManagement(ManagementModel management) throws SQLException {
        String sql = "INSERT INTO management (fullName, address, phoneNo, email, userName, password, position) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, management.getFullName());
            pstmt.setString(2, management.getAddress());
            pstmt.setString(3, management.getPhoneNo());
            pstmt.setString(4, management.getEmail());
            pstmt.setString(5, management.getUserName());
            pstmt.setString(6, management.getPassword());
            pstmt.setString(7, management.getPosition());

            pstmt.executeUpdate();
            System.out.println("Management inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateManagement(ManagementModel management) throws SQLException {
        String sql = "UPDATE management SET fullName = ?, address = ?, phoneNo = ?, email = ?, password = ?, position = ? WHERE userName = ? AND id = ?";
        boolean isUpdated = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, management.getFullName());
            pstmt.setString(2, management.getAddress());
            pstmt.setString(3, management.getPhoneNo());
            pstmt.setString(4, management.getEmail());
            pstmt.setString(5, management.getPassword());
            pstmt.setString(6, management.getPosition());
            pstmt.setString(7, management.getUserName());  // Assuming userName is unique and used as a condition
            pstmt.setInt(8, management.getId());  // Assuming id is unique and used as a condition

            int rowsAffected = pstmt.executeUpdate();
            isUpdated = (rowsAffected > 0);

            System.out.println("Update query executed: " + pstmt);
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }

    public void insertRecruit(RecruitModel recruit) throws SQLException {
        String sql = "INSERT INTO recruit (fullName, address, phoneNo, email, userName, password, interviewDate, qualificationLevel) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, recruit.getFullName());
            pstmt.setString(2, recruit.getAddress());
            pstmt.setString(3, recruit.getPhoneNo());
            pstmt.setString(4, recruit.getEmail());
            pstmt.setString(5, recruit.getUserName());
            pstmt.setString(6, recruit.getPassword());
            pstmt.setString(7, recruit.getInterviewDate());
            pstmt.setString(8, recruit.getQualificationLevel());

            pstmt.executeUpdate();
            System.out.println("Recruit inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateRecruit(RecruitModel recruit) {
        String sql = "UPDATE recruit SET fullName = ?, address = ?, phoneNo = ?, email = ?, "
                + "userName =?, password = ?, interviewDate = ?, qualificationLevel = ?, department = ? WHERE id = ?";
        boolean isUpdated = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, recruit.getFullName());
            pstmt.setString(2, recruit.getAddress());
            pstmt.setString(3, recruit.getPhoneNo());
            pstmt.setString(4, recruit.getEmail());
            pstmt.setString(5, recruit.getUserName());
            pstmt.setString(6, recruit.getPassword());
            pstmt.setString(7, recruit.getInterviewDate());
            pstmt.setString(8, recruit.getQualificationLevel());
            pstmt.setString(9, recruit.getDepartment());
            pstmt.setInt(10, recruit.getId());

            int rowsAffected = pstmt.executeUpdate();
            isUpdated = (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean addOTP(int recruitId, String provideOTP) {
        String sql = "UPDATE recruit SET otp = ? WHERE id = ?";
        boolean isUpdated = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, provideOTP);
            pstmt.setInt(2, recruitId);

            int rowsAffected = pstmt.executeUpdate();
            isUpdated = (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }

    public void createManagementTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            // Check if the table exists
            ResultSet resultSet = stmt.executeQuery("SHOW TABLES LIKE 'management'");
            String sql = "UPDATE management SET fullName = ?, address = ?, phoneNo = ?, email = ?, password = ?, position = ? WHERE userName = ? AND id = ?";
            if (!resultSet.next()) {
                // Table does not exist, create it
                stmt.executeUpdate("CREATE TABLE management ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "fullName VARCHAR(255) NOT NULL,"
                        + "address VARCHAR(255),"
                        + "phoneNo VARCHAR(20),"
                        + "email VARCHAR(255),"
                        + "password VARCHAR(100),"
                        + "position VARCHAR(100),"
                        + "userName VARCHAR(100)"
                        + ")");
                System.out.println("Management table created successfully.");
            } else {
                System.out.println("Management table already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createRecruitsTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            // Check if the table exists
            ResultSet resultSet = stmt.executeQuery("SHOW TABLES LIKE 'recruit'");
            if (!resultSet.next()) {
                // Table does not exist, create it
                stmt.executeUpdate("CREATE TABLE recruit ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "fullName VARCHAR(255) NOT NULL,"
                        + "address VARCHAR(255),"
                        + "phoneNo VARCHAR(20),"
                        + "email VARCHAR(255),"
                        + "userName VARCHAR(100),"
                        + "password VARCHAR(100),"
                        + "interviewDate VARCHAR(100),"
                        + "qualificationLevel VARCHAR(100),"
                        + "department VARCHAR(100),"
                        + "otp VARCHAR(100)"
                        + ")");
                System.out.println("Recruits table created successfully.");
                uploadRecruitsDataFromCSV();
            } else {
                System.out.println("Recruits table already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createAdminTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            // Check if the table exists
            ResultSet resultSet = stmt.executeQuery("SHOW TABLES LIKE 'admin'");
            if (!resultSet.next()) {
                // Table does not exist, create it
                stmt.executeUpdate("CREATE TABLE admin ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "fullName VARCHAR(255) NOT NULL,"
                        + "address VARCHAR(255),"
                        + "phoneNo VARCHAR(20),"
                        + "email VARCHAR(255),"
                        + "userName VARCHAR(100),"
                        + "password VARCHAR(100),"
                        + "level VARCHAR(100),"
                        + "branch VARCHAR(100)"
                        + ")");
                System.out.println("Admin table created successfully.");
                uploadAdminDataFromCSV();
            } else {
                System.out.println("Admin table already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void uploadRecruitsDataFromCSV() throws ParseException {
        String sql = "INSERT INTO recruit (fullName, address, phoneNo, email, userName, password, interviewDate, qualificationLevel, department) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader("recruits.csv")); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            String line;
            br.readLine(); // Skip header line if exists
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Set values to prepared statement
                pstmt.setString(1, data[0]); // fullName
                pstmt.setString(2, data[1]); // address
                pstmt.setString(3, data[2]); // phoneNo
                pstmt.setString(4, data[3]); // email
                pstmt.setString(5, data[4]); // userName
                pstmt.setString(6, data[5]); // password
                pstmt.setString(7, data[6]); // interviewDate
                pstmt.setString(8, data[7]); // qualificationLevel
                pstmt.setString(9, data[8]); // department

                pstmt.addBatch();
            }

            int[] batchResult = pstmt.executeBatch();
            System.out.println("Uploaded " + batchResult.length + " records from CSV to recruits table.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void uploadAdminDataFromCSV() {
        String sql = "INSERT INTO admin (fullName, address, phoneNo, email, userName, password, level, branch) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader("staff.csv")); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            String line;
            br.readLine(); // Skip header line if exists
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Set values to prepared statement
                pstmt.setString(1, data[0]); // fullName
                pstmt.setString(2, data[1]); // address
                pstmt.setString(3, data[2]); // phoneNo
                pstmt.setString(4, data[3]); // email
                pstmt.setString(5, data[4]); // userName
                pstmt.setString(6, data[5]); // password
                pstmt.setString(7, data[6]); // level
                pstmt.setString(8, data[7]); // branch

                pstmt.addBatch();
            }

            int[] batchResult = pstmt.executeBatch();
            System.out.println("Uploaded " + batchResult.length + " records from CSV to admin table.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
