package com.example.coffee;
import com.example.coffee.DAO.RegisterDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Register implements Initializable{

    @FXML
    private TextField tfDisplayName;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private PasswordField tfRe_Password;

    @FXML
    private TextField tfUserName;

    @FXML
    private RadioButton checkBoxStaff = new RadioButton("Staff");

    @FXML
    private RadioButton checkBoxAdmin = new RadioButton("Admin");


    @FXML
    void ClickButtonRegister() throws SQLException, IOException {
        int rdButtonPosition = RegisterDAO.getInstance().RadioButtonPosition(checkBoxStaff, checkBoxAdmin);
        String displayName = tfDisplayName.getText();
        String userName = tfUserName.getText();
        String password = tfPassword.getText();
        String re_password = tfRe_Password.getText();

        RegisterDAO.getInstance().ButtonRegister(displayName, userName, password, rdButtonPosition, re_password);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


