package com.example.acookbookapp.MainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acookbookapp.R;
import com.example.acookbookapp.UploadRecipe.UploadActivity;

public class CreateFragment extends Fragment {
    //this class will display a button that will redirect the user to another activity from where they can upload a new recipe
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//change the structure of fragments
       // https://www.thetopsites.net/article/50390215.shtml
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        Button create = (Button)view.findViewById(R.id.button2);
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                movePage(v);
            }
        });
        return view;
    }


    public void movePage(View v)
    {
        Intent jumpPage = new Intent(getActivity(), UploadActivity.class);
        startActivity(jumpPage);
    }

}
