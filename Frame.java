import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Frame extends JFrame implements ActionListener{
  public static JButton[][] buttons = new JButton[5][5];
  public static char[][] tempBoard = new char[5][5];
  char n = 'n';
  Container container = this.getContentPane();
  JPanel stage = new JPanel();
  int flag = 1;


  public Frame(char[][] board){
    this.tempBoard = board;
    this.setPreferredSize(new Dimension(500, 500));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    stage.setLayout(new GridLayout(5, 5));
    //tempBoard = board.getBoard();
    //stage.setFocusable(true);
    this.initialize();
    container.add(stage);
    this.pack();
    this.setVisible(true);
  }

  public void setFrameBoard(char[][] board){
    this.tempBoard = board;
  }

  public void initialize(){
    this.checkGameState();
    for(int x=0; x<5; x++){
      for(int y=0; y<5; y++){
        System.out.print(tempBoard[x][y]);
      }
      System.out.println();
    }

    for(int x=0; x<5; x++){
			for(int y=0; y<5; y++){
        switch(tempBoard[x][y]){
          case 'n':
            buttons[x][y] = new JButton();
            buttons[x][y].setBackground(Color.YELLOW);
            buttons[x][y].setOpaque(true);
            buttons[x][y].setBorder(new LineBorder(Color.WHITE));
            buttons[x][y].addActionListener(this);
            stage.add(buttons[x][y]);
            break;
          case 'f':
            buttons[x][y] = new JButton();
            buttons[x][y].setBackground(Color.BLACK);
            buttons[x][y].setOpaque(true);
            buttons[x][y].setBorder(new LineBorder(Color.WHITE));
            buttons[x][y].addActionListener(this);
            stage.add(buttons[x][y]);
            break;
          default:
            System.out.println(tempBoard[x][y]);
        }
			}
		}
    return;
  }
  public void actionPerformed(ActionEvent e){
    this.checkGameState();
    if(flag==0){
      JLabel end = new JLabel("WINNER!", JLabel.CENTER);
      end.setFont(new Font("Serif", Font.PLAIN, 14));

      final JDialog dialog = new JDialog(this, "HELLO");
      dialog.setSize(300, 150);
      dialog.setLocationRelativeTo(this);
      dialog.add(end);
      dialog.setVisible(true);
      this.saveGame();
      return;
    }

    int x=0;
    int y=0;
    int a=0;
    int b=0;
    boolean done = true;
    if(e.getSource() instanceof JButton){
      for(x=0; x<5; x++){
        for(y=0; y<5; y++){
          if((JButton)e.getSource()==buttons[x][y]) {
            a = x;
            b = y;
            break;
          }
        }
      }
    }

    changeColor(a, b);
    if(checkUpBounds(a+1)==true){
      changeColor(a+1, b);
    }
    if(checkUpBounds(b+1)==true){
      changeColor(a, b+1);
    }
    if(checkDownBounds(a-1)==true){
      changeColor(a-1, b);
    }
    if(checkDownBounds(b-1)==true){
      changeColor(a, b-1);
    }
    this.saveGame();
    stage.revalidate();
    stage.repaint();

  }

  public int plusOne(int num){
    return num++;
  }

  public int minusOne(int num){
    return num--;
  }

  public void changeColor(int a, int b){
    switch(tempBoard[a][b]){
      case 'n':
        tempBoard[a][b] = 'f';
        buttons[a][b].setBackground(Color.BLACK);
        buttons[a][b].setOpaque(true);
        //buttons[a][b].setBorderPainted(false);
        buttons[a][b].setBorder(new LineBorder(Color.WHITE));
        return;
      case 'f':
        tempBoard[a][b] = 'n';
        buttons[a][b].setBackground(Color.YELLOW);
        buttons[a][b].setOpaque(true);
        //buttons[a][b].setBorderPainted(false);
        buttons[a][b].setBorder(new LineBorder(Color.WHITE));
        return;
      default:
        System.out.println("error");
    }
  }

  public boolean checkUpBounds(int num){
    if(num>4){
      return false;
    }else{
      return true;
    }
  }

  public boolean checkDownBounds(int num){
    if(num<0){
      return false;
    }else{
      return true;
    }
  }

  public void checkGameState(){
    flag = 0;
    for(int x=0; x<5; x++){
      for(int y=0; y<5; y++){
        switch(tempBoard[x][y]){
          case 'n': flag+=1; break;
          default: break;
        }
      }
    }
  }

  public void saveGame(){
    File file = new File("puzzle.in");
    System.out.println();
    for(int x=0; x<5; x++){
      for(int y=0; y<5; y++){
        System.out.print(tempBoard[x][y]);
      }
      System.out.println();
    }

    try{
      FileWriter fileWriter = new FileWriter(file);
      BufferedWriter writer = new BufferedWriter(fileWriter);
      for(int x=0; x<5; x++){
        for(int y=0; y<5; y++){
          writer.write(tempBoard[x][y]);
          writer.write(" ");
        }
        writer.write("\n");
      }
      writer.close();
    }catch(Exception e){
          System.out.println("Error");
    }
  }


}
