package code.amitginat.commands.games;

import code.amitginat.commands.AbstractCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.FileUpload;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;


public class TicTacToeCommand extends AbstractCommand {
    public static HashMap<Long, TicTacToeCommand> players = new HashMap<>();
    char[][] board;
    long memberId;
    @Override
    public void run() {
        init();
    }
    public void stop(){
        players.remove(memberId);
        channel.sendMessage("Stopped the game with %s".formatted(member.getAsMention())).queue();
    }
    public void resumeGame(){
        try{
            if(message.toLowerCase().contains("stop")){
                   stop();
                return;
            }
            int location = Integer.parseInt(message);
            if(location < 1 || location > 9){
                channel.sendMessage("hey %s! the number is invalid. stopping the game.".formatted(member.getAsMention())).queue();
                stop();
                return;
            }
            if(isOccupied(location)){
                channel.sendMessage("hey %s! location is occupied!".formatted(member.getAsMention())).queue();
                stop();
                return;
            }
            putInBoard(location, 'X');
            channel.sendMessage("%s, the board after your move looks like:".formatted(member.getAsMention())).addFiles(animateBoard()).queue();
            if(checkWin()){
                channel.sendMessage("mf'ing %s won.".formatted(member.getAsMention())).queue();
                stop();
                return;
            }
            if(draw()){
                channel.sendMessage("%s oof. draw".formatted(member.getAsMention())).addFiles(animateBoard()).queue();

                stop();
                return;
            }

            int myLocation = new Random().nextInt(10);
            while(findValueAtLocation(myLocation) != '@'){
                myLocation = new Random().nextInt(10);
            }
            putInBoard(myLocation, 'O');
            channel.sendMessage("\nthe board after my move looks like:").addFiles(animateBoard()).queue();
            if(checkWin()){
                channel.sendMessage("%s i won haha".formatted(member.getAsMention())).queue();
                stop();
            }
        }
        catch (NumberFormatException e){
            channel.sendMessage("hey %s! you should send only numbers. stopping the game.".formatted(member.getAsMention())).queue();
            stop();
        }
    }

    private boolean draw() {
        boolean isDraw = true;
        for(char[] row : board){
            for(char col : row){
                if (col == '@') {
                    isDraw = false;
                    break;
                }
            }
        }
        return isDraw;
    }


    private void putInBoard(int location, char val) {
        switch (location){
            case 1 -> board[0][0] = val;
            case 2 -> board[0][1] = val;
            case 3 -> board[0][2] = val;
            case 4 -> board[1][0] = val;
            case 5 -> board[1][1] = val;
            case 6 -> board[1][2] = val;
            case 7 -> board[2][0] = val;
            case 8 -> board[2][1] = val;
            case 9 -> board[2][2] = val;
            default -> {
            }
        }
    }

    private boolean isOccupied(int location) {

        char thisValue = findValueAtLocation(location);
        return thisValue != '@';
    }

    @Override
    public String getType() {
        return "game";
    }
    @Override
    public String name() {
        return "tictactoe";
    }
    @Override
    public String explain() {
        return "starts a tictactoe game with you!";
    }

    private void init(){
        board = emptyBoard();
        memberId = member.getIdLong();
        players.put(memberId, this);
        String toSend =  """
                hey %s lets play! say "stop" to stop the game.
                below is an empty board! you start (you are x).
                send the number of the location on the board you want to play.
                where the locations are:
                1 | 2 | 3
                4 | 5 | 6
                7 | 8 | 9
                """.formatted(member.getAsMention());
        channel.sendMessage(toSend).addFiles(animateBoard()).queue();
    }
    public static void resume(MessageReceivedEvent event, long id){
        TicTacToeCommand thisCommand = players.get(id);
        thisCommand.setEvent(event);
        thisCommand.resumeGame();
    }
    public static boolean isPlaying(long id){
        return players.containsKey(id);
    }
    private char[][] emptyBoard(){
        return new char[][]{
                {'@', '@', '@'},

                {'@', '@', '@'},

                {'@', '@', '@'}
        };
    }
    private char findValueAtLocation(int location){
        return switch (location){
            case 1 -> board[0][0];
            case 2 -> board[0][1];
            case 3 -> board[0][2];
            case 4 -> board[1][0];
            case 5 -> board[1][1];
            case 6 -> board[1][2];
            case 7 -> board[2][0];
            case 8 -> board[2][1];
            case 9 -> board[2][2];
            default -> 'X';
        };
    }

    private boolean checkWin(){
        // rows
        for(char[] row : board){
            if(row[0] == row[1] && row[1] == row[2] && row[2] != '@'){
                return true;
            }
        }
        for(int i = 0; i < board.length; i++){
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] &&  board[2][i] != '@' ){
                return true;
            }
        }
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] != '@'){
            return true;
        }

        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] != '@'){
            return true;
        }
        return false;
    }

    private FileUpload animateBoard(){
        int cellSize = 100; // Size of each cell
        int imageSize = cellSize * 3; // Size of the whole image

        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.fillRect(0, 0, imageSize, imageSize); // Background
        g2d.setColor(Color.BLACK);

        for (int i = 0; i <= 3; i++) {
            g2d.drawLine(0, i * cellSize, imageSize, i * cellSize); // Horizontal lines
            g2d.drawLine(i * cellSize, 0, i * cellSize, imageSize); // Vertical lines
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int x = j * cellSize + cellSize / 2;
                int y = i * cellSize + cellSize / 2;

                if (board[i][j] == 'X') {
                    g2d.drawLine(x - cellSize / 4, y - cellSize / 4, x + cellSize / 4, y + cellSize / 4);
                    g2d.drawLine(x - cellSize / 4, y + cellSize / 4, x + cellSize / 4, y - cellSize / 4);
                } else if (board[i][j] == 'O') {
                    g2d.drawOval(x - cellSize / 4, y - cellSize / 4, cellSize / 2, cellSize / 2);
                }
            }
        }

        g2d.dispose();

        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] data = byteArrayOutputStream.toByteArray();
            return FileUpload.fromData(data, "board.png");
        }
        catch (IOException e){
            System.out.println(e);
            return null;
        }
    }

}
