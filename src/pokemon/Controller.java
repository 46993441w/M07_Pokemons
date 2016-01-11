package pokemon;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Optional;

public class Controller {
    public ListView lvPokemon;
    public ObservableList<String> llistaPokemons = FXCollections.observableArrayList();
    public ArrayList llistaPokemonsApi = new ArrayList();
    public Label txtId;
    public Label txtNom;
    public Label txtTipus;
    public Label txtHP;
    public Label txtAtac;
    public Label txtDefense;
    public Label txtSp_Atc;
    public Label txtSp_Def;
    public Label txtVel;
    public Label txtAlt;
    public Label txtPes;
    public ImageView imgPokemon;
    public TextInputDialog dialog;
    public Alert alert;
    public ScrollPane scrollPane;
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
    public TableView tableAtacs;
    public TableColumn tableColumnNom;
    public TableColumn tableColumnManera;
    public TableColumn tableColumnNivell;

    /**
     * Mètode que s'inicia automaticament al començament
     */
    public  void initialize(){
        zoomscroll();
        PokeApi pokeApi = new PokeApi();
        pokeApi.getLlistaPokemon(llistaPokemons,llistaPokemonsApi);
        lvPokemon.setItems(llistaPokemons);
        lvPokemon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int fname = lvPokemon.getFocusModel().getFocusedIndex();
                String api = (String) llistaPokemonsApi.get(fname);
                System.out.println(llistaPokemonsApi.get(fname));
                mostrarPokemon(api);
            }
        });
        createDialog();
    }

    private void zoomscroll() {
        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                imgPokemon.setFitWidth(zoomProperty.get() * 2);
                imgPokemon.setFitHeight(zoomProperty.get() * 3);
            }
        });
        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
            }
        });
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void dialogID(ActionEvent actionEvent) {
        modificaDialog( "ID" );
    }

    public void dialogNOM(ActionEvent actionEvent) {
        modificaDialog("NOM");
    }

    public void modificaDialog(String tipus){
        dialog.setTitle( "Buscar per " + tipus );
        dialog.setHeaderText( "Escriu el " + tipus + " del Pokemon que vols buscar" );

        Optional<String> result = dialog.showAndWait();
        String entered = "";

        if ( result.isPresent() ) {
            entered = result.get();
        }

        String msg = "Text entered: ";
        if( !entered.isEmpty() ) {
            if ( tipus.equals("ID") && entered.matches( "[0-9]+" ) ) {
                System.out.println( "id correcto" );
                String api = "api/v1/pokemon/" + entered;
                mostrarPokemon(api);
            } else if ( tipus.equals( "NOM" ) && entered.matches( "[a-zA-Z]+" ) ) {
                System.out.println( "nombre correcto" );
                for ( int i = 0; i < llistaPokemons.size(); i++ ){
                    if(llistaPokemons.get(i).equalsIgnoreCase(entered)){
                        String api = (String) llistaPokemonsApi.get(i);
                        mostrarPokemon(api);
                    }
                }
            } else if ( tipus.equals("ID") ){
                String s = "El text no pot contenir lletres.";
                alert.setContentText( s );
                alert.show();
                msg = "Invalid text entered: ";
            } else {
                String s = "El text no pot contenir números.";
                alert.setContentText( s );
                alert.show();
                msg = "Invalid text entered: ";
            }
        } else {
            String s = "El text no pot estar en blanc.";
            alert.setContentText( s );
            alert.show();
            msg = "Invalid text entered: ";
        }

        System.out.println( msg + entered );
    }

    public void createDialog(){
        dialog = new TextInputDialog("Pokemon que vols Buscar");
        alert = new Alert( Alert.AlertType.ERROR );
        alert.setTitle( "ERROR" );
    }

    public void mostrarPokemon(String api){
        Pokemon poke = new Pokemon();
        PokeApi pokeApi = new PokeApi();
        pokeApi.getPokemon(api, poke);
        // agafo la imatge de internet
        Image fxImage = new Image(pokeApi.getPokemonImage(poke));
        // col·loco la imatge al imageView
        imgPokemon.setImage(fxImage);
        txtId.setText(String.valueOf(poke.getNational_id()));
        txtNom.setText(poke.getNom());
        txtTipus.setText(poke.getTipus());
        txtHP.setText(String.valueOf(poke.getHp()));
        txtAtac.setText(String.valueOf(poke.getAtac()));
        txtDefense.setText(String.valueOf(poke.getDefensa()));
        txtSp_Atc.setText(String.valueOf(poke.getEsp_atac()));
        txtSp_Def.setText(String.valueOf(poke.getEsp_def()));
        txtVel.setText(String.valueOf(poke.getVelocitat()));
        txtAlt.setText(poke.getAltura());
        txtPes.setText(poke.getPes());
        mostrarAtaques(poke);
    }

    private void mostrarAtaques(Pokemon poke) {
        tableAtacs.setItems(poke.getAtaques());
        tableColumnNom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ataques, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ataques, String> p) {
                // p.getValue() returns the Person instance for a particular TableView row
                return p.getValue().nameProperty();
            }
        });
        tableColumnNivell.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ataques, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ataques, String> p) {
                // p.getValue() returns the Person instance for a particular TableView row
                return p.getValue().levelProperty();
            }
        });
        tableColumnManera.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ataques, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ataques, String> p) {
                // p.getValue() returns the Person instance for a particular TableView row
                return p.getValue().aprenderProperty();
            }
        });

        tableAtacs.getColumns().setAll(tableColumnNom, tableColumnNivell, tableColumnManera );
    }

}
