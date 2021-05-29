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

	Figure(int x, int y, ArrayList<Image> images, Shell shell) {
		this.x = x;
		this.y = y;
		this.shell = shell;
		this.images = images;
		this.blocks = new ArrayList<>();
		set_color();
		set_type();
	}

	// поворот фигуры
	public void rotate(boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {
		boolean[][] placement_table_to_check = MatrixInMatrix.matrixrotate(placement_table); // проверка на возможность поворота
		if (rotation_check(placement_table_to_check, matrix_placement_table, matrix_height, matrix_width) == true) {
			placement_table = placement_table_to_check; // изменение таблицы расположения фигуры
			rotate_blocks(); // изменение внешнего вида
		}
	}
	
	// проверка на возможность поворота
	private boolean rotation_check(boolean[][] placement_table_to_check, boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {
		if (height + x > matrix_width || width + y > matrix_height) { // ширина и высота меняются местами при повороте
			return false;
		}
		else {
			// если блок удраяется о блок при повороте
			for (int row = 0; row < width; row++) {
				for (int column = 0; column < height; column++) {
					if ((placement_table_to_check[row][column] && matrix_placement_table[row + y][column + x]) == true) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	// проверка на возможность появления
	public boolean appearance_check(boolean[][] placement_table_to_check, boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {
		if (width + x > matrix_width || height + y > matrix_height) {
			return false;
		}
		else {
			// если блок удраяется о блок при повороте
			for (int row = 0; row < width; row++) {
				for (int column = 0; column < height; column++) {
					if ((placement_table_to_check[row][column] && matrix_placement_table[row + y][column + x]) == true) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// сдвиг вниз
	boolean move_down(boolean[][] matrix_placement_table, int matrix_height, int matrix_width) {
		// координата + высота - 1 (тк индексация с нуля) + 1 (тк проверка следущего
		// шага)
		if (y + height - 1 + 1 > matrix_height - 1) { // если блок достиг границы поля
			return false;
		}
		// если блок удраяется о блок
		// я проверяю все блоки фигуры, а не только её край, тк фигура может иметь
		// выступ после края (например, т-фигура)
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if ((placement_table[row][column] && matrix_placement_table[row + y + 1][column + x]) == true) {
					return false;
				}
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
		// если блок удраяется о блок
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if ((placement_table[row][column] && matrix_placement_table[row + y][column + x + 1]) == true) {
					return false;
				}
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
		// если блок удраяется о блок
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if ((placement_table[row][column] && matrix_placement_table[row + y][column + x - 1]) == true) {
					return false;
				}
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
		int type_number = random.nextInt(3);
		if (type_number == 0) { // квадрат 2 на 2
			this.placement_table = new boolean[][] { { true, true }, { true, true } };
			add_blocks();

		} else if (type_number == 1) {
			this.placement_table = new boolean[][] { { true, true, true }, { false, true, false },
					{ false, true, false } };
			add_blocks();

		} else if (type_number == 2) {
			this.placement_table = new boolean[][] { { true, true, true } };
			add_blocks();

		}
	}

	// добавление блоков
	private void add_blocks() {
		this.height = placement_table.length;
		this.width = placement_table[0].length;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (placement_table[y][x] == true) { // если в таблице размещения true
					this.blocks.add(new Block(x + this.x, y + this.y, image, shell));
				}
			}
		}
	}

	private void rotate_blocks() {

		swap_H_W();

		int block_count = 0; // кол-во блоков с изм. координатами
		for (Block block : blocks) {
			boolean changed = false;
			int current_block_count = block_count; // кол-во блоков, которое нужно пропустить
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (placement_table[y][x] == true) { // если в таблице размещения true
						if (current_block_count > 0) { // пропуск блока
							current_block_count--;
							continue;
						}
						block.x = x + this.x;
						block.y = y + this.y;
						block.update_base(); // очень незаметная, но важная вещь (особенно когда фигуру поворачивают перед остановкой)
						changed = true;
						block_count += 1;
						break;
					}
				}
				if (changed == true) {
					break;
				}
			}
		}
	}

	// смена ширины высотой
	private void swap_H_W() {
		int buffer = this.height;
		this.height = this.width;
		this.width = buffer;
	}

}
