package com.example.expensemanagerfirebase;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagerfirebase.databinding.ActivityAddTransactionBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AddTransactionActivity extends AppCompatActivity {
    ActivityAddTransactionBinding binding;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fStore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        binding.expenseCheckBox.setOnClickListener(v -> {
            type="Expense";
            binding.expenseCheckBox.setChecked(true);
            binding.incomeCheckBox.setChecked(false);
        });
        binding.incomeCheckBox.setOnClickListener(v -> {
            type="Income";
            binding.expenseCheckBox.setChecked(false);
            binding.incomeCheckBox.setChecked(true);
        });
        binding.btnAddTransaction.setOnClickListener(v -> {
            String amount=binding.userAmountAdd.getText().toString().trim();
            String note=binding.userNoteAdd.getText().toString().trim();
            if (amount.length() == 0){
                return;
            }
            if(type.length() == 0){
                Toast.makeText(AddTransactionActivity.this, "Selected transaction type", Toast.LENGTH_SHORT).show();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            String id= UUID.randomUUID().toString();
            Map<String,Object> transaction=new HashMap<>();
            transaction.put("id", id);
            transaction.put("amount", amount);
            transaction.put("note", note);
            transaction.put("type", type);
            transaction.put("date",currentDateandTime);

            fStore.collection("Expenses").document(Objects.requireNonNull(firebaseAuth.getUid())).collection("note").document(id)
                    .set(transaction)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(AddTransactionActivity.this, "Added", Toast.LENGTH_SHORT).show();
                        binding.userNoteAdd.setText("");
                        binding.userAmountAdd.setText("");
                    })
                    .addOnFailureListener(e -> Toast.makeText(AddTransactionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}