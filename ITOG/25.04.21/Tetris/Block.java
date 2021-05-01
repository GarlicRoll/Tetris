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
	
	Block(int x, int y, Image image, Shell shell){
		this.x = x;
		this.y = y;
		this.image = image;
		this.base = new Label(shell, SWT.BORDER | SWT.CENTER);
	}
	
	void move_right() {
		
	}
	
	void move_left() {
		
	}
	
	void move_down() {
		
	}
	
}
