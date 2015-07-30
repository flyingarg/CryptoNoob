package com.rajumoh.cryptnoob;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int section = this.getArguments().getInt(ARG_SECTION_NUMBER);
            if ( section == 1){
                final View rootView = inflater.inflate(R.layout.fragment_1, container, false);
                EditText algoSave = (EditText)rootView.findViewById(R.id.algo_save);
                EditText testTextSave = (EditText)rootView.findViewById(R.id.test_text_save);
                algoSave.setText("" +
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
                        "}\n");
                testTextSave.setText("Test plain text");
                Button runTest = (Button)rootView.findViewById(R.id.run_test);
                runTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText algoSave = (EditText)rootView.findViewById(R.id.algo_save);
                        EditText testTextSave = (EditText)rootView.findViewById(R.id.test_text_save);
                        String encryptTest = "encryptTest(testString);\n";
                        String encDecTest = "decryptTest(encryptTest(testString));\n";

                        GrooidShell shell = new GrooidShell(getActivity().getApplicationContext().getDir("dynclasses", 0), this.getClass().getClassLoader());
                        Log.i("rajumoh", "Evaluating EncryptionTest : " + shell.evaluate(algoSave.getText()+"\ntestString = \""+testTextSave.getText()+"\"\n"+encryptTest).getResult());
                        Log.i("rajumoh", "Evaluating EncDecTest : " + shell.evaluate(algoSave.getText()+"\ntestString = \""+testTextSave.getText()+"\"\n"+encDecTest).getResult());
                        String response = shell.evaluate(algoSave.getText()+"\ntestString = \""+testTextSave.getText()+"\"\n"+encDecTest).getResult();
                        if(response.equals(testTextSave.getText().toString())) {
                            Toast.makeText(getActivity().getApplicationContext(), "Test Success.", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Test Failed. Your Crypt does not seem to be working", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return rootView;
            }else if( section == 2){
                View rootView = inflater.inflate(R.layout.fragment_2, container, false);
                TextView temp = (TextView)rootView.findViewById(R.id.section_label);
                temp.setText("Fragment Two");
                return rootView;
            }else {
                View rootView = inflater.inflate(R.layout.fragment_3, container, false);
                TextView temp = (TextView)rootView.findViewById(R.id.section_label);
                temp.setText("Fragment Three");
                return rootView;
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
