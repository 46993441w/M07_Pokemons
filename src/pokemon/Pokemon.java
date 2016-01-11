package pokemon;

import javafx.collections.ObservableList;

/**
 * Created by alumne on 10/12/15.
 */
public class Pokemon {
    private String nom;
    private String tipus;
    private int hp;
    private int defensa;
    private int atac;
    private int esp_atac;
    private int esp_def;
    private int velocitat;
    private String altura;
    private String pes;
    private int national_id;
    private ObservableList<Ataques> ataques;

    public Pokemon() {
    }

    public String getNom() {
        return nom;
    }

    public String getTipus() {
        return tipus;
    }

    public int getHp() {
        return hp;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getAtac() {
        return atac;
    }

    public int getEsp_atac() {
        return esp_atac;
    }

    public int getEsp_def() {
        return esp_def;
    }

    public int getVelocitat() {
        return velocitat;
    }

    public String getAltura() {
        return altura;
    }

    public String getPes() {
        return pes;
    }

    public int getNational_id() {
        return national_id;
    }

    public ObservableList<Ataques> getAtaques() {
        return ataques;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setAtac(int atac) {
        this.atac = atac;
    }

    public void setEsp_atac(int esp_atac) {
        this.esp_atac = esp_atac;
    }

    public void setEsp_def(int esp_def) {
        this.esp_def = esp_def;
    }

    public void setVelocitat(int velocitat) {
        this.velocitat = velocitat;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public void setPes(String pes) {
        this.pes = pes;
    }

    public void setNational_id(int national_id) {
        this.national_id = national_id;
    }

    public void setAtaques(ObservableList<Ataques> ataques) {
        this.ataques = ataques;
    }
}
