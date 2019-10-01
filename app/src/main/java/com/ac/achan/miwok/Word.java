package com.ac.achan.miwok;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */

public class Word {

    // Word of the Default translation
    private String mDefaultTranslation;

    // Word of the Miwok translation
    private String mMiwokTranslation;

    // Drawable resource ID
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    // Audio resource ID of the Word
    private int mAudioResourceId;

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word of the Default translation
     * @param miwokTranslation is the word of the Miwok translation
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Create a new Word object with image.
     *
     * @param defaultTranslation is the word of the Default translation
     * @param miwokTranslation is the word of the Miwok translation
     * @param audioResourceId is the audio resource ID
     * @param imageResourceId is the image resoruce ID
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId, int imageResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
        mImageResourceId = imageResourceId;
    }

    /**
     * Get the word of the Default translation
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the word of the Miwok translation
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * Get the image resource ID
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * Get the audio resource ID
     */
    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
