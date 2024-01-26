package com.example.miniprojet1;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class dua extends Fragment {

    private RecyclerView duaRV;
    private List<DuaModel> duaModels;
    private DuaRVAdapter duaRVAdapter;


    public dua() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        duaModels = new ArrayList<>();

        DuaModel duaModel = new DuaModel("اللهم اختر لي الافضل في كل امور حياتي ثم ارضني باختيارك\n");
        duaModels.add(duaModel);

        DuaModel duaModel1 = new DuaModel("اللهم يا عزيز يا جبار اجعل قلوبنا تخشع من تقواك واجعل عيوننا تدمع من خشياك واجعلنا يا رب من أهل التقوى وأهل المغفرة يا رب الذي يرسل هذا الدعاء اجعله مع حبيبك ورسولك المصطفى في الجنة.\n");

        duaModels.add(duaModel1);

        DuaModel duaModel2 = new DuaModel("اللهم اجعلنا ممن يورثون الجنان ويبشرون بروح وريحان ورب غير غضبان، اللهم آمين.\n");
        duaModels.add(duaModel2);

        DuaModel duaModel3 = new DuaModel("رَبَّنَا لَا تُؤَاخِذْنَا إِن نَّسِينَا أَوْ أَخْطَأْنَا ۚ رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَا إِصْرًا كَمَا حَمَلْتَهُ عَلَى الَّذِينَ مِن قَبْلِنَا ۚ رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهِ ۖ وَاعْفُ عَنَّا وَاغْفِرْ لَنَا وَارْحَمْنَا ۚ.");
        duaModels.add(duaModel3);

        DuaModel duaModel4 = new DuaModel("اللهم إني أسألك الجنة وما قرب إليها من قول أو فعل، وأعوذ بك من النار وما قرب إليها من قول أو فعل، وأسألك ممّ سألك به عبدك ونبيك محمد صلى الله عليه وسلم، وأعوذ بك مما تعوذ منه عبدك ونبيك محمد صلى الله عليه وسلم، وما قضيت لي من قضاء فاجعل عاقبته رشدا");
        duaModels.add(duaModel4);

        DuaModel duaModel5 = new DuaModel("اللهم يا حنان يا منان يا من لا تغفل ولا تنام نسألك العفو والعافية.. وأن تيسر لنا أمرنا كله، ولا تكلنا إلى نفسنا طرفة أعين.. وبارك لنا في رزقنا كله.");
        duaModels.add(duaModel5);

        DuaModel duaModel6 = new DuaModel("اللهم أعني ولا تعن علي وأنصرني ولا تنصر علي وأمكر لي ولا تمكر علي وأهدني ويسر الهدى لي وأنصرني على من بغى علي.");
        duaModels.add(duaModel6);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dua, container, false);
        duaRV = view.findViewById(R.id.duaRV);



        // Vous devez créer et définir l'adaptateur après avoir ajouté des éléments à la liste
        duaRVAdapter = new DuaRVAdapter(getActivity(), duaModels);
        duaRV.setAdapter(duaRVAdapter);

        return view;
    }

}