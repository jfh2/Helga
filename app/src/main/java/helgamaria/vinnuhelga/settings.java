package helgamaria.vinnuhelga;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import helgamaria.vinnuhelga.sql.dbFunctions;


public class settings extends ActionBarActivity {
    dbFunctions dbFunc = new dbFunctions(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //adding listeners
        deleteButtonListener();
        addConstantListener();
    }

    private void deleteButtonListener(){
        //listener to delete all jobs button
        Button button = (Button)findViewById(R.id.deleteAllJobs);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(settings.this)
                        .setMessage("Are you sure you want to ereas all jobs?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                dbFunc.deleteAllJobs();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                            }
                        }).show();
            }
        });
        //listener to delete all constants button
        button = (Button)findViewById(R.id.deleteAllConstants);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(settings.this)
                        .setMessage("Are you sure you want to ereas all defined names?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                dbFunc.deleteAllConstants();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                            }
                        }).show();
            }
        });
    }

    private void addConstantListener(){
        /*
        TODO: setja í loopu ef hægt
         */
        Button button;

        int [] buttons = {R.id.addtoOnebutton,R.id.addtoTwobutton,
                R.id.addToThreebutton,R.id.addToFourButton,
                R.id.addToFiveButton,R.id.addToSixButton};
        final int [] texts = {R.id.addToOne, R.id.addToTwo,
                R.id.addToThree,R.id.addToFour,
                R.id.addToFive,R.id.addToSix};
        //add to number 1
        for(int i = 0; i< buttons.length; i++){

            button = (Button)findViewById(buttons[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText ed;
                    ed = (EditText)findViewById(texts[i]);
                    String text = ed.getText().toString();
                    if(text.equals("")){
                        new AlertDialog.Builder(settings.this)
                                .setMessage("cannot be empty").setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do Nothing
                            }
                        }).show();
                    }else{
                        dbFunc.insertOneConstant(text, "1");
                        ed.setText("");
                    }
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
