//package l17;

import java.util.Random;

public class MatrixInMatrix {

	public static void main(String[] args) {
		boolean[][] matrix1 = matrix(4, 4);
		// matrixprint(matrix1);
		System.out.println();
		boolean[][] matrix2 = matrix(3, 1);
		// matrixprint(matrix2);
		System.out.println();
		// matrixprint(matrixsum(matrix1, matrix2, 2, 2));
		matrixprint(matrixrotate(matrix2));
	}

	static boolean[][] matrix(int N, int M) {

		boolean[][] m = new boolean[N][M];
		Random rand = new Random();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				int num = rand.nextInt(2);
				if (num == 0) {
					m[i][j] = true;
				} else {
					m[i][j] = false;
				}
			}
		}

		return m;
	}

	static void matrixprint(boolean[][] m) {
		// вывод
		for (int i = 0; i < m.length; i++) {

			System.out.println();

			for (int j = 0; j < m[i].length; j++) {
				System.out.print(" ");
				// if (m[i][j] >= 0) {
				// System.out.print(" ");
				// }
				System.out.print(m[i][j]);
			}

		}
	}

	static boolean[][] matrixsum(boolean[][] m1, boolean[][] m2, int x, int y) {
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

	static boolean[][] matrixrotate(boolean[][] m) {
		// поворачивается вправо при большей ширине и влево при большей высоте...
		boolean[][] new_m = new boolean[m[0].length][m.length];
		for (int y = 0; y < new_m.length; y++) {
			for (int x = 0; x < new_m[0].length; x++) {
				new_m[y][x] = m[x][new_m.length - 1 - y];
			}
		}
		return new_m;
	}

}
