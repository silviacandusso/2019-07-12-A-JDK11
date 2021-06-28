/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodCalories;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	int porz=0;
    	try {
    		porz=Integer.parseInt(this.txtPorzioni.getText());
    	}catch(NumberFormatException ne) {
    		txtResult.appendText("Il numero deve essere intero");
    		return;
    	}
    	if(porz<0) {
    		txtResult.appendText("Il numero deve essere positivo");
    		return;
    	}
    	this.model.creaGrafo(porz);
    	List<Food>vertici= new ArrayList<Food>(this.model.getVerticiv());
    	this.boxFood.getItems().addAll(vertici);
    	this.btnCalorie.setDisable(false);
    	txtResult.appendText("#VERTICI: "+model.getVertici()+"\n#ARCHI: "+model.getArchi()+"\n");
    	
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Analisi calorie...\n");
    	Food f= this.boxFood.getValue();
    	if(f==null) {
    		txtResult.appendText("Seleziona un cibo");
    		return;
    	}
    	
    	List<FoodCalories> maxCalorie= this.model.calorie5(f);
    	
    		for(int i=0; i<5 && i<maxCalorie.size(); i++) {
        		txtResult.appendText(maxCalorie.get(i).getF()+" calorie "+maxCalorie.get(i).getCalorie()+"\n");
      	  }
    	
    	this.btnSimula.setDisable(false);
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...\n");
    	int k=-1;
    	try {
    		k=Integer.parseInt(this.txtK.getText());
    	}catch(NumberFormatException ne) {
    		txtResult.appendText("Inserire un numero intero tra 1 e 10");
    		return;
    	}
    	if(k<1||k>10) {
    		txtResult.appendText("Inserire un numero intero tra 1 e 10");
    		return;
    	}
    	this.model.simula(k,this.boxFood.getValue());
    	
    	Double tempo=model.getTempoTot();
    	int ore=(int)(tempo/60);
    	double minuti= (tempo%60);
    	txtResult.appendText("Tempo di preparazione totale= "+tempo+" minuti\ncioè "+ore+" ore e "+minuti+" minuti\n");
    	txtResult.appendText("Cibi preparati in totale: "+model.getCibiPreparati());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnCalorie.setDisable(true);
    	this.btnSimula.setDisable(true);
    }
}
