package com.example.licenseactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MedicalViewHolder> {

    private Context context;
    private ArrayList<MedicalRecord> medicalRecords;

    public MedicalAdapter(Context context, ArrayList<MedicalRecord> medicalRecords) {
        this.context = context;
        this.medicalRecords = medicalRecords;
    }

    @NonNull
    @Override
    public MedicalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medical, parent, false);
        return new MedicalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalViewHolder holder, int position) {
        MedicalRecord record = medicalRecords.get(position);
        holder.id_med.setText(String.valueOf(record.getId()));
        holder.name_spital.setText(record.getSpital());
        holder.name_departament.setText(record.getDepartament());
        holder.name_doctor.setText(record.getDoctor());
    }

    @Override
    public int getItemCount() {
        return medicalRecords.size();
    }

    public static class MedicalViewHolder extends RecyclerView.ViewHolder {
        TextView id_med, name_spital, name_departament, name_doctor;

        public MedicalViewHolder(@NonNull View itemView) {
            super(itemView);
            id_med = itemView.findViewById(R.id.id_med);
            name_spital = itemView.findViewById(R.id.name_spital);
            name_departament = itemView.findViewById(R.id.name_departament);
            name_doctor = itemView.findViewById(R.id.name_doctor);
        }
    }
}


