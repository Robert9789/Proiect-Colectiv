package com.example.shop.Admin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.Model.Products;
import com.example.shop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminCheckNewProductsActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    RecyclerView .LayoutManager layoutManager;
    private DatabaseReference unverifiedProductsRef;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_new_products);

        unverifiedProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");



recyclerView = findViewById(R.id.admin_products_checklist);
recyclerView.setHasFixedSize(true);
layoutManager = new LinearLayoutManager(this);
recyclerView.setLayoutManager(layoutManager);

    }

@Override
protected void onStart() {
    super.onStart();



    FirebaseRecyclerOptions<Products> options =
           new FirebaseRecyclerOptions.Builder<Products>()
                   .setQuery(unverifiedProductsRef.orderByChild("productState").equals("Neaprobat"), Products.class)
                   .build();

    FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
            new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options)
    {
        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Products products) {

        }


     public void onBlindViewHolder(@NonNull ProductViewHolder holder,int position,@NonNull final Products model)
     {

         holder.txtProductName.setText(model.getPname());
         holder.txtProductDescription.setText(model.getDescription());
         holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
         Picasso.get().load(model.getImage()).into(holder.imageView);

      holder.itemView.setOnClickListener(new View.OnClickListener()
      {
          @Override
          public void onClick(View view)
          {
              final int protectID = model.getPid();

              CharSequence options[] = new CharSequence[]
                      {
                              "Yes",
                              "No"
                      };
              AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckNewProductsActivity.this);
              builder.setTitle("Dori??i s?? aproba??i acest produs.Sunte??i sigur?");
              builder.setItems(options, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int position)
                  {
                      if (position == 0)
                      {
                          ChangeProductState(protectID);

                      }
                      if (position ==1 )
                      {
                      }
                  }


              });
              builder.show();
          }
      });
    }
        @NonNull
            @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType )
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
}
};

    recyclerView.setAdapter(adapter);
    adapter.startListening();

}


private void ChangeProductState(int protectID)
    {
        unverifiedProductsRef.child(String.valueOf(protectID)).child("productState")
                .setValue("Approved")
                .addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
                    public void onComplete(@NonNull Task<Void> task)
        {
            Toast.makeText(AdminCheckNewProductsActivity.this, "Acest produs a fost aprobat ??i este acum disponibil pentru v??nzare de la v??nz??tor. ", Toast.LENGTH_SHORT).show();
        }
    });
    }
}