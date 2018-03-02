package com.sandec.wakhyudi.usba.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.sandec.wakhyudi.usba.R;
import com.sandec.wakhyudi.usba.model.SoalItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;

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
        RadioButton rbA, rbB, rbC, rbD, rbE,rbN;
        ImageView ivSoal;
        public SoalViewHolder(View itemView) {
            super(itemView);
            tvSoal = (TextView) itemView.findViewById(R.id.tv_item_question);
            rgJawaban = (RadioGroup) itemView.findViewById(R.id.rg_item_question);

            rbA = (RadioButton) itemView.findViewById(R.id.rb_a);
            rbB = (RadioButton) itemView.findViewById(R.id.rb_b);
            rbC = (RadioButton) itemView.findViewById(R.id.rb_c);
            rbD = (RadioButton) itemView.findViewById(R.id.rb_d);
            rbE = (RadioButton) itemView.findViewById(R.id.rb_e);
           // rbN = (RadioButton) itemView.findViewById(R.id.rb_n);

            ivSoal = (ImageView) itemView.findViewById(R.id.iv_item_question);
        }
    }

    @Override
    public void onBindViewHolder(final SoalAdapter.SoalViewHolder holder, final int position) {
        if(!(listSoal.get(position).getIdGambar() =="")){
            holder.ivSoal.setVisibility(View.VISIBLE);
            String linkGambar = listSoal.get(position).getIdGambar();
            Glide.with(context).load("https://drive.google.com/thumbnail?id="+linkGambar).into(holder.ivSoal);
        }else{
            holder.ivSoal.setVisibility(View.GONE);
        }

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
                        loadSharedPreferences(clickedPos);
                    }

                }
            }
        });

        holder.rgJawaban.check(listSoal.get(position).getSelectedRadioButtonId());

//        holder.rbA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                loadSharedPreferences (position);
//
//            }
//        });
//
//        holder.rbB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                loadSharedPreferences (position);
//
//            }
//        });
//
//        holder.rbC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                loadSharedPreferences (position);
//
//            }
//        });
//
//        holder.rbD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                loadSharedPreferences (position);
//
//            }
//        });
//
//        holder.rbE.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                loadSharedPreferences (position);
//
//            }
//        });


    }

    private void loadSharedPreferences(int position) {
        SharedPreferences sp = context.getSharedPreferences("jawaban",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean("soal"+position,true);
        editor.commit();

       RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(sp);

        //SharedPreferences sp2 = context.getSharedPreferences("jawaban",Context.MODE_PRIVATE);
//        final boolean spCoba = sp.getBoolean("soal"+position,false);
//        final Observable<Boolean> spObservable = Observable.create(new ObservableOnSubscribe<Boolean>() {
//            @Override
//            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
//           //assert spCoba != null;
//               // spCoba
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listSoal.size();
    }


}
