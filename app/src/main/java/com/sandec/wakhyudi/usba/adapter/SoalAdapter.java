package com.sandec.wakhyudi.usba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sandec.wakhyudi.usba.R;
import com.sandec.wakhyudi.usba.model.SoalItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wakhyudi on 18/02/18.
 */

public class SoalAdapter extends RecyclerView.Adapter<SoalAdapter.SoalViewHolder> {
    private List<SoalItem> listSoal;
    private Context context;
    List<String> listJawaban;

    public SoalAdapter(List<SoalItem> listSoal, Context context) {
        this.listSoal = listSoal;
        this.context = context;
    }

    @Override
    public SoalAdapter.SoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new SoalViewHolder(v);
    }

    public class SoalViewHolder extends RecyclerView.ViewHolder {
        TextView tvSoal;
        RadioGroup rgJawaban;
        RadioButton rbA, rbB, rbC, rbD, rbE;

        public SoalViewHolder(View itemView) {
            super(itemView);
            tvSoal = (TextView) itemView.findViewById(R.id.tv_item_question);
            rgJawaban = (RadioGroup) itemView.findViewById(R.id.rg_item_question);

            rbA = (RadioButton) itemView.findViewById(R.id.rb_a);
            rbB = (RadioButton) itemView.findViewById(R.id.rb_b);
            rbC = (RadioButton) itemView.findViewById(R.id.rb_c);
            rbD = (RadioButton) itemView.findViewById(R.id.rb_d);
            rbE = (RadioButton) itemView.findViewById(R.id.rb_e);
        }
    }

    @Override
    public void onBindViewHolder(final SoalAdapter.SoalViewHolder holder, int position) {
        holder.tvSoal.setText(listSoal.get(position).getSoal());
        //memasukan pilihan jawaban di setiap pertanyaan ke dalam ArrayList
        listJawaban = new ArrayList<>();
        listJawaban.add(listSoal.get(position).getAnswerA());
        listJawaban.add(listSoal.get(position).getAnswerB());
        listJawaban.add(listSoal.get(position).getAnswerC());
        listJawaban.add(listSoal.get(position).getAnswerD());
        listJawaban.add(listSoal.get(position).getAnswerE());

        //membuat pilihan jawaban teracak
        Collections.shuffle(listJawaban);

        //memasukan pilihan jawaban ke dalam masing2 radiobutton sesuai posisinya
        holder.rbA.setText(listJawaban.get(0));
        holder.rbB.setText(listJawaban.get(1));
        holder.rbC.setText(listJawaban.get(2));
        holder.rbD.setText(listJawaban.get(3));
        holder.rbE.setText(listJawaban.get(4));


//        holder.rbA.setText(listSoal.get(position).getAnswerA());
//        holder.rbB.setText(listSoal.get(position).getAnswerB());
//        holder.rbC.setText(listSoal.get(position).getAnswerC());
//        holder.rbD.setText(listSoal.get(position).getAnswerD());
//        holder.rbE.setText(listSoal.get(position).getAnswerE());

        holder.rgJawaban.setTag(position);


        holder.rgJawaban.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {

                    int radioButtonId = group.getCheckedRadioButtonId();
                    //menghindari efek duplikasi kita pakai tag
                    int clickedPos = (Integer) group.getTag();

                    listSoal.get(clickedPos).setSelectedRadioButtonId(radioButtonId);


                    if (radioButtonId > 0) {
                        RadioButton rb = group.findViewById(radioButtonId);
                        listSoal.get(clickedPos).setFinalAnswer(rb.getText().toString());

                    }

                }
            }
        });

        holder.rgJawaban.check(listSoal.get(position).getSelectedRadioButtonId());
    }

    @Override
    public int getItemCount() {
        return listSoal.size();
    }


}
