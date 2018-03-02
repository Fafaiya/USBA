package com.sandec.wakhyudi.usba.adapter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.sandec.wakhyudi.usba.R;
import com.sandec.wakhyudi.usba.model.QuestionNumberModel;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by wakhyudi on 24/02/18.
 */

public class NoSoalAdapter extends RecyclerView.Adapter<NoSoalAdapter.NoSoalViewHolder> {
    private Context context;
    private List<QuestionNumberModel> listNo;
    //public int noSoalTerpilih = 1;

    public NoSoalAdapter(Context context, List<QuestionNumberModel> listNo) {
        this.context = context;
        this.listNo = listNo;
    }

    @Override
    public NoSoalAdapter.NoSoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_question_number, parent, false);
        return new NoSoalViewHolder(v);
    }

    public class NoSoalViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNumber;

        public NoSoalViewHolder(View itemView) {
            super(itemView);
            tvQuestionNumber = (TextView) itemView.findViewById(R.id.tv_item_question_number);
        }
    }


    @Override
    public void onBindViewHolder(final NoSoalAdapter.NoSoalViewHolder holder, int position) {
        holder.tvQuestionNumber.setText("" + listNo.get(position).getQuestionNumber());

        SharedPreferences sp = context.getSharedPreferences("jawaban", Context.MODE_PRIVATE);
        RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(sp);
        //Boolean soal = sp.getBoolean("soal"+position,false);


//        Observer<Boolean> tvObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(@Nullable Boolean aBoolean) {
//
//            }
//        };

        Preference<Boolean> soal = rxSharedPreferences.getBoolean("soal"+position, false);

        soal.asObservable().subscribe(new io.reactivex.Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean == true) {
                    holder.tvQuestionNumber.setBackgroundColor(Color.BLUE);
                } else {
                    holder.tvQuestionNumber.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
// if(soal){
//                holder.tvQuestionNumber.setBackgroundColor(Color.BLUE);
//            }else{
//                holder.tvQuestionNumber.setBackgroundColor(Color.RED);
//            }

    }

    @Override
    public int getItemCount() {
        return listNo.size();
    }

//    public int posisiSoal(){
//        return noSoalTerpilih;
//    }


}
