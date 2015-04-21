package helgamaria.vinnuhelga;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import helgamaria.vinnuhelga.sql.dbFunctions;

public class settings extends ActionBarActivity {
    dbFunctions dbFunc = new dbFunctions(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //adding listeners
        deleteButtonListener();
        addTheGodDamnListener();
        createRoleListener();
        //get roles and add to spinner
        getAllRolesToSpinner();
    }
    private void getAllRolesToSpinner(){
        List<String> roles = dbFunc.getAllRoles();
        for(int i = 0; i < roles.size(); i++){
            System.out.println(roles.get(i));
        }

    }

    private void createRoleListener(){
        Button button = (Button)findViewById(R.id.addRole);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final Dialog dialog = new Dialog(settings.this
                );
                dialog.setContentView(R.layout.create_role_dialog);
                dialog.setTitle("Add role:");

                // set the custom dialog components - text, image and button
                Button dialogButtonOk = (Button) dialog.findViewById(R.id.roleDialogOk);
                Button dialogButtonCancel = (Button)dialog.findViewById(R.id.roleDialogCancel);
                //if cancel button is pressed close dialog
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // if button is clicked, save the string to database
                dialogButtonOk.setOnClickListener(new View.OnClickListener() {

                    final EditText ed = (EditText)findViewById(R.id.role_name_to_save);
                    String save_string = ed.getText().toString();

                    @Override
                    public void onClick(View v) {


                        try{
                            if(save_string.equals("") || save_string == null){
                                Toast.makeText(getApplicationContext(), "Must specify a name", Toast.LENGTH_LONG).show();
                            }else{
                                dbFunc.createRole(save_string);
                                Toast.makeText(getApplicationContext(), "Role created", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Couldn't create role", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
    });
    }
    private void deleteButtonListener(){
        //listener to delete all jobs button
        Button button = (Button)findViewById(R.id.deleteAllJobs);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(settings.this)
                        .setMessage("Are you sure you want to delete all jobs?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                try{
                                    dbFunc.deleteAllJobs();
                                    Toast.makeText(getApplicationContext(), "All jobs deleted successfully", Toast.LENGTH_LONG).show();
                                }catch(Exception e){
                                    Toast.makeText(getApplicationContext(), "Failed to delete, please try again", Toast.LENGTH_LONG).show();
                                }
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
                        .setMessage("Are you sure you want to delete all defined job names?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                try{
                                    dbFunc.deleteAllConstants();
                                    Toast.makeText(getApplicationContext(), "All defined job names deleted successfully", Toast.LENGTH_LONG).show();
                                }catch(Exception e){
                                    Toast.makeText(getApplicationContext(), "Failed to delete, please try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                            }
                        }).show();
            }
        });
    }
    private void addTheGodDamnListener(){
        int [] buttons = {R.id.addtoOnebutton,R.id.addtoTwobutton,
                R.id.addToThreebutton,R.id.addToFourButton,
                R.id.addToFiveButton,R.id.addToSixButton};
        int [] texts = {R.id.addToOne, R.id.addToTwo,
                R.id.addToThree,R.id.addToFour,
                R.id.addToFive,R.id.addToSix};
        for(int i = 0; i < buttons.length; i++){
            String number = "";
            switch (i){
                case 0:
                    number = "1";
                    break;
                case 1:
                    number = "2";
                    break;
                case 2:
                    number = "3";
                    break;
                case 3:
                    number = "4";
                    break;
                case 4:
                    number = "5";
                    break;
                case 5:
                    number = "6";
                    break;
            }
            EditText ed = (EditText)findViewById(texts[i]);
            Button button = (Button)findViewById(buttons[i]);
            addConstantListener(button, ed, i, number);
        }
    }

    private void addConstantListener(final Button button, final EditText ed, final int i, final String number){

        //add to number 1
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                        try{
                            dbFunc.insertOneConstant(text, number, "doctor");
                            Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                            ed.setText("");
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Couldn\'t save, please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

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
        if (id == R.id.action_overview) {
            Intent intent = new Intent(getApplicationContext(), overview.class);
            startActivity(intent);
        }
        if(id == R.id.action_home){
            Intent intent = new Intent(getApplicationContext(), main.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
