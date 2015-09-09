package com.rajumoh.cryptnoob;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity{

    private static final String DEFAULT_ALGO =
            "public encryptTest(plainText){\n" +
            " String response = \"\";\n" +
            " for(int i=0; i<plainText.length(); i++){\n" +
            "  response += (char)((((int)plainText.charAt(i))+21)%254);\n" +
            " }\n" +
            " return response;\n" +
            "}\n" +
            "\n" +
            "public decryptTest(encString){\n" +
            " String response = \"\";\n" +
            " for(int i=0; i<encString.length(); i++){\n" +
            "  response += (char)((((int)encString.charAt(i))+233)%254);\n" +
            " }\n" +
            " return response;\n" +
            "}\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        navigationDrawerFragment = new NavigationDrawerFragment();
        Bundle argsNDF = new Bundle();
        argsNDF.putInt("position", 0);
        navigationDrawerFragment.setArguments(getIntent().getExtras());
        navigationDrawerFragment.setArguments(argsNDF);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, navigationDrawerFragment).addToBackStack(null).commit();
    }

    public void onNavigationDrawerItemSelected(int position) {
        Log.i("rajumoh", "MainActivity position : " + position);
        super.onNavigationDrawerItemSelected(position);
    }

    @Override
    public View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_1, container, false);
        EditText algoSave = (EditText)rootView.findViewById(R.id.algo_save);
        EditText testTextSave = (EditText)rootView.findViewById(R.id.test_text_save);
        if(noEntryInDb()) {
            algoSave.setText(DEFAULT_ALGO);
        }else{
            algoSave.setText(getAlgoFromDb());
        }
        testTextSave.setText("Test plain text");
        Button runTest = (Button)rootView.findViewById(R.id.run_test);
        runTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText algoSave = (EditText) rootView.findViewById(R.id.algo_save);
                EditText testTextSave = (EditText) rootView.findViewById(R.id.test_text_save);
                String encryptTest = "encryptTest(testString);\n";
                String encDecTest = "decryptTest(encryptTest(testString));\n";

                GrooidShell shell = new GrooidShell(getApplicationContext().getDir("dynclasses", 0), this.getClass().getClassLoader());
                ///Log.i("rajumoh", "Evaluating EncryptionTest : " + shell.evaluate(algoSave.getText() + "\ntestString = \"" + testTextSave.getText() + "\"\n" + encryptTest).getResult());
                //Log.i("rajumoh", "Evaluating EncDecTest : " + shell.evaluate(algoSave.getText()+"\ntestString = \""+testTextSave.getText()+"\"\n"+encDecTest).getResult());
                String response = shell.evaluate(algoSave.getText() + "\ntestString = \"" + testTextSave.getText() + "\"\n" + encDecTest).getResult();
                if (response.equals(testTextSave.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Test Success.", Toast.LENGTH_LONG).show();
                    setAlgoToDb(algoSave.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Test Failed. Your Crypt does not seem to be working", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

    //TODO : Complete the rest of the database.
    private boolean noEntryInDb(){
        SqlDbHelper dbHelper = new SqlDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return false;
    }

    private String getAlgoFromDb(){
        SqlDbHelper dbHelper = new SqlDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return "";
    }

    private boolean setAlgoToDb(String data){
        SqlDbHelper dbHelper = new SqlDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return true;
    }
}
