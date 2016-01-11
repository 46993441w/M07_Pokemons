package pokemon;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alumne on 10/12/15.
 */
public class PokeApi {
    public String BASE_HTML = "http://pokeapi.co/";
    /**
     * Mètode que es connecta amb la api i torna la resposta en un String
     * @param urlToRead
     * @return
     * @throws Exception
     */
    public String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    /**
     * Posar els pokemons al listView i guardar la referencia a api del pokemon
     * @param llistaPokemons
     * @param llistaPokemonsApi
     */
    public void getLlistaPokemon(ObservableList<String> llistaPokemons, ArrayList llistaPokemonsApi){
        try{
            String pokemons = getHTML( BASE_HTML + "api/v1/pokedex/" );
            JSONObject arra01 =(JSONObject)JSONValue.parse(pokemons);
            JSONArray arra02 = (JSONArray) arra01.get("objects");
            JSONObject jo = (JSONObject) arra02.get(0);
            JSONArray arra03 = (JSONArray) jo.get("pokemon");
            for(int i = 0; i < arra03.size(); i++) {
                JSONObject jo2 = (JSONObject) arra03.get( i );
                llistaPokemons.add((String) jo2.get("name"));
                llistaPokemonsApi.add(jo2.get("resource_uri"));
            }
        } catch (Exception e){
            System.out.println("Error en la peticion");
        }
    }

    public void getPokemon(String api, Pokemon poke){
        try{
            String pokemons = getHTML( BASE_HTML + api );
            String tipus ="";
            JSONObject arra01 =(JSONObject)JSONValue.parse(pokemons);
            JSONArray arra02 = (JSONArray) arra01.get("types");
            for(int i = 0; i < arra02.size(); i++) {
                JSONObject jo = (JSONObject) arra02.get( i );
                if(i > 0)
                    tipus += ", ";
                tipus += jo.get("name");
            }
            poke.setNom((String) arra01.get("name"));
            poke.setAltura((String) arra01.get("height"));
            poke.setAtac(Integer.parseInt(String.valueOf(arra01.get("attack"))));
            poke.setPes((String) arra01.get("weight"));
            poke.setDefensa(Integer.parseInt(String.valueOf(arra01.get("defense"))));
            poke.setEsp_atac(Integer.parseInt(String.valueOf(arra01.get("sp_atk"))));
            poke.setTipus(tipus);
            poke.setEsp_def(Integer.parseInt(String.valueOf(arra01.get("sp_def"))));
            poke.setHp(Integer.parseInt(String.valueOf(arra01.get("hp"))));
            poke.setVelocitat(Integer.parseInt(String.valueOf(arra01.get("speed"))));
            poke.setNational_id(Integer.parseInt(String.valueOf(arra01.get("national_id"))));
            Ataques ataque = new Ataques();
            JSONArray arra03 = (JSONArray) arra01.get("moves");
            ObservableList<Ataques> ataques = FXCollections.observableArrayList();
            for (int i = 0; i < arra03.size(); i++){
                JSONObject jo = (JSONObject) arra03.get( i );
                ataque.setName((String) jo.get("name"));
                ataque.setLevel(String.valueOf(jo.get("level")));
                ataque.setAprender((String) jo.get("learn_type"));
                ataques.add(ataque);
            }
            poke.setAtaques(ataques);
        } catch (Exception e){
            System.out.println("Error en la peticion");
        }
    }

    public String getPokemonImage(Pokemon poke) {
        String cadena = BASE_HTML;
        try {
            String pokemons = getHTML( BASE_HTML + "api/v1/sprite/" + (poke.getNational_id()+1) );
            JSONObject arra01 =(JSONObject)JSONValue.parse(pokemons);
            cadena += arra01.get("image");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cadena;
    }
}
