import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class curtainsDetailsPage extends JFrame{
    private JButton goBackButton;
    private JToggleButton controlSwitch;
    private JLabel curtains;
    private JLabel curtainPicture;
    private JLabel state;
    private JPanel curtainsDetailsPage;

    public curtainsDetailsPage(String title) {
        super(title);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// to avoid closing help page but closing all pages
        this.setContentPane(curtainsDetailsPage);

        DB db = new DB();
        String response = db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/select_real_time_control/" + App.ID);
        String status = db.getString(response, "curtainState");
        state.setText(status.equals("1") ? "ON" : "OFF");
        controlSwitch.setSelected(status.equals("1"));

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        controlSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlSwitch.isSelected())
                {
                    state.setText("ON");
                    db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/update_real_time_control_cs/1/" + App.ID);
                }
                else
                {
                    state.setText("OFF");
                    db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/update_real_time_control_cs/0/" + App.ID);
                }
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame curtainsDetailsPage = new curtainsDetailsPage("details page");
        curtainsDetailsPage.setVisible(true);
        curtainsDetailsPage.pack();
    }
}
