package com.ac.achan.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NumbersFragment extends Fragment {

    // Handles playback of all the sound files
    private MediaPlayer mMediaPlayer;

    // Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    // This listener gets triggered when the MediaPlayer has completed playing the audio file
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    // This listener gets triggered whenever the audio changes focus
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mMediaPlayer.pause();
                        // Play the word from beginning
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback and clean up resources
                        releaseMediaPlayer();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback
                        mMediaPlayer.start();
                    }
                }
            };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

            // Create and setup the AudioManager to request audio focus
            mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

            // Create a list of words
            final ArrayList<Word> words = new ArrayList<Word>();

            words.add(new Word("one", "lutti", R.raw.number_one, R.drawable.number_one));
            words.add(new Word("two", "otiiko", R.raw.number_two, R.drawable.number_two));
            words.add(new Word("three", "tolookosu", R.raw.number_three, R.drawable.number_three));
            words.add(new Word("four", "oyyisa", R.raw.number_four, R.drawable.number_four));
            words.add(new Word("five", "massokka", R.raw.number_five, R.drawable.number_five));
            words.add(new Word("six", "temmokka", R.raw.number_six, R.drawable.number_six));
            words.add(new Word("seven", "kenekaku", R.raw.number_seven, R.drawable.number_seven));
            words.add(new Word("eight", "kawinta", R.raw.number_eight, R.drawable.number_eight));
            words.add(new Word("nine", "wo'e", R.raw.number_nine, R.drawable.number_nine));
            words.add(new Word("ten", "na'aacha", R.raw.number_ten, R.drawable.number_ten));

            // Create an WordAdapter, whose data source is a list of words
            WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

            // Get a reference to the ListView, and attach the adapter to the listView.
            ListView listView = (ListView) rootView.findViewById(R.id.list);
            listView.setAdapter(itemsAdapter);

            // Set a click listener to play the audio when the list item is clicked on
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the Word object at the given position the user clicked on
                    Word word = words.get(position);

                    // Release the media player if it currently exists because we are about to play a different sound file
                    releaseMediaPlayer();

                    // Request audio focus for playback
                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            // Use the music stream.
                            AudioManager.STREAM_MUSIC,
                            // Request permanent focus.
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        // We have audio focus now

                        // Create and setup the MediaPlayer for the audio resource associated with the current word
                        mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());

                        // Start the audio file
                        mMediaPlayer.start();

                        // Setup a listener on the media player, so that we can stop and release the media player once the sound has finished playing
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
                }
            });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        //When the activity is stopped, release the media player resources because we won't be playing anymore sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that setting the media player to null is an easy way to tell that the media player is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
