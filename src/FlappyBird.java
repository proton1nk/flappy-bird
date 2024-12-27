package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int broadheight = 640;
    int broadwidth = 360;
    Image background;
    Image birdimg;
    Image toppipe;
    Image bottompipe;

    int birdX = broadheight / 8;
    int birdY = broadwidth / 2;
    int birdheight = 40;
    int birdwidth = 28;
    int birdYspeed = 0;

    class Bird {

        int x = birdX;
        int y = birdY;
        int width = birdwidth;
        int height = birdheight;
        Image img;

        public Bird(Image img) {
            this.img = img;
        }

    }
    int pipeX = broadheight;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    double score=0;
    Random random = new Random();
    class Pipe {

        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }

    }
    // Logic game
    Bird bird;
    int veliocityX = -5;// move left speed
    int veliocityY = 0; // move up/ down speed
    int gravity = 1; // gravity
    ArrayList<Pipe> pipes;

    Timer gameloop;
    Timer pipeTimer;
    boolean gameover=false;


    FlappyBird() {
        setPreferredSize(new Dimension(broadwidth, broadheight));
        setFocusable(true);
        addKeyListener(this);
        background = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdimg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipe = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipe = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdimg);
        pipes = new ArrayList<Pipe>();
        pipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipe();
            }
        });
        pipeTimer.start();
        gameloop = new Timer(1000 / 60, this);
        gameloop.start();
    }

    public void placePipe() {
        int randomPipeY=(int)(pipeY- pipeHeight/4-Math.random()*(pipeHeight/2)); 
        int opening_space = broadheight / 4; 
        Pipe topPipe = new Pipe(toppipe);
        topPipe.y = randomPipeY;;
        pipes.add(topPipe);
        Pipe bottomPipe = new Pipe(bottompipe);
        bottomPipe.y = topPipe.y + pipeHeight + opening_space;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        //background
        g.drawImage(background, 0, 0, this.broadwidth, this.broadheight, null);

        //bird
        g.drawImage(birdimg, bird.x, bird.y, bird.width, bird.height, null);
        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);

        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 20));
         if(gameover)
         {
            g.setFont(new Font("Impact", Font.BOLD, 20));
            g.drawString("Game over ", 120, 256);
            g.drawString("Your mark : "+String.valueOf((int)score),120, 256+30);

         }
         else{
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(String.valueOf((int)score), 10, 30);
         }
    }

    boolean collsion (Bird a, Pipe b){
        return a.x<b.x+b.width
        && a.y<b.y+b.height
        &&a.x+a.width>b.x
        &&a.y+a.height>b.y;

    }
    public void move() {
        veliocityY += gravity;
        bird.y += veliocityY;
        bird.y = Math.max(bird.y, 0);

        for(int i=0;i< pipes.size();i++){
            Pipe pipe = pipes.get(i);
            pipe.x += veliocityX;
            if (!pipe.passed && bird.x > pipe.x + pipe.width){
                score+=0.5;
                pipe.passed = true;
            }
            if (collsion(bird,pipe)){
                gameover=true;
            }
            if (bird.y > broadheight) {
                gameover = true;
            }
        }
            
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameover){
            gameloop.stop();
            pipeTimer.stop();
            
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            veliocityY = -9;
            if(gameover){
                bird.y=birdY;
            pipes.clear();
            score=0;
            gameover=false;
            veliocityY=0;
            gameloop.start();
            pipeTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
