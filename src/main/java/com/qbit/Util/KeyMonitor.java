package com.qbit.Util;

import com.qbit.Assignment.ActiveTracker;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * User: cbates
 */
public class KeyMonitor implements NativeKeyListener{

    public static final String SPACE_KEY_TEXT = NativeKeyEvent.getKeyText(NativeKeyEvent.VC_SPACE);
    public static final String TAB_KEY_TEXT = NativeKeyEvent.getKeyText(NativeKeyEvent.VC_TAB);
    public static final String ENTER_KEY_TEXT = NativeKeyEvent.getKeyText(NativeKeyEvent.VC_ENTER);

    private List<Integer> keyList = new ArrayList<>();
    private ActiveTracker tracker;
    private int previousKeyStroke;

    public KeyMonitor(ActiveTracker tracker) {
        this.tracker = tracker;
    }

    public List<Integer> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<Integer> keyList) {
        this.keyList = keyList;
    }

    public void startListening() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

            // This is to stop libraries own logs
            logger.setFilter(new Filter() {
                public boolean isLoggable(LogRecord record) {
                    return !record.getLoggerName().equals("org.jnativehook");
                }});
        } catch (NativeHookException exc) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(exc.getMessage());
        }
    }

    public void stopListening() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        // TODO: handle if delete was hit with text highlighted

        int keyCode = nativeKeyEvent.getKeyCode();
        if ((previousKeyStroke == NativeKeyEvent.VC_CONTROL_L || previousKeyStroke == NativeKeyEvent.VC_CONTROL_R) && keyCode == NativeKeyEvent.VC_V) {
            tracker.updateProjectCount(countWordsAndLogKeys(getClipboardContents()));
        }

        String previousKeyText;
        if (!keyList.isEmpty()) {
            previousKeyText = NativeKeyEvent.getKeyText(keyList.get(keyList.size() - 1));
        } else {
            previousKeyText = "";
        }

        if (keyCode == NativeKeyEvent.VC_BACKSPACE && previousKeyStroke != NativeKeyEvent.VC_BACKSPACE) {
            if (previousKeyText.equals(SPACE_KEY_TEXT) || previousKeyText.equals(ENTER_KEY_TEXT) || previousKeyText.equals(TAB_KEY_TEXT)) {
                keyList.remove(keyList.size() - 1);
                previousKeyStroke = keyCode;
                return;
            }
        }

        if (keyCode == NativeKeyEvent.VC_BACKSPACE) {
            if (previousKeyText.equals(SPACE_KEY_TEXT) || previousKeyText.equals(TAB_KEY_TEXT) || previousKeyText.equals(ENTER_KEY_TEXT)) {
                String previousToPreviousText = NativeKeyEvent.getKeyText(keyList.get(keyList.size() - 2));
                // Check it's not special case where there were multiple count trigger keys in a row
                if (!previousToPreviousText.equals(SPACE_KEY_TEXT) && !previousToPreviousText.equals(TAB_KEY_TEXT) && !previousToPreviousText.equals(ENTER_KEY_TEXT)) {
                    tracker.updateProjectCount(-1);
                }
            }
            if (!keyList.isEmpty()) {
                keyList.remove(keyList.size() - 1);
            } else {
                tracker.updateProjectCount(-99);
            }
        } else {
            keyList.add(keyCode);
        }

        if (keyCode == NativeKeyEvent.VC_TAB || keyCode == NativeKeyEvent.VC_ENTER || keyCode == NativeKeyEvent.VC_SPACE) {
            if (!previousKeyText.equals(ENTER_KEY_TEXT) && !previousKeyText.equals(TAB_KEY_TEXT) && !previousKeyText.equals(SPACE_KEY_TEXT)) {
                tracker.updateProjectCount(1);
            }
        }

        previousKeyStroke = keyCode;
    }

    public int countWordsAndLogKeys(String str) {
        int wordCount = 0;
        int positionOfLastCount = 0;
        List<Integer> keyStrokeList = new ArrayList<>();
        for (int i = 0; i <= str.length() - 1; i++) {
            keyStrokeList.add(KeyConverter.getKeyInt(String.valueOf(str.charAt(i))));
            if (str.charAt(i) == ' ' && str.charAt(i-1) != ' ') {
                wordCount++;
                positionOfLastCount = i;
            }
        }

        keyList.addAll(keyStrokeList);
        if (positionOfLastCount != str.length()) {
            wordCount++;
        }

        return wordCount;
    }

	public String getClipboardContents() {
	    String result = "";
	    Clipboard clipboard = tracker.getToolkit().getSystemClipboard();
	    Transferable contents = clipboard.getContents(null);
	    boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
	    if (hasTransferableText) {
	      try {
	        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
	      }
	      catch (Exception ex){
	        System.out.println(ex.getMessage());
	      }
	    }
	    return result;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        // Unneeded method
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        // Unneeded method
    }
}
