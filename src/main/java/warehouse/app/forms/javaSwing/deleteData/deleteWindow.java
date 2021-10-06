package warehouse.app.forms.javaSwing.deleteData;

import warehouse.Entity.Employee;
import warehouse.app.forms.javaSwing.FrameInheritance;
import warehouse.app.forms.javaSwing.mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class deleteWindow extends FrameInheritance {
    private JComboBox listOfEmployeeJComboBox;
    private JButton deleteUserButton;
    private JButton backToShowDataButton;
    private JPanel mainPanel;
    private JTable infoTableAboutEmployee;


    public deleteWindow(){
        startSession();

        List employeeId = session.createQuery("select e.employeeId from Employee e").getResultList();

        stopSession();

        listOfEmployeeJComboBox.addItem("--- choose id of employee ---");
        for (Object employee : employeeId) {
            listOfEmployeeJComboBox.addItem(employee);
        }

        setContentPane(mainPanel);

        setVisible(true);


        backToShowDataButton.addActionListener(e -> {
            new MainFrame();
            dispose();
        });

        listOfEmployeeJComboBox.addActionListener(e -> setInfoToLabel());

        deleteUserButton.addActionListener(e -> {
            int employeeId1 = (int) listOfEmployeeJComboBox.getItemAt(listOfEmployeeJComboBox.getSelectedIndex());
            startSession();

            session.createQuery("delete from Employee where employeeId=:employeeId")
                    .setParameter("employeeId", employeeId1).executeUpdate();

            stopSession();
            new deleteWindow();
            dispose();

        });
    }

    private void setInfoToLabel() {
        if(listOfEmployeeJComboBox.getSelectedIndex()!=0){
            int employeeId = (int) listOfEmployeeJComboBox.getItemAt(listOfEmployeeJComboBox.getSelectedIndex());
            startSession();

            Employee gotEmployee = session.get(Employee.class, employeeId);

            stopSession();

            List<String[]> list = new ArrayList<>();
            list.add(new String[]{"employee id", String.valueOf(gotEmployee.getEmployeeId())});
            list.add(new String[]{"employee name", gotEmployee.getNameOfEmployee()});
            list.add(new String[]{"employee surname", gotEmployee.getLastNameOfEmployee()});
            list.add(new String[]{"employee position", gotEmployee.getLastNameOfEmployee()});

            DefaultTableModel dtm = new DefaultTableModel(new String[]{"employee","data"},0);
            infoTableAboutEmployee.setModel(dtm);
            for (String[] strings : list) {
                dtm.addRow(strings);
            }


        }else {
            List<String[]> list = new ArrayList<>();
            list.add(new String[]{"employee id", ""});
            list.add(new String[]{"employee name", ""});
            list.add(new String[]{"employee surname",""});
            list.add(new String[]{"employee position",""});

            DefaultTableModel dtm = new DefaultTableModel(new String[]{"employee","data"},0);
            infoTableAboutEmployee.setModel(dtm);
            for (String[] strings : list) {
                dtm.addRow(strings);
            }
        }
    }
}
