package com.example.sehs4681_gp_grp2.Eric;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sehs4681_gp_grp2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Level3Fragment extends Fragment {

    // Variable declaration - start
    final String MESSAGE_WITH_LETTERS_TRIED = "Letters tried: ";
    final String WINNING_MESSAGE = "You won!";
    final String LOSING_MESSAGE = "You lost!";

    String wordToBeGuessed, wordDisplayedString, triesLeft, lettersTried;
    char[] wordDisplayedCharArray;
    ArrayList<String> myListOfWords;
    EditText etInput;
    TextView txtLettersTried, txtWordToBeGuessed, txtTriesLeft;
    Button btn_Reset;

    Animation rotateAnimation, scaleAnimation, scaleAndRotateAnimation;
    // Variable declaration - End


    public Level3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* findViewById() is located at onCreateView() below */
        myListOfWords = new ArrayList<String>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level3, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtWordToBeGuessed = (TextView) view.findViewById(R.id.tv_txtWordToBeGuessed);
        etInput = (EditText) view.findViewById(R.id.et_Input);
        txtLettersTried = (TextView) view.findViewById(R.id.tv_txtLettersTried);
        txtTriesLeft = (TextView) view.findViewById(R.id.tv_txtTriesLeft);
        btn_Reset = (Button) view.findViewById(R.id.btn_Reset);

        loadWords();
        initGame();

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    checkLetterIsInWord(s.charAt(0));
                    hideKeyboard(); // !!! Might need to revise it as it might not work in fragment
                    etInput.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initGame();
            }
        });
    }

    // ====== BELOW ARE CUSTOM FUNCTIONS ======

    private void loadWords() {
        String[] wordList = {"side", "organize", "spider", "aviation", "heel", "sum", "related", "exempt", "hike", "terrify" };

        myListOfWords.addAll(Arrays.asList(wordList));
    }

    private void initGame() {
        // 1. Shuffle Word list -> get first element -> remove the word from the list
        Collections.shuffle(myListOfWords);
        wordToBeGuessed = myListOfWords.get(0);

        // Turn the selected word to be guessed to array
        wordDisplayedCharArray = wordToBeGuessed.toCharArray();

        // Add underscore
        for (int i = 1; i < wordDisplayedCharArray.length - 1; i++) {
            wordDisplayedCharArray[i] = '_';
        }

        // set the input text field enable
        etInput.setEnabled(true);

        // reveal all occurrences of the first letter
        revealLetterInWord(wordDisplayedCharArray[0]);

        // reveal all occurrences of the last letter
        revealLetterInWord(wordDisplayedCharArray[wordDisplayedCharArray.length - 1]);

        // init a string from the char array (for search)
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);

        // Display word
        displayWordOnScreen();

        // 2. Input
        // Clear input text
        etInput.setText("");

        // 3. Letter Tried
        //init string for letter tried with a space
        lettersTried = " ";

        // display on screen
        txtLettersTried.setText(MESSAGE_WITH_LETTERS_TRIED);

        // 4. Tried left
        triesLeft = " X X X X X";
        txtTriesLeft.setText(triesLeft);
    }

    private void displayWordOnScreen() {
        String formattedText = "";

        for (char character : wordDisplayedCharArray) {
            formattedText += character + " ";
        }
        txtWordToBeGuessed.setText(formattedText);
    }

    private void revealLetterInWord(char letter) {
        int indexOfLetter = wordToBeGuessed.indexOf(letter);

        // loop if index is positive or 0
        while (indexOfLetter >= 0) {
            wordDisplayedCharArray[indexOfLetter] = wordToBeGuessed.charAt(indexOfLetter);
            indexOfLetter = wordToBeGuessed.indexOf(letter, indexOfLetter + 1);
        }

        //update the string
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
    }

    private void checkLetterIsInWord(char letter) {
        // if the letter was found inside the word to be guessed
        if (wordToBeGuessed.indexOf(letter) >= 0) {

            // if the letter was not displayed yet
            if (wordDisplayedString.indexOf(letter) < 0) {
                // replace the underscore with the letter
                revealLetterInWord(letter);
                // update the changes on screen
                displayWordOnScreen();
                // check if the game is won
                if (!wordDisplayedString.contains("_")) {
                    txtTriesLeft.setText(WINNING_MESSAGE);
                }
            }
        } else { // otherwise, if the letter was not found Inside the word to be guessed
            // decrease the number of tried left, and show it on screen
            decreaseAndDisplayTriesLeft();

            // check if the game lost
            if (triesLeft.isEmpty()) {
                txtTriesLeft.setText(LOSING_MESSAGE);
                txtWordToBeGuessed.setText(wordToBeGuessed);
                etInput.setText("");
                etInput.setEnabled(false);
            }
        }

        // display the letter that was tries
        if (lettersTried.indexOf(letter) < 0) {
            lettersTried += letter + ", ";
            String messageToBeDisplayed = MESSAGE_WITH_LETTERS_TRIED + lettersTried;
            txtLettersTried.setText(messageToBeDisplayed);
            vibrate();
        }


    }
    private void decreaseAndDisplayTriesLeft() {
        // if there are still some tries left
        if (!triesLeft.isEmpty()) {
            triesLeft = triesLeft.substring(0, triesLeft.length() - 2);
            txtTriesLeft.setText(triesLeft);
        }
    }

    private void vibrate () {
        final Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        final VibrationEffect vibrationEffect;

        // this is the only type of the vibration which requires system version Oreo (API 26)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // this effect creates the vibration of default amplitude for 1000ms(1 sec)
            vibrationEffect = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE);

            // it is safe to cancel other vibrations currently taking place
            vibrator.cancel();
            vibrator.vibrate(vibrationEffect);
        }
    }

    // !!! Might need to revise it as it might not work in fragment
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);;
    }
    // ====== ABOVE ARE CUSTOM FUNCTIONS ======
}