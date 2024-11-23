package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import calculations.Operation;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
		JPanel container = new JPanel();
		JPanel group1Container = new JPanel(); 
		JPanel group2Container = new JPanel();
		
		JPanel displayContainer = new JPanel();
		JLabel mainDisplay = new JLabel("0", SwingConstants.RIGHT); // Affichage principal
		
		Dimension dim1 = new Dimension(60,50);
		Dimension dim2 = new Dimension(60,40);
		
		Font buttonFont = new Font("Arial", Font.BOLD, 16);
		Font resultFont = new Font("Arial", Font.BOLD, 22);
		
		String [] tab_string = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "x", "/"};
		JButton [] tab_buttons = new JButton[tab_string.length];
		
		private double number1 = 0;
		private double number2 = 0;
		private double result = 0;
		String operator;
		
		boolean isOperator = false;
		boolean isDecimal = false;
		String text = "";
		

		public MainFrame() {
			this.setTitle("Calculatrice");
			this.setSize(320,350);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setResizable(false);
					
			// ---- Buttons -------------------
			
			// Group1
			for (int i = 0; i<=11; i++) {
				tab_buttons[i] = new JButton(tab_string[i]);
				tab_buttons[i].addActionListener(new ButtonListener());
				tab_buttons[i].setFont(buttonFont);
				tab_buttons[i].setPreferredSize(dim1); 
				group1Container.add(tab_buttons[i]);
			}
			
			//Group2
			for (int i = 12; i<=16; i++) {
				tab_buttons[i] = new JButton(tab_string[i]);
				tab_buttons[i].addActionListener(new ButtonListener());
				tab_buttons[i].setFont(buttonFont);
				tab_buttons[i].setPreferredSize(dim1);
				if (tab_string[i].equals("C")) {
					tab_buttons[i].setForeground(Color.RED);
				}
				group2Container.add(tab_buttons[i]);
			}
			
			// ---- Display -----------------------------
			
			mainDisplay.setPreferredSize(new Dimension(this.getWidth() - 36, 40));
			mainDisplay.setBorder(new EmptyBorder(0,0,0,10));
			mainDisplay.setFont(resultFont);
			
			displayContainer.setBorder(new LineBorder(Color.black, 1));
			displayContainer.setBackground(Color.white);
			
			displayContainer.add(mainDisplay);
			
			// ---- Containers --------------------------
			
			GridLayout gl1 = new GridLayout(4,3);
			gl1.setHgap(5);
			gl1.setVgap(5);
			group1Container.setLayout(gl1);
			
			GridLayout gl2 = new GridLayout(5,1);
			gl2.setHgap(5);
			gl2.setVgap(5);
			group2Container.setLayout(gl2);
			
			BorderLayout bl = new BorderLayout();
			bl.setHgap(10);
			bl.setVgap(10);
			container.setLayout(bl);
			container.setBorder(new EmptyBorder(10,10,10,10));
			container.add(displayContainer, BorderLayout.NORTH);
			container.add(group1Container, BorderLayout.CENTER);
			container.add(group2Container, BorderLayout.EAST);
			
			this.getContentPane().add(container);
			this.setVisible(true);
		}
		
		
		private void reset(String operator) {
			// Choix de l'élément neutre pour ne pas fausser le chainage d'opérations
			if (operator.equals("+") || operator.equals("-")) {
				number1 = 0;
			} else {
				number1 = 1;
			}
			
			text="";
			isOperator = true;
			isDecimal = false;
		}
		
		private void resetAll() {
			number1 = 0;
			number2 = 0;
			result = 0;
			text="";
			isOperator = false;
			isDecimal = false;
			operator = null;
			mainDisplay.setText("0");
		}
		
		private class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = e.getActionCommand();
				Display display = new Display(mainDisplay);
				Operation operation = new Operation();
				
				switch (value) {
				
				case "+":
					number2 = operation.calculateNumber2(isOperator, number1, number2, operator);
					operator = "+";
					display.displayOperation(number2, operator);
					reset(operator);
					break;
				
				case "-":
					number2 = operation.calculateNumber2(isOperator, number1, number2, operator);
					operator = "-";
					display.displayOperation(number2, operator);
					reset(operator);
					break;
				
				case "x":
					number2 = operation.calculateNumber2(isOperator, number1, number2, operator);
					operator = "x";
					display.displayOperation(number2, operator);
					reset(operator);
					number1 = 1;
					break;
				
				case "/":
					number2 = operation.calculateNumber2(isOperator, number1, number2, operator);
					System.out.println(number2);
					operator = "/";
					display.displayOperation(number2, operator);
					reset(operator);
					number1 = 1;
					break;
				
				case "=":
					result = operation.calculate(operator, number2, number1);
					display.displayResult(result);
					// On réinitialise les variables pour un nouveau calcul. On garde en mémoire le résultat de l'ancienne
					// opération dans "number1" pour pouvoir chaîner les opérations si besoin.
					reset(operator);
					number1 = result;
					number2 = 0;
					isOperator=false;
					break;
				
				case "C":
					// On réinitialise toutes les variables de calcul
					resetAll();
					break;
				
				case ".":
					if (!isDecimal) {
						// Indique qu'une décimale est présente (évite deux points dans le même nombre
						isDecimal = true;
						text += value;
						mainDisplay.setText(text);
						try {
							number1 = Double.parseDouble(text);
						} catch (NumberFormatException e2) {
							e2.printStackTrace();
						}
						number1 = Double.parseDouble(text);
						break;
					}
					break;

				default:
					// Si une opération est en cours ou si l'afficheur affiche "0", on efface le texte 
					// pour afficher correctement les nouvelles valeurs
					if (isOperator || mainDisplay.getText().equals("0")) {
						mainDisplay.setText("");
					}
					// On ajoute la valeur reçue au texte déjà affiché pour obtenir un nombre (plusieurs chiffres)
					text += value;
					mainDisplay.setText(text);
					// On enregistre le nombre affiché dans la variable number1 pour effectuer le calcul
					try {
						number1 = Double.parseDouble(text);
					} catch (NumberFormatException e2) {
						e2.printStackTrace();
					}
					break;
				
				}
				
			}
			
		}
}


