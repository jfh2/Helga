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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import helgamaria.vinnuhelga.sql.dbFunctions;

public class settings extends ActionBarActivity {
    dbFunctions dbFunc = new dbFunctions(this);
    Spinner spinner = null;
    String role_from_spinner = "";
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
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //listen to changes in spinner
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        String Text = spinner.getSelectedItem().toString();
                        role_from_spinner = Text;
                        System.out.println(role_from_spinner);

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

    }

    private void createRoleListener(){

         Button button = (Button)findViewById(R.id.addRole);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(settings.this);
                alertDialog.setTitle("Add Role");

                final EditText input = new EditText(settings.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);


                alertDialog.setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String save_string = input.getText().toString();
                                try{
                                    if(save_string.equals("") || save_string == null){
                                        Toast.makeText(getApplicationContext(), "Must specify a name", Toast.LENGTH_LONG).show();
                                    }else{
                                        //System.out.println(save_string);
                                        dbFunc.createRole(save_string);
                                        Toast.makeText(getApplicationContext(), "Role created", Toast.LENGTH_SHORT).show();

                                    }
                                }catch(Exception e){
                                    Toast.makeText(getApplicationContext(), "Couldn't create role", Toast.LENGTH_LONG).show();
                                }
                                recreate();
                            }
                        });

                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
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
                            dbFunc.insertOneConstant(text, number, role_from_spinner);
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
    @Override
    public void recreate()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }
}
