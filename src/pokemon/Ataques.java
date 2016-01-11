package pokemon;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by alumne on 18/12/15.
 */
public class Ataques {
    StringProperty name;
    StringProperty level;
    StringProperty aprender;

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "Nom");
        return name;
    }

    public void setName(String name) {
        nameProperty().set(name);
    }

    public String getLevel() {
        return level.get();
    }

    public StringProperty levelProperty() {
        if (level == null) level = new SimpleStringProperty(this, "Nivell");
        return level;
    }

    public void setLevel(String level) {
        levelProperty().set(level);
    }

    public String getAprender() {
        return aprender.get();
    }

    public StringProperty aprenderProperty() {
        if (aprender == null) aprender = new SimpleStringProperty(this, "Manera");
        return aprender;
    }

    public void setAprender(String aprender) {
        aprenderProperty().set(aprender);
    }
}
