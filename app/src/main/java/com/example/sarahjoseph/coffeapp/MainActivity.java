package com.example.sarahjoseph.coffeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.sarahjoseph.coffeapp.R.id.addresse_field_view;
import static com.example.sarahjoseph.coffeapp.R.id.text;


public class MainActivity extends AppCompatActivity {

    //initialize quantity to zero and made it global variable
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * create an object variable for the addition button
     * i call the global variable quantity so that it increases by one each
     * time the button is clicked
     */
    public void increment(View view) {
        if (quantity == 100) {
            //You can only order 100 cups of coffee at most
            Toast.makeText(this, "You can only oder 100 cups of Coffees at a time", Toast.LENGTH_SHORT).show();
            //returns a value
            return;
        }
            //This adds up 1 to the quantity already stored
            quantity += 1;
        //Displays the quantity ordered in terms of decrements
        displayQuantity(quantity);

    }

    /**
     * create an object variable for the subtraction button
     * i call the global variable quantity so that it decreases by one each
     * time the button is clicked
     */


    public void decrement(View view) {
        //use a conditional statement
        if (quantity == 1) {
            //the coffee order cant be less than 1 and displays a message to indicate that.
            Toast.makeText(this, "You cant order less than one cup of Coffee", Toast.LENGTH_SHORT).show();
            //returns a value
            return;
        }
        //this makes it possible to reduce the number of cupds ordered by 1
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * the submit order
     *
     * @param view displays the result of the orders made
     */
    public void submitOder(View view) {

        EditText nameFieldView = (EditText) findViewById(R.id.name_field_view);
        String name = nameFieldView.getText().toString();

        EditText addresseFieldView = (EditText) findViewById(R.id.addresse_field_view);
        String addresse = addresseFieldView.getText().toString();
        


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedcheckedBox_text_view);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainAcivity", "Has whipped Cream" + hasWhippedCream);

        CheckBox chocolateCreamCheckBox = (CheckBox) findViewById(R.id.chocoBox_text_view);
        boolean hasChocolateCream = chocolateCreamCheckBox.isChecked();
        Log.v("MainAcivity", "Has Chocolate Cream" + hasChocolateCream);


        int price = calculatePrice(hasChocolateCream, hasWhippedCream);

        String priceMessage = createOrderSummary(addresse, name, price, hasWhippedCream, hasChocolateCream);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee order summary for " +  "\n" + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * calculates the price of a cup of coffee
     *
     * @return value is an integer
     */

    private int calculatePrice(boolean addChocolateCream, boolean addWhippedCream) {

        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolateCream) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * creates a summary of the order
     *
     * @param price inbeding the price return value
     * @return the value here is a string
     */

    private String createOrderSummary(String addresse,String name,int price, boolean addWhppedCream, boolean addChocoCream) {


        String priceMessage  = "\nAddresse :" + addresse;
        priceMessage += "\nChocolate Cream " + addChocoCream;
        priceMessage += "\nWhipped cream " + addWhppedCream;
        priceMessage = priceMessage + "\nQuantity : " + quantity + " cups of Coffee";
        priceMessage = priceMessage + "\nTotal $ " + price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you) ;
        return priceMessage;
    }

    /**
     * it displays the number of cups of coffee need by a customer
     *
     * @param number is definitely an integer value
     */

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * displaymessage of the order summary on the screen
     * @param message
     */


}
