package com.example.shop.Sellers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.Model.Products;
import com.example.shop.R;
import com.example.shop.Buyers.MainActivity;
import com.example.shop.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
//import  com.firebase.ui.database.FirebaseRecyclerAdapter;
public class SellerHomeActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;

    RecyclerView .LayoutManager layoutManager;
    private DatabaseReference unverifiedProductsRef;
    private TextView mTextMessage;

   private boolean mOnNavigationItemSelectedListener
            (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home: {
                Intent intentHome = new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                startActivity(intentHome);
                return true;
            }
            case R.id.navigation_add:

                Intent intentCate = new Intent(SellerHomeActivity.this, SellerProductCategoryActivity.class);
                startActivity(intentCate);
                return true;


            case R.id.navigation_logout :

                final FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                Intent intentMain = new Intent(SellerHomeActivity.this, MainActivity.class);
                intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentMain);
                finish();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView navView=findViewById(R.id.nav_view);
        mTextMessage=findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener());

        unverifiedProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");


        recyclerView=findViewById(R.id.seller_home_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        }

   // private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener() {
    //}
   private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener() {
       return null;
   }
    @Override

    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()

                        //nu importa sid dn SellerRegistrationActivity
                        .setQuery(unverifiedProductsRef.orderByChild(sid).equals(FirebaseAuth.getInstance().getCurrentUser().getUid()), Products.class)
                       .build();


        FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options)
                {
                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i, @NonNull Products products) {

                    }

                    //  @Override
                    protected void onBlindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Products model)
                    {

                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductStatus.setText("State : "+model.getProductState());
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                                builder.setTitle("Vreti sa stergeti sa elinati acest produs?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int position)
                                    {

                                        if (position == 0)
                                        {
                                            deleteProduct(protectID);
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
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType )
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                        ItemViewHolder holder = new ItemViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteProduct(int protectID)
    {

        unverifiedProductsRef.child(String.valueOf(protectID))
               .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(SellerHomeActivity.this, "Acest produs a fost elimat cu succes", Toast.LENGTH_SHORT).show();
                    }
                });
    }  }