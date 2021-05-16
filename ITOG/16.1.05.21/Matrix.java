package Tetris;

import java.util.ArrayList;

public class Matrix {

    boolean[][] placement_table; // таблица для расстановки фигур
    int height; // высота таблицы
    int width; // ширина таблицы
    ArrayList<Figure> figures; // падающие фигуры
    ArrayList<Block> blocks; // все блоки

    Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        this.placement_table = new boolean[height][width];
        this.figures = new ArrayList<>();
        this.blocks = new ArrayList<>();
    }

    protected void delete_line() { // удаление строки

    }

    protected void shift_blocks_down() { // смещение блоков вниз

    }

    protected void add_figure(Figure figure) { // добавление фигуры
        figures.add(figure);
        for (Block block : figure.blocks) {
            blocks.add(block);
        }
    }

    public void figure_shift_loop() { // цикл для смещения фигур
        Figure figure_to_remove = null;
        for (Figure figure : figures) {

            if (!figure.move_down(placement_table, height, width)) {
                figure_to_remove = figure; // остановка фигуры
                placement_table = MatrixInMatrix.matrixsum(placement_table, figure_to_remove.placement_table,
                        figure_to_remove.x, figure_to_remove.y);

                break;
            } else {

            }
        }
        figures.remove(figure_to_remove);
    }

}