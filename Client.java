package chatting.application;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.*;
import java.text.*;
import javax.swing.border.EmptyBorder;
import java.net.*;


public class Client implements ActionListener{
    JTextField text;
    static JPanel a1;
    static Box vertical=Box.createVerticalBox();
    JScrollPane scrollPane;
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    Client()
    {
        f.setLayout(null);
        
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7,84,94));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons-20240229T164033Z-001/icons/3.png"));
        Image i2= i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
         ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons-20240229T164033Z-001/icons/2.png"));
        Image i5= i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);
        
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons-20240229T164033Z-001/icons/video.png"));
        Image i8= i7.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video=new JLabel(i9);
        video.setBounds(310,16,30,30);
        p1.add(video);
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons-20240229T164033Z-001/icons/phone.png"));
        Image i11= i10.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel phone=new JLabel(i12);
        phone.setBounds(365,16,30,30);
        p1.add(phone);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons-20240229T164033Z-001/icons/3icon.png"));
        Image i14= i13.getImage().getScaledInstance(11,25, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel hamburger=new JLabel(i15);
        hamburger.setBounds(410,16,13,30);
        p1.add(hamburger);
        
        JLabel name=new JLabel("Adi");
        name.setBounds(115,23,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        p1.add(name);
        
        a1=new JPanel();
        a1.setLayout(new BoxLayout(a1, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(a1);
        scrollPane.setBounds(5,75,440,570);
        f.add(scrollPane);
        
        text=new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,18));
        f.add(text);
        
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        
        JButton send=new JButton("Send");
        send.setBounds(320,655,123,40);
        send.addActionListener(this);
        send.setBackground(new Color(7,84,94));
        send.setForeground(Color.WHITE);
        f.add(send);
        //Hover Color Change
        send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                send.setBackground(Color.RED); // Change color when mouse enters
            }

            @Override
            public void mouseExited(MouseEvent e) {
                send.setBackground(new Color(7, 94, 84)); // Change color back when mouse exits
            }
        });
        
        back.addMouseListener(new MouseAdapter()
        {
           @Override
           public void mouseClicked(MouseEvent e)
           {
               System.exit(0);
           }
        });
        f.setSize(450,700);
       f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
       try{
            String out =text.getText();
        
//        JLabel output=new JLabel(out);
        if(out.isEmpty()) {
        	return ;
        }
        JPanel p2=formatLabel(out);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right=new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical,BorderLayout.PAGE_START);
         
        dout.writeUTF(out);
        text.setText("");
       
        f.repaint();
        f.invalidate();
        f.validate();
       }catch(Exception ep)
       {
           ep.printStackTrace();
       }
    }
    private void sendMessage() {
        String out = text.getText().trim(); // Trim whitespace
        if (!out.isEmpty()) {
            JPanel p2 = formatLabel(out);
            vertical.add(p2); // Add the formatted message panel directly to the main panel

            // Scroll to the bottom of the scroll pane
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());

            text.setText(""); // Clear the text field
            
            vertical.revalidate();
            vertical.repaint();
        }
    }
    
public static JPanel formatLabel(String out)
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output=new JLabel("<html><p style=\"width:150px\">"+ out +"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,14));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(8,15,15,40));
        panel.add(output);
        
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        return panel;
    }
            public static void main(String[] args)
    {
       Client c= new Client();
       try
       {
        Socket s=new Socket("192.168.135.73",6001);
        DataInputStream din=new DataInputStream(s.getInputStream());
        dout=new DataOutputStream(s.getOutputStream());
         while(true)
                {
                    a1.setLayout(new BorderLayout());
                    String msg=din.readUTF();
                    JPanel panel=formatLabel(msg);
                    
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical,BorderLayout.PAGE_START);
                    f.validate();
                }
       }catch(Exception e)
       {
           e.printStackTrace();
       }
       
    }
}