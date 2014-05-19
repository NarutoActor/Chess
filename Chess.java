import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;


public class Chess extends JFrame{
CardLayout c = new CardLayout();
GridLayout g = new GridLayout(8,8);
JPanel p1 = new JPanel(new BorderLayout());
JPanel p2 = new JPanel(c);//cardpanel
JPanel p3 = new JPanel(g);//Player 1's Board
JPanel p4 = new JPanel(new BorderLayout());//Player 2's Board
JPanel p5 = new JPanel();//top bar
JPanel p6 = new JPanel(new BorderLayout());//P1 card
JLabel l1 = new JLabel("Start Game?");
JLabel l2 = new JLabel("P1's Turn");
JLabel l3 = new JLabel("P2's Turn");
JButton b1 = new JButton("New Game");
JButton b2 = new JButton("Undo LastMove");
JButton b3 = new JButton("Move Log");
JButton[][] board = new JButton[8][8];
int X1=0,X2=0,Y1=0,Y2=0;
boolean isClick = false;
boolean player1 = true;
public Chess(){
	boolean bcolor = true;
	p3.setBorder(BorderFactory.createRaisedBevelBorder());
	Move m = new Move();
	for(int i=0;i<board.length;i++){
		if(bcolor){
			bcolor = false;
		}
		else{
			bcolor = true;
		}
		for(int j=0;j<board.length;j++){
			if(bcolor){
				board[i][j] = new JButton();
				board[i][j].setBackground(Color.GRAY);
				board[i][j].setOpaque(true);
				board[i][j].setBorderPainted(false);
				bcolor = false;
			}
			else{
				board[i][j] = new JButton();
				board[i][j].setBackground(Color.WHITE);
				board[i][j].setOpaque(true);
				board[i][j].setBorderPainted(false);
				bcolor = true;
			}
			board[i][j].setEnabled(false);
			board[i][j].addActionListener(m);
			p3.add(board[i][j]);
		}
		
	}
	/* Tessting Purposes
	for(int i=0;i<board.length;i++){
		for(int j=0;j<board.length;j++){
			board[i][j].setText("("+i+","+j+")");
		}
	}*/
	b1.addActionListener(new NewGameButton());
	p5.add(b1);
	p5.add(b2);
	p5.add(b3);
	p4.add(l1,BorderLayout.CENTER);
	p2.add("B",p3);
	p2.add("P1S",p4);
	p1.add(p2, BorderLayout.CENTER);
	p1.add(p5, BorderLayout.NORTH);
	add(p1);
	
	
}
public void mirror(){
	String temp="";
	for(int i=0;i<board.length;i++){
		for(int j=i;j<board.length;j++){
			temp = board[i][j].getText();
			board[i][j].setText(board[board.length-1-i][board.length-1-j].getText());
			board[board.length-1-i][board.length-1-j].setText(temp);
			//System.out.println("OldName" + temp+ ":NewName:"+board[i][j].getText()+i+j);
			
		
		}
	}
	for(int i=0;i<board.length;i++){
		for(int j=0;j<board.length;j++){
			if(i==j){
			temp = board[i][j].getText();
			board[i][j].setText(board[board.length-1-i][board.length-1-j].getText());
			board[board.length-1-i][board.length-1-j].setText(temp);
			//System.out.println("OldName" + temp+ ":NewName:"+board[i][j].getText()+i+j);
			}
			if(i>2&&j>2){
				break;
			}
		
		}
	}
}
public class Move implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if(!isClick){//has not been clicked
			
