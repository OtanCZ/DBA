package w32.sqlite;
// Převeďte data z https://onemocneni-aktualne.mzcr.cz/api/v2/covid-19
// (COVID-19: Přehled osob s prokázanou nákazou dle hlášení krajských
// hygienických stanic (v2)).
// K nalezení zde: X:\stemberk\verejne_zaci\osoby.csv
// do databáze (můžete si vybrat mezi SQLite, či MySQL).
// Totéž proveďte pro soubor X:\stemberk\verejne_zaci\staty.csv a zobrazte
// v TableView, případně v nějakém grafu JavaFX.

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Struktura tabulky:
 * id,datum,vek,pohlavi,kraj_nuts_kod,okres_lau_kod,nakaza_v_zahranici,nakaza_zeme_csu_kod,reportovano_khs
 * 1ea976a2-896a-40b2-b617-b780a713323d,2020-03-01,43,M,CZ042,CZ0421,1,IT,1
 */
public class FUkol {
    public static void main(String[] args) throws IOException {
        FileReader fr = null;
        String radka;
        int cnt = 0;
        fr = new FileReader("X:\\stemberk\\verejne_zaci\\osoby.csv");
        BufferedReader br = new BufferedReader(fr);
        br.readLine(); // prvni radku zahodime;

        Connection c = null;
        Statement stmt = null;
        try {
            c = AMainDBConn.connect();
            System.out.println("Database Opened...\n");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS osoby " +
                    "(id VARCHAR, " +
                    "datum TIMESTAMP, " +
                    "vek INTEGER, " +
                    "pohlavi VARCHAR(1)," +
                    "kraj_nuts_kod VARCHAR, " +
                    "okres_lau_kod VARCHAR, " +
                    "nakaza_v_zahranici BIT, " +
                    "nakaza_zeme_csu_kod VARCHAR, " +
                    "reportovano_khs BIT) ";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        while((radka = br.readLine()) != null) {
            String id = "";
            String datum = "";
            int vek = 0;
            String mf = "";
            String kraj = "";
            String okres = "";
            Boolean vZahranici = false;
            String stat = "";
            Boolean reportovanoKhs = false;

            String[] hodnoty = radka.split(",");
            if(hodnoty.length > 0 ) id = hodnoty[0];
            if(hodnoty.length > 1 ) datum = hodnoty[1];
            try{
                if(hodnoty.length > 2 ) vek = Integer.parseInt(hodnoty[2]);
            } catch (Exception x){
                System.out.println(x.getMessage());
            }

            if(hodnoty.length > 3 ) mf = hodnoty[3];
            if(hodnoty.length > 4 ) kraj = hodnoty[4];
            if(hodnoty.length > 5 ) okres = hodnoty[5];
            if(hodnoty.length > 6 ) vZahranici = Boolean.parseBoolean(hodnoty[6]);
            if(hodnoty.length > 7 ) stat = hodnoty[5];
            if(hodnoty.length > 8 ) reportovanoKhs = Boolean.parseBoolean(hodnoty[6]);
            System.out.format("%s, %s, %d, %s, %s, %s, %b, %s, %b%n", id, datum, vek, mf, kraj, okres, vZahranici, stat, reportovanoKhs);
            insert(id, datum, vek, mf, kraj, okres, vZahranici, stat, reportovanoKhs);

            if (cnt++ > 9001) break;

        }
    }

    public static void insert(String id, String datum, double vek, String pohlavi, String kraj_nuts_kod, String okres_lau_kod, boolean nakaza_v_zahranici, String nakaza_zeme_csu_kod, boolean reportovano_khs) {
        String sql = "INSERT INTO osoby(id, datum, vek, pohlavi, kraj_nuts_kod, okres_lau_kod, nakaza_v_zahranici, nakaza_zeme_csu_kod, reportovano_khs) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = AMainDBConn.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, datum);
            pstmt.setDouble(3, vek);
            pstmt.setString(4, pohlavi);
            pstmt.setString(5, kraj_nuts_kod);
            pstmt.setString(6, okres_lau_kod);
            pstmt.setBoolean(7, nakaza_v_zahranici);
            pstmt.setString(8, nakaza_zeme_csu_kod);
            pstmt.setBoolean(9, reportovano_khs);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
