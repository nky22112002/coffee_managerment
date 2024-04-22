package com.example.coffee.DAO;

import com.example.coffee.DTO.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDAO implements Initializable {
    private static AdminDAO instance;
    private PreparedStatement pst;
    private ResultSet rs;
    private Parent root;
    private Scene scene;
    public static AdminDAO getInstance() {
        if (instance == null)
            instance = new AdminDAO();
        return instance;
    }

    public void MenuAdmin(MenuBar mnb) throws IOException {
        URL url = Paths.get("src/main/resources/com/example/coffee/rsMenuAdmin.fxml").toUri().toURL();
        root = FXMLLoader.load(url);
        Stage stage = (Stage) mnb.getScene().getWindow();
        stage.setTitle("Admin");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                final Stage stage, stage1;
                FXMLLoader pane;
                Parent taskselectwindow = null;

                event.consume();
                stage = (Stage) event.getSource();
                stage.close();
                URL url1 = null;
                try {
                    url1 = Paths.get("src/main/resources/com/example/coffee/rsMenuManager.fxml").toUri().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                pane = new FXMLLoader(url1);
                try {
                    taskselectwindow = (Parent) pane.load();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage1 = new Stage();
                stage1.setScene(new Scene(taskselectwindow));
                stage1.setTitle("Coffee Manager");
                stage1.show();
            }
        });
    }

    public void LoadDataAccount(TableView tbvAccount, ObservableList<AccountData> dtAccount) throws SQLException {
        tbvAccount.getItems().clear();
        pst = DBConnection.connection().prepareStatement("SELECT display_name, user_name, type_account FROM dbo.account");
        rs = pst.executeQuery();
        while (rs.next()){
            if(rs.getInt(3) == 1){
                dtAccount.add(new AccountData(rs.getString(1),rs.getString(2),"Admin"));
            }
            else {
                dtAccount.add(new AccountData(rs.getString(1),rs.getString(2),"Staff"));
            }
        }
        tbvAccount.setItems(dtAccount);
    }
    public void LoadDataFood(TableView tbvFoods, ObservableList<FoodsData> dtFoods) throws SQLException {
        try {
            tbvFoods.getItems().clear();
            pst = DBConnection.connection().prepareStatement("select food.id, food.name, food.price, food_category.category_name\n" +
                    "from food, food_category\n" +
                    "where food.id_category = food_category.id");
            rs = pst.executeQuery();
            while (rs.next()){
                dtFoods.add(new FoodsData(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pst.close();
        rs.close();
        tbvFoods.setItems(dtFoods);
    }
    public void LoadDataTurnover(TableView tbvTurnover, ObservableList<TurnoverData> dtTurnover, LocalDate dateStart, LocalDate dateLast, int pages) throws SQLException {
        try {
            tbvTurnover.getItems().clear();
            pst = DBConnection.connection().prepareStatement("exec USP_GetListBillByDate @checkIn = '" + dateStart
                    + "' ,@checkOut = '" + dateLast + "', @pages = " + pages);
            rs = pst.executeQuery();
            while (rs.next()){
                dtTurnover.add(new TurnoverData(rs.getInt(1),rs.getString(2), rs.getDouble(3), rs.getDate(4), rs.getDate(5),rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pst.close();
        rs.close();
        tbvTurnover.setItems(dtTurnover);
    }
    public void AddAcount(String displayName, String userName, int typeAccount) throws SQLException {
        String query = "exec USP_addAccount @displayName = '" + displayName
                + "' , @userName = " + userName
                + " ,@typeAccount = " + typeAccount;
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public boolean isExistAccount(TextField tfUserNameAccount) throws SQLException {
        String query = "select count(*) from account where user_name = '" + tfUserNameAccount.getText() + "'";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        rs.next();
        if (rs.getInt(1) == 1) {
            return true;
        }
        return false;
    }
    public void RepairAccount(String displayName, int typeAccount, String userName) throws SQLException {
        String query = "update account set display_name = '" + displayName
                + "' , type_account = " + typeAccount
                + " where user_name = '" + userName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public void DeleteAccount(String userName) throws SQLException {
        String query = "delete account where user_name = '" + userName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }



    public boolean isExistFood(TextField tfFoodName, TextField tfPriceFoods) throws SQLException {
        String query = "select count(*) from food where name = '" + tfFoodName.getText() + "'";
        double price = Double.parseDouble(tfPriceFoods.getText());
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        rs.next();
        if (rs.getInt(1) == 1){
            JOptionPane.showMessageDialog(null,"This food is already on the menu","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Successful!!!");
            return false;
        }
    }
    public void AddFoods(String foodName, int ID, double price) throws SQLException {
        String query = "exec USP_addFood @foodName = '" + foodName
                + "' , @idCategory = " + ID
                + " ,@price = " + price;
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public void DeleteFood(String foodName) throws SQLException {
        String query = "delete food where name = '" + foodName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public void SearchFood(String foodName, TableView tbvFoods, ObservableList<FoodsData> dtFoods) throws SQLException {
        tbvFoods.getItems().clear();
        String query = "select food.id, food.name, food.price, food_category.category_name " +
                "from food, food_category" + " " +
                "where name like N'%"+ foodName +"%' and food.id_category = food_category.id";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        while (rs.next()){
            dtFoods.add(new FoodsData(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4)));
        }
        rs.close();
        pst.close();
        tbvFoods.setItems(dtFoods);
    }
    public void RepairFood(int id, String foodName, int idCategory, double price) throws SQLException {
        String query = "update food set name = '" + foodName
                + "' , id_category = " + idCategory
                + " , price = " + price + " where id = " + id;
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }


    public int LoadNumPages() throws SQLException {
        String query = "select count (*) from bill";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public void LoadDataCategory(TableView tbvCategory, ObservableList<CategoryData> dtCategory) throws SQLException {
        try{
            tbvCategory.getItems().clear();

            String query = "select * from food_category";
            pst = DBConnection.connection().prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()){
                dtCategory.add(new CategoryData(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pst.close();
        rs.close();

    }
    public void AddCategory(String categoryName) throws SQLException {
        String query = "exec USP_addCategory @categoryName = '" + categoryName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public void RepairCategory(String categoryName, int id) throws SQLException {
        String query = "USP_repairCategory @categoryName = '" + categoryName + "', @id = " + id;
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public void DeleteCategory(String categoryName) throws SQLException {
        String query = "USP_deleteCategory @categoryName =  '" + categoryName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public void setDataCategory(ComboBox cbbCategoryFood) throws SQLException {
        String query = "select category_name from food_category";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        while(rs.next()){
            cbbCategoryFood.getItems().addAll(rs.getString(1));
        }
        rs.close();
        pst.close();
    }
    public int selectIDCategory(String categoryName) throws SQLException {
        String query = "select * from food_category where category_name = '" + categoryName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        if (rs.next()){
            int id = rs.getInt(1);
            return id;
        }
        else{
            return -1;
        }
    }
    public void isExistCategory(TextField tfCategoryName) throws SQLException {
        String categoryName = tfCategoryName.getText();
        String query = "select count(*) from food_category where category_name = '" + categoryName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        rs.next();
        try{
            if (rs.getInt(1) == 1){
                JOptionPane.showMessageDialog(null,"This category is already on the menu","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                AdminDAO.getInstance().AddCategory(categoryName);
                JOptionPane.showMessageDialog(null, "Successful!!!");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Please enter the full information","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
        }

    }


    public void LoadDataTableFood(TableView tbvTableFood, ObservableList<TableFoodData> dtTableFood) throws SQLException {
        tbvTableFood.getItems().clear();
        String query = "select * from table_food";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        while (rs.next()){
            dtTableFood.add(new TableFoodData(rs.getInt(1),rs.getString(2),rs.getString(3)));
        }
        pst.close();
        rs.close();
        tbvTableFood.setItems(dtTableFood);
    }
    public void RepairTableFood(int id, String tablefoodName) throws SQLException {
        String query = "exec USP_repairTableFood @id = " + id + ", @tablefoodName = '" + tablefoodName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public void DeleteTableFood(String tablefoodName) throws SQLException {
        String query = "delete table_food where name = '" + tablefoodName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }
    public void AddTableFood(String tablefoodName) throws SQLException {
        String query = "exec USP_addTableFood @tablefoodName = '" + tablefoodName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }


    private AdminDAO(){}
    public static void setInstance(AdminDAO instance) {
        AdminDAO.instance = instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
