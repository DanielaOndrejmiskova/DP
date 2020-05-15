package diplomovaPraca;

import diplomovaPraca.MainForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class Login extends JFrame {

   // JButton  cancel;
    private JPanel panel;
    private JTextField meno;
    private JButton potvrdenie;
    private JPasswordField heslo;
    private JLabel menoLabel;
    private JLabel hesloLabel;
    private JLabel spravaLabel;
    MainForm mf ;
   public Login() {

        add(panel);
        setTitle("Prihlasovanie");
        setSize(350,300);
        setVisible(true);//making the frame visible
        setLocationRelativeTo(null);
        potvrdenie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String prihlasovacieMeno = meno.getText();
                char[] prihlasovacieHeslo = heslo.getPassword();
               String hesloString = String.valueOf(prihlasovacieHeslo);
                if (prihlasovacieMeno.trim().equals("Daniela") && hesloString.trim().equals("euba")) {
                    JOptionPane.showMessageDialog(null, "Vitajte! ");
                    setVisible(false);

                    try {
                        mf = new MainForm();
                        mf.setLocationRelativeTo(null);
                        mf.setSize(400,500);//400 width and 500 height
                        mf.setVisible(true);//making the frame visible
                        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Zadali ste nesprávne prihlasovacie údaje");
                }

            }

        });
    }



}