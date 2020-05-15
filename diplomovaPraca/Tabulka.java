package diplomovaPraca;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tabulka extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton Insert;
    private JButton Delete;
    private JButton Add;
    private JTextField textField1;
    private JButton okButton;
    private JButton updateSQL;
    private JScrollPane pane;
    private JFrame f;
    private List<Integer> indexyNONE = new ArrayList();
    diplomovaPraca.TableModel model;
    Databaza db;


    private String filterPolozka;
    private int pocetNovych;
    public static int krok;     //staticka premenna pre ucely sekvencneho ukladania dat do tabuliek
    private int[] selection;
    ListVlastnictva lv;
    private TableRowSorter<TableModel> sorter;
    //  String cestaSuboru = "D:\\Danka\\Škola\\5.rocnik\\diplomovka\\lv.txt";
    private List<List<Object>> tabulkaVlastnik;


    public Tabulka(Databaza db) throws SQLException {

        super("Tabulka");
        this.db = db;


        f = new JFrame();
        f.setSize(900, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       // panel1.setAutoscrolls(true);
        f.add(panel1);

        vytvorenieTabulky();


        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int moznost = JOptionPane.showConfirmDialog(null,
                        "Naozaj chcete vymazať riadok v tabuľke?", "Delete",
                        JOptionPane.YES_NO_OPTION);
                if (moznost == 0) {
                    try {
                        deleteRowsFromJTable();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }

        });
        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int moznost = JOptionPane.showConfirmDialog(null, "Naozaj chcete pridat nový riadok/nové riadky do tabuľky?", "Add", JOptionPane.YES_NO_OPTION);
                if (moznost == 0) {
                    insertRowsInJTable();

                }

            }
        });


        /****************INSERT a UPDATE JTABLE******/

        Insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertFromPDFIntoSQL();
                    krok++;
                    f.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });



        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterPolozka = textField1.getText();
                System.out.println("lv je " + filterPolozka);
                if (filterPolozka.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(filterPolozka));   //filtruje vsetky stlpce v riadku
                    //    newFilter();                                            //filtruje len stlpec s cislom LV
                }
            }
        });
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {

                    filterPolozka = textField1.getText();
                    if (filterPolozka.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(filterPolozka));   //filtruje vsetky stlpce v riadku
                        //    newFilter();                                            //filtruje len stlpec s cislom LV
                    }
                }
            }
        });
        updateSQL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int moznost = JOptionPane.showConfirmDialog(null, "Naozaj chcete zmeniť dáta v tabuľke?", "Update", JOptionPane.YES_NO_OPTION);
                if (moznost == 0) {
                    try {
                        updateSQL();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }
                /***NEFUNGUJE**********************************************************/
                else {
                    try {
                        db.getData("select * from " + db.getNazovTabulky() + ";",indexyNONE);
                        //  model = new diplomovaPraca.TableModel(db);
                        //  table1 = new JTable(model);
                        vytvorenieTabulky();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    /***NEFUNGUJE**********************************************************/

                }

            }
        });
    }


    private void insertRowsInJTable()   {
        selection = table1.getSelectedRows();
        for (int i = 0; i<selection.length; i++) {
            //  System.out.println(selection[i]);
            selection[i] = table1.convertRowIndexToModel(selection[i]);
            System.out.println("selection "+selection[i]);
            model.fireTableRowsInserted(selection[i], selection[i]);

        }

    }

    private void deleteRowsFromJTable() throws SQLException {

        int[] selection = table1.getSelectedRows();
        for (int i = 0; i<selection.length; i++) {
            selection[i] = table1.convertRowIndexToModel(selection[i]);

        }   db.deleteSQL(selection);
        model.vymazRiadkovZTabulky(selection);
    }

    private void newFilter()
    {
        RowFilter<TableModel, Object> rf = null;
        //deklaracia row filtra pre tablemodel tabulky
        try
        {
            rf = RowFilter.regexFilter( textField1.getText(),0);  //filtruje len stlpec cisloLV
            //inicializacia cez regex
        }
        catch (java.util.regex.PatternSyntaxException e)
        {
            return;
        }
        sorter.setRowFilter(rf);  //filtrovanie riadku
    }
    private void insertFromPDFIntoSQL() throws SQLException {

        List<Object> riadkyData = new ArrayList<>();
        List<List<Object>> tabulka = new ArrayList<>();
        // String query;
        // query="insert into databaza_pozemky.vlastnik (cisloVlastnik,menoVlastnik,priezviskoVlastnik,rodnePriezviskoVlastnik,inyIdentifikator,datumNarodeniaVlastnik,adresaVlastnik,ICOVlastnik)" + "values (?,?,?,?,?,?,?,?)";

        //  System.out.println(model.getTabulka1());
        // System.out.println(tabulkaVlastnik);
        // db.setTabulkaData(model.getTabulka1());
        if(!db.isDataZDB()) db.insertFromPDF(model.getTabulka1());

        else {
            System.out.println("ahoj"+model.getTabulka1().size());
            for (int i = model.getTabulka1().size()-1; i >=model.getTabulka1().size()-selection.length; i--) {
                for (int j = 0; j < model.getTabulka1().get(1).size(); j++) {
                    riadkyData.add(model.getTabulka1().get(i).get(j));

                }
                tabulka.add(riadkyData);
                riadkyData=new ArrayList<>();
            }

            db.insertFromPDF(tabulka);

        }

    }


    private void updateSQL() throws SQLException {
        List<List<Object>> updateData = new ArrayList<>();
        updateData=model.getUpdatedTabulka();
        System.out.println(model.getUpdatedTabulka());
        System.out.println("UPDATED TABULKA "+model.getUpdatedTabulka());
        if(db.getNazovTabulky().equals("lv_vlastnik"))
        {
            //kalkulaciaPodielov(updateData);
            db.updateSQL(kalkulaciaPodielov(updateData));

        }
        else{db.updateSQL(model.getUpdatedTabulka());}



    }

    private  List<List<Object>> kalkulaciaPodielov( List<List<Object>> data ) {
        Kalkulacie kalkulacie = new Kalkulacie();
        List<Integer> zlomokSucetMenovatelovKupa = new ArrayList<>(); //citatel je 0 polozka a menovatel je 1 polozka
        int citatelPredaj=0,menovatelPredaj=1,citatelKupa=0,menovatelKupa=1;
        zlomokSucetMenovatelovKupa.add((int) 0);
        zlomokSucetMenovatelovKupa.add((int)1);
        //  int citatelSucetPodielovKupa=0;
        //  int menovatelSucetPodielovKupa=0;

        for(int i=0;i<data.size();i++){
            if((data.get(i).get(2).equals("akcia"))&& (data.get(i).get(0).equals("2"))) {

                // citatelPredaj=(int ) db.getTabulkaData().get((int) data.get(i).get(1)).get(5);
                citatelPredaj=(int ) db.getTabulkaData().get((int) data.get(i).get(1)).get(db.najdiIndex("podielCitatel"));

                menovatelPredaj = (int )db.getTabulkaData().get((int) data.get(i).get(1)).get(db.najdiIndex("podielMenovatel"));
                model.setValueAt(0,(int)data.get(i).get(1),db.najdiIndex("novyPodielCitatel"));
                model.fireTableDataChanged();
            }
        }
        for(int i=0;i<data.size();i++){

            if((data.get(i).get(2).equals("akcia"))&& (data.get(i).get(0).equals("1"))) {

                System.out.println("AKCIA  1  Citatel je "+db.getTabulkaData().get((int) data.get(i).get(1)).get(db.najdiIndex("podielCitatel")));
                System.out.println("AKCIA  1  Menovatel je "+db.getTabulkaData().get((int) data.get(i).get(1)).get(db.najdiIndex("podielMenovatel")));
                citatelKupa=(int ) db.getTabulkaData().get((int) data.get(i).get(1)).get(db.najdiIndex("podielCitatel"));
                menovatelKupa = (int )db.getTabulkaData().get((int) data.get(i).get(1)).get(db.najdiIndex("podielMenovatel"));
                //  System.out.println("Zakladny tvar "+kalkulacie.zakladnyTvarZlomku(citatelKupa,menovatelKupa));
                zlomokSucetMenovatelovKupa=kalkulacie.sucetZlomkov(zlomokSucetMenovatelovKupa.get(0),zlomokSucetMenovatelovKupa.get(1),citatelKupa,menovatelKupa);
                System.out.println("Sucet  i=" + i + " podielov AKCIA 1 kupa je "+zlomokSucetMenovatelovKupa);
            }


        }


        for(int i=0;i<data.size();i++) {
            List<Integer> medzivysledok = new ArrayList<>();
            if ((data.get(i).get(2).equals("akcia")) && (data.get(i).get(0).equals("1"))) {
                citatelKupa=(int ) db.getTabulkaData().get((int) data.get(i).get(1)).get(db.najdiIndex("podielCitatel"));
                menovatelKupa = (int )db.getTabulkaData().get((int) data.get(i).get(1)).get(db.najdiIndex("podielMenovatel"));
                medzivysledok = kalkulacie.podielZlomkov(citatelKupa,menovatelKupa,zlomokSucetMenovatelovKupa.get(0),zlomokSucetMenovatelovKupa.get(1));
                System.out.println("MV po podiely i="+i+  "  " +medzivysledok);
                medzivysledok = kalkulacie.sucinZlomkov(citatelPredaj,menovatelPredaj,medzivysledok.get(0),medzivysledok.get(1));
                System.out.println("MV po sucine  i="+i+  "  " +medzivysledok);

                medzivysledok=kalkulacie.sucetZlomkov(citatelKupa,menovatelKupa,medzivysledok.get(0),medzivysledok.get(1));
                System.out.println("Konecny vysledok po sucte i" + i + "  " +medzivysledok);
                model.setValueAt(medzivysledok.get(0),(int)data.get(i).get(1),db.najdiIndex("novyPodielCitatel"));
                model.setValueAt(medzivysledok.get(1),(int)data.get(i).get(1),db.najdiIndex("novyPodielMenovatel"));
                model.fireTableDataChanged();

            }
        }
        // int i =11;

        return data;
    }


    private void vytvorenieTabulky() throws SQLException {

        model = new TableModel(db);
        sorter = new TableRowSorter<>(model);

        table1.setModel(model);
        table1.setRowSorter(sorter);

        pane.setSize(500,300);
        for(int i=0;i<db.getColumnNames().size();i++){
           if (db.getColumnNames().get(i).equals("akcia")){


               TableColumn stlpecAkcia = table1.getColumnModel().getColumn(i);
               JComboBox<String> comboBoxAkcia = new JComboBox<>();
               comboBoxAkcia.addItem("0");
               comboBoxAkcia.addItem("1");
               comboBoxAkcia.addItem("2");

               stlpecAkcia.setCellEditor(new DefaultCellEditor(comboBoxAkcia));


           }


        }

        table1.setDefaultRenderer(String.class, new TableModel.VypisRetezca());
        table1.setDefaultRenderer(java.util.Date.class, model.tableCellRenderer);
        table1.setDefaultEditor(java.util.Date.class, new diplomovaPraca.TableModel.DatumEditor());
        for(int i=0;i<db.getCols();i++) {
            if (db.getColumnsType().get(i).equals("DATE"))
                table1.getColumnModel().getColumn(i).setCellRenderer(model.tableCellRenderer);
        }

        table1.setDefaultRenderer(Integer.class, new diplomovaPraca.TableModel.VypisInteger());
        table1.setDefaultRenderer(Double.class, new diplomovaPraca.TableModel.VypisDouble());

    }

}




