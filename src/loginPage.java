import javax.swing.*;

public class loginPage extends JFrame{
    private JButton button1;
    private JButton button2;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JTextField jtf;
    private JButton button3;
    private JPasswordField jpf;
    private JButton button4;
    private JLabel welcome;
    private JPanel origin;
    private JLabel userName;
    private JLabel password;

    public static void main(String[] args) {
        loginPage page = new loginPage("Welcome to ");
        page.setVisible(true);
        page.pack();
    }

    public loginPage(String title){
        super(title);
        setContentPane(origin);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userName.setIcon(new ImageIcon("src/icons/username.png"));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        welcome = new JLabel(new ImageIcon("src/icons/smart-houses.jpg"));
        welcome.setText("");

    }
}
