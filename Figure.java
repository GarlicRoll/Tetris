package Tetris;

import java.util.ArrayList;
import java.util.Random;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class Figure {

	ArrayList<Image> images;
	Image image;
	ArrayList<Block> blocks;
	boolean[][] placement_table; // таблица для расстановки блоков
	int height;
	int width;
	int x; // положение фигуры по верхнему левому блоку
	int y;
	Shell shell;
	
	Figure(ArrayList<Image> images, Shell shell) {
		this.shell = shell;
		this.images = images;
		this.blocks = new ArrayList<>();
		set_color();
		set_type();
	}

	void rotate() {

	}
	
	// сдвиг вниз
	boolean move_down(boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {
		if (y + 1 > matrix_height - 1) { // если блок достиг границы поля
			return false;
		}
		for (int column = 0; column < width; column ++) {
			// если блок удраяется о блок
			if ((placement_table[height - 1][column] && matrix_placement_table[y + 1][column]) == true) {
				return false;
			}
		}
		
		y += 1;
		return true; // если всё хорошо
	}
	
	// сдвиг вправо
	void move_right(boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {
		
	}
	
	// сдвиг влево
	void move_left(boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {

	}
	
	
	private void set_color() { // случайный цвет
		Random random = new Random();
		int color_number = random.nextInt(images.size());
		this.image = images.get(color_number);
	}

	private void set_type() { // случайный вид фигуры
		this.placement_table = new boolean[][] { { true, true }, { true, true } }; // функционал временно ограничен квадратом 2 на 2
		
		this.blocks.add(new Block(0, 0, image, shell));
		this.blocks.add(new Block(0, 1, image, shell));
		this.blocks.add(new Block(1, 0, image, shell));
		this.blocks.add(new Block(1, 1, image, shell));
		
		this.height = 2;
		this.width = 2;
	}
}
