package gameClient;


import gameClient.util.Range;
import gameClient.util.Range2D;
;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class EnterScreen extends JFrame implements ActionListener{
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    private boolean startGame = false;
    private JTextField textforid;
    private JPanel loginpanel;
    private JSpinner spinner1;
    private JButton button1=new JButton("start game");

    public EnterScreen(){
        super("EnterScreen");
        loginpanel = new JPanel();
        ImageIcon img=new ImageIcon("images//backforenter.jpg");
        JLabel background=new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 23, 1);
        spinner1=new JSpinner(model1);
        spinner1.setBounds(180,580,150,20);
        textforid = new JTextField(15);
        textforid.setBounds(200, 30, 150, 20);
        setSize(368, 368);
        setLocation((int) (width/2), (int) (height/2));
        button1.addActionListener(this);
        //add(background);
        loginpanel.add(textforid);
        loginpanel.add(spinner1);
        loginpanel.add(button1);
        loginpanel.add(background);
        loginpanel.setBackground(new Color(50, 122, 191));
        getContentPane().add(loginpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    private void updateFrame() {
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-10,150);
        Range2D frame = new Range2D(rx,ry);
    }
    public int getid()
    {
        if(textforid.getText().isBlank())
            return 319121158;
        try{
            int id=Integer.parseInt(textforid.getText());
            return  id;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }

    }
    public int getlevel()
    {
        try {
            int lv = (Integer) (spinner1.getValue());
            return Math.min(23, Math.max(0, lv));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    public boolean getStartGame()
    {
        return this.startGame;
    }
    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == button1)
        {
            this.startGame = true;
            this.dispose();
        }
    }

}
