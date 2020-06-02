package com.example.acookbookapp.RecipeShow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acookbookapp.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<ListIngredient> listIngredients;
    private Context context;
    //Same structure as List adapter
    //this is used to show each ingredient in the recycler view in the show recipe fragment
    public IngredientsAdapter(List<ListIngredient> ListIngredients, Context context) {
        this.listIngredients = ListIngredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from((parent.getContext()))
                .inflate(R.layout.recyclerview_ingredient_s, parent, false);
        return new IngredientsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        ListIngredient listIngredient = listIngredients.get(position);


        holder.textViewHead.setText(listIngredient.getHead());


    }

    @Override
    public int getItemCount() {
        return listIngredients.size();
    }



    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewHead;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.ingred);

               itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {

            Toast.makeText(view.getContext(), "", Toast.LENGTH_SHORT).show();

        }




    }
}
