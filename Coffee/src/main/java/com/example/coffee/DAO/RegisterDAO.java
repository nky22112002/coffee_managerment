package com.example.coffee.DAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDAO {
    private static RegisterDAO instance;
    private Stage stage;

    public static RegisterDAO getInstance() {
        if (instance == null)
            instance = new RegisterDAO();
        return instance;
    }
    public void MenuRegister(ActionEvent event) throws IOException {
        URL url = Paths.get("src/main/resources/com/example/coffee/rsRegister.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Register");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public int RadioButtonPosition(RadioButton checkBoxStaff, RadioButton checkBoxAdmin){
        ToggleGroup group = new ToggleGroup();
        checkBoxStaff.setToggleGroup(group);
        checkBoxStaff.setSelected(true);
        checkBoxAdmin.setToggleGroup(group);
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        if (selected.getText().contains("Staff"))
            return 0;
        return 1;
    }
    public int CheckUserName(String userName) throws SQLException {
        String sql = "Select count(*) from account where user_name = N'" + userName + "'";
        PreparedStatement pst = DBConnection.connection().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    public void ButtonRegister(String tfDisplayName, String tfUserName, String tfPassword, int rdButtonPosition, String tfRe_Password) throws SQLException, IOException {
        String sql = "exec USP_Register @displayName = N'" + tfDisplayName
                + "', @userName = N'" + tfUserName
                + "', @password = '" + tfPassword
                + "',@typeAccount = " + rdButtonPosition;
        PreparedStatement pst = DBConnection.connection().prepareStatement(sql);
        if (tfRe_Password.equals(tfPassword)) {
            if (RegisterDAO.getInstance().CheckUserName(tfUserName) == 1) {
                JOptionPane.showMessageDialog(null,
                        "USER NAME ALREADY EXISTS!!!", "Warning!!!", JOptionPane.INFORMATION_MESSAGE);

            } else {
                pst.executeUpdate();
                DBConnection.connection().commit();
                JOptionPane.showMessageDialog(null, "REGISTER IS SUCCESS!!!",
                        "Congratulations", JOptionPane.INFORMATION_MESSAGE);

                this.stage.close();
                LoginDAO.getInstance().MenuLogin();

            }

        }
        else{
            JOptionPane.showMessageDialog(null, "PASSWORD ARE NOT THE SAME",
                    "Warning!!!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private RegisterDAO(){}
    public static void setInstance(RegisterDAO instance) {
        RegisterDAO.instance = instance;
    }
}
