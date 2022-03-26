import javax.swing.*;

public class helpPage extends JFrame {
    private JTextArea helpText;
    private JPanel helpPage;

    public helpPage(String title) {
        super(title);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// to avoid closing help page but closing all pages
        this.setContentPane(helpPage);

        helpText.setText("This is an initial version of the UI home page and details pages, many features still need to be added later\n\n" +
                "The three buttons on the home page control whether to activate the automatic control function " +
                "(for example, if the switch of the desk lamp system is off, the lamp will still not come on even if " +
                "it is dark and people are sitting down)\n" +
                "\n" +
                "The buttons on the details page are for direct control of the desk lamp and curtain " +
                "(for example, when I turn on this switch, the desk lamp will light up regardless of the brightness and distance)\n"+
                "\n" +
                "RED icon means 'ON' and BLACK icon means 'OFF' \n\n"+
                "No details page has been added to the door lock system yet, because the work of the parts of this system does not seem to need to be controlled separately."
        );
        helpText.setEditable(false);
    }

    public static void main(String[] args)
    {
        JFrame helpPage = new helpPage("help page");
        helpPage.setVisible(true);
        helpPage.pack();
    }
}
