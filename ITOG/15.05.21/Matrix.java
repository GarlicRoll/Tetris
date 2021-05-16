//package Tetris;

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

    protected void add_figure(Figure figure) { // добавление фигур
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
                placement_table = matrixsum(placement_table, figure_to_remove.placement_table, figure_to_remove.x,
                        figure_to_remove.y);
                break;
            } else {

            }
        }

        figures.remove(figure_to_remove);
    }

    private boolean[][] matrixsum(boolean[][] m1, boolean[][] m2, int x, int y) {
        // m1 - исходная матрица
        // m2 - накладываемая матрица
        // x, y - координаты в исходной матрице, туда будем вставлять
        for (int m1_y = 0; m1_y < m2.length; m1_y++) {
            for (int m1_x = 0; m1_x < m2[m1_y].length; m1_x++) {
                m1[m1_y + y][m1_x + x] = m2[m1_y][m1_x];
            }
        }
        return m1;
    }

    static void matrixprint(boolean[][] m) {
        // вывод
        System.out.println();
        for (int i = 0; i < m.length; i++) {

            System.out.println();

            for (int j = 0; j < m[i].length; j++) {
                System.out.print(" ");

                System.out.print(m[i][j]);
            }
        }
    }
}