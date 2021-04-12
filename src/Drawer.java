import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Drawer extends Canvas {

    public static int CANVAS_WIDTH = 600;
    public static int CANVAS_HEIGHT = 600;

    Board board;
    Solution solution;

    int gap;
    int padX;
    int padY;

    public static void drawSolution(Board board, Solution solution, String title){
        JFrame frame = new JFrame(title);
        Canvas canvas = new Drawer(board, solution);
        canvas.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public Drawer(Board board, Solution solution){
        this.board = board;
        this.solution = solution;

        gap = Math.min(CANVAS_WIDTH / (board.width + 1), CANVAS_HEIGHT / (board.height + 1));
        padX = (CANVAS_WIDTH - ((board.width - 1) * gap)) / 2;
        padY = (CANVAS_HEIGHT - ((board.height - 1) * gap)) / 2;
    }

    @Override
    public void paint(Graphics g){
        paintBorders(g);

        paintPoints(g);

        for(Path path : solution.paths){
            paintPath(g, path);
        }
    }

    private void paintBorders(Graphics g){
        g.drawLine(padX - gap / 2, padY - gap / 2, padX - gap / 2, CANVAS_HEIGHT - padY + gap/ 2);
        g.drawLine(padX - gap / 2, padY - gap / 2, CANVAS_WIDTH - padX + gap / 2, padY - gap / 2);
        g.drawLine(padX - gap / 2, CANVAS_HEIGHT - padY + gap / 2, CANVAS_WIDTH - padX + gap / 2, CANVAS_HEIGHT - padY + gap / 2);
        g.drawLine(CANVAS_WIDTH - padX + gap / 2, padY - gap / 2, CANVAS_WIDTH - padX + gap / 2, CANVAS_HEIGHT - padY + gap / 2);
    }

    private void paintPoints(Graphics g){
        for(int i = 0; i < board.width; i++){
            for(int j = 0; j < board.height; j++){
                g.fillOval(i * gap + padX, j * gap + padY, 2, 2);
            }
        }
    }

    private void paintPath(Graphics g, Path path){
        Random random = new Random();
        int R = random.nextInt(255);
        int G = random.nextInt(255);
        int B = random.nextInt(255);
        g.setColor(new Color(R, G, B));

        g.fillOval(path.points.get(0).x * gap + padX - 3, path.points.get(0).y * gap + padY - 3, 7, 7);
        for(int i = 0; i < path.points.size() - 1; i++){
            int x1 = path.points.get(i).x * gap + padX;
            int y1 = path.points.get(i).y * gap + padY;
            int x2 = path.points.get(i + 1).x * gap + padX;
            int y2 = path.points.get(i + 1).y * gap + padY;
            g.drawLine(x1, y1, x2, y2);
        }
        g.fillOval(path.points.get(path.points.size()-1).x * gap + padX - 3, path.points.get(path.points.size()-1).y * gap + padY - 3, 7, 7);
    }

}
