package com.example.miniprojet1;

import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;


public class blution_for_prayer extends Fragment {

    VideoView videoView;

    public blution_for_prayer() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blution_for_prayer, container, false);


        videoView = view.findViewById(R.id.videoView);
        // Récupérer l'URI de la vidéo depuis le répertoire res/raw
        String videoPath=("android.resource://" + getActivity().getPackageName() + "/" + R.raw.ablutation);
        Uri uri = Uri.parse(videoPath);


        // Définir l'URI de la vidéo sur le VideoView
        videoView.setVideoURI(uri);

        // Ajouter un contrôleur multimédia pour permettre à l'utilisateur de contrôler la vidéo (lecture, pause, avance rapide, etc.)
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView); // Lier le contrôleur multimédia au VideoView
        videoView.setMediaController(mediaController);

        // Démarrer la lecture de la vidéo
        videoView.start();

        return view;
    }
}