			JButton jspot = (JButton)(e.getSource());
			if(jspot.getText().equals("")){
				//Color cb = jspot.getBackground();
				//jspot.setBackground(Color.RED);
				//jspot.revalidate();
				
				//jspot.setBackground(cb);
				//jspot.validate();
				
				return;
			}
			if(jspot.getText().charAt(0)=='B' && player1){
				Color cb = board[Y1][X1].getBackground();
				board[Y1][X1].setBackground(Color.RED);
				board[Y1][X1].setBackground(cb);
				return;
			}
			if(jspot.getText().charAt(0)=='W' && !player1){
				Color cb = board[Y1][X1].getBackground();
				board[Y1][X1].setBackground(Color.RED);
				board[Y1][X1].setBackground(cb);
				return;
			}
			for(int i=0;i<board.length;i++){
				for(int j=0;j<board.length;j++){
					if(jspot==board[i][j]){
						//System.out.println(i+","+j);
						Y1 = i;
						X1 = j;
						break;
					}
				}
			}
			isClick = true;
		}else{
			JButton jspot = (JButton)(e.getSource());
			if(jspot.getText().equalsIgnoreCase("")){
				
			}
			else if(jspot.getText().charAt(0)=='B' && !player1){
				isClick = false;
				Color cb = board[Y1][X1].getBackground();
				board[Y1][X1].setBackground(Color.RED);
				board[Y1][X1].setBackground(cb);
				return;
			}
			else if(jspot.getText().charAt(0)=='W' && player1){
				isClick = false;
				Color cb = board[Y1][X1].getBackground();
				board[Y1][X1].setBackground(Color.RED);
				board[Y1][X1].setBackground(cb);
				return;
			}
			for(int i=0;i<board.length;i++){
				for(int j=0;j<board.length;j++){
					if(jspot==board[i][j]){
						//System.out.println(i+","+j);
						Y2 = i;
						X2 = j;
						break;
					}
				}
			}
			if(!isLegal(board[Y1][X1].getText(),Y1,X1,Y2,X2)){
				isClick = false;
				Color cb = board[Y1][X1].getBackground();
				board[Y1][X1].setBackground(Color.RED);
				board[Y1][X1].setBackground(cb);
				System.out.println("Not Legal");
				return;
			}

			board[Y2][X2].setText(board[Y1][X1].getText());
			board[Y1][X1].setText("");
			isClick = false;
			System.out.println("Legal");
			//change turn
			//player1 = false;
		}
	}
}
public class NewGameButton implements ActionListener{
	public void actionPerformed(ActionEvent e) {
	c.next(p2);
	//need some sort of pause
	
	c.next(p2);
		//board[0][1].setForeground(board[0][1].getBackground()); <-----This will be good for when I switch over to Icons
	isClick = false;
	for(int i=0;i<board.length;i++){
		for(int j=0;j<board.length;j++){
			board[i][j].setEnabled(true);
			if((i==0&&j==0)||(i==0&&j==7)){
				board[i][j].setText("BRook");
			}
			else if((i==7&&j==0)||(i==7&&j==7)){
				board[i][j].setText("WRook");
			}
			else if((i==0&&j==1)||(i==0&&j==6)){
				board[i][j].setText("BKnight");
			}
			else if((i==7&&j==1)||(i==7&&j==6)){
				board[i][j].setText("WKnight");
			}
			else if((i==0&&j==2)||(i==0&&j==5)){
				board[i][j].setText("BBishop");
			}
			else if((i==7&&j==2)||(i==7&&j==5)){
				board[i][j].setText("WBishop");
			}
			else if((i==0&&j==3)){
				board[i][j].setText("BQueen");
			}
			else if(i==0&&j==4){
				board[i][j].setText("BKing");
			}
			else if((i==7&&j==3)){
				board[i][j].setText("WQueen");
			}
			else if(i==7&&j==4){
				board[i][j].setText("WKing");
			}
			else if(i==1){
				board[i][j].setText("BPon");
			}
			else if(i==6){
				board[i][j].setText("WPon");
			}
			else{
				board[i][j].setText("");
			}
		}
	}
		
	}
	
}
public class changer implements ActionListener{
	public void actionPerformed(ActionEvent e) {
	mirror();
		
	}
	
}
public boolean isLegal(String type, int y1, int x1, int y2, int x2){//y<->i, x<->j
	System.out.print(type + "from" + x1+","+y1+":to:"+x2+","+y2);
	type = type.substring(1);
	if(x1==x2 && y1==y2){//clicked the same spot
		return false;
	}
	if(type.equalsIgnoreCase("Rook")){
		if(x1 == x2){
			if(y2>y1){
				for(int i = y1+1;i<y2;i++){
					if(!board[i][x1].getText().equalsIgnoreCase("")){//there is a collision
						return false;
					}
				}
				return true;
			}
			else{
				for(int i = y2+1;i<y1;i++){
					if(!board[i][x1].getText().equalsIgnoreCase("")){//there is a collision
						return false;
					}
				}
				return true;
			}
		}
		else if(y1==y2){
			if(x2>x1){
				for(int i = x1+1;i<=x2;i++){
					if(!board[y1][i].getText().equalsIgnoreCase("")){//there is a collision
						return false;
					}
				}
				return true;
			}
			else{
				for(int i = x2+1;i<x1;i++){
					if(!board[y1][i].getText().equalsIgnoreCase("")){//there is a collision
						return false;
					}
				}
				return true;
			}
		}
		else{
			return false;
		}
	}
	else if(type.equalsIgnoreCase("Pon")){
		if(Y2+1==Y1 && X2 == X1){
			if(board[Y2][X2].getText().equalsIgnoreCase("")){
				return true;
			}
			else{
				return false;
			}
		}
		else if(Y2 + 1==Y1 && (X2+1==X1||X1+1==X2)){
			if(board[Y2][X2].getText().equalsIgnoreCase("")){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}
	}
	else if(type.equalsIgnoreCase("King")){//4 <(min) >(plus)
		if(x2>x1&&y2>y1){//8
			if(board[Y1+1][X1+1]==board[Y2][X2]){
				return true;
			}return false;
		}
		else if(x2==x1&&y2>y1){//7
			if(board[Y1+1][X1]==board[Y2][X2]){
				return true;
			}return false;
		}
		else if(x2>x1&&y2==y1){//5
			if(board[Y1][X1+1]==board[Y2][X2]){
				return true;
			}return false;
		}
		else if(x2<x1&&y2<y1){//0
			if(board[Y1-1][X1-1]==board[Y2][X2]){
				return true;
			}return false;
		}
		else if(x2==x1&&y2<y1){//1
			if(board[Y1-1][X1]==board[Y2][X2]){
				return true;
			}return false;
		}
		else if(x2<x1&&y2==y1){//3
			if(board[Y1][X1-1]==board[Y2][X2]){
				return true;
			}return false;
		}
		else if(y2<y1&&x2>x1){//2
			if(board[Y1-1][X1+1]==board[Y2][X2]){
				return true;
			}return false;
		}
		else if(y2>y1 && x2<x1){//6
			if(board[Y1+1][X1-1]==board[Y2][X2]){
				return true;
			}return false;
		}
		else{
			return false;
		}
	}
	else if(type.equalsIgnoreCase("Queen")){
		if(isLegal("BBishop",y1,x1,y2,x2)||isLegal("BRook",y1,x1,y2,x2)){
			return true;
		}
		else{
			return false;
		}
	}
	else if(type.equalsIgnoreCase("Bishop")){
		if((Math.abs(X1-X2))==(Math.abs(Y1-Y2))){//slope of 1, correct path, now check for collisions
			if(x2>x1 && y1>y2){
				for(int i=1;i<Math.abs(X1-X2);i++){
					if(!board[Y1-i][X1+i].getText().equalsIgnoreCase("")){
						return false;
					}
				}
				return true;
			}
			else if(x2>x1 && y2>y1){
				for(int i=1;i<Math.abs(X1-X2);i++){
					if(!board[Y1+i][X1+i].getText().equalsIgnoreCase("")){
						return false;
					}
				}
				return true;
			}
			else if(x1>x2 && y2>y1){
				for(int i=1;i<Math.abs(X1-X2);i++){
					if(!board[Y1+i][X1-i].getText().equalsIgnoreCase("")){
						return false;
					}
				}
				return true;
			}
			else if(x1>x2 && y1>y2){
				for(int i=1;i<Math.abs(X1-X2);i++){
					if(!board[Y1-i][X1-i].getText().equalsIgnoreCase("")){
						return false;
					}
				}
				return true;
			}
		}
		else{
			return false;
		}
	}
	else if(type.equalsIgnoreCase("Kight")){
		if(x2>x1 && y1>y2){
			if(board[Y1+2][X1+1].getText().equalsIgnoreCase(board[Y2][X2].getText())){
				return true;
			}
			else if(board[Y1+1][X1+2].getText().equalsIgnoreCase(board[Y2][X2].getText())){
				return true;
			}
			return false;
		}
		else if(x2>x1 && y2>y1){//good
			if(board[Y1-2][X1+1].getText().equalsIgnoreCase(board[Y2][X2].getText())){
				return true;
			}
			else if(board[Y1-1][X1+2].getText().equalsIgnoreCase(board[Y2][X2].getText())){
				return true;
			}
			return false;
		}
		else if(x1>x2 && y2>y1){
			if(board[Y1-2][X1-1].getText().equalsIgnoreCase(board[Y2][X2].getText())){
				return true;
			}
			else if(board[Y1-1][X1-2].getText().equalsIgnoreCase(board[Y2][X2].getText())){
				return true;
			}
			return false;
		}
		else if(x1>x2 && y1>y2){
			if(board[Y1+2][X1-1].getText().equalsIgnoreCase(board[Y2][X2].getText())){
				return true;
			}
			else if(board[Y1+1][X1-2].getText().equalsIgnoreCase(board[Y2][X2].getText())){
				return true;
			}
			return false;
		}
	}
	return false;
}

public static void main(String[] args) {
	UIManager.put("Button.disabledText", new ColorUIResource(Color.BLACK));
	Chess r1 = new Chess();
	r1.setSize(800, 780);
	r1.setMinimumSize(new Dimension(600, 400));
	r1.setVisible(true);
	r1.setTitle("Clasic Chess");
	r1.setLocationRelativeTo(null);
	r1.setDefaultCloseOperation(EXIT_ON_CLOSE);

}
}
