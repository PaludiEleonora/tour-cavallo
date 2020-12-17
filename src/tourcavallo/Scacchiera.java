package tourcavallo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Scacchiera extends JFrame {
    
    private static final int DIM = 8;
    private JLabel[][] caselle = new JLabel[DIM][DIM];
    private BufferedImage horse;
    private BufferedImage xIcon;
    private int[][] percorsoCavallo = new int[DIM][DIM];
    
    private int x;
    private int y;
    
    int[] case0 = {2,1};
    int[] case1 = {2,-1};
    int[] case2 = {1,2};
    int[] case3 = {1,-2};
    int[] case4 = {-2,1};
    int[] case5 ={-2,-1};
    int[] case6 = {-1,2};
    int[] case7 = {-1,-2};
    
    public Scacchiera() throws InterruptedException {
        setLayout(new GridLayout(8,8));
        try {
            horse = ImageIO.read(new File("horse.png"));
            xIcon = ImageIO.read(new File("x.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        boolean isBlack = true;
        for(int r = 0; r < DIM; r++){
            if (r%2 != 0) {
                isBlack = false;
            }else{
                isBlack = true;
            }
            
            for(int c = 0; c<DIM; c++){
                caselle[r][c] = new JLabel(new ImageIcon(horse));
                caselle[r][c].setOpaque(true);
                if(isBlack) {
                    caselle[r][c].setBackground(Color.darkGray);
                    isBlack = false;
                }else{
                    caselle[r][c].setBackground(Color.WHITE);
                    isBlack = true;
                }

                add(caselle[r][c]);
            }
        }
        x = (int)Math.round(Math.random()*7);
        y = (int)Math.round(Math.random()*7);
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.start();
    }
    
    public void start() throws InterruptedException {
        percorsoCavallo[y][x] = 1;
        rappresenta(y,x);
        Thread.sleep(1000);
        boolean isWorking = true;
        ArrayList<Integer> notUse = new ArrayList<>();
        while(isWorking) { //Inizio algoritmo...le istruzioni sotto riportate vanno modificate
            
            int[] moves = nextMove(y,x,notUse);
            if(moves == null){
                isWorking = false;
                JOptionPane.showMessageDialog(null,"Mosse finite");
            } else{
                if(percorsoCavallo[y+moves[0]][x+moves[1]] == 0){
                y += moves[0];
                x += moves[1];
                percorsoCavallo[y][x] = 1;
                rappresenta(y,x);
                }
            }
            Thread.sleep(1000);
        }
    }
 // <=
 // >=

    public int nMoves(int y, int x) {
        int moves = 0;
        if (y+2 <= 7 && y+2 >= 0){
            if (x+1 <= 7 && x+1 >= 0){
                if(percorsoCavallo[y+2][x+1] == 0){
                    moves += 1;
                }
            }
            if(x-1 >= 0 && x-1 <= 7){
                if (percorsoCavallo[y+2][x-1] == 0){
                    moves += 1;
                }
            }
        }
        if(y-2 >= 0 && y-2 <= 7) {
            if (x+1 <= 7  && x+1 >= 0){
                if(percorsoCavallo[y-2][x+1] == 0){
                    moves += 1;
                }
            }
            
            if(x-1 >= 0 && x-1 <= 7){
                if(percorsoCavallo[y-2][x-1] == 0){
                    moves += 1;
                }
            }
        }
        if(y+1 <= 7 && y+1 >= 0) {
            if (x+2 <= 7 && x+2 >= 0){
                if(percorsoCavallo[y+1][x+2] == 0){
                    moves += 1;
                }
            }
            if(x-2 >= 0 && x-2 <= 7){
                if(percorsoCavallo[y+1][x-2] == 0){
                    moves += 1;
                }
            }
        }
        if(y-1 >= 0 && y-1 <= 7) {
            if (x+2 <= 7 && x+2 >= 0){
                if(percorsoCavallo[y-1][x+2] == 0){
                    moves += 1;
                }
            }
        
            if(x-2 >= 0 && x-2 <= 7){
               if(percorsoCavallo[y-1][x-2] == 0){
                   moves += 1;
               } 
            }
        }
        return moves;
    }
   
    
    public int[] nextMove(int y, int x, ArrayList<Integer> notUse) {
        
        int[] moves = new int[8];
        
        moves[0] = nMoves(y+2,x+1);
        moves[1] = nMoves(y+2,x-1);       
        moves[2] = nMoves(y+1,x+2);       
        moves[3] = nMoves(y+1,x-2);
        moves[4] = nMoves(y-2,x+1);          
        moves[5] = nMoves(y-2,x-1);         
        moves[6] = nMoves(y-1,x+2);     
        moves[7] = nMoves(y-1,x-2);              
        
        int n = 0;
        int pos = -1;
        
        for(int i = 0; i < moves.length; i++){
            if(moves[i] > n && !notUse.contains(i)){
                pos = i;
                n = moves[i];
            }
        }
        switch(pos) {
            case 0:
                if(y+2 <= 7 && y+2 >= 0 && x+1 <= 7 && x+1 >= 0 && percorsoCavallo[y+2][x+1] == 0){
                    notUse.clear();
                    return case0;
                }else{
                    notUse.add(pos);
                    return nextMove(this.y,this.x,notUse);
                }
            case 1:
                if(y+2 <= 7 && y+2 >= 0 && x-1 <= 7 && x-1 >= 0 && percorsoCavallo[y+2][x-1] == 0){
                    notUse.clear();
                    return case1;
                } else{
                    notUse.add(pos);
                    return nextMove(this.y,this.x,notUse);
                }
            case 2:
                if(y+1 <= 7 && y+1 >= 0 && x+2 <= 7 && x+2 >= 0 && percorsoCavallo[y+1][x+2] == 0){
                    notUse.clear();
                    return case2;
                } else{
                    notUse.add(pos);
                    return nextMove(this.y,this.x,notUse);
                }
            case 3:
                if(y+1 <= 7 && y+1 >= 0 && x-2 <= 7 && x-2 >= 0 && percorsoCavallo[y+1][x-2] == 0){
                    notUse.clear();
                    return case3;
                } else{
                    notUse.add(pos);
                    return nextMove(this.y,this.x,notUse);
                }
            case 4:
                if(y-2 <= 7 && y-2 >= 0 && x+1 <= 7 && x+1 >= 0 && percorsoCavallo[y-2][x+1] == 0){
                    notUse.clear();
                    return case4;
                } else{
                    notUse.add(pos);
                    return nextMove(this.y,this.x,notUse);
                }
            case 5:
                if(y-2 <= 7 && y-2 >= 0 && x-1 <= 7 && x-1 >= 0 && percorsoCavallo[y-2][x-1] == 0){
                    notUse.clear();
                    return case5;
                } else{
                    notUse.add(pos);
                    return nextMove(this.y,this.x,notUse);
                }
            case 6:
                if(y-1 <= 7 && y-1 >= 0 && x+2 <= 7 && x+2 >= 0 && percorsoCavallo[y-1][x+2] == 0){
                    notUse.clear();
                    return case6;
                } else{
                    notUse.add(pos);
                    return nextMove(this.y,this.x,notUse);
                }
            case 7:
                if(y-1 <= 7 && y-1 >= 0 && x-2 <= 7 && x-2 >= 0 && percorsoCavallo[y-1][x-2] == 0){
                    notUse.clear();
                    return case7;    
                } else{
                    notUse.add(pos);
                    return nextMove(this.y,this.x,notUse);
                }
        }
        return null;
    }

    public void rappresenta(int y, int x) {
        for(int i = 0; i<DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                if(i == y && j == x) {
                    caselle[i][j].setIcon(new ImageIcon(horse));
                }else if(percorsoCavallo[i][j] == 1) {
                    caselle[i][j].setIcon(new ImageIcon(xIcon));
                }else{
                    caselle[i][j].setIcon(null);
                }
            }
        }
    }   
}
