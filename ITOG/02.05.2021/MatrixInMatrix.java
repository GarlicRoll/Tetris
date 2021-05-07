package l17;

import java.util.Random;

public class MatrixInMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] matrix = matrix(4, 3);
		matrixprint(matrix);
		System.out.println();
		System.out.println();
		matrixprint(matrixrotate(matrix));
	}
	
	static int[][] matrix(int N, int M){
		
		int[][] m = new int[N][M];
		Random rand = new Random();
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				m[i][j] = rand.nextInt(2);
			}
		}
		
		return m;
	}
	
	static void matrixprint(int[][] m) {
		//вывод
		for (int i = 0; i < m.length; i++) {
							
			System.out.println();
							
			for (int j = 0; j < m[i].length; j++) {			
				System.out.print(" ");
				if (m[i][j] >= 0) {
					System.out.print(" ");
				}
				System.out.print(m[i][j]);	
			}
			
		}
	}
	
	static int[][] matrixsum(int[][] m1, int[][] m2, int x, int y) { 
		// m1 - исходная матрица
		// m2 - накладываемая матрица
		// x, y - координаты в исходной матрице, туда будем вставлять
		for (int m1_y = y; m1_y < m2.length; m1_y ++) {
			for (int m1_x = x; m1_x < m2[m1_y].length; m1_x ++) {
				m1[m1_y][m1_x] = m2[m1_y - y][m1_x - x];
			}
		}
		return m1;
	}
	
	static int[][] matrixrotate(int[][] m){
		int[][] new_m = new int[m[0].length][m.length];
		for (int y = 0; y < m.length; y ++) {
			for (int x = 0; x < m[0].length; x ++) {
				new_m[y][x] = m[x][m.length - 1 - y];
			}
		}
		return new_m;
	}

}
