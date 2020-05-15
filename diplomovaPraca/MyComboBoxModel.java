package diplomovaPraca;

import javax.swing.*;

public class MyComboBoxModel extends DefaultComboBoxModel implements ComboBoxModel {
    String[] dataComboBox ;
    public MyComboBoxModel(Databaza db){
        dataComboBox = new String[db.getTabulkaData().size()];
        for(int i=0;i<db.getTabulkaData().size();i++){

            dataComboBox[i]=db.getTabulkaData().get(i).get(0).toString();
            // System.out.println(okresy[i]);
        }

    }

    String selection = null;

    public Object getElementAt(int index) {
        return dataComboBox[index];
    }

    public int getSize() {
        return dataComboBox.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (String) anItem; // to select and register an
    } // item from the pull-down list

    // Methods implemented from the interface ComboBoxModel
    public Object getSelectedItem() {
        return selection; // to add the selection to the combo box
    }


}
