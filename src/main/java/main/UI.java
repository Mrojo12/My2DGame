package main;

import object.OBJ_Key;

import java.awt.*;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    Font arial_80B, arial_40;

    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/Marmonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/PurisaBold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        // PLAY STATE
        if(gp.gameState == gp.playState){
            // DO PLAY STUFF LATER
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        // DIALOG STATE
        if (gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
    }

    public void drawTitleScreen(){
        if (titleScreenState == 0){
            // BACKGROUND
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
            String text = " Blue Boy Adventure";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);

            // MAIN TEXT
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // BLUE BOY IMAGE
            x = gp.screenWidth / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x - (gp.tileSize * 2) + 25, y, gp.tileSize * 3, gp.tileSize * 3, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

            // NEW GAME
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;

            if (commandNum == 0){
                g2.drawString(">", x - 30, y);
            }

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // LOAD GAME
            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;

            if (commandNum == 1){
                g2.drawString(">", x - 30, y);
            }

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);


            // QUIT
            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;

            if (commandNum == 2){
                g2.drawString(">", x - 30, y);
            }

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
        }else if (titleScreenState == 1){
            // CLASS SLECCTION SCREEN
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));

            String text = "SELECT YOUR CLASS!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(42F));
            // FIGHTER
            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0){
                g2.drawString(">", x - gp.tileSize, y);
            }

            // THIEF
            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1){
                g2.drawString(">", x - gp.tileSize, y);
            }

            // SOURCERER
            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2){
                g2.drawString(">", x - gp.tileSize, y);
            }

            // BACK
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 3){
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 210);

        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text){
        long length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - (int) length / 2;
        return x;
    }
}
