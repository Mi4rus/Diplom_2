package praktikum.order;

import java.util.ArrayList;


public class Order {
    private final ArrayList<String> ingredients;

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public static Order orderWithoutIngredients(){
        return new Order(new ArrayList<>());
    }

    public static Order orderWithWrongIngredients(){
        return new Order(new ArrayList<>(){
            {add("111111111111111111");}});
    }

    public Order(ArrayList<String> ingredients){
        this.ingredients = ingredients;
    }

}
