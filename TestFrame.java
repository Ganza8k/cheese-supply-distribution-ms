import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame {
    public TestFrame() {
        setTitle("TEST - Role Field");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());
        
        add(new JLabel("Role:"));
        JTextField roleField = new JTextField("ADMIN", 15);
        roleField.setFont(new Font("Arial", Font.BOLD, 16));
        add(roleField);
        
        JButton testButton = new JButton("TEST");
        add(testButton);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new TestFrame();
    }
}