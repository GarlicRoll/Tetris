package Tetris;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Block { // класс блока

	Image image; // картинка для блока
	Label base; // физическая метка, которую можно двигать
	Shell shell; // оболочка
	int x; // координаты в матрице для обработки столкновений
	int y;
	int width;
	int height;

	Block(int x, int y, Image image, Shell shell) {
		this.width = 32;
		this.height = 32;
		this.x = x;
		this.y = y;
		this.image = image;
		this.base = new Label(shell, SWT.BORDER | SWT.CENTER);
		install_image();
	}

	protected void move_right() {
		x += 1;
		update_base();
	}

	protected void move_left() {
		x -= 1;
		update_base();
	}

	protected void move_down() {
		y += 1;
		update_base();
	}

	protected void update_base() {
		base.setLocation(x * width, y * height);
	}

	private void install_image() {
		base.setBounds(x, y, width, height);
		base.setImage(image);
	}

}
