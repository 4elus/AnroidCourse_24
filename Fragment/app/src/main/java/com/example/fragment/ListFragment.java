package com.example.fragment;

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
    public OnFragmentSendDataListener fragmentSendDataListener;
    String[] menu = {"Бургер", "Кола", "Комбо"};
    int[] menuPrice = {15, 5, 20};
    int[] menuImg = {R.drawable.burger, R.drawable.ca, R.drawable.combo};
    String[] menuDesc = {"Вкусный бургер", "Вкусная кола", "Комбо"};

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
        ListView menuList = view.findViewById(R.id.menuList);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, menu);
        // устанавливаем для списка адаптер
        menuList.setAdapter(adapter);
        // добавляем для списка слушатель
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем выбранный элемент
                String selectedItem = (String)parent.getItemAtPosition(position);

                int price = menuPrice[position];
                String desc = menuDesc[position];
                int img = menuImg[position];

                // Посылаем данные Activity
                fragmentSendDataListener.onSendData(selectedItem);

                fragmentSendDataListener.onSendPrice(price);
                fragmentSendDataListener.onSendDesc(desc);
                fragmentSendDataListener.onSendImg(img);



            }
        });

        return view;
    }

}