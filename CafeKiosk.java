import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class MenuItem {
    private String name;
    private int price;

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class Order {
    private List<MenuItem> items;

    public Order() {
        items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void displayOrder() {
        int total = 0;
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("\n--- 주문서 ---\n");
        for (MenuItem item : items) {
            orderDetails.append(String.format("%s: %d원\n", item.getName(), item.getPrice()));
            total += item.getPrice();
        }
        orderDetails.append(String.format("합계: %d원\n", total));
        orderDetails.append("-------------");
        JOptionPane.showMessageDialog(null, orderDetails.toString());
    }
}

public class CafeKiosk extends JFrame {
    private List<MenuItem> menu;
    private Order order;

    public CafeKiosk() {
        menu = new ArrayList<>();
        order = new Order();
        loadMenu();
        initUI();
    }

    private void loadMenu() {
        menu.add(new MenuItem("아메리카노", 3000));
        menu.add(new MenuItem("카페라떼", 3500));
        menu.add(new MenuItem("카푸치노", 4000));
        menu.add(new MenuItem("에스프레소", 2500));
        menu.add(new MenuItem("모카라떼", 4500));
        menu.add(new MenuItem("아이스티", 2000));
        menu.add(new MenuItem("샌드위치", 5000));
    }

    private void initUI() {
        setTitle("카페 키오스크");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(menu.size(), 1));

        for (MenuItem item : menu) {
            JButton button = new JButton(item.getName() + ": " + item.getPrice() + "원");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    order.addItem(item);
                    JOptionPane.showMessageDialog(null, item.getName() + "를 목록에 추가했습니다.");
                }
            });
            menuPanel.add(button);
        }

        JButton showOrderButton = new JButton("주문서 보기");
        showOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order.displayOrder();
            }
        });

        add(menuPanel, BorderLayout.CENTER);
        add(showOrderButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CafeKiosk kiosk = new CafeKiosk();
                kiosk.setVisible(true);
            }
        });
    }
}