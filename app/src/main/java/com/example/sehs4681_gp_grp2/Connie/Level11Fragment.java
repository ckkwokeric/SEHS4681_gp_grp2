package com.example.sehs4681_gp_grp2.Connie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.R;

import java.util.Arrays;
import java.util.Collections;

public class Level11Fragment extends Fragment {

    int numMatchedPairs = 0;
    TextView tv_point;

    ImageView[] cards = new ImageView[12];
    //array for images
    Integer[] cardsArray = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};

    //actual image
    int image_101, image_102, image_103, image_104, image_105, image_106,
            image_201, image_202, image_203, image_204, image_205, image_206;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    int turn = 1;

    public Level11Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level11, container, false);

        tv_point = view.findViewById(R.id.tv_title);

        cards[0] = view.findViewById(R.id.question_mark_1a);
        cards[1] = view.findViewById(R.id.question_mark_1b);
        cards[2] = view.findViewById(R.id.question_mark_1c);
        cards[3] = view.findViewById(R.id.question_mark_1d);
        cards[4] = view.findViewById(R.id.question_mark_2a);
        cards[5] = view.findViewById(R.id.question_mark_2b);
        cards[6] = view.findViewById(R.id.question_mark_2c);
        cards[7] = view.findViewById(R.id.question_mark_2d);
        cards[8] = view.findViewById(R.id.question_mark_3a);
        cards[9] = view.findViewById(R.id.question_mark_3b);
        cards[10] = view.findViewById(R.id.question_mark_3c);
        cards[11] = view.findViewById(R.id.question_mark_3d);

        for (int i = 0; i < cardsArray.length; i++) {
            cards[i].setTag(Integer.toString(i));
            cards[i].setOnClickListener(v -> {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(cards[theCard], theCard);
            });
        }
        // Load card images
        frontOfCardsResources();

        // Shuffle the images
        Collections.shuffle(Arrays.asList(cardsArray));


        return view;
    }

    // Set the correct image to the imageview
    private void doStuff(ImageView imageView, int card) {
        if(cardsArray[card] == 101) {
            imageView.setImageResource(image_101);
        } else if (cardsArray[card] == 102){
            imageView.setImageResource(image_102);
        } else if (cardsArray[card] == 103){
            imageView.setImageResource(image_103);
        } else if (cardsArray[card] == 104){
            imageView.setImageResource(image_104);
        } else if (cardsArray[card] == 105){
            imageView.setImageResource(image_105);
        } else if (cardsArray[card] == 106){
            imageView.setImageResource(image_106);
        } else if (cardsArray[card] == 201){
            imageView.setImageResource(image_201);
        } else if (cardsArray[card] == 202){
            imageView.setImageResource(image_202);
        } else if (cardsArray[card] == 203){
            imageView.setImageResource(image_203);
        } else if (cardsArray[card] == 204){
            imageView.setImageResource(image_204);
        } else if (cardsArray[card] == 205){
            imageView.setImageResource(image_205);
        } else if (cardsArray[card] == 206){
            imageView.setImageResource(image_206);
        }

        // Check which image is selected and save it to temporary variable
        if(cardNumber == 1) {
            firstCard = cardsArray[card];
            if(firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            imageView.setEnabled(false);
        } else if(cardNumber == 2) {
            secondCard = cardsArray[card];
            if(secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            cards[0].setEnabled(false);
            cards[1].setEnabled(false);
            cards[2].setEnabled(false);
            cards[3].setEnabled(false);
            cards[4].setEnabled(false);
            cards[5].setEnabled(false);
            cards[6].setEnabled(false);
            cards[7].setEnabled(false);
            cards[8].setEnabled(false);
            cards[9].setEnabled(false);
            cards[10].setEnabled(false);
            cards[11].setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                // Check if the selected image are equal
                calculate();
            }, 1500);
        }
    }

    private void calculate() {
        // Check if images are equal, then remove them and add point
        if(firstCard == secondCard) {
            numMatchedPairs++;
            cards[clickedFirst].setVisibility(View.INVISIBLE);
            cards[clickedSecond].setVisibility(View.INVISIBLE);
            if(turn == 1 && numMatchedPairs < cards.length / 2) {
                Toast.makeText(getActivity(), "Keep Going!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Wrong Pair, Try Again!", Toast.LENGTH_SHORT).show();
            for (ImageView card : cards) {
                if (card.getVisibility() == View.VISIBLE) {
                    card.setImageResource(R.drawable.image_question_mark);
                }
            }
        }

        for (ImageView card : cards) {
            card.setEnabled(card.getVisibility() == View.VISIBLE);
        }

        // Check if the game is over
        checkEnd();
    }

    private void checkEnd() {
        boolean allInvisible = true;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getVisibility() != View.INVISIBLE) {
                allInvisible = false;
                break;
            }
        }
        if (allInvisible) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("CONGRATULATIONS!")
                    .setCancelable(false)
                    .setPositiveButton("EXIT", (dialogInterface, i) -> {
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.flFragment, homeFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
    private void frontOfCardsResources() {
        image_101 = R.drawable.image_a1;
        image_102 = R.drawable.image_b1;
        image_103 = R.drawable.image_c1;
        image_104 = R.drawable.image_d1;
        image_105 = R.drawable.image_e1;
        image_106 = R.drawable.image_f1;
        image_201 = R.drawable.image_a2;
        image_202 = R.drawable.image_b2;
        image_203 = R.drawable.image_c2;
        image_204 = R.drawable.image_d2;
        image_205 = R.drawable.image_e2;
        image_206 = R.drawable.image_f2;
    }

}