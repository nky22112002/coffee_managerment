package com.example.coffee;

import com.example.coffee.DAO.DBConnection;
import com.example.coffee.DAO.LoginDAO;
import com.example.coffee.DAO.ManagerDAO;
import com.example.coffee.DAO.RegisterDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    @FXML
    public TextField userName;
    @FXML
    private PasswordField passWord;
    public PreparedStatement pst;
    public ResultSet rs = null;
    @FXML
    void fLogin(ActionEvent event) throws IOException, SQLException {
        String un = userName.getText();
        String pw = passWord.getText();
        if((LoginDAO.getInstance().CheckLogin(un,pw)) == 1){
            LoginDAO.getInstance().UpdateAccountLogin(un);
            ManagerDAO.getInstance().MenuManager(event);
        }
        else {
            JOptionPane.showMessageDialog(null,"User Name or password is failed!!!","Warning!!!",JOptionPane.INFORMATION_MESSAGE);

        }
    }
    @FXML
    void fRegister(ActionEvent event) throws IOException {
        RegisterDAO.getInstance().MenuRegister(event);
    }
}
