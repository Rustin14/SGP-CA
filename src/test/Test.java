package test;


import javafx.scene.control.TextField;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        TextField campo1 = new TextField();
        TextField campo2 = new TextField();
        TextField campo3 = new TextField();
        ArrayList<TextField> campos = new ArrayList<>();
        campos.add(campo1);
        campos.add(campo2);
        campos.add(campo3);
        for (Field Field : campos.getClass().getFields()){
            System.out.println(Field.getName());
        }
    }
}
