//package Tetris;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

	static int time = 300; // мл секунды
	static int WINDOW_W = 900;
	static int WINDOW_H = 1000;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(WINDOW_W, WINDOW_H);

		ArrayList<String> images_paths = new ArrayList<>(); // добавление путей картинок (цвета блоков)
		String source = "src/images/";
		images_paths.add(source + "blue.jpg");
		images_paths.add(source + "green.jpg");
		images_paths.add(source + "purple.jpg");
		images_paths.add(source + "red.jpg");
		ArrayList<Image> images = make_images(images_paths, display); // преобразование в картинки
		///////

		Matrix matrix = new Matrix(28, 21);

		///////

		Runnable timer = new Runnable() {
			public void run() {
				if (matrix.figures.size() == 0) {
					matrix.add_figure(new Figure(images, shell));
					time = 300;
				}
				matrix.figure_shift_loop();
				display.timerExec(time, this);
			}
		};
		display.timerExec(time, timer);

		shell.open();

		shell.addKeyListener(new KeyAdapter() { // управление
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_UP) {
				}
				if (e.keyCode == SWT.ARROW_DOWN) {
					if (matrix.figures.size() != 0) {
						time = 50;
					}
				}
				if (e.keyCode == SWT.ARROW_LEFT) {
					if (matrix.figures.size() != 0) {
						matrix.figures.get(0).move_left(matrix.placement_table, matrix.height, matrix.width);
					}
				}
				if (e.keyCode == SWT.ARROW_RIGHT) {
					if (matrix.figures.size() != 0) {
						matrix.figures.get(0).move_right(matrix.placement_table, matrix.height, matrix.width);
					}
				}
				if (e.keyCode == SWT.SPACE) {
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
