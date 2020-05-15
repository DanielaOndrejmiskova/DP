package diplomovaPraca;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Databaza {

    private   List<String> columns_config = new ArrayList<>();
    private   ResultSetMetaData rsmd;
    private int cols;
    private String nazovTabulky;
    private List<String> columnNames;
    private List<String> columnsType;
    private List<List<Object>> tabulkaData;
    private boolean dataZDB=true;
    ResultSet rs = null;
    Statement stmt = null;
    Connection connection=null;
    PreparedStatement preparedStmt=null;
    String nazovServera = "localhost:3306";  //host a port
    String nazovDatabazy = "databaza_pozemky";
   Object primaryKeyColumns;

    private  String url = "jdbc:mysql://" + nazovServera + "/" + nazovDatabazy + "?useUnicode=yes&characterEncoding=UTF-8";
    private String user = "root";  //diplomovka
    private String heslo = "Regulator*51";  //Danielka18!



    String PKName;
    int indexPK;

    public List<Integer> getIndexyNeeditovatelne() {
        return indexyNeeditovatelne;
    }

    private List<Integer> indexyNeeditovatelne = new ArrayList<>();


  //  final public HashMap<String, String> menaStlpcov = new HashMap<String, String>();
      final public HashMap<String, String> menaStlpcov = new HashMap<String, String>();

    private List<Object> risultato;


    public List<Object> getRisultato() {
        return risultato;
    }




    public Databaza(boolean dataZDB, String nazovTabulky) throws SQLException {
        this.dataZDB=dataZDB;
        this.nazovTabulky=nazovTabulky;
       nacitanieMetaData();
       nazvoslovie();

    }

    public boolean isDataZDB() {
        return dataZDB;
    }

    public HashMap<String, String> getMenaStlpcov() {


        return menaStlpcov;
    }

    public void setTabulkaData(List<List<Object>> tabulkaData) {
        this.tabulkaData = tabulkaData;
    }
    public int getCols() {
        if(isDataZDB())   return cols;
                 else return cols-1;   //plati pre nacitanie mimo databazy bez PK, lebo z getterov nedostaneme PK, az z DB
    }

    public List<List<Object>> getTabulkaData() {
      //  System.out.println(tabulkaData);
        return tabulkaData;
    }

    public String getNazovTabulky() {
        return nazovTabulky;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<String> getColumnsType() {
        return columnsType;
    }



    private void nacitanieMetaData()   throws SQLException {


            try{ Class.forName("com.mysql.jdbc.Driver"); }   //1.krok je pripojit sa na databazu

            catch(Exception e){ System.out.println("Chyba driveru");

                System.out.println(e.toString());

            }

            try {
                connection = DriverManager.getConnection(url, user, heslo);
                stmt = connection.createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_READ_ONLY);

                rs = stmt.executeQuery("select * from "+nazovTabulky +";");
                rsmd = rs.getMetaData();

                columnNames = new ArrayList<>();
                columnsType = new ArrayList<>();
              // rs = stmt.getGeneratedKeys();
                nazovTabulky = rsmd.getTableName(1);
                DatabaseMetaData metaData = connection.getMetaData();
                //Retrieving the columns in the database
                ResultSet rs = metaData.getPrimaryKeys("databaza_pozemky", null, nazovTabulky);
                while (rs.next()) {
                    System.out.println("Table name: " + rs.getString("TABLE_NAME"));
                    PKName =rs.getString("COLUMN_NAME");
                    System.out.println("Column name: " + rs.getString("COLUMN_NAME"));
                    System.out.println("Catalog name: " + rs.getString("TABLE_CAT"));
                    System.out.println("Primary key sequence: " + rs.getString("KEY_SEQ"));
                    System.out.println("Primary key name: " + rs.getString("PK_NAME"));
                    System.out.println(" ");

                }
                cols = rsmd.getColumnCount();
                    if(isDataZDB()) {

                        for(int i=1;i<=cols;i++){
                            columnNames.add(rsmd.getColumnName(i));
                            if(PKName==rsmd.getColumnName(i)) indexPK=i-1;
                            columnsType.add(rsmd.getColumnTypeName(i));
                        }

                    }
                    else {

                        for(int i=2;i<=cols;i++){
                            columnNames.add(rsmd.getColumnName(i));
                            columnsType.add(rsmd.getColumnTypeName(i));
                        }
                    }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vynimka - zlyhanie pripojenia k databaze");
        }
        finally {

            stmt.close();

        }
    }


    // query="insert into databaza_pozemky.vlastnik (cisloVlastnik,menoVlastnik,priezviskoVlastnik,rodnePriezviskoVlastnik,inyIdentifikator,datumNarodeniaVlastnik,adresaVlastnik,ICOVlastnik) values (?,?,?,?,?,?,?,?)";


      public List<Object> insertFromPDF(List<List<Object>> insertedTable) throws SQLException {
 risultato = new ArrayList<>();

        try {
            String query="";

            connection = DriverManager.getConnection(url, user, heslo);

            query = "insert into "+nazovTabulky+ " (";
            for(int i=0;i<columnNames.size();i++) {
               if(i==columnNames.size()-1){ query=query.concat(columnNames.get(i));
                   query=query.concat(") values (");}

                else {
                   query=query.concat(columnNames.get(i));
                   query=query.concat(",");
                }

            }
            for(int i=0;i<columnNames.size();i++) {
                if (i == columnNames.size() - 1) {
                    query=query.concat("?)");
                } else {
                    query= query.concat("?,");
                }
            }
            System.out.println(query);
                preparedStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);


             //   preparedStmt.setObject(2, tabulkaData.get((int) insertedTable.get(i).get(1)).get(indexPK));
                //  preparedStmt.setObject(2,  updatedTable.get(i).get(1));
             //   System.out.println(query);

           for (int i = 0; i < insertedTable.size(); i++) {
            for (int j = 0; j < insertedTable.get(0).size(); j++) {

                    preparedStmt.setObject(j+1, insertedTable.get(i).get(j));
                    System.out.println(insertedTable.get(i).get(j));

                }
                preparedStmt.executeUpdate();

               ResultSet rss = preparedStmt.getGeneratedKeys();
               if (rss.next()){
                   risultato.add(rss.getObject(1));
                   System.out.println("pk=DDDDDDDDDDDDDDDDDDDDDDDDDDDD  "+risultato+"  ");

               }

                }
      }
        catch (SQLException se)
                {
                    se.printStackTrace();

                }
        finally
                {
                    // close the statement when all the INSERT's are finished
                    preparedStmt.close();
                    connection.close();
                    return risultato;
                }
            }

    public void updateSQL(List<List<Object>> updatedTable) throws SQLException {


        try {
            String query="";
            connection = DriverManager.getConnection(url, user, heslo);
            for(int i=0;i<updatedTable.size();i++) {
                query = "update "+nazovTabulky+ " set "+ updatedTable.get(i).get(2)+" = ? where "+PKName+" = ?";
                preparedStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setObject(1, updatedTable.get(i).get(0));
                preparedStmt.setObject(2, tabulkaData.get((int) updatedTable.get(i).get(1)).get(indexPK));
              //  preparedStmt.setObject(2,  updatedTable.get(i).get(1));
                System.out.println(query);
                System.out.println(updatedTable.get(i).get(0));
                System.out.println(updatedTable.get(i).get(1));
                System.out.println( tabulkaData.get((int) updatedTable.get(i).get(1)).get(indexPK));
                preparedStmt.executeUpdate();

            }

        }

        catch (SQLException se)
        {
            se.printStackTrace();

        }
        finally
        {
            // close the statement when all the INSERT's are finished
            preparedStmt.close();
            connection.close();

        }
    }

    public void deleteSQL(int[] rowsDeleted) throws SQLException {

        try {
            String query="";

            //pripojenie sa na databázu
            connection = DriverManager.getConnection(url, user, heslo);

            for(int i=0;i<rowsDeleted.length;i++) {

            //vytvorenie query
                query = "delete from "+nazovTabulky+ " where "+ PKName+ " = ?";

            //pouzitie prepared statement
                preparedStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setObject(1, tabulkaData.get(rowsDeleted[i]).get(indexPK));

             //vykonanie príkazu cez executeUpdate()
                preparedStmt.executeUpdate();

            }

        }

        catch (SQLException se)
        {
            se.printStackTrace();

        }
        finally
        {
            // uzatvorenie zdrojov
            preparedStmt.close();
            connection.close();

        }
    }






    //RETRIEVE DATA
    public List<List<Object>> getData(String query, List<Integer> indexyNeeditovatelne) throws SQLException {
        this.indexyNeeditovatelne = indexyNeeditovatelne;
        List<Object> rowData = new ArrayList<>();
        tabulkaData = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url, user, heslo);
            preparedStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            rs = preparedStmt.executeQuery(query);
            rsmd = rs.getMetaData();
            cols = rsmd.getColumnCount();
            nazovTabulky = rsmd.getTableName(1);

            columnNames = new ArrayList<>(cols);
            columnsType = new ArrayList<>(cols);
            for(int i=1;i<=cols;i++){

                columnNames.add(rsmd.getColumnName(i));
                columnsType.add(rsmd.getColumnTypeName(i));
            }
            while (rs.next()) {
                //ziskanie hodnot
                for(int i=1;i<=cols;i++) {
                    rowData.add(rs.getObject(i));

                }

                tabulkaData.add(rowData);
                rowData = new ArrayList<>();
            }
            System.out.println( columnNames);
            System.out.println( columnsType);
            System.out.println( tabulkaData);



        } catch (Exception ex) {
            ex.printStackTrace();
        }

    return tabulkaData;

    }

