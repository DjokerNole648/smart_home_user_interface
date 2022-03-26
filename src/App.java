import javax.swing.*;

public class App {
    private static final DB db = new DB();
    private final static String response = db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/selectuser");
    protected static int ID = 1;

    public App(int ID){
        App.ID = ID;
    }

    public static void main(String[] args)
    {
        JFrame page = new loginPage();
        loginPage newPage = new loginPage();
        if(db.checkIfRemember(response)){
            newPage.jtf.setText(db.getRememberedUserName(response));
            newPage.jpf.setText(db.getRememberedPassword(response));
        }
        page.setVisible(true);

    }
}
