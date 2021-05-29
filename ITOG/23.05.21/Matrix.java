package Tetris;

import java.util.ArrayList;

public class Matrix {

	static int BLOCK_SIZE = 32;
	
    boolean[][] placement_table; // таблица для расстановки фигур
    int height; // высота таблицы
    int width; // ширина таблицы
    ArrayList<Figure> figures; // падающие фигуры
    ArrayList<Block> blocks; // все блоки
    int score; // очки

    Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        this.placement_table = new boolean[height][width];
        this.figures = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.score = 0;
    }

    private void delete_line(ArrayList<Block> line) { // удаление строки
    	int y = line.get(0).y;
    	for (Block block: line) {
    		blocks.remove(block); // удаление ссылки на блок из матрицы
    		block.base.dispose(); // удаление графики
    		placement_table[block.y][block.x] = false; // отключение коллизии в матрице для столкновений
    	}
    	shift_blocks_down(y);
    }

    private void shift_blocks_down(int y_start) { // смещение блоков вниз (y - линия, выше которой смещение)
    	/*
    	// ускоренный поиск нужных блоков
    	for (Block block: blocks) {
    		if (block.y < y) { // необходим порядок снизу вверх!!!
    			placement_table[block.y][block.x] = false;
    			placement_table[block.y + 1][block.x] = true;
    			block.move_down();
    		}
    	}
    	*/
    	for(int y = y_start - 1; y >= 0; y--) {
    		for(int x = 0; x < width; x++) {
    			if (placement_table[y][x] == true) { // если блок существует
    				Block block = get_block(x, y); // получаем блок
    				placement_table[block.y][block.x] = false; // сдвигаем в матрице для коллизий
        			placement_table[block.y + 1][block.x] = true;
        			block.move_down(); // сдвигаем графику
    			}
    		}
    	}
    }
    
    // не всегда удаляется линия!!!
    private ArrayList<Block> check_line(int y) { // проверка линии на заполненость блоками
    	ArrayList<Block> line = new ArrayList<>();
    	for (int x = 0; x < width; x++) {
    		if (placement_table[y][x] == false) {
    			return null;
    		}
    		else {
    			line.add(get_block(x, y));
    		}
    		//Block block = get_block(x, y);
    		//if (block == null) {
    			//return null;
    		//}
    		//else {
    			//line.add(block);
    		//}
    	}
    	return line;
    }
    
    private Block get_block(int x, int y) { // поиск блока по координатам
    	for (int i = 0; i < blocks.size(); i++) {
    		Block block = blocks.get(i);
    		if (block.x == x && block.y == y) {
    			return block;
    		}
    	}
    	return null;
    }

    protected boolean add_figure(Figure figure, boolean[][] matrix_placement_table, int matrix_height, int matrix_width) { // добавление фигуры
    	if (figure.set_type(matrix_placement_table, matrix_height, matrix_width) == true) {
	        figures.add(figure);
	        for (Block block : figure.blocks) {
	            blocks.add(block);
	        }
    	}
    	else {
    		return false;
    	}
    	return true;
    }

    public void figure_shift_loop() { // цикл для смещения фигур
        Figure figure_to_remove = null;
        for (Figure figure : figures) {
            if (!figure.move_down(placement_table, height, width)) {
                figure_to_remove = figure; // остановка фигуры
                
                placement_table = MatrixInMatrix.matrixsum(placement_table, figure_to_remove.placement_table,
                        figure_to_remove.x, figure_to_remove.y); // добавление остановившийся фигуры в матрицу для коллизий
                
                ArrayList<ArrayList<Block>> lines_to_delete = new ArrayList<>();
                
                for (int y = figure_to_remove.y; y < figure_to_remove.height + figure_to_remove.y; y++){ // проверка на составленные линии в области установки фигуры
                	
                	ArrayList<Block> line = check_line(y); // линия с блоками для проверки
                	
                	if (line != null) { // если линия не пуста, то 
                		 lines_to_delete.add(line); // удаляем её
                	}
                	
                }
                add_score(lines_to_delete.size()); // начисляем очки
                for (ArrayList<Block> line : lines_to_delete) { // удаляем и смещаем после перебора
                	delete_line(line);
                }
                break;
            }
        }
        figures.remove(figure_to_remove);
    }
    
    private void add_score(int size) {  // правила для начисления очков
    	if (size == 1) {
    		score += 100;
    	}
    	else if (size == 2) {
    		score += 300;
    	}
    	else if (size == 3) {
    		score += 500;
    	}
    	else {
    		score += 100 * size * 5; 
    	}
    }

}