private void nazvoslovie() {
    menaStlpcov.put("IDLV", "ID listu vlastníctva");
    menaStlpcov.put("cisloLV", "Číslo listu vlastníctva");
    menaStlpcov.put("katastralneUzemieCislo", "Číslo katastrálneho územia");
    menaStlpcov.put("katastralneUzemieNazov", "Názov katastrálneho územia");
    menaStlpcov.put("okresCislo", "Číslo okresu");
    menaStlpcov.put("okresNazov", "Názov okresu");
    menaStlpcov.put("obecCislo", "Číslo obce");
    menaStlpcov.put("obecNazov", "Názov obce");
    menaStlpcov.put("datumVyhotovenia", "Dátum vyhotovenia");
    menaStlpcov.put("casVyhotovenia", "Čas vyhotovenia");
    menaStlpcov.put("platnostLV", "Údaje platné k");
    menaStlpcov.put("pocetParciel", "Počet parciel");
    menaStlpcov.put("pocetSpravcov", "Počet správcov");
    menaStlpcov.put("pocetVlastnikov", "Počet vlastníkov");
    menaStlpcov.put("IDParcela", "ID parcely");
    menaStlpcov.put("typParcely", "Typ parcely");
    menaStlpcov.put("parcelneCislo", "Parcelné číslo");
    menaStlpcov.put("vymera", "Výmera");
    menaStlpcov.put("druhPozemku", "Druh pozemku");
    menaStlpcov.put("povodneKU", "Pôvodné katastrálne územie");
    menaStlpcov.put("sposobVyuzitia", "Spôsob využívania pozemku");
    menaStlpcov.put("druhCHN", "Druh chránenej nehnuteľnosti");
    menaStlpcov.put("spolocnaNehnutelnost", "Spoločná nehnuteľnosť");
    menaStlpcov.put("umiestneniePozemku", "Umiestnenie pozemku");
    menaStlpcov.put("druhPV", "Druh právneho vzťahu");
    menaStlpcov.put("IDVlastnik", "ID vlastníka");
    menaStlpcov.put("cisloVlastnik", "Číslo vlastníka");
    menaStlpcov.put("menoVlastnik", "Meno vlastníka");
    menaStlpcov.put("priezviskoVlastnik", "Priezvisko vlastníka");
    menaStlpcov.put("rodnePriezviskoVlastnik", "Rodné priezvisko vlastníka");
    menaStlpcov.put("inyIdentifikator", "Iný identifikátor");
    menaStlpcov.put("datumNarodeniaVlastnik", "Dátum narodenia vlastníka");
    menaStlpcov.put("adresaVlastnik", "Adresa vlastníka");
    menaStlpcov.put("ICOVlastnik", "IČO vlastníka");
    menaStlpcov.put("ID_ST", "ID spájacej tabuľky");
    menaStlpcov.put("titulNadobudnutia", "Titul nadobudnutia");
    menaStlpcov.put("ineUdaje", "Iné údaje");
    menaStlpcov.put("poznamky", "Poznámky");
    menaStlpcov.put("akcia", "Akcia");
    menaStlpcov.put("podielCitatel", "Čitateľ zlomku");
    menaStlpcov.put("podielMenovatel", "Menovateľ zlomku");
    menaStlpcov.put("novyPodielCitatel", "Nový čitateľ zlomku");
    menaStlpcov.put("novyPodielMenovatel", "Nový menovateľ zlomku");
    menaStlpcov.put("plomba", "Plomba");


}
    public int najdiIndex(String nazovStlpca){
        int j=-1;
        for(int i=0;i<getColumnNames().size();i++){

            if(nazovStlpca.equals(getColumnNames().get(i))){
                j=i;
                break;

            }

        }

        return j;
    }

    }


