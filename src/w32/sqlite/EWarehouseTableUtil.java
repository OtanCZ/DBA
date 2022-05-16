package w32.sqlite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import w32.Covid;
import w32.Warehouse;

import java.sql.*;
import java.time.LocalDate;

public class EWarehouseTableUtil {
    /* Returns an observable list of persons */
    public static ObservableList<Covid> getWarehouseList() {
        ObservableList<Covid> ret = FXCollections.<Covid>observableArrayList();
        Connection c = AMainDBConn.connect();
        String sql = "SELECT * FROM osoby;";
        try (Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            int cnt = 0;
            while(rs.next()) {
                System.out.println(cnt++);
                //System.out.format("id:%d, name: %s, c:%d%n", rs.getInt("id"), rs.getString("name"), rs.getInt("capacity"));
                //System.out.println(new Warehouse(rs.getInt("id"), rs.getString("name"), rs.getInt("capacity")).toString());
                ret.add(new Covid(rs.getString("id"), rs.getString("datum"), rs.getDouble("vek"), rs.getString("pohlavi"), rs.getString("kraj_nuts_kod"), rs.getString("okres_lau_kod"), rs.getBoolean("nakaza_v_zahranici"), rs.getString("nakaza_zeme_csu_kod"), rs.getBoolean("reportovano_khs")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Warehouse p1 = new Warehouse(0, "Sharan", 4000 );
        return ret; //FXCollections.<Warehouse>observableArrayList(p1);
    }

    /* Returns Id TableColumn */
    public static TableColumn<Covid, String> getIdColumn() {
        TableColumn<Covid, String> personIdCol = new TableColumn<>("id");
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        return personIdCol;
    }
    public static TableColumn<Covid, String> getDatumColumn() {
        TableColumn<Covid, String> personIdCogl = new TableColumn<>("datum");
        personIdCogl.setCellValueFactory(new PropertyValueFactory<>("datum"));
        return personIdCogl;
    }
    public static TableColumn<Covid, String> getPohlaviColumn() {
        TableColumn<Covid, String> personIdCdogl = new TableColumn<>("pohlavi");
        personIdCdogl.setCellValueFactory(new PropertyValueFactory<>("pohlavi"));
        return personIdCdogl;
    }
    public static TableColumn<Covid, Double> getVekColumn() {
        TableColumn<Covid, Double> personIdColt = new TableColumn<>("vek");
        personIdColt.setCellValueFactory(new PropertyValueFactory<>("vek"));
        return personIdColt;
    }
    public static TableColumn<Covid, String> getkraj_nuts_kodColumn() {
        TableColumn<Covid, String> perswonIdCol = new TableColumn<>("kraj_nuts_kod");
        perswonIdCol.setCellValueFactory(new PropertyValueFactory<>("kraj_nuts_kod"));
        return perswonIdCol;
    }
    public static TableColumn<Covid, String> getokres_lau_kodColumn() {
        TableColumn<Covid, String> persdonIdCol = new TableColumn<>("okres_lau_kod");
        persdonIdCol.setCellValueFactory(new PropertyValueFactory<>("okres_lau_kod"));
        return persdonIdCol;
    }
    public static TableColumn<Covid, Boolean> getnakaza_v_zahraniciColumn() {
        TableColumn<Covid, Boolean> persoanIdCol = new TableColumn<>("nakaza_v_zahranici");
        persoanIdCol.setCellValueFactory(new PropertyValueFactory<>("nakaza_v_zahranici"));
        return persoanIdCol;
    }
    public static TableColumn<Covid, String> getnakaza_zeme_csu_kodColumn() {
        TableColumn<Covid, String> personIadCol = new TableColumn<>("nakaza_zeme_csu_kod");
        personIadCol.setCellValueFactory(new PropertyValueFactory<>("nakaza_zeme_csu_kod"));
        return personIadCol;
    }
    public static TableColumn<Covid, Boolean> getreportovano_khsColumn() {
        TableColumn<Covid, Boolean> personreportovano_khsCol = new TableColumn<>("reportovano_khs");
        personreportovano_khsCol.setCellValueFactory(new PropertyValueFactory<>("reportovano_khs"));
        return personreportovano_khsCol;
    }
}
