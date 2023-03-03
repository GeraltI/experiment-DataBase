import JDBC.category.food;

import javax.swing.*;

public class BusinessFoodGUI {
    public JPanel MainPanel;
    private JTextField FoodNameField;
    private JTextField FoodPriceField;
    private JTextField FoodApplicationField;
    private JTextField FoodRestField;

    private food food;

    public BusinessFoodGUI(String foodName,double foodPrice,String foodApplication,Integer foodRest){
        food = new food();
        food.setFood_name(foodName);
        food.setFood_single_price(foodPrice);
        food.setFood_application(foodApplication);
        food.setFood_rest(foodRest);

        FoodNameField.setText(foodName);
        FoodPriceField.setText(foodPrice + "");
        FoodApplicationField.setText(foodApplication);
        FoodRestField.setText(foodRest + "");

    }

    public food getFood() {
        food.setFood_name(FoodNameField.getText());
        food.setFood_single_price(Double.parseDouble(FoodPriceField.getText()));
        food.setFood_application(FoodApplicationField.getText());
        food.setFood_rest(Integer.valueOf(FoodRestField.getText()));
        return food;
    }
}
