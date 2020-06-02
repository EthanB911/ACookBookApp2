package com.example.acookbookapp.UploadRecipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.acookbookapp.MainMenu.MainActivity;
import com.example.acookbookapp.R;
import com.example.acookbookapp.SqLite.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class UploadActivity extends AppCompatActivity {
        //upload is spit in 4 fragments


    Fragment frag = new Fragment();
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;
    ArrayList<String> ingredientListMain;
    EditText nam, instruc;
    Spinner cat, diff;
    String name, ingredients, instructions, category, difficulty;
    byte[] img;
    ImageView imageView;
    SQLiteHelper myDb;
    Button next;
    Button back;
    String userId;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_upload);
            myDb = new SQLiteHelper(this);

           back = findViewById(R.id.back);
           back.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    openPrevFragment();
                }
            });
           back.setText("Cancel");

            SharedPreferences settings = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
           userId = settings.getString("idKey", "");

            next = findViewById(R.id.next);
            next.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    openNextFragment();
                }
            });
            ingredientListMain =  new ArrayList<String>();


            //initializing each fragment so as to keep data in them when going between on fragment and the other
            fragment1 = new Create1Fragment();
            fragment2 = new Create2Fragment();
            fragment3 = new Create3Fragment();
            fragment4 = new Create4Fragment();

              //load fragment 1 upon launching activity
            if (savedInstanceState != null)
            {
                frag = getSupportFragmentManager().getFragment(savedInstanceState, "frag");
            }
            else
                loadFragment(fragment1);




            //to initialize first fragment
            //openAddFragment();
        }
        //method to open next fragment when button is clicked
        public void openNextFragment(){
            Fragment fragment = null;

            //apart from switching fragment data inputed in current fragment is stored here so as to be available when creating a recipe
            if ( frag == fragment1) {
                //set name and category and go to frag2
                nam = (EditText) frag.getActivity().findViewById(R.id.editText);
                cat = (Spinner) frag.getActivity().findViewById(R.id.spinner1) ;
                 name = nam.getText().toString();
                 category = cat.getSelectedItem().toString();
                back.setText("Back");
                fragment = fragment2;
            } else if (frag == fragment2) {
                //get all ingredients and go to frag3
                if(ingredientListMain.size()!=0)
                     ingredients = getAllIngredients();
                fragment = fragment3;
            } else if (frag == fragment3) {
                //get instructions and go to frag4
                instruc = (EditText) frag.getActivity().findViewById(R.id.editTex2);
                instructions = instruc.getText().toString();
                next.setText("Add");
                fragment = fragment4;
            }else if(frag == fragment4){
                //get time and difficulty and try to add recipe.
                diff = (Spinner) frag.getActivity().findViewById(R.id.difficulty) ;
                difficulty= diff.getSelectedItem().toString();

                imageView= (ImageView)frag.getActivity().findViewById(R.id.imageView);
                //check if all fields have been filled prior to attempting to create a recipe
                //for future apart from giving an error message in toast, take user to the fragment where data is missing
                if(imageView.getDrawable() != null)
                   img = imageViewToByte(imageView);
                else {
                    img = null;
                    Toast.makeText(UploadActivity.this,"No image given",Toast.LENGTH_LONG).show();
                }
                if((name.length() == 0) || (instructions.length() == 0)) {
                    if(name.length() == 0)
                        Toast.makeText(UploadActivity.this,"Name is empty",Toast.LENGTH_LONG).show();
                    if(instructions.length() == 0)
                        Toast.makeText(UploadActivity.this,"Instructions empty",Toast.LENGTH_LONG).show();
                }else{

                    save();
                    finish();
                }
            }
            if (fragment != null)
             loadFragment(fragment);
        }

        //method to transform list of ingredients in one string for saving in db
    //note that user id not allowed to insert comma in ingredient so as not to disrupt the flow of converting to a string
        public String getAllIngredients(){
            String allIngredients = "";
            for(String ing : ingredientListMain){

                     allIngredients+= (ing +", ");
            }
            //to remove last comma and space
            return allIngredients.substring(0,allIngredients.length()-2);
        }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public boolean save(){
        boolean isInserted = myDb.insertDataRecipe(name,
                ingredients,
                instructions, " 9", difficulty, category, img, userId );
        if(isInserted == true)
            Toast.makeText(UploadActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(UploadActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

        return isInserted;
    }


    //method to open previous fragment when button is clicked
    public void openPrevFragment(){

        Fragment fragment = null;
            //changing button text from back to cancel or from add to next depending on which fragment user is currently on.
        if ( frag == fragment1) {
            finish();
        } else if (frag == fragment2) {
            back.setText("Cancel");
            fragment = fragment1;
        } else if (frag == fragment3) {
            fragment = fragment2;
        }else if (frag == fragment4) {
            fragment = fragment3;
            next.setText("Next");
        }

        loadFragment(fragment);
    }

    //method to load the needed fragment but keep the previous fragment for possible future reference
    private boolean loadFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().hide(fragment1).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            getSupportFragmentManager().beginTransaction().show(fragment).commit();

            frag = fragment;
            return true;
        }
        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "Frag", frag);
    }



    //unused method
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = UploadActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

}


