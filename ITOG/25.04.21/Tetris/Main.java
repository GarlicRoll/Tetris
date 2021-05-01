package Tetris;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import l27.snake_game.Snake;

public class Main {
	
	static int WINDOW_W = 500;
	static int WINDOW_H = 500;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(WINDOW_W, WINDOW_H);
		
		ArrayList<String> images_paths = new ArrayList<>(); // добавление путей картинок (цвета блоков)
		String source = "src/Tetris/images/";
		images_paths.add(source + "blue.png");
		images_paths.add(source + "green.png");
		images_paths.add(source + "purple.png");
		images_paths.add(source + "red.png");
		ArrayList<Image> images = make_images(images_paths, display); // преобразование в картинки
		
		int time = 200; // мл секунды
		Runnable timer = new Runnable() {
			public void run() {
				display.timerExec(time, this);
			}

		};
		display.timerExec(time, timer);

		shell.open();

		shell.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_UP) {
				}
				if (e.keyCode == SWT.ARROW_DOWN) {
				}
				if (e.keyCode == SWT.ARROW_LEFT) {
				}
				if (e.keyCode == SWT.ARROW_RIGHT) {
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
		for (String path: images_paths) {
			images.add(new Image(display, path));
		}
		return images;
	}

}
