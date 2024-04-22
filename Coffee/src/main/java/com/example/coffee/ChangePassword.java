package com.example.coffee;

import com.example.coffee.DAO.ChangePasswordDAO;
import com.example.coffee.DAO.LoginDAO;
import com.example.coffee.DAO.ManagerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class ChangePassword {

    @FXML
    private TextField tfUserName;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private PasswordField tfNewPassWord;

    @FXML
    private PasswordField tfRePassWord;

    @FXML
    void ClickOnUpdate(ActionEvent event) throws SQLException, IOException {
        String userName = tfUserName.getText();
        String password = tfPassword.getText();
        String newPassword = tfNewPassWord.getText();
        String re_newPassword = tfRePassWord.getText();
        if (!re_newPassword.equals(newPassword)){
            JOptionPane.showMessageDialog(null, "Re-entered password is incorrect!!!");
        }
        else if ((LoginDAO.getInstance().CheckLogin(userName, password)) == 1){
            ChangePasswordDAO.getInstance().UpdateAccount(userName, password, newPassword);
            JOptionPane.showMessageDialog(null, "Successful!!!");

            //ChangePasswordDAO.getInstance().MenuChangePassword(event)
        }
        else{
            JOptionPane.showMessageDialog(null,"User Name or password is failed!!!","Warning!!!",JOptionPane.INFORMATION_MESSAGE);

        }

    }
    @FXML
    void ClickOnCancel(ActionEvent event) throws IOException {
        //ManagerDAO.getInstance().MenuManager(event);
        JOptionPane.showMessageDialog(null, "See you soon!!");

    }

}
