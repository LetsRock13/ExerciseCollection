
public class GameOfLive {

	private final int FIELD = 50;
	private final char DEAD = 'x';
	private final char ALLIVE = '1';
	private char[][] field = new char[FIELD / 2][FIELD];

	private char[][] startPatern = {{'x','x','1'},
									{'x','1','x'},
									{'x','x','1'}};

	public GameOfLive() {
		run();
	}

	private final void run() {

		//System.out.println("FIELD length y" + field.length);
		//System.out.println("startPatern length " + startPatern.length);

		// Start -> Initialize and render the field
		for(int i = 0; i < FIELD / 2; i++) {
			for(int j = 0; j < FIELD; j++) {
				field[i][j] = DEAD;
			}
		}

		int k = 0;
		for(int i = field.length / 2 - startPatern.length; i <= i + startPatern.length - 1; i++) {
			int l = 0;

			//System.out.println("i = " + i);
			//System.out.println("k = " + k);

			for(int j = FIELD / 2 - startPatern.length; j <= j + startPatern.length - 1; j++) {

				//System.out.println("j = " + j);
				//System.out.println("l = " + l);

				field[i][j] = startPatern[k][l];
				
				//System.out.println(String.format("field[%d][%d] = %c",i,j, field[i][j]));
				//System.out.println(String.format("startPatern[%d][%d] = %c",k,l, startPatern[k][l]));

				if(l == 2) break;

				l++;
			}

			if(k == 2) break;	

			k++;
		}

		renderField(field);
		// End -> Initialize and render the field
		

		// Rules from Wikipedia -> https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens
		// 1. Stays alive >= 2 && <= 3 neighboars
		// xx1
		// x1x Middle cell stays alive
		// xx1
		//
		// 2. Dead cell will be born == 3 neighboars 
		// xxx
		// 1xx Middle cell will be born
		// x11
		//
		// 3. Dies < 2 || > 3 ->
		
		for(int i = 0; i < FIELD / 2; i++) {
			for( int j = 0; j < FIELD; j++) {
				if (i == 0 && j == 0) {
				// TODO: Check neighboars and sum the living up	
				}
			}
		}
	}
		
	private void renderField(char[][] field) {

		StringBuilder strBuilder = new StringBuilder();

		for(int i = 0; i < FIELD / 2; i++) {
			for(int j = 0; j < FIELD; j++) {
				strBuilder.append(field[i][j]);
			}
			strBuilder.append("\n");
		}

		System.out.println(strBuilder.toString());
	}

	public static void main(String[] args) {
		new GameOfLive();
	}	

}
