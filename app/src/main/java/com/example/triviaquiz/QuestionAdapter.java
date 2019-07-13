package com.example.triviaquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuestionData> questionData;
    private Context context;
    private OnAnswerClicked listener;

    public QuestionAdapter(List<QuestionData> questionData, Context context, OnAnswerClicked listener) {
        this.questionData = questionData;
        this.context = context;
        this.listener = listener;
    }

    public QuestionAdapter(List<QuestionData> questionData){
        this.questionData = questionData;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.question_item, parent, false);
        context = parent.getContext();
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.tvQuestion.setText(questionData.get(position).getQuestion());
        holder.button1.setText(questionData.get(position).getCorrect_answer());
    }


    @Override
    public int getItemCount() {
        return questionData.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder{

        TextView tvQuestion;
        Button button1;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            button1 = itemView.findViewById(R.id.btn1);
        }
    }

    public interface OnAnswerClicked{
        void answerClicked(String answer);
    }
}
