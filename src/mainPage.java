import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPage extends JFrame{ //1
    private JPanel mainPage;
    private JLabel someonesHome;
    private JLabel lock;
    private JLabel deskLight;
    private JLabel curtains;
    private JLabel lockState;
    protected JLabel lightState;
    private JLabel curtainsState;
    private JButton help;
    private JLabel lockIcon;
    private JToggleButton lockSwitch;
    private JLabel lightIcon;
    protected JToggleButton lightSwitch;
    private JLabel curtainsIcon;
    private JToggleButton curtainsSwitch;
    private JPanel lockPanel;
    private JButton lockInformation;
    private JButton lightInformation;
    private JButton curtainsInformation;

    public mainPage(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPage);                             //2
        this.setIconImage(new ImageIcon("src/icons/logo.png").getImage());

        //someonesHome.setText("someone" + "'s home"); //getUsername()
        DB db = new DB();
        String response = db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/selectuser" );
        someonesHome.setText(db.getUserName(response, App.ID) + "'s home");

        String[] info = db.getAllString(db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/select_real_time_control/"+App.ID));
        //lockState.setText("Offline" + " | " + "Living Room");//getState(), getPlace()
        //lightState.setText("Offline" + " | " + "Bedroom");//getState(), getPlace()
        //curtainsState.setText("Offline" + " | " + "Living Room");//getState(), getPlace() from DB
        lockState.setText(info[0]);
        lightState.setText(info[1]);
        curtainsState.setText(info[2]);
        lockSwitch.setSelected(info[0].equals("Online"));
        lightSwitch.setSelected(info[1].equals("Online"));
        curtainsSwitch.setSelected(info[2].equals("Online"));

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame helpPage = new helpPage("help page");
                helpPage.setVisible(true);
                helpPage.pack();
            }
        });


        lightInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame lightDetailsPage = new lightDetailsPage("details page");
                lightDetailsPage.setVisible(true);
                lightDetailsPage.pack();
            }
        });

        lightSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //lightDetailsPage.controlSwitch.doClick();
                //lightDetailsPage.state
                //change light state (maybe in DB??)
                String newEnlp = lightSwitch.isSelected()? "Online" : "Offline";
                db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/update_real_time_control_enlp/"+newEnlp+"/"+App.ID);
                lightState.setText(newEnlp);
            }
        });
        curtainsInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame curtainsDetailsPage = new curtainsDetailsPage("details page");
                curtainsDetailsPage.setVisible(true);
                curtainsDetailsPage.pack();
            }
        });
        curtainsSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(lightSwitch.isSelected());
                String newEnct = curtainsSwitch.isSelected()? "Online" : "Offline";
                //System.out.println(newEnct);
                //System.out.println("https://studev.groept.be/api/a21ib2c04/update_real_time_control_enlp/"+newEnct+"/"+App.ID);
                db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/update_real_time_control_enct/"+newEnct+"/"+App.ID);
                curtainsState.setText(newEnct);
            }
        });
        lockSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newEnlk = lockSwitch.isSelected()? "Online" : "Offline";
                db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/update_real_time_control_enlk/"+newEnlk+"/"+App.ID);
                lockState.setText(newEnlk);
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame mainPage = new mainPage("main page");
        mainPage.setVisible(true);
        mainPage.pack();
    }                                                        //3
}