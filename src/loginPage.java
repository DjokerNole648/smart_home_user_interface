import javax.swing.*;
import java.awt.event.ItemEvent;

public class loginPage extends JFrame{
    private JButton login;
    private JButton about;
    private JCheckBox rememberMe;
    private JCheckBox showPassword;
    JTextField jtf;
    private JButton reset;
    JPasswordField jpf;
    private JButton hint;
    private JLabel welcome;
    private JPanel origin;
    private JLabel userName;
    private JLabel password;
    private JLabel kul;
    private static int ID;
    private JPanel welkom;
    private static final DB db = new DB();
    private final static String response = db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/selectuser");

    public static void main(String[] args) {
        loginPage page = new loginPage("Welcome to the smart home!");
        page.setVisible(true);
        page.pack();

        if(db.checkIfRemember(response)){
            page.jtf.setText(db.getRememberedUserName(response));
            page.jpf.setText(db.getRememberedPassword(response));
        }
    }

    public loginPage(String title) {
        super("Welcome to the smart home!");
        setContentPane(origin);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setIconImage(new ImageIcon("src/icons/logo.png").getImage());
        userName.setIcon(new ImageIcon("src/icons/username.png"));
        password.setIcon(new ImageIcon("src/icons/password.png"));
        hint.setIcon(new ImageIcon("src/icons/Hint.png"));
        reset.setIcon(new ImageIcon("src/icons/reset.png"));
        kul.setIcon(new ImageIcon("src/icons/KU_Leuven_logo.svg.png"));

        about.addActionListener(actionEvent -> JOptionPane.showMessageDialog(origin,
                """
                        Welcome to the smart home system!
                        This login interface is implemented by Shibo Liu of team C4
                        If you have any suggestions please send an email to shibo.liu@student.kuleuven.be
                        Hope you will enjoy it!.""", "About", JOptionPane.PLAIN_MESSAGE));


        hint.addActionListener(e -> JOptionPane.showMessageDialog(origin,
                """
                        The username is your full name and password is your username plus 123
                        For example: your name is Novak Djokovic then your Username is NovakDjokovic and password is NovakDjokovic123
                        Or you can use Admin; Admin123 to sample this system""",
                "Hint", JOptionPane.INFORMATION_MESSAGE));

        reset.addActionListener(e -> {
            jtf.setText("");
            jpf.setText("");
        });

        login.addActionListener(e -> {

            ID = db.getUserID(response, jtf.getText());

            if(jtf.getText().length() == 0 || jpf.getText().length() == 0){
                JOptionPane.showMessageDialog(origin, "Username and password are required for login!",
                        "Oops!", JOptionPane.WARNING_MESSAGE);
            }
            else if(db.checkUser(response, jtf.getText(), String.valueOf(jpf.getPassword()))){
                App app = new App(ID);
                JFrame mainPage = new mainPage("main page");
                mainPage.setVisible(true);
                mainPage.pack();
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(origin,
                        "Sorry your username or password is invalid, if you need help please click forget!",
                        "Oops", JOptionPane.ERROR_MESSAGE);
            }
        });

        showPassword.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                jpf.setEchoChar((char)0);
            }
            else{
                jpf.setEchoChar('*');
            }
        });

        rememberMe.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                db.rememberLastUser(response, jtf.getText(), String.valueOf(jpf.getPassword()));
            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        welcome = new JLabel(new ImageIcon("src/icons/smart-houses.jpg"));
        welcome.setText("");
    }
}
