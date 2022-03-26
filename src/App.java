import javax.swing.*;

public class App {
    protected static int ID = 1;

    public App(int ID){
        App.ID = ID;
    }

    public static void main(String[] args)
    {
        JFrame login = new logIn();
        DB db = new DB();
        if(db.checkIfRemember("https://studev.groept.be/api/a21ib2c04/selectuser")){
            logIn.jtf.setText(db.getUserName("https://studev.groept.be/api/a21ib2c04/selectuser", ID));
            logIn.jpf.setText(db.getPassword("https://studev.groept.be/api/a21ib2c04/selectuser", ID));
        }
        login.setVisible(true);
        App app = new App(ID);

        //mainPage.pack();
    }
}
