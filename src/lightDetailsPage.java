import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lightDetailsPage extends JFrame{
    private JButton goBackIcon;
    private JLabel deskLight;
    private JLabel lampPicture;
    protected JToggleButton controlSwitch;
    private JLabel state;
    private JPanel lightDetailsPage;

    public lightDetailsPage(String title) {
        super(title);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// to avoid closing help page but closing all pages
        this.setContentPane(lightDetailsPage);
        this.setIconImage(new ImageIcon("src/icons/logo.png").getImage());

        DB db = new DB();
        String response = db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/select_real_time_control/" + App.ID);
        String status = db.getString(response, "lampState");
        state.setText(status.equals("1") ? "ON" : "OFF");
        controlSwitch.setSelected(status.equals("1"));

        controlSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlSwitch.isSelected())
                {
                    state.setText("ON");
                    db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/update_real_time_control_ls/1/" + App.ID);
                }
                else
                {
                    state.setText("OFF");
                    db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/update_real_time_control_ls/0/" + App.ID);
                }
            }
        });
        goBackIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

        public static void main(String[] args)
        {
            JFrame lightDetailsPage = new lightDetailsPage("details page");
            lightDetailsPage.setVisible(true);
            lightDetailsPage.pack();
        }
}
