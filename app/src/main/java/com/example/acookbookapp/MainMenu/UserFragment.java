package com.example.acookbookapp.MainMenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acookbookapp.ListRecipe.ListAdapter;
import com.example.acookbookapp.ListRecipe.ListItem;
import com.example.acookbookapp.R;
import com.example.acookbookapp.SqLite.SQLiteHelper;
import com.example.acookbookapp.login_register.login;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
View view;
    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterR;

    private List<ListItem> listItems;
    SQLiteHelper myDb;
    String userId;
    Button logout;
    TextView user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        logout = (Button)view.findViewById(R.id.logout);
        user = (TextView)  view.findViewById(R.id.name);
        SharedPreferences settings = getActivity().getSharedPreferences(login.MyPREFERENCES, Context.MODE_PRIVATE);
        userId = settings.getString("idKey", "");
        user.setText(userId);
        //get recycler view
        recyclerView   = (RecyclerView)view.findViewById(R.id.user_recipes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //get category from listclass activity

        myDb = new SQLiteHelper(getActivity());
        listItems = new ArrayList<>();
//method used for testing purpose to check that recycler view works properly
//        for(int i =0; i<10; i++)
//        {
//            ListItem li = new ListItem(
//                    "head" + i,
//                    "lorem " + i
//            );
//
//            listItems.add(li);
//        }
        //check if an actual category is passed or if user asked to show all recipes.

            //not category is passed get all recipes
            Cursor res = myDb.getAllRecipesByUserId(userId);
            while (res.moveToNext()) {

                ListItem li = new ListItem(
                        res.getString(0),
                        res.getString(1),
                        res.getString(2),
                        res.getBlob(7)
                );

                listItems.add(li);
            }

        //if recipes exists show them in the recycler view.
        if (listItems.size() != 0) {


            adapterR = new UserListAdapter(listItems, view.getContext());

            recyclerView.setAdapter(adapterR);
        }



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current DashBoard activity on button click.
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences(login.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();

                Toast.makeText(view.getContext(),"Log Out Successfull", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }
}
