package diplomovaPraca; /**METODY DATOVEJ VRSTVY*******/
import diplomovaPraca.Databaza;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

public  class TableModel  extends AbstractTableModel {

    private List<List<Object>> tabulka1;
    private List<List<Object>> updatedTabulka = new ArrayList<>();
    private List<List<Object>> deletedTabulka = new ArrayList<>();
    private Databaza db;



    public TableModel(Databaza db) throws SQLException {

        this.db = db;
        this.tabulka1=db.getTabulkaData();
    }

    public List<List<Object>> getTabulka1() {


        return tabulka1;
    }

    public String getNazovTabulky() {
        return db.getNazovTabulky();
    }


    @Override
    public String getColumnName(int column) {
        // columnNames[column];


        return db.getColumnNames().get(column);

    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (db.getColumnsType().get(column)) {
            case "CHAR":
            case "VARCHAR":
            case "TEXT":
            case "LONGVARCHAR":
                return String.class;

            case "INT":
            case "TINYINT":
            case "SMALLINT":
                return Integer.class;

            case "BIGINT":
                return Long.class;

            case "FLOAT":
            case "DOUBLE":
                return Double.class;

            case "DATE":
                //return GregorianCalendar.class;
                return java.util.Date.class;

            case "BIT":
                return Boolean.class;

            default:
                return Object.class;
        }
    }


    @Override
    public int getColumnCount() {
        return db.getCols();
    }

    @Override
    public int getRowCount() {
        return tabulka1.size();

    }
    public List<List<Object>> getUpdatedTabulka() {
        return updatedTabulka;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        return tabulka1.get(rowIndex).get(columnIndex);

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        for(int i=0;i<db.getIndexyNeeditovatelne().size();i++) {
            if (columnIndex ==db.getIndexyNeeditovatelne().get(i)) return false;
        }
        return true;
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        List<Object> pomocny = new ArrayList<>();
        System.out.println(aValue);
        tabulka1.get(rowIndex).set(columnIndex, aValue);
        pomocny.add(aValue);
        //pomocny.add(tabulka1.get(rowIndex).get(0));
        pomocny.add(rowIndex);
        pomocny.add(getColumnName(columnIndex));
        updatedTabulka.add(pomocny);
         System.out.println(updatedTabulka);
    }


    public void vymazRiadkovZTabulky(int[] Row) {

        for(int i=Row.length-1;i>=0;i--) {
            tabulka1.remove(Row[i]);
        }
        System.out.println(tabulka1);

        for(int i=Row.length-1;i>=0;i--) {

            super.fireTableRowsDeleted(Row[i], Row[i]);
        }

    }


    @Override
    public void fireTableRowsInserted(int firstRow,
                                      int lastRow){

    List<Object> riadkyData = new ArrayList<>();

    for(int i=0;i<getColumnCount();i++) {
        riadkyData.add(null);

    }
    tabulka1.add(riadkyData);
    riadkyData = new ArrayList<>();
    super.fireTableRowsInserted(firstRow, lastRow);

    }

    public TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if( value instanceof Date) {
                value = f.format(value);
                int i=1;
            }
            return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
        }
    };


    static class   VypisRetezca extends DefaultTableCellRenderer {
        VypisRetezca() {
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setVerticalAlignment(SwingConstants.CENTER);
            // this.setBackground(Color.green);
        }
    }

    static class VypisDatumu extends DefaultTableCellRenderer {
        VypisDatumu() {

            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setVerticalAlignment(SwingConstants.CENTER);
            // this.setBackground(Color.blue);
        }
    }


    static class VypisInteger extends DefaultTableCellRenderer {
        VypisInteger() {
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setVerticalAlignment(SwingConstants.CENTER);
            // this.setBackground(Color.red);
        }
    }

    static class VypisDouble extends DefaultTableCellRenderer {
        VypisDouble() {
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setVerticalAlignment(SwingConstants.CENTER);
            //   this.setBackground(Color.YELLOW);
        }
    }


    static class VypisBoolean extends DefaultCellEditor {
        public VypisBoolean() {
            super(new JComboBox());
            JComboBox jcb = (JComboBox) this.getComponent();
            jcb.addItem(" 0 ");
            jcb.addItem(" 1 ");
            jcb.addItem(" 2 ");
        }
    }


     static class DatumEditor extends AbstractCellEditor
            implements TableCellEditor {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        public JTextField datumTF;

        public DatumEditor() {
            datumTF = new JTextField();
            datumTF.setFont(new Font("MonoSpaced", Font.BOLD, 12));
            datumTF.setHorizontalAlignment(JLabel.RIGHT);

        }


        public Object getCellEditorValue() {
            String textFieldValue = datumTF.getText();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


            Date date = new Date();
            try {
                date = dateFormat.parse(textFieldValue);
                //System.out.println(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            // return cal;
            return  date;
        }


        public Component getTableCellEditorComponent(JTable table,
                                                     Object value, boolean isSelected,
                                                     int row, int column) {

            if (value instanceof java.util.Date) {
                java.util.Date date = (java.util.Date) value;
                String strDate = sdf.format(date);
                datumTF.setText(strDate);
                // System.out.println(strDate);
            }

            return datumTF;
        }
    }



}
