package View;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.PetStoreController;

public class LoginView extends JFrame {
	
	
	
	public LoginView(PetStoreController controller)
	{
		setTitle("Happy Paws Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel(" Username:"));
        JTextField userField = new JTextField();
        add(userField);

        add(new JLabel(" Password:"));
        JPasswordField passField = new JPasswordField();
        add(passField);

        JButton loginBtn = new JButton("Login");
        add(new JLabel("")); 
        add(loginBtn);
        
        loginBtn.addActionListener(e -> {
        	if(controller.authenticate(userField.getText(), new String(passField.getPassword())))
        	{
        		this.dispose();
        		new MainView(controller).setVisible(true);
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        	}
        });
	}

}
