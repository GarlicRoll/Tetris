//package Tetris;

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
		this.x = 0;
		this.y = 0;
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
		// координата + высота - 1 (тк индексация с нуля) + 1 (тк проверка следущего
		// шага)
		if (y + height - 1 + 1 > matrix_height - 1) { // если блок достиг границы поля
			return false;
		}
		for (int column = 0; column < width; column++) {
			// если блок удраяется о блок
			if ((placement_table[height - 1][column]
					&& matrix_placement_table[y + height - 1 + 1][column + x]) == true) {
				return false;
			}
		}
		// если всё хорошо
		for (Block block : blocks) {
			block.move_down();
		}
		y += 1;
		return true;
	}

	// сдвиг вправо
	boolean move_right(boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {
		if (x + width - 1 + 1 > matrix_width - 1) { // если блок достиг границы поля
			return false;
		}
		for (int row = 0; row < height; row++) {
			// если блок удраяется о блок

			if ((placement_table[row][width - 1] && matrix_placement_table[row + y][x + width - 1 + 1]) == true) {
				return false;
			}
		}
		// если всё хорошо
		for (Block block : blocks) {
			block.move_right();
		}
		x += 1;
		return true;
	}

	// сдвиг влево
	boolean move_left(boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {
		if (x - 1 < 0) { // если блок достиг границы поля
			return false;
		}
		for (int row = height - 1; row >= 0; row--) {
			// если блок удраяется о блок
			if ((placement_table[row][0] && matrix_placement_table[row + y][x - 1]) == true) {
				return false;
			}
		}
		// если всё хорошо
		for (Block block : blocks) {
			block.move_left();
		}
		x -= 1;
		return true;
	}

	private void set_color() { // случайный цвет
		Random random = new Random();
		int color_number = random.nextInt(images.size());
		this.image = images.get(color_number);
	}

	private void set_type() { // случайный вид фигуры
		Random random = new Random();
		int type_number = random.nextInt(2);
		if (type_number == 0) {

			this.placement_table = new boolean[][] { { true, true }, { true, true } };
			// квадрат 2 на 2
			this.blocks.add(new Block(0, 0, image, shell));
			this.blocks.add(new Block(0, 1, image, shell));
			this.blocks.add(new Block(1, 0, image, shell));
			this.blocks.add(new Block(1, 1, image, shell));

			this.height = 2;
			this.width = 2;
		} else if (type_number == 1) {
			this.placement_table = new boolean[][] { { true, true, true }, { false, true, false },
					{ false, true, false } };

			this.blocks.add(new Block(0, 0, image, shell));
			this.blocks.add(new Block(1, 0, image, shell));
			this.blocks.add(new Block(2, 0, image, shell));
			this.blocks.add(new Block(1, 1, image, shell));
			this.blocks.add(new Block(1, 2, image, shell));

			this.height = 3;
			this.width = 3;
		}
	}
}
