package warehouse.app.forms;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import warehouse.app.db.DbConnector;

import javax.swing.*;
import java.awt.*;

public abstract class FrameInheritance  extends JFrame {
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 400;

    protected DbConnector dbConnector = new DbConnector();
    protected SessionFactory sf = dbConnector.getSf();
    protected Session session =sf.getCurrentSession();

    public  FrameInheritance(){
        setTitle("Hibernate");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
    }

    protected void startSession(){
        session = sf.getCurrentSession();
        session.beginTransaction();
    }

    protected void stopSession(){
        session.getTransaction().commit();
    }
}
