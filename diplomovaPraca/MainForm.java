package diplomovaPraca;

import org.apache.tika.exception.TikaException;

import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainForm  extends JFrame {


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /********************************************* listener pre menu  item **********************************/
    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //System.out.println("****Selected: " + e.getActionCommand());
            try {
                menu_events(e.getActionCommand());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private JMenuItem z1;
    private JMenuItem z2;
    private JMenuItem vy4;
    private JPanel panel;
    private JButton importTXT;
    private JLabel label4;
    private JButton potvrdCisloLV;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBo;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JComboBox comboBox5;
    private JLabel label5;
    private JComboBox comboBox6;
    private JLabel label6;
    private JComboBox comboBox4;
    private JComboBox comboBox7;
    private JLabel label7;
    String cestaSuboru;
    List<List<Object>> parcely;
    List<List<Object>> vlastnici;
    List<Object> LVvseobecne;
    Databaza db;
    Databaza dbLV;
    Databaza dbVLastnik;
    Databaza dbParcela;
    Databaza dbLVVlastnik;
    int cols;
    Object[] columnsName;
    List<String> columns_config = new ArrayList<>();
    ResultSetMetaData rsmd;
    String nazovTabulky;
    String zatlaceneMenu="";
    private boolean priznak = false;
    private boolean priznakICO = false;
    //  private JTextField textField1;
    //  WordDocument doc = new WordDocument();
    private DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

    public List<Object> getCislaLvZmluvy() {
        return cislaLvZmluvy;
    }

    // private DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
    private List<Object> cislaLvZmluvy = new ArrayList<>();
    ListVlastnictva lv;
    Tabulka sk;
    private List<Integer> indexyNONE = new ArrayList<>();
    Set<Object> set= new HashSet<>();

    public MainForm() throws SQLException {


        JMenuBar mb = new JMenuBar();  //vytvorenie JMenuBar
        JMenu importLV = new JMenu("Import");  //vytvorenie JMenu

        // vytvorenie  poloziek  menu typu JMenuItem
        JMenuItem v1 = new JMenuItem("Import txt");
        JMenuItem v2 = new JMenuItem("Import pdf");
        JMenuItem v3 = new JMenuItem("Import xlsx");

        importLV.setMnemonic(KeyEvent.VK_I);
        v1.addActionListener(new MenuActionListener());  //vytvorenie listenerov
        v2.addActionListener(new MenuActionListener());
        v3.addActionListener(new MenuActionListener());

        importLV.add(v1);   //pridanie poloziek do JMenu
        importLV.add(v2);
        importLV.add(v3);

        mb.add(importLV);   //pridanie JMenu do JMenuBar


        JMenu zobrazenie = new JMenu("Zobrazenie");

        JMenuItem p1 = new JMenuItem("Listy vlastníctva");
        JMenuItem p2 = new JMenuItem("Vlastníci");
        JMenuItem p3 = new JMenuItem("Parcely");
        JMenuItem p4 = new JMenuItem("Vlastníci a LV");

        p1.addActionListener(new MenuActionListener());
        p2.addActionListener(new MenuActionListener());
        p3.addActionListener(new MenuActionListener());
        p4.addActionListener(new MenuActionListener());

        zobrazenie.setMnemonic(KeyEvent.VK_Z);
        zobrazenie.add(p1);
        zobrazenie.add(p2);
        zobrazenie.add(p3);
        zobrazenie.add(p4);

        mb.add(zobrazenie);

        JMenu zmluvy = new JMenu("Zmluvy");

        z1 = new JMenuItem("Kalkulácie");
        z2 = new JMenuItem("Export");
        z1.setEnabled(false);
        z2.setEnabled(false);

        zmluvy.setMnemonic(KeyEvent.VK_M);
        z1.addActionListener(new MenuActionListener());
        z2.addActionListener(new MenuActionListener());


        zmluvy.add(z1);
        zmluvy.add(z2);

        mb.add(zmluvy);

        JMenu vyhladavanie = new JMenu("Vyhľadávanie");
        JMenuItem vy1 = new JMenuItem("Hľadaj list vlastníctva");
        JMenuItem vy2 = new JMenuItem("Hľadaj vlastníka");
        JMenuItem vy3 = new JMenuItem("Hľadaj parcelu");
        //vy4 = new JMenuItem("Hľadaj vlastníka a LV");

        vy1.addActionListener(new MenuActionListener());
        vy2.addActionListener(new MenuActionListener());
        vy3.addActionListener(new MenuActionListener());
      //  vy4.addActionListener(new MenuActionListener());

        vyhladavanie.setMnemonic(KeyEvent.VK_V);
        vyhladavanie.add(vy1);
        vyhladavanie.add(vy2);
        vyhladavanie.add(vy3);
      //  vyhladavanie.add(vy4);

        mb.add(vyhladavanie);

        JMenu logOut = new JMenu("Používateľ");  //vytvorenie JMenu
        JMenuItem logoutItem = new JMenuItem("Odhlásiť sa");
        logOut.setMnemonic(KeyEvent.VK_O);
        logoutItem.addActionListener(new MenuActionListener());  //vytvorenie listenerov
        logOut.add(logoutItem);
        mb.add(logOut);   //pridanie JMenu do JMenuBar


        setJMenuBar(mb);

        add(panel);







        importTXT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sk.krok == 0) {
                    try {
                        nacitajLV();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                if (sk.krok == 1) {
                    try {


                        nacitajVlastnika();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                if (sk.krok == 2) {
                    try {


                        nacitajParcelu();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                if (sk.krok == 3) {
                    try {


                        nacitajLVVlastnik();
                        importTXT.setVisible(false);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e);
                //System.out.println(comboBox1.getSelectedItem());


                try {
                    if(zatlaceneMenu.equals("Hľadaj list vlastníctva")) {
                        comboBox5.setVisible(false);
                        comboBox4.setVisible(false);
                        label5.setVisible(false);
                        label4.setVisible(false);
                        db.getData("SELECT DISTINCT lv.obecNazov \n" +
                                " FROM  list_vlastnictva lv \n" +
                                " WHERE lv.okresNazov = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);
                    }
                    if(zatlaceneMenu.equals("Hľadaj vlastníka")) {
                        comboBox5.setVisible(false);
                        comboBox4.setVisible(true);
                        label5.setVisible(false);
                        label4.setVisible(true);
                        db.getData("SELECT DISTINCT v.menoVlastnik \n" +
                                " FROM  vlastnik v \n" +
                                " WHERE v.priezviskoVlastnik = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);

                    }
                    if(zatlaceneMenu.equals("Hľadaj parcelu")) {
                        comboBox5.setVisible(true);
                        comboBox4.setVisible(true);
                        label5.setVisible(true);
                        label4.setVisible(true);

                        db.getData("SELECT DISTINCT lv.obecNazov \n" +
                                " FROM  list_vlastnictva lv \n" +
                                " WHERE lv.okresNazov = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);
                    }
                    //System.out.println("velkost "+db.getTabulkaData().size());


                    comboBox2.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                    comboBox2.setSelectedIndex(0);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e);
                //System.out.println(comboBox2.getSelectedItem());

                try {


                    if(zatlaceneMenu.equals("Hľadaj list vlastníctva")) {
                        comboBox5.setVisible(false);
                        comboBox4.setVisible(false);
                        label5.setVisible(false);
                        label4.setVisible(false);
                        db.getData("SELECT DISTINCT lv.katastralneUzemieNazov \n" +
                                " FROM  list_vlastnictva lv \n" +
                                " WHERE lv.okresNazov = " +   "'"+comboBox1.getSelectedItem() +"'" +
                                " AND lv.obecNazov = " +   "'"+comboBox2.getSelectedItem() +   "'"+";",indexyNONE);
                        comboBox3.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                        comboBox3.setSelectedIndex(0);

                    }
                    if(zatlaceneMenu.equals("Hľadaj vlastníka")) {
                        comboBox5.setVisible(false);
                        comboBox4.setVisible(true);
                        label5.setVisible(false);
                        label4.setVisible(true);
                        db.getData("SELECT DISTINCT v.datumNarodeniaVlastnik \n" +
                                " FROM  vlastnik v \n" +
                                " WHERE v.menoVlastnik = " +   "'"+comboBox2.getSelectedItem() +   "'"+
                                " AND v.priezviskoVlastnik = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);


                        if((db.getTabulkaData().get(0).get(0))!=(null)){
                            System.out.println("tabulka "+db.getTabulkaData());
                            comboBox3.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                            comboBox3.setSelectedIndex(0);
                            priznak=false;
                        }

                        else {
                            priznak = true;
                            DefaultComboBoxModel model = new DefaultComboBoxModel();
                            model.removeAllElements();
                            // comboBox3.setModel(new MyComboBoxModel(db));
                            comboBox3.setModel(model);
                            //  comboBox3.setSelectedIndex(0);
                        }


                        db.getData("SELECT DISTINCT v.ICOVlastnik \n" +
                                " FROM  vlastnik v \n" +
                                " WHERE v.menoVlastnik = " +   "'"+comboBox2.getSelectedItem() +   "'"+
                                " AND v.priezviskoVlastnik = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);


                        if((db.getTabulkaData().get(0).get(0))!=(null)){
                            System.out.println("tabulka "+db.getTabulkaData());
                            comboBox4.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                            comboBox4.setSelectedIndex(0);
                            priznakICO=false;
                        }

                        else {
                            priznakICO = true;
                            DefaultComboBoxModel model = new DefaultComboBoxModel();
                            model.removeAllElements();
                            // comboBox5.setModel(new MyComboBoxModel(db));
                            comboBox4.setModel(model);
                            //  comboBox5.setSelectedIndex(0);
                        }


                    }



                    if(zatlaceneMenu.equals("Hľadaj parcelu")) {
                        comboBox5.setVisible(true);
                        comboBox4.setVisible(true);
                        label5.setVisible(true);
                        label4.setVisible(true);
                        db.getData("SELECT DISTINCT lv.katastralneUzemieNazov \n" +
                                " FROM  list_vlastnictva lv \n" +
                                " WHERE lv.okresNazov = " +   "'"+comboBox1.getSelectedItem() +"'" +
                                " AND lv.obecNazov = " +   "'"+comboBox2.getSelectedItem() +   "'"+";",indexyNONE);
                        comboBox3.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                        comboBox3.setSelectedIndex(0);
                    }

                    //   System.out.println("SELECT DISTINCT lv.obecNazov \n" + "FROM  list_vlastnictva lv \n" + "WHERE lv.okresNazov = " +   "'"+comboBox1.getSelectedItem() +   "'"+";");




                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });


        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e);
                //System.out.println(comboBox3.getSelectedItem());

                try {

                    if(zatlaceneMenu.equals("Hľadaj list vlastníctva")) {
                        comboBox5.setVisible(false);
                        comboBox4.setVisible(false);
                        label5.setVisible(false);
                        label4.setVisible(false);
                        db.getData("SELECT DISTINCT lv.cisloLV \n" +
                                " FROM  list_vlastnictva lv \n" +
                                " WHERE lv.okresNazov = " +   "'"+comboBox1.getSelectedItem() +"'" +
                                " AND lv.obecNazov = " +   "'"+comboBox2.getSelectedItem() +   "'"+
                                " AND lv.katastralneUzemieNazov = " +   "'"+comboBox3.getSelectedItem() +   "'"+";",indexyNONE);
                        potvrdCisloLV.setVisible(true);


                        comboBox6.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                        comboBox6.setSelectedIndex(0);
                    }
                    if(zatlaceneMenu.equals("Hľadaj vlastníka")) {
                        comboBox5.setVisible(false);
                        comboBox4.setVisible(true);
                        label5.setVisible(false);
                        label4.setVisible(true);


                     if (priznak==false) {
                            db.getData("SELECT DISTINCT lv.cisloLV \n" +
                                    " FROM  vlastnik v, lv_vlastnik lvv,  list_vlastnictva lv \n" +
                                    " WHERE lvv.IDVlastnik = v.IDVlastnik  \n" +
                                    " AND lvv.IDLV = lv.IDLV  \n" +
                                    " AND v.datumNarodeniaVlastnik = " + "'" + comboBox3.getSelectedItem() + "'" +
                                    " AND v.menoVlastnik = " + "'" + comboBox2.getSelectedItem() + "'" +
                                    " AND v.priezviskoVlastnik = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);
                            System.out.println("ds");


                     }

                         if (priznak==true ) {
                            db.getData("SELECT DISTINCT lv.cisloLV \n" +
                                    " FROM  vlastnik v, lv_vlastnik lvv,  list_vlastnictva lv \n" +
                                    " WHERE lvv.IDVlastnik = v.IDVlastnik  \n" +
                                    " AND lvv.IDLV = lv.IDLV  \n" +
                                    " AND v.menoVlastnik = " + "'" + comboBox2.getSelectedItem() + "'" +
                                    " AND v.priezviskoVlastnik = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);

                        }
                }
                    potvrdCisloLV.setVisible(true);

                    comboBox6.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                    comboBox6.setSelectedIndex(0);


                    if(zatlaceneMenu.equals("Hľadaj parcelu")) {
                        comboBox5.setVisible(true);
                        comboBox4.setVisible(true);
                        label5.setVisible(true);
                        label4.setVisible(true);
                        db.getData("SELECT DISTINCT p.typParcely \n" +
                                " FROM  parcela p, list_vlastnictva lv \n" +
                                " WHERE lv.okresNazov = " + "'" + comboBox1.getSelectedItem() + "'" +
                                " AND lv.obecNazov = " +   "'"+comboBox2.getSelectedItem() +   "'"+
                                " AND lv.IDLV = p.IDLV " +
                                " AND lv.katastralneUzemieNazov = " +   "'"+comboBox3.getSelectedItem() +   "'"+";",indexyNONE);

                        comboBox4.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                        comboBox4.setSelectedIndex(0);
                    }




                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });

        comboBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e);
                //System.out.println(comboBox2.getSelectedItem());

                try {
                    if(zatlaceneMenu.equals("Hľadaj vlastníka")) {
                        comboBox5.setVisible(false);
                        comboBox4.setVisible(true);
                        label5.setVisible(false);
                        label4.setVisible(true);

                    if (priznakICO==false) {
                        //  System.out.println("asdfsaf");
                        db.getData("SELECT DISTINCT lv.cisloLV \n" +
                                " FROM  vlastnik v, list_vlastnictva lv, lv_vlastnik lvvl \n" +
                                " WHERE v.menoVlastnik = " + "'" + comboBox2.getSelectedItem() + "'" +
                                " AND v.ICOVlastnik = " + "'" + comboBox4.getSelectedItem() + "'" +
                                " AND v.IDVlastnik = lvvl.IDVlastnik " +
                                " AND lv.IDLV = lvvl.IDLV " +
                                " AND v.priezviskoVlastnik = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);

                    }


                    if (priznakICO==true&&priznak==true) {

                                db.getData("SELECT DISTINCT lv.cisloLV \n" +
                                        " FROM  vlastnik v, list_vlastnictva lv, lv_vlastnik lvvl \n" +
                                        " WHERE v.menoVlastnik = " + "'" + comboBox2.getSelectedItem() + "'" +
                                        " AND v.IDVlastnik = lvvl.IDVlastnik " +
                                        " AND lv.IDLV = lvvl.IDLV " +
                                        " AND v.priezviskoVlastnik = " + "'" + comboBox1.getSelectedItem() + "'" + ";",indexyNONE);


                    }
                        potvrdCisloLV.setVisible(true);

                        comboBox6.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                        comboBox6.setSelectedIndex(0);


                    }



                    if(zatlaceneMenu.equals("Hľadaj parcelu")) {
                        comboBox5.setVisible(true);
                        comboBox4.setVisible(true);
                        label5.setVisible(true);
                        label4.setVisible(true);
                        db.getData("SELECT DISTINCT p.parcelneCislo \n" +
                                " FROM  parcela p, list_vlastnictva lv \n" +
                                " WHERE lv.okresNazov = " + "'" + comboBox1.getSelectedItem() + "'" +
                                " AND lv.obecNazov = " +   "'"+comboBox2.getSelectedItem() +   "'"+
                                " AND lv.IDLV = p.IDLV \n" +
                                " AND p.typParcely = " +   "'"+comboBox4.getSelectedItem() +   "'"+
                                " AND lv.katastralneUzemieNazov = " +   "'"+comboBox3.getSelectedItem() +   "'"+";",indexyNONE);

                        comboBox5.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                        comboBox5.setSelectedIndex(0);



                    }

                    //   System.out.println("SELECT DISTINCT lv.obecNazov \n" + "FROM  list_vlastnictva lv \n" + "WHERE lv.okresNazov = " +   "'"+comboBox1.getSelectedItem() +   "'"+";");




                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });

        comboBox5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e);
                //System.out.println(comboBox2.getSelectedItem());

                try {

                    if(zatlaceneMenu.equals("Hľadaj parcelu")) {
                        comboBox5.setVisible(true);
                        comboBox4.setVisible(true);
                        label5.setVisible(true);
                        label4.setVisible(true);
                        db.getData("SELECT DISTINCT lv.cisloLV \n" +
                                " FROM  parcela p, list_vlastnictva lv\n" +
                                " WHERE lv.okresNazov = " + "'" + comboBox1.getSelectedItem() + "'" +
                                " AND lv.IDLV = p.IDLV " +
                                " AND lv.obecNazov = " +   "'"+comboBox2.getSelectedItem() +   "'"+
                                " AND p.typParcely = " +   "'"+comboBox4.getSelectedItem() +   "'"+
                                " AND p.parcelneCislo = " +   "'"+comboBox5.getSelectedItem() +   "'"+
                                " AND lv.katastralneUzemieNazov = " +   "'"+comboBox3.getSelectedItem() +   "'"+";",indexyNONE);

                        potvrdCisloLV.setVisible(true);

                        comboBox6.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
                        comboBox6.setSelectedIndex(0);






                    }

                    //   System.out.println("SELECT DISTINCT lv.obecNazov \n" + "FROM  list_vlastnictva lv \n" + "WHERE lv.okresNazov = " +   "'"+comboBox1.getSelectedItem() +   "'"+";");




                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });

        potvrdCisloLV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    z1.setEnabled(true);


                    db.getData("SELECT lv.cisloLV, lv.IDLV "+
                            "FROM list_vlastnictva lv\n" +
                            " WHERE lv.cisloLV = " +   "'"+ comboBox6.getSelectedItem() + "'"+";",indexyNONE);


                    for(int i=0;i<db.getTabulkaData().size();i++){

                        cislaLvZmluvy.add(db.getTabulkaData().get(i).get(1));
                       // set.add(db.getTabulkaData().get(i).get(1));
                    }



                    //System.out.println("AHOJ "+cislaLvZmluvy);


                    db.getData("SELECT lv.cisloLV, p.parcelneCislo, p.vymera, p.druhPozemku, p.sposobVyuzitia, p.spolocnaNehnutelnost, p.umiestneniePozemku, p.druhPV, lvlast.novyPodielCitatel, lvlast.novyPodielMenovatel, v.menoVlastnik,v.priezviskoVlastnik, v.datumNarodeniaVlastnik, v.adresaVlastnik\n" +
                            "FROM vlastnik v, parcela p, lv_vlastnik lvlast, list_vlastnictva lv\n" +
                            "WHERE lv.IDLV=p.IDLV\n" +
                            "AND lvlast.IDLV=lv.IDLV\n" +
                            "AND lvlast.IDVlastnik=v.IDVlastnik\n" +
                            " AND lv.cisloLV = " +   "'"+ comboBox6.getSelectedItem() +   "'"+";",indexyNONE);

                    setSize(500,500);
                    comboBox7.setVisible(true);
                    label7.setVisible(true);
                    model.addElement(comboBox6.getSelectedItem().toString());
                    comboBox7.setModel(model);
                   // FormTables ft = new FormTables(db);
                    Tabulka sk = new Tabulka(db);


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        comboBox7.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
               if (e.getKeyChar()==127) {

                   // System.out.println(comboBox7.getSelectedIndex());
                  if(comboBox7.getSelectedIndex()>=0) model.removeElementAt(comboBox7.getSelectedIndex());




               }
            }
        });
    }


    public void menu_events(String menu_zatl) throws Exception {
        //  System.out.println("zatlacene tlacitko menu  " + menu_zatl);
        zatlaceneMenu = menu_zatl;
        switch (menu_zatl) {
            case "Import txt":
                menuImportTxt();
                importTXT.setVisible(true);
                break;
            case "Import pdf":
                menuImportPdf();
                importTXT.setVisible(true);
                break;
            case "Import xlsx":
                menuImportXLSX();
                importTXT.setVisible(true);
                break;
            case "Vlastníci":
                menu_selectFromSQL("databaza_pozemky.vlastnik");
                break;
            case "Listy vlastníctva":
                menu_selectFromSQL("databaza_pozemky.list_vlastnictva");
                break;
            case "Parcely":
                menu_selectFromSQL("databaza_pozemky.parcela");
                break;
            case "Vlastníci a LV":
                menu_selectFromSQL("databaza_pozemky.lv_vlastnik");
                break;
            case "Kalkulácie":

              kalkulaciePodielov();

                break;
            case "Export":
                WordDoc doc = new WordDoc(db,cislaLvZmluvy);
                break;
            case "Hľadaj list vlastníctva":
                menu_search();
                break;
            case "Hľadaj vlastníka":
                menu_search();
                break;
            case "Hľadaj parcelu":
                menu_search();
                break;
            case "Odhlásiť sa":

                this.dispose();
                Login login =new Login();
                break;

        }
    }

    private void kalkulaciePodielov() throws SQLException {

        db = new Databaza(true, "databaza_pozemky.lv_vlastnik");
        String query ="";

        query="SELECT lvlast.ID_ST,lv.cisloLV, v.IDVlastnik, lvlast.podielCitatel, lvlast.podielMenovatel,lvlast.novyPodielCitatel, lvlast.novyPodielMenovatel, v.menoVlastnik,v.priezviskoVlastnik, v.datumNarodeniaVlastnik, v.adresaVlastnik,lvlast.akcia\n" +
        " FROM vlastnik v, lv_vlastnik lvlast, list_vlastnictva lv\n" +
        " WHERE lvlast.IDLV=lv.IDLV\n" +
        " AND lvlast.IDVlastnik=v.IDVlastnik\n"+
        " AND ( ";
        for(int i=0;i<cislaLvZmluvy.size()-1;i++) {

           query=query.concat("lv.IDLV= "+ "' "+cislaLvZmluvy.get(i).toString() +  "' "+" OR ");

        }



            query=query.concat("lv.IDLV= "+ "' "+ cislaLvZmluvy.get(cislaLvZmluvy.size()-1).toString() + "' " + ");");

        System.out.println(query);

        indexyNONE.add(0);
        indexyNONE.add(1);
        indexyNONE.add(2);
        indexyNONE.add(7);
        indexyNONE.add(8);
        indexyNONE.add(9);
        indexyNONE.add(10);
        db.getData(query,indexyNONE);
       // FormTables formTables = new FormTables(db);
        Tabulka sk = new Tabulka(db);
        z2.setEnabled(true);





    }



    private List<Object> nacitajLV() throws SQLException {
        List<Object> PK = new ArrayList<>();
        List<List<Object>> tabulkaPomocna = new ArrayList<>();
        List<Object> pomocnyRiadok = new ArrayList<>();

        List<List<Object>> tabulkaLV;
        pomocnyRiadok = new ArrayList<>();

        pomocnyRiadok.add(lv.getCisloLV());
        pomocnyRiadok.add(lv.getKatastralneUzemieCislo());
        pomocnyRiadok.add(lv.getKatastralneUzemieNazov());
        pomocnyRiadok.add(lv.getOkresCislo());
        pomocnyRiadok.add(lv.getOkresNazov());
        pomocnyRiadok.add(lv.getObecCislo());
        pomocnyRiadok.add(lv.getObecNazov());
        pomocnyRiadok.add(lv.getDatumVyhotovenia());
        pomocnyRiadok.add(lv.getCasVyhotovenia());
        pomocnyRiadok.add(lv.getPlatnostLV());
        pomocnyRiadok.add(lv.getPocetParciel());
        pomocnyRiadok.add(lv.getPocetSpravcov());
        pomocnyRiadok.add(lv.getPocetVlastnikov());
        pomocnyRiadok.add(lv.getTarchy());

        tabulkaLV = new ArrayList<>();
        tabulkaLV.add(pomocnyRiadok);
        System.out.println("PR je " + pomocnyRiadok);
        System.out.println("Tabulka LV je " + tabulkaLV);

        dbLV = new Databaza(false, "databaza_pozemky.list_vlastnictva");
        dbLV.setTabulkaData(tabulkaLV);
         sk = new Tabulka(dbLV);

        return PK;
    }

    private void nacitajVlastnika() throws SQLException {
        List<List<Object>> tabulkaPomocna = new ArrayList<>();
        List<Object> pomocnyRiadok = new ArrayList<>();
        /*******nacitanie udajov z txt do tabulky vlastnik*************************************/

        tabulkaPomocna.add((List) lv.getIDVlastnik());
        tabulkaPomocna.add((List) lv.getMenoVlastnika());
        tabulkaPomocna.add((List) lv.getPriezviskoVlastnika());
        tabulkaPomocna.add((List) lv.getRodnePriezviskoVlastnika());
        tabulkaPomocna.add((List) lv.getInyIdentifikator());
        tabulkaPomocna.add((List) lv.getDatumNarodenia());
        tabulkaPomocna.add((List) lv.getAdresaV());
        tabulkaPomocna.add((List) lv.getICO());
        List<List<Object>> tabulkaVlastnik = new ArrayList<>(tabulkaPomocna.size());

        for (int i = 0; i < tabulkaPomocna.get(1).size(); i++) {
            for (int j = 0; j < tabulkaPomocna.size(); j++) {
                pomocnyRiadok.add(tabulkaPomocna.get(j).get(i));

            }
            tabulkaVlastnik.add(pomocnyRiadok);
            pomocnyRiadok = new ArrayList<>();
        }
        dbVLastnik = new Databaza(false, "databaza_pozemky.vlastnik");
        dbVLastnik.setTabulkaData(tabulkaVlastnik);

         sk = new Tabulka(dbVLastnik);


    }


    private void nacitajParcelu() throws SQLException {

        /*******nacitanie udajov z txt do tabulky parcela*************************************/
        List<List<Object>> tabulkaPomocna = new ArrayList<>();
        tabulkaPomocna = new ArrayList<>();
        List<List<Object>> tabulkaParcela;
        List<Object> pomocnyRiadok = new ArrayList<>();

        tabulkaPomocna.add((List) lv.getTypParcely());
        tabulkaPomocna.add((List) lv.getParcelneCislo());
        tabulkaPomocna.add((List) lv.getVymera());
        tabulkaPomocna.add((List) lv.getDruhPozemku());
        tabulkaPomocna.add((List) lv.getPovodneKU());
        tabulkaPomocna.add((List) lv.getSposobVyuzitia());
        tabulkaPomocna.add((List) lv.getDruhCHN());
        tabulkaPomocna.add((List) lv.getSpolocnaNehnutelnost());
        tabulkaPomocna.add((List) lv.getUmiestneniePozemku());
        tabulkaPomocna.add((List) lv.getDruhPV());


        System.out.println("PK je " + dbLV.getRisultato());
        tabulkaParcela = new ArrayList<>(tabulkaPomocna.size());


        for (int i = 0; i < tabulkaPomocna.get(1).size(); i++) {
            for (int j = 0; j < tabulkaPomocna.size(); j++) {
                pomocnyRiadok.add(tabulkaPomocna.get(j).get(i));

            }
            pomocnyRiadok.add(dbLV.getRisultato().get(0));
            tabulkaParcela.add(pomocnyRiadok);
            pomocnyRiadok = new ArrayList<>();
        }
        dbParcela = new Databaza(false, "databaza_pozemky.parcela");
        dbParcela.setTabulkaData(tabulkaParcela);

        sk = new Tabulka(dbParcela);
        System.out.println(sk.krok);


    }

    private void nacitajLVVlastnik() throws SQLException {

        /*******nacitanie udajov z txt do spajacej tabulky*************************************/
        List<List<Object>> tabulkaPomocna = new ArrayList<>();
        tabulkaPomocna = new ArrayList<>();
        List<List<Object>> tabulkaLVVlastnik;
        List<Object> pomocnyRiadok = new ArrayList<>();

        tabulkaPomocna.add((List) lv.getTitulNadobudnutia());
        tabulkaPomocna.add((List) lv.getIneUdaje());
        tabulkaPomocna.add((List) lv.getPoznamky());
        tabulkaPomocna.add((List) lv.getAkcia());
        tabulkaPomocna.add((List) lv.getPodielCitatel());
        tabulkaPomocna.add((List) lv.getPodielMenovatel());
        tabulkaPomocna.add((List) lv.getNovyPodielCitatel());
        tabulkaPomocna.add((List) lv.getNovyPodielMenovatel());
        tabulkaPomocna.add((List) lv.getPlomba());


        System.out.println("PK je " + dbLV.getRisultato());
        tabulkaLVVlastnik = new ArrayList<>(tabulkaPomocna.size());


        for (int i = 0; i < tabulkaPomocna.get(1).size(); i++) {
            for (int j = 0; j < tabulkaPomocna.size(); j++) {
                pomocnyRiadok.add(tabulkaPomocna.get(j).get(i));

            }
            pomocnyRiadok.add(dbLV.getRisultato().get(0));
            pomocnyRiadok.add(dbVLastnik.getRisultato().get(i));
            System.out.println(dbLV.getRisultato().get(0));
            System.out.println(dbVLastnik.getRisultato().get(i));
            tabulkaLVVlastnik.add(pomocnyRiadok);
            pomocnyRiadok = new ArrayList<>();
        }

        dbLVVlastnik = new Databaza(false, "databaza_pozemky.lv_vlastnik");
        dbLVVlastnik.setTabulkaData(tabulkaLVVlastnik);

       // ft = new FormTables(dbLVVlastnik);
        sk = new Tabulka(dbLVVlastnik);
        System.out.println(sk.krok);


    }


    private void menuImportTxt() throws SQLException, TikaException, IOException, SAXException {
        sk.krok = 0;

        cestaSuboru = JOptionPane.showInputDialog("Zadajte cestu txt súboru", "C:\\Users\\igoro\\OneDrive\\Desktop\\dp_definitiva\\lv857.txt");
        lv = new ListVlastnictva(cestaSuboru,"txt");


/*******nacitanie udajov z txt do tabulky List vlastnictva*************************************/


    }


        private void menuImportPdf() throws TikaException, IOException, SAXException, SQLException {
            sk.krok = 0;

            cestaSuboru = JOptionPane.showInputDialog("Zadajte cestu pdf súboru",
                    "C:\\Users\\igoro\\OneDrive\\Desktop\\dp_definitiva\\lv332.pdf");
            lv = new ListVlastnictva(cestaSuboru,"pdf");
        }




    private void menuImportXLSX() throws IOException, TikaException, SAXException {
        sk.krok = 0;
       // JOptionPane.showMessageDialog(null, "Zatlacene ImportXLSX");
        cestaSuboru = JOptionPane.showInputDialog("Zadajte cestu xlsx súboru", "C:\\Users\\igoro\\OneDrive\\Desktop\\dp_definitiva\\lv332.xlsx");


        lv = new ListVlastnictva(cestaSuboru,"xlsx");

    }

    private void menu_selectFromSQL(String nazovTabulka) {

        try {

            Databaza db = new Databaza(true,nazovTabulka);

            if(nazovTabulka.equals("databaza_pozemky.list_vlastnictva")){
                indexyNONE = new ArrayList<>();
                indexyNONE.add(0);
                indexyNONE.add(1);
            }
            if(nazovTabulka.equals("databaza_pozemky.vlastnik")){
                indexyNONE = new ArrayList<>();
                indexyNONE.add(0);

            }
            if(nazovTabulka.equals("databaza_pozemky.parcela")){
                indexyNONE = new ArrayList<>();
                indexyNONE.add(0);
                indexyNONE.add(11);
            }
            if(nazovTabulka.equals("databaza_pozemky.lv_vlastnik")){
                indexyNONE = new ArrayList<>();
                indexyNONE.add(0);
                indexyNONE.add(10);
                indexyNONE.add(11);
            }

            db.getData("select * from "+nazovTabulka +";",indexyNONE);
            Tabulka sk = new Tabulka(db);

        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vynimka getData");
        }
    }



    private void menu_search() {
        switch (zatlaceneMenu) {
            case "Hľadaj list vlastníctva":
                searchLV();
                break;
            case "Hľadaj vlastníka":
                searchVlastnik();
                break;
            case "Hľadaj parcelu":
                searchParcela();
                break;

        }
    }


    private void searchLV() {
        label1.setText("Okres:");
        label2.setText("Obec:");
        label3.setText("Katastrálne územie:");
        label6.setText("Číslo LV:");
        try {

            db = new Databaza(true,"list_vlastnictva");
            db.getData("SELECT DISTINCT lv.okresNazov \n" +
                    "FROM  list_vlastnictva lv;",indexyNONE);


            //comboBox1 = new JComboBox(new MyComboBoxModel(db));
            comboBox1.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
            comboBox1.setSelectedIndex(0);

            // System.out.println(db.getTabulkaData());
            //FormTables ft = new FormTables(db);
        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vynimka Data");
        }
    }


    private void searchVlastnik() {
        label1.setText("Priezvisko:");
        label2.setText("Meno:");
        label3.setText("Dátum narodenia:");
        label4.setText("IČO:");
        label6.setText("Číslo LV:");
        try {

            db = new Databaza(true,"vlastnik");
            db.getData("SELECT DISTINCT v.priezviskoVlastnik \n" +
                    "FROM  vlastnik v;",indexyNONE);
            //comboBox1 = new JComboBox(new MyComboBoxModel(db));

            comboBox1.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
            comboBox1.setSelectedIndex(0);

            // System.out.println(db.getTabulkaData());
            //FormTables ft = new FormTables(db);
        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vynimka Data");
        }
    }

    private void searchParcela() {
        label1.setText("Okres:");
        label2.setText("Obec:");
        label3.setText("Katastrálne územie:");
        label4.setText("Typ parcely:");
        label5.setText("Číslo parcely:");
        label6.setText("Číslo LV:");
        try {

            db = new Databaza(true,"parcela");
            db.getData("SELECT DISTINCT lv.okresNazov \n" +
                    "FROM  list_vlastnictva lv, parcela p \n" +
                    "WHERE lv.IDLV=p.IDLV;",indexyNONE);

            comboBox1.setModel(new MyComboBoxModel(db));  //prepisanie modelu z designera
            comboBox1.setSelectedIndex(0);

            // System.out.println(db.getTabulkaData());
            //FormTables ft = new FormTables(db);
        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vynimka Data");
        }
    }
}





