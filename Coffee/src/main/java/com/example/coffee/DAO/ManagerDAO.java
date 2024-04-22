package com.example.coffee.DAO;

import com.example.coffee.DTO.MenuData;
import com.example.coffee.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.coffee.constant.constants.ICON_COFFEE;

public class ManagerDAO {
    private static ManagerDAO instance;
    private PreparedStatement pst;
    private ResultSet rs;
    public static ManagerDAO getInstance() {
        if (instance == null)
            instance = new ManagerDAO();
        return instance;
    }
    public void MenuManager(ActionEvent event) throws IOException {
        URL url = Paths.get("src/main/resources/com/example/coffee/rsMenuManager.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Coffee Manegement");
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(ICON_COFFEE));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void LoadCbbTableFood(ObservableList dtCbbTableFood, ComboBox cbbTableFood){
        try {
            pst = DBConnection.connection().prepareStatement("select name from table_food");
            rs = pst.executeQuery();
            while(rs.next()){
                dtCbbTableFood.add(rs.getString("name"));
            }
            pst.close();
            rs.close();
        }catch (SQLException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }
        cbbTableFood.setItems(dtCbbTableFood);
    }
    public int CountTableFood() throws SQLException {
        String query = "select count(*) from table_food";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    public int getIdBill(int idTable) throws SQLException {
        if(ManagerDAO.getInstance().CountRowBill(idTable) > 0){
            String query = "select * from bill where id_table = " + idTable + "and status = 0";
            pst = DBConnection.connection().prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();
            int idBill = 0;
            idBill = rs.getInt(1);
            return idBill;
        }
        return -1;
    }
    public int CountRowBill(int id) throws SQLException {
        String query = "select count(*) from bill where id_table = " + id + " and status = 0";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    public void InsertBill(int id) throws SQLException {
        pst = DBConnection.connection().prepareStatement("exec USP_InsertBill @idTable = " + id );
        pst.executeUpdate();
        pst.close();
    }
    public void InsertBillInfo(int idBill, int idFood, double count) throws SQLException {
        pst = DBConnection.connection().prepareStatement("exec USP_InsertBillInfo @idBill = " + idBill + "," +
                " @idFood = " + idFood + ", @count = " + count);
        pst.executeUpdate();
        pst.close();
    }
    public void LoadTable(int id, TableView tbvMenumanager, ObservableList<MenuData> mnTable, TextField tfTotalPrice) throws SQLException {
        try {
            tbvMenumanager.getItems().clear();
            double total = 0;
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            pst = DBConnection.connection().prepareStatement("select f.name, bi.count, f.price, f.price * bi.count as totalPrice\n" +
                    "from dbo.bill_info as bi, dbo.bill as b, dbo.food as f\n" +
                    "where bi.id_bill = b.id and bi.id_food = f.id and b.status = 0 and b.id_table = " + id);
            rs = pst.executeQuery();
            while (rs.next()){
                mnTable.add(new MenuData(rs.getString(1), rs.getInt(2),rs.getDouble(3), rs.getDouble(4)));
                total += rs.getDouble(4);
            }
            tfTotalPrice.setText(formatter.format(total));
            pst.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tbvMenumanager.setItems(mnTable);
    }
    public void UpdateBill( int id, double discount, double totalPrice) throws SQLException {
        String query = "update bill set status = 1, date_check_out = getdate() ,discount = " + discount + "," +
                " total_price = " + totalPrice + " where id = " + id;
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
    }
    public int getMaxIDBill() throws SQLException {
        int idBill;
        pst = DBConnection.connection().prepareStatement("select max(id) from bill");
        rs = pst.executeQuery();
        rs.next();
        if (rs.getInt(1) > 0){
            idBill = rs.getInt(1);
            return idBill;
        }
        pst.close();
        rs.close();
        return 1;
    }
    public int getIDFood(String nameFood) throws SQLException {
        try {
            int idFood;
            pst = DBConnection.connection().prepareStatement("select id from food where name = '" + nameFood + "'");
            rs = pst.executeQuery();
            rs.next();
            idFood = rs.getInt(1);
            pst.close();
            rs.close();
            return idFood;
        }catch (SQLException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }
        return -1;
    }
    public void setAccount(String userName, MenuBar mnb) throws SQLException {
        String query = "select * from account where active = 1";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();

        if (rs.next()){
            userName = rs.getString(2);
            if (rs.getInt(4) == 0){
                mnb.getMenus().get(0).setDisable(true);
            }
        }
        ManagerDAO.getInstance().UpdateAccount(userName);

    }
    public void UpdateAccount(String userName) throws SQLException {
        String query = "update account set active = 0 where user_name = '" + userName + "'";
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();

    }
    public void getFoodName(int id, ObservableList dtFoods, ComboBox cbboxFoods) throws SQLException {
        try {
            pst = DBConnection.connection().prepareStatement("select name from food where id_category = " + id);
            rs = pst.executeQuery();
            while(rs.next()){
                dtFoods.add(rs.getString("name"));
            }
            pst.close();
            rs.close();
        }catch (SQLException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }

        cbboxFoods.setItems(dtFoods);
    }
    public void selectcbbTableFood(ComboBox cbbTableFood, int idTable2) throws SQLException {
        String nameTable = String.valueOf(cbbTableFood.getValue());
        String query = "select id from table_food where name = N'" + nameTable + "'";
        pst = DBConnection.connection().prepareStatement(query);
        rs = pst.executeQuery();
        rs.next();
        idTable2 = rs.getInt(1);
        pst.close();
    }
    public void FoodCategories(ObservableList dtCategory, ComboBox cbboxFoodCategories) throws SQLException {
        try {
            pst = DBConnection.connection().prepareStatement("select * from food_category");
            rs = pst.executeQuery();
            while(rs.next()){
                dtCategory.add(rs.getString("category_name"));
            }
            pst.close();
            rs.close();
        }catch (SQLException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }
        cbboxFoodCategories.setItems(dtCategory);
    }


    private ManagerDAO(){}
    public static void setInstance(ManagerDAO instance) {
        ManagerDAO.instance = instance;
    }
}
