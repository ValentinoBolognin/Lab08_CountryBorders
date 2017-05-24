/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	public void setModel(Model model) {
		this.model = model;	
	}
	
	@FXML
	void doCalcolaConfini(ActionEvent event) {
		
		txtResult.clear();
		
		try {
		
			int anno = Integer.parseInt(txtAnno.getText());
			
			if(anno < 1816 || anno > 2006){
				txtResult.setText("Inserire un anno nel range 1816 - 2006.");
				return;
			}
			
			model.creaGrafo(anno);
		
			int componentiConnesse = model.componentiConnesse();
			txtResult.appendText("Componenti connesse: "+componentiConnesse+"\n\n");
			
			List<Country> stati = model.trovaStati();
			
			for(Country stato : stati) {
				int confini = model.statiConfinanti(stato);
				txtResult.appendText("Stato: "+stato.toString()+"   Stati confinanti: "+confini+"\n");
			}
		
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire il numero di lettere nel formato corretto.");
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}

}
