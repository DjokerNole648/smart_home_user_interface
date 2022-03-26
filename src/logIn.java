import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

class logIn extends JFrame{

    protected JLabel jl1;
    protected JFrame f;
    protected JButton jb1, jb2, jb3, jb5;
    protected JPanel jp1;

    protected JTabbedPane jtp;
    protected JPanel jp2, jp3, jp4;

    protected JLabel jl2, jl3, jl5;
    protected static JTextField jtf;
    protected static JPasswordField jpf;
    protected JButton jb4;
    protected JCheckBox jcb1, jcb2;

    protected static int ID;

    public static void main(String[] args) {
        logIn UI = new logIn();
        DB db = new DB();
        if(db.checkIfRemember("https://studev.groept.be/api/a21ib2c04/selectuser")){
            jtf.setText(db.getUserName("https://studev.groept.be/api/a21ib2c04/selectuser", App.ID));
            jpf.setText(db.getPassword("https://studev.groept.be/api/a21ib2c04/selectuser", App.ID));
            System.out.println(jtf);
            System.out.println(jpf);
        }

    }

    public logIn()
    {
        f = new JFrame();
        jl1 = new JLabel(new ImageIcon("src/icons/smart-houses.jpg"));

        jtp = new JTabbedPane();
        jp2 = new JPanel();
        jl2 = new JLabel("Username:", JLabel.CENTER);
        jl2.setIcon(new ImageIcon("src/icons/username.png"));
        jl2.setFont(new Font("Segoe UI", Font.PLAIN, 45));
        jl3 = new JLabel("Password:", JLabel.CENTER);
        jl3.setIcon(new ImageIcon("src/icons/password.png"));
        jl3.setFont(new Font("Segoe UI", Font.PLAIN, 45));
        jb5 = new JButton("Forget?");
        jb5.setIcon(new ImageIcon("src/icons/Hint.png"));
        jb5.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        jb5.addActionListener(e -> JOptionPane.showMessageDialog(f,
                """
                        The Username is your student email and password is your full name plus 123
                        For example: your name is Novak Djokovic then your Username is novak.djokovic@student.kuleuven.be and password is NovakDjokovic123
                        Or you can use Admin, Admin123 to sample this system""",
                "Hint", JOptionPane.INFORMATION_MESSAGE));

        jl5 = new JLabel();

        jcb1 = new JCheckBox("Remember Me");
        jcb1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        //If remember me is pressed, the userName and password are recorded to the database
        jcb1.addItemListener(e -> {
            DB db = new DB();
            String response = db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/selectuser");
            if(e.getStateChange() == ItemEvent.SELECTED){
                db.rememberLastUser(response, jtf.getText(), String.valueOf(jpf.getPassword()));
            }
        });

        jcb2 = new JCheckBox("Show Password");
        jcb2.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        jcb2.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                jpf.setEchoChar((char)0);
            }
            else{
                jpf.setEchoChar('*');
            }
        });

        jtf = new JTextField(18);
        jtf.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        jpf = new JPasswordField(18);
        jpf.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        jb4 = new JButton("Reset");
        jb4.setIcon(new ImageIcon("src/icons/reset.png"));
        jb4.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        jb4.addActionListener(e -> {
            jtf.setText("");
            jpf.setText("");
        });

        jp1 = new JPanel();
        jb1 = new JButton("Login");
        jb1.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        DB db = new DB();
        jb1.addActionListener(e -> {

            ID = db.getUserID(db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/selectuser"), jtf.getText());

            String response = db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/selectuser");
            if(jtf.getText().length() == 0 || jpf.getText().length() == 0){
                JOptionPane.showMessageDialog(null, "Username and password are required for login!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
            else if(db.checkUser(response, jtf.getText(), String.valueOf(jpf.getPassword()))){
                //jump to next page
                App app = new App(ID);
                JFrame mainPage = new mainPage("main page");
                mainPage.setVisible(true);
                mainPage.pack();
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null,
                        "Sorry your username or password is invalid, if you need help please click forget!", "Warning",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        jb2 = new JButton("Quit");
        jb2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        jb3 = new JButton("About");
        jb3.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        jb2.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    null, "Are you sure to close the interface?",
                    "Think Twice",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
            if(result == JOptionPane.OK_OPTION){
                System.exit(0);
            }
        });

        jb3.addActionListener(actionEvent -> JOptionPane.showMessageDialog(f,
                """
                        Welcome to the smart home system!
                        This login interface is implemented by Shibo Liu of team C4
                        If you have any suggestions please send an email to shibo.liu@student.kuleuven.be
                        Hope you will enjoy it!.""", "About", JOptionPane.PLAIN_MESSAGE));

        jp2.setLayout(new GridLayout(3 , 3));

        jp1.add(jb1);
        jp1.add(jb2);
        jp1.add(jb3);

        jp2.add(jl2);
        jp2.add(jtf);
        jp2.add(jb4);
        jp2.add(jl3);
        jp2.add(jpf);
        jp2.add(jb5);
        jp2.add(jcb1);
        jp2.add(jcb2);
        jp2.add(jl5);

        jtp.add("UserID", jp2);
        jtp.add("Phone Number", jp3);
        jtp.add("Email address", jp4);
        jtp.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        this.add(jl1, BorderLayout.NORTH);
        this.add(jp1, BorderLayout.SOUTH);
        this.add(jtp, BorderLayout.CENTER);

        this.setIconImage(new ImageIcon("src/icons/logo.png").getImage());
        this.setTitle("Welcome to the smart house!");
        this.setSize(2000, 1000);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
