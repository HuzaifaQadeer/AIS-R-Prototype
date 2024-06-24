package com.ais.tcpserver;
// Importing required classes for networking and I/O operations

import com.ais.model.AdminModel;
import com.ais.model.ManagementModel;
import com.ais.model.RecruitModel;
import com.ais.tcpserver.util.DBManager;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class representing the TCP server.
 */
public class TCPServer {

    /**
     * The main method that starts the server.
     *
     * @param args Command line arguments (not used in this example).
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String args[]) throws NoSuchAlgorithmException {
        DBManager dbManager = null;
        try {
            // Setting up the server socket to listen on port 6789
            int serverPort = 2112;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            dbManager = new DBManager();

            System.out.println(
                    "-----------------------------------");
            System.out.println(
                    "Server listening on port " + serverPort + " for object transfer...");
            System.out.println(
                    "-----------------------------------");

            // Continuously accepting incoming client connections
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection con = new Connection(clientSocket, dbManager);
                con.getName();
            }
        } catch (IOException | SQLException e) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, "Listen socket: {0}", e.getMessage());
        } finally {
            if (dbManager != null) {
                dbManager.close();
            }
        }
    }
}

/**
 * A thread representing a connection with a client.
 */
class Connection extends Thread {

    DataInputStream in;
    ObjectInputStream objIn;
    ObjectOutputStream out;
    private DBManager dbManager;

    // Socket representing the client connection
    Socket clientSocket;

    /**
     * Constructor for the Connection thread.
     *
     * @param aClientSocket The socket representing the client connection.
     */
    public Connection(Socket aClientSocket, DBManager db) {
        try {
            // Initializing input and output streams for the client connection
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            objIn = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            dbManager = db;

            // Starting the thread
            this.start();
        } catch (IOException e) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Connection: {0}", e.getMessage());
        }
    }

    /**
     * The main run method for the Connection thread.
     */
    @Override
    public void run() {
        try {
            String requestType = in.readUTF();
            Object obj = objIn.readObject();
            AdminModel admin;
            ManagementModel management;
            RecruitModel recruit;
            switch (requestType) {
                case "REGISTER":
                    if (obj instanceof AdminModel) {
                        admin = (AdminModel) obj;
                        dbManager.insertAdmin(admin);
                    } else if (obj instanceof ManagementModel) {
                        management = (ManagementModel) obj;
                        dbManager.insertManagement(management);
                    } else if (obj instanceof RecruitModel) {
                        recruit = (RecruitModel) obj;
                        dbManager.insertRecruit(recruit);
                        out.writeUTF("SUCCESS");
                    } else {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Unknown object received");
                        throw new UnknownError();
                    }
                    break;
                case "UPDATE":
                    if (obj instanceof AdminModel) {
                        admin = (AdminModel) obj;
                        dbManager.updateAdmin(admin);
                    } else if (obj instanceof ManagementModel) {
                        management = (ManagementModel) obj;
                        dbManager.updateManagement(management);
                    } else if (obj instanceof RecruitModel) {
                        recruit = (RecruitModel) obj;
                        dbManager.updateRecruit(recruit);
                        out.writeUTF("SUCCESS");
                    } else {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Unknown object received");
                        throw new UnknownError();
                    }
                    break;
                case "GET_RECRUITS":
                    out.writeObject(dbManager.getRecruits());
                    out.flush();
                    break;
                case "GET_MANAGEMENTS":
                    out.writeObject(dbManager.getManagements());
                    out.flush();
                    break;
                case "MANAGEMENTS_DASHBOARD":
                    out.writeObject(dbManager.getManagementDashboard());
                    out.flush();
                    break;
                case "SEND_OTP":
                    recruit = (RecruitModel) obj;
                    dbManager.addOTP(recruit.getId(), recruit.getOtp());
                    break;
                case "GET_RECRUIT_USER_NAME":
                    recruit = (RecruitModel) obj;
                    recruit = dbManager.getRecruitByUserName(recruit.getUserName());
                    out.writeObject(recruit);
                    out.flush();
                    break;
                case "GET_RECRUITS_ORDER_BY_FULLNAME":
                    out.writeObject(dbManager.getRecruitsOrderWithFullName());
                    out.flush();
                    break;
                case "GET_RECRUITS_ORDER_BY_QUALIFICATION":
                    out.writeObject(dbManager.getRecruitsOrderWithQualification());
                    out.flush();
                    break;
                case "LOGIN_ADMIN":
                    admin = (AdminModel) obj;
                    admin = dbManager.getAdminByUserNameAndPassword(
                            admin.getUserName(), admin.getPassword());
                    out.writeObject(admin);
                    out.flush();
                    break;
                case "LOGIN_MANAGEMENT":
                    management = (ManagementModel) obj;
                    management = dbManager.getManagementByUserNameAndPassword(
                            management.getUserName(), management.getPassword());
                    out.writeObject(management);
                    out.flush();
                    break;
                case "LOGIN_RECRUIT":
                    recruit = (RecruitModel) obj;
                    recruit = dbManager.getRecruitByUserNameAndPassword(
                            recruit.getUserName(), recruit.getPassword());
                    out.writeObject(recruit);
                    out.flush();
                    break;
                default:
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Unknown request type: {0}", requestType);
            }

        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "IO Exception: {0}", ex.getMessage());
        } catch (SQLException e) {
            try {
                out.writeUTF("FAIL");
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "SQL exception: {0}", e.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            try {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "Class not found: {0}", ex.getMessage());
                // Sending a response to the client indicating class not found
                out.writeUTF("CLASS_NOT_FOUND");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, "IO Exception: {0}", ex1);
            }
        }
    }
}
