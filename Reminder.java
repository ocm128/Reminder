
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;


/**
 *   @author Kalasni
 *   @version 1.0
 *    kalassni@gmail.com 
 */

public class Reminder
extends JFrame {
    private JLabel eleccion;
    private JTextField entrada_usuario;
    private JButton aceptar;
    private JButton parar;
    private JFrame frame;
    private JPanel jp;
    private Border borde;
    private final Toolkit kit = Toolkit.getDefaultToolkit();
    private Subtarea subt = null;

    public Reminder() {
        try {
            this.addComponentes();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addComponentes() throws Exception {
        this.frame = new JFrame("Reminder");
        this.frame.addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.jp = new JPanel();
        this.eleccion = new JLabel("Introduzca tiempo: ");
        this.entrada_usuario = new JTextField(10);
        this.entrada_usuario.setBackground(Color.white);
        this.entrada_usuario.setFont(new Font("Dialog", 1, 13));
        this.entrada_usuario.setEditable(true);
        this.entrada_usuario.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                Reminder.this.devuelveEntrada();
            }
        });
        this.aceptar = new JButton();
        this.aceptar.setFont(new Font("Dialog", 1, 13));
        this.aceptar.setText(" OK ");
        this.aceptar.setBorder(BorderFactory.createLineBorder(Color.white));
        this.aceptar.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                Reminder.this.frame.addWindowListener(new WindowAdapter(this){
                    final /* synthetic */  this$1;

                    public void windowIconified(WindowEvent e) {
                    }
                });
                if (Reminder.this.subt == null) {
                    Reminder.access$2(Reminder.this, new Subtarea());
                }
            }
        });
        this.parar = new JButton();
        this.parar.setFont(new Font("Dialog", 1, 13));
        this.parar.setText("DETENER");
        this.parar.setBorder(BorderFactory.createLineBorder(Color.white));
        this.parar.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                if (Reminder.this.subt != null) {
                    Reminder.this.subt.invertirEjecutar();
                    Reminder.this.subt.interrupt();
                    Reminder.access$2(Reminder.this, null);
                }
            }
        });
        this.jp.add(this.eleccion);
        this.jp.add(this.entrada_usuario);
        this.jp.add(this.aceptar);
        this.jp.add(this.parar);
        this.borde = BorderFactory.createEtchedBorder();
        this.jp.setBorder(BorderFactory.createTitledBorder(this.borde, " v1.0 by Kalasni"));
        this.jp.setLayout(new GridLayout(2, 2, 5, 8));
        this.frame.getContentPane().add(this.jp);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setResizable(false);
    }

    private int devuelveEntrada() {
        int num = Integer.parseInt(this.entrada_usuario.getText());
        return num;
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception var1_1) {
            // empty catch block
        }
        Reminder rm = new Reminder();
    }

    static /* synthetic */ void access$2(Reminder reminder, Subtarea subtarea) {
        reminder.subt = subtarea;
    }

    private class Subtarea
    extends Thread {
        int n;
        String cad1;
        String cad2;
        Object[] options;
        private boolean ejecutar;

        Subtarea() {
            this.cad1 = "Seguir";
            this.cad2 = "Terminar";
            this.options = new Object[]{this.cad1, this.cad2};
            this.ejecutar = true;
            this.start();
        }

        void invertirEjecutar() {
            this.ejecutar = !this.ejecutar;
        }

        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Reminder.this.kit.beep();
                    Thread.sleep(Reminder.this.devuelveEntrada() * 60 * 1000);
                }
                catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, "Temporizador detenido. Puedes comenzar, o no", "Reminder", 1);
                    Reminder.access$2(Reminder.this, null);
                    return;
                }
                Reminder.this.kit.beep();
                this.n = JOptionPane.showOptionDialog(null, "\u00bfDeseas repetir el ciclo?", "Confirmar", 0, 3, null, this.options, this.cad1);
                if (this.n == 0 || this.n != 1) continue;
                Reminder.this.subt.invertirEjecutar();
                Reminder.access$2(Reminder.this, null);
                return;
            }
        }
    }

}