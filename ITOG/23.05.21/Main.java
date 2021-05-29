package Tetris;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Main {

	static int TIME = 300; // мл секунды
	static int current_time = TIME;
	static int total_time = 0;
	static int total_minutes = 0;
	static int total_seconds = 0;
	
	static int BLOCK_SIZE = 32;
	
	static int game_table_height = 21;
	static int game_table_width = 10;
	
	static double acceleration_factor = 2;
	
	static int WINDOW_W = BLOCK_SIZE * game_table_width + BLOCK_SIZE * 5;
	static int WINDOW_H = BLOCK_SIZE * game_table_height + BLOCK_SIZE * 2;
	
	static boolean acceleration = false;
	
	static boolean game_is_on = true;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(WINDOW_W, WINDOW_H);

		ArrayList<String> images_paths = new ArrayList<>(); // добавление путей картинок (цвета блоков)
		String source = "src/Tetris/images/";
		images_paths.add(source + "blue.jpg");
		images_paths.add(source + "green.jpg");
		images_paths.add(source + "purple.jpg");
		images_paths.add(source + "red.jpg");
		ArrayList<Image> images = make_images(images_paths, display); // преобразование в картинки
		///////

		Matrix matrix = new Matrix(game_table_height, game_table_width); // y и x
		
		Label label_timer = new Label(shell, SWT.BORDER | SWT.CENTER); // время 
		label_timer.setBounds(matrix.width * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE * 4, BLOCK_SIZE * 2);
		label_timer.setFont(new Font(display, source, 30, 10));
		
		Label label_score = new Label(shell, SWT.BORDER | SWT.CENTER); // очки
		label_score.setBounds(matrix.width * BLOCK_SIZE, BLOCK_SIZE * 3, BLOCK_SIZE * 4, BLOCK_SIZE * 2);
		label_score.setFont(new Font(display, source, 30, 10));
		
		///////
		
		Runnable timer = new Runnable() { // таймер для игровых вычислений
			public void run() {
				if (game_is_on == true) {
					if (matrix.figures.size() == 0) {
						Random random = new Random();
						int x = random.nextInt(matrix.width - 3); // -3 для правой границы, чтобы никто там точно не
																	// появился
						if (matrix.add_figure(new Figure(x, 0, images, shell), matrix.placement_table, matrix.height, matrix.width) == false) { // добавление фигуры
							game_is_on = false;
						}
						label_score.setText(String.valueOf(matrix.score));
						acceleration = false;
						current_time = (int) (TIME * acceleration_factor);
						
						if (acceleration_factor > 60){ // ограничение для ускорения
							acceleration_factor /= 1.1;
						}
					}
					total_time += current_time;
					total_minutes = total_time / 1000 / 60;
					total_seconds = total_time / 1000 % 60;
					label_timer.setText(String.valueOf(total_minutes) + " : "+ String.valueOf(total_seconds)); // установка текста
					matrix.figure_shift_loop();
					display.timerExec(current_time, this);
				}
			}
		};
		
		display.timerExec(current_time, timer);

		shell.open();

		shell.addKeyListener(new KeyAdapter() { // управление
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN && acceleration == false) {
					if (matrix.figures.size() != 0) {
						current_time = 50;
						acceleration = true;
					}
				}
				// acceleration == true - фигура быстро падает вниз, поворачивать и двигать её уже нельзя
				if (e.keyCode == SWT.ARROW_LEFT && acceleration == false) {
					if (matrix.figures.size() != 0) {
						matrix.figures.get(0).move_left(matrix.placement_table, matrix.height, matrix.width);
					}
				}
				if (e.keyCode == SWT.ARROW_RIGHT && acceleration == false) {
					if (matrix.figures.size() != 0) {
						matrix.figures.get(0).move_right(matrix.placement_table, matrix.height, matrix.width);
					}
				}
				if (e.keyCode == SWT.SPACE && acceleration == false) {
					if (matrix.figures.size() != 0) {
						matrix.figures.get(0).rotate(matrix.placement_table, matrix.height, matrix.width);
					}
				}
			}
		});

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		// disposes all associated windows and their components
		display.dispose();

	}

	private static ArrayList<Image> make_images(ArrayList<String> images_paths, Display display) {
		ArrayList<Image> images = new ArrayList<>();
		for (String path : images_paths) {
			images.add(new Image(display, path));
		}
		return images;
	}
}