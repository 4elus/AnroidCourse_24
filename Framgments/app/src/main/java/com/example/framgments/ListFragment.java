package com.example.framgments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFragment extends Fragment {

    private OnFragmentSendDataListener fragmentSendDataListener;

    String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        // получаем элемент ListView
        ListView countriesList = view.findViewById(R.id.countriesList);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, countries);
        // устанавливаем для списка адаптер
        countriesList.setAdapter(adapter);

        // добавляем для списка слушатель
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем выбранный элемент
                String selectedItem = (String)parent.getItemAtPosition(position);
                // Посылаем данные Activity
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });
        return view;



    }
}