package ihm;

import javax.swing.JLabel;

public class Display {
	
	JLabel mainDisplay; //Affichage principal issue de la MainFrame
	
	public Display(JLabel mainDisplay) {
		this.mainDisplay = mainDisplay;
	}
	
	public void displayOperation(double number, String operator) {
		// Si le résultat de l'opération donne une nombre entier (décimale = 0), on n'affiche pas la décimale
		String strNumber = String.valueOf(number);
		int index = strNumber.indexOf(".");
		if (strNumber.substring(index+1).equals("0")){
			mainDisplay.setText((int)number + " " + operator +" ");
		} else {
			mainDisplay.setText(number + " " + operator +" ");
		}
	}
	
	public void displayResult (double result) {
		String strNumber = String.valueOf(result);
		int index = strNumber.indexOf(".");
		if (strNumber.substring(index+1).equals("0")){
			mainDisplay.setText(String.valueOf((int)result));
		} else {
			mainDisplay.setText(String.valueOf(result));
		}
	}
}
