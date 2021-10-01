package warehouse.app.forms.showOrdersWindow;

import warehouse.app.forms.FrameInheritance;
import warehouse.app.forms.mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class ShowOrders extends FrameInheritance {
    private JTable ordersTable ;
    private JButton backButton1;
    private JPanel orderPanel;
    private JTable orderDetailJTable;

    public ShowOrders(){
        setContentPane(orderPanel);
        setVisible(true);

        drawTableOrder();

        backButton1.addActionListener(e -> {
            new MainFrame();
            dispose();
        });


        System.out.println(ordersTable.getSelectedRow());

        ordersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getInfoAboutOrder(ordersTable.getSelectedRow());
            }
        });
    }

    private void getInfoAboutOrder(int row) {
        if(row!=0){
            startSession();
            List resultList = session.createQuery("select distinct o.idOrder from OrderDetail od join od.order o order by o.idOrder").getResultList();
            stopSession();

            int idOrder = (int) resultList.get(row-1);

            startSession();
            List resultList1 = session.createQuery("select o.idOrder, p.productName , p.price, od.quantity, p.price*od.quantity " +
                    "from OrderDetail od join od.product p join od.order o where o.idOrder=:idOrder")
                    .setParameter("idOrder",idOrder).getResultList();
            stopSession();

            setInfoToOrderDetailTable(resultList1);
        }else {
            deleteDataFromTableOrderDetail();
        }
    }

    private void deleteDataFromTableOrderDetail() {
        orderDetailJTable.setModel(new DefaultTableModel());
    }

    private void setInfoToOrderDetailTable(List<Object[]> resultList) {
        DefaultTableModel dtm  = new DefaultTableModel(new String[]{"id order","product name","price","quantity","value"},0);
        orderDetailJTable.setModel(dtm);

        dtm.addRow(new String[]{
                "<html><b>id order</b></html>",
                "<html><b>product name</b></html>",
                "<html><b>price [$]</b></html>",
                "<html><b>quantity</b></html>",
                "<html><b>value[quantity*price]</b></html>"});

        for (Object[] objects : resultList) {
            dtm.addRow(objects);
        }
    }

    private void drawTableOrder() {
        DefaultTableModel dtm = new DefaultTableModel(new String[]{"id order","name and surname","value"},0);
        ordersTable.setModel(dtm);

        String ordersGetterQuery =
                "select o.idOrder,concat(c.name,' ',c.lastName), sum(p.price*od.quantity) " +
                "from OrderDetail od join od.order o join od.product p join o.customer c " +
                "group by o.idOrder order by o.idOrder";

        startSession();

        List orderList = session.createQuery(ordersGetterQuery).getResultList();

        stopSession();

        dtm.addRow(new String[]{
                "<html><b>id order</b></html>",
                "<html><b>name and surname</b></html>",
                "<html><b>value</b></html>"});
        for (Object order : orderList) {
            dtm.addRow((Object[]) order);
        }
        ordersTable.setGridColor(Color.black);
    }